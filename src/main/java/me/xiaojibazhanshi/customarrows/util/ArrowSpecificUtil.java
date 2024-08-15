package me.xiaojibazhanshi.customarrows.util;

import me.xiaojibazhanshi.customarrows.CustomArrows;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.Random;

public class ArrowSpecificUtil {


    /* HOMING ARROW */


    public static LivingEntity findEntityInSight(Player player, int maxDistance) {
        Location eyeLocation = player.getEyeLocation();
        Vector direction = eyeLocation.getDirection();

        RayTraceResult target = player.getWorld().rayTraceEntities
                        (eyeLocation, direction, maxDistance,
                        entity -> (entity instanceof LivingEntity && entity != player));

        if (target != null) {
            return (LivingEntity) target.getHitEntity();
        }

        return null;
    }

    public static boolean isTargetWithinDegrees(Entity entity, LivingEntity target, int maxDegrees) {
        Vector homingDirection = entity.getVelocity().normalize();
        Vector toTarget = target.getLocation().toVector().subtract(entity.getLocation().toVector());

        double angle = homingDirection.angle(toTarget);
        double angleDegrees = Math.toDegrees(angle);

        return angleDegrees <= maxDegrees;
    }

    public static Vector getDirectionFromEntityToTarget(Entity entity, LivingEntity target) {
        Vector entityLocation = entity.getLocation().toVector();
        Vector targetLocation = target.getEyeLocation().toVector();

        if (target.getType() == EntityType.ENDER_DRAGON)
            targetLocation.add(new Vector(0, -2, 0));

        Vector direction = targetLocation.subtract(entityLocation);

        return direction.normalize();
    }

    public static boolean isDistanceGreaterThan(Entity entity1, Entity entity2, double distance) {
        Vector location1 = entity1.getLocation().toVector();
        Vector location2 = entity2.getLocation().toVector();

        double squaredDistance = location1.distanceSquared(location2);

        return squaredDistance > distance * distance;
    }


    /* SPLIT ARROW */


    public static void initiateFourWayArrowsOn(LivingEntity target) {
        Vector[] directions = {
                new Vector(0, 0, -4.5),
                new Vector(0, 0, 4.5),
                new Vector(4.5, 0, 0),
                new Vector(-4.5, 0, 0)
        };

        Arrow[] arrows = new Arrow[4];
        Location targetLocation = target.getEyeLocation().clone();

        for (int i = 0; i < directions.length; i++) {
            Location arrowLocation = targetLocation.clone().add(directions[i]);

            arrows[i] = spawnNoGravityArrow(arrowLocation);
            animateSplitArrow(arrows[i], arrowLocation, targetLocation);
        }

        for (int i = 0; i < arrows.length; i++) {
            final int index = i;

            new BukkitRunnable() {
                @Override
                public void run() {
                    directArrowTowardsEntity(arrows[index], target);
                }
            }.runTaskLater(CustomArrows.getInstance(), 15L * (i + 1));

            GeneralUtil.removeArrowAfter(arrows[index], 30L * (i + 1));
        }
    }

    private static void directArrowTowardsEntity(Arrow arrow, LivingEntity target) {
        if (target == null || target.isDead()) {
            arrow.remove();
            return;
        }

        Location targetLocation = target.getEyeLocation();
        Vector directionToPlayer = targetLocation.toVector().subtract(arrow.getLocation().toVector()).normalize();

        arrow.setVelocity(directionToPlayer.multiply(2));
    }

    private static Arrow spawnNoGravityArrow(Location arrowLocation) {
        assert arrowLocation.getWorld() != null;
        Arrow arrow = arrowLocation.getWorld().spawn(arrowLocation, Arrow.class);

        arrow.setGravity(false);
        arrow.setCritical(false);
        arrow.setGlowing(true);

        return arrow;
    }

    private static void animateSplitArrow(Arrow arrow, Location arrowLocation, Location targetLocation) {
        Vector directionToTarget = targetLocation.toVector().subtract(arrowLocation.toVector()).normalize();

        arrow.setVelocity(new Vector(0, 0.3, 0));

        Bukkit.getScheduler().runTaskLater(CustomArrows.getInstance(), () -> {
            arrow.setVelocity(directionToTarget.multiply(0.05));
        }, 10);
    }


    /* Repulsion Arrow */


    public static void repelEntitiesNearby(Location center) {
        World world = center.getWorld();
        if (world == null) return;

        center.setY(center.getY() - 0.1); // so it's not on ground level

        for (Entity entity : world.getNearbyEntities(center, 3, 3, 3)) {
            // some complex calculations that took me fucking ages
            if (entity.isDead() || !(entity instanceof LivingEntity livingEntity)) continue;

            Vector toEntity = livingEntity.getLocation().toVector().subtract(center.toVector());
            double distance = toEntity.length();

            double forceMagnitude = 1 / (distance + 0.05);
            Vector repulsionForce = toEntity.normalize().multiply(forceMagnitude);

            clampVector(repulsionForce, 0.8, 0.2);

            Bukkit.getScheduler().runTaskLater(CustomArrows.getInstance(), () -> {
                livingEntity.setVelocity(livingEntity.isOnGround()
                                ? repulsionForce.multiply(2.3) : repulsionForce.multiply(1.4));
            }, 4); // simulate actual repulsion
        }
    }

    private static void clampVector(Vector vector, double maxMagnitude, double minYSpeed) {
        // some other complex calculations that took me fucking ages
        double x = vector.getX();
        double y = vector.getY();
        double z = vector.getZ();

        double magnitudeXZ = Math.sqrt(x * x + z * z);

        if (magnitudeXZ > maxMagnitude) {
            double scale = maxMagnitude / magnitudeXZ;
            x *= scale;
            z *= scale;
        }

        y = Math.max(minYSpeed, Math.min(maxMagnitude, y));

        if (x == 0 && z == 0) {
            x = 0.2;
            z = 0.2;
        }

        vector.setX(x);
        vector.setY(y);
        vector.setZ(z);
    }

    public static void detonateRepulsionFirework(Location location) {
        World world = location.getWorld();
        if (world == null) return;

        Firework firework = world.spawn(location, Firework.class);
        FireworkMeta meta = firework.getFireworkMeta();

        FireworkEffect effect = FireworkEffect.builder()
                .withColor(Color.AQUA)
                .with(FireworkEffect.Type.BALL_LARGE)
                .build();

        meta.addEffect(effect);
        meta.setPower(0);

        firework.setFireworkMeta(meta);
        firework.detonate();
    }


    /* Molotov Arrow */


    public static void setFiresAround(Block centerBlock, int radius) {
        World world = centerBlock.getWorld();

        int centerX = centerBlock.getX();
        int centerZ = centerBlock.getZ();

        for (int x = centerX - radius; x <= centerX + radius; x++) {

            for (int z = centerZ - radius; z <= centerZ + radius; z++) {

                Block highestBlock = world.getHighestBlockAt(x, z);
                // This makes sure it won't just fire up something 50 blocks above/below
                if (highestBlock.getY() > centerBlock.getY() + 3) continue;
                if (highestBlock.getY() < centerBlock.getY() - 3) continue;

                Block blockAbove = highestBlock.getRelative(0, 1, 0);

                int randomNumber = new Random().nextInt(3) + 1;

                if (blockAbove.getType() != Material.WATER && blockAbove.getType() != Material.LAVA
                        && randomNumber != 3) {
                    blockAbove.setType(Material.FIRE);
                }
            }

        }
    }


    /* Other Arrow */


    //
}
