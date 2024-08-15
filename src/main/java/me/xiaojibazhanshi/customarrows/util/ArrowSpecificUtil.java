package me.xiaojibazhanshi.customarrows.util;

import me.xiaojibazhanshi.customarrows.CustomArrows;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

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
                new Vector(0, 0, -3.0),
                new Vector(0, 0, 3.0),
                new Vector(3.0, 0, 0),
                new Vector(-3.0, 0, 0)
        };

        Arrow[] arrows = new Arrow[4];
        Location targetLocation = target.getEyeLocation();

        for (int i = 0; i < directions.length; i++) {
            arrows[i] = spawnNoGravityArrow(targetLocation.add(directions[i]));
        }

        for (int i = 0; i < arrows.length; i++) {
            final int index = i;

            new BukkitRunnable() {
                @Override
                public void run() {
                    directArrowTowardsPlayer(arrows[index], target);
                }
            }.runTaskLater(CustomArrows.getInstance(), 15L * (i + 1));

            new BukkitRunnable() {
                @Override
                public void run() {
                    arrows[index].remove();
                }
            }.runTaskLater(CustomArrows.getInstance(), 30L * (i + 1));
        }
    }

    private static void directArrowTowardsPlayer(Arrow arrow, LivingEntity target) {
        Location targetLocation = target.getLocation();
        Vector directionToPlayer = targetLocation.toVector().subtract(arrow.getLocation().toVector()).normalize();

        arrow.setVelocity(directionToPlayer.multiply(2));
    }

    private static Arrow spawnNoGravityArrow(Location location) {
        assert location.getWorld() != null;
        Arrow arrow = location.getWorld().spawn(location, Arrow.class);

        arrow.setGravity(false);
        arrow.setCritical(false);

        return arrow;
    }

}
