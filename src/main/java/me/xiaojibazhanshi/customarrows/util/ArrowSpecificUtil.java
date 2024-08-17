package me.xiaojibazhanshi.customarrows.util;

import me.xiaojibazhanshi.customarrows.CustomArrows;
import me.xiaojibazhanshi.customarrows.runnables.LightningStrikeTask;
import me.xiaojibazhanshi.customarrows.runnables.SmokeCloudTask;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class ArrowSpecificUtil {


    /* HOMING ARROW */


    public static LivingEntity findEntityInSight(Player player, int maxDistance, double rayTraceSize) {
        Location eyeLocation = player.getEyeLocation();
        Vector direction = eyeLocation.getDirection();

        RayTraceResult target = player.getWorld().rayTraceEntities
                (eyeLocation, direction, maxDistance, rayTraceSize,
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
        Vector entityLocation = entity instanceof LivingEntity livingEntity
                ? livingEntity.getEyeLocation().toVector() : entity.getLocation().toVector();

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
                    directFWArrowTowardsEntity(arrows[index], target);
                }
            }.runTaskLater(CustomArrows.getInstance(), 15L * (i + 1));

            GeneralUtil.removeArrowAfter(arrows[index], 30L * (i + 1));
        }
    }

    private static void directFWArrowTowardsEntity(Arrow arrow, LivingEntity target) {
        if (target == null || target.isDead()) {
            arrow.remove();
            return;
        }

        Location targetLocation = target.getEyeLocation();
        Vector directionToPlayer = targetLocation.toVector().subtract(arrow.getLocation().toVector()).normalize();

        arrow.setVelocity(directionToPlayer.multiply(2));
    }

    private static Arrow spawnNoGravityArrow(Location location) {
        assert location.getWorld() != null;
        Arrow arrow = location.getWorld().spawn(location, Arrow.class);

        arrow.setGravity(false);
        arrow.setCritical(false);
        arrow.setGlowing(true);

        return arrow;
    }

    private static void animateSplitArrow(Arrow arrow, Location startingLocation, Location targetLocation) {
        Vector directionToTarget = targetLocation.toVector().subtract(startingLocation.toVector()).normalize();

        arrow.setVelocity(new Vector(0, 0.3, 0));

        Bukkit.getScheduler().runTaskLater(CustomArrows.getInstance(), () ->
                arrow.setVelocity(directionToTarget.multiply(0.05)), 10);
    }


    /* Repulsion Arrow */


    public static void repelEntitiesNearby(Location center) {
        World world = center.getWorld();
        if (world == null) return;

        center.setY(center.getY() - 0.1); // so it's not on ground level
        int range = 3;

        for (Entity entity : world.getNearbyEntities(center, range, range, range)) {
            // some complex calculations that took me fucking ages
            if (entity.isDead() || !(entity instanceof LivingEntity livingEntity)) continue;

            Vector toEntity = livingEntity.getLocation().toVector().subtract(center.toVector());
            double distance = toEntity.length();

            double forceMagnitude = 1 / (distance + 0.05);
            Vector repulsionForce = toEntity.normalize().multiply(forceMagnitude);
            clampRepulsionForceVector(repulsionForce);

            double onGroundMultiplier = 2.3;
            double inAirMultiplier = 1.4;
            int delay = 4;

            // simulate actual repulsion
            Bukkit.getScheduler().runTaskLater(CustomArrows.getInstance(), () ->
                    livingEntity.setVelocity(livingEntity.isOnGround()
                    ? repulsionForce.multiply(onGroundMultiplier) : repulsionForce.multiply(inAirMultiplier)), delay);
        }
    }

    private static void clampRepulsionForceVector(Vector vector) {
        // some other complex calculations that took me fucking ages
        double maxMagnitude = 0.8;
        double minYSpeed = 0.8;

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

        double minXAndZ = 0.2;
        if (x == 0 && z == 0) {
            x = minXAndZ;
            z = minXAndZ;
        }

        vector.setX(x);
        vector.setY(y);
        vector.setZ(z);
    }

    public static void detonateFirework(Location location, FireworkEffect.Type type, Color color) {
        World world = location.getWorld();
        if (world == null) return;

        Firework firework = world.spawn(location, Firework.class);
        FireworkMeta meta = firework.getFireworkMeta();

        FireworkEffect effect = FireworkEffect.builder()
                .withColor(color)
                .with(type)
                .build();

        meta.addEffect(effect);
        meta.setPower(0);

        firework.setFireworkMeta(meta);
        firework.detonate();
    }


    /* Aim Assist Arrow */


    public static void provideAimAssist(Entity projectile, LivingEntity target) {
        Vector initialSpeed = projectile.getVelocity();

        Vector directionToTarget = ArrowSpecificUtil.getDirectionFromEntityToTarget(projectile, target);
        Vector finalVelocity = directionToTarget.multiply(initialSpeed.length());

        projectile.setVelocity(finalVelocity.multiply(1.15).multiply(new Vector(1, 1.1, 1)));
    }


    /* Chained Arrow */


    public static void chainTargets(List<LivingEntity> targetList, LivingEntity hitEntity) {
        for (LivingEntity target : targetList) {
            if (!hitEntity.hasLineOfSight(target)) continue;

            Vector hitEntityToTarget = ArrowSpecificUtil.getDirectionFromEntityToTarget(hitEntity, target);
            Vector clampedDirection = hitEntityToTarget.multiply(0.3);
            double yCopy = hitEntityToTarget.getY();

            Arrow newArrow = hitEntity.getWorld().spawn(hitEntity.getEyeLocation().add(clampedDirection), Arrow.class);

            newArrow.setPickupStatus(AbstractArrow.PickupStatus.DISALLOWED);
            newArrow.setVelocity(hitEntityToTarget.multiply(5.0).setY(yCopy));

            GeneralUtil.removeArrowAfter(newArrow, 30L);
        }
    }


    /* Molotov Arrow */


    public static void setFiresAround(Block centerBlock, int radius) {
        World world = centerBlock.getWorld();

        int centerX = centerBlock.getX();
        int centerZ = centerBlock.getZ();

        for (int x = centerX - radius; x <= centerX + radius; x++) {

            for (int z = centerZ - radius; z <= centerZ + radius; z++) {

                Block highestBlock = world.getHighestBlockAt(x, z);

                // this makes sure it won't just fire up something 50 blocks above/below
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


    /* Seeker Arrow */


    public static LivingEntity findFirstEntityBelow(Entity entity, int radius, int maxHeight) {
        // this way is just faster, idc if it's less performant
        return (LivingEntity) entity.getNearbyEntities(radius, maxHeight, radius)
                .stream()
                .filter(entityNearby -> entityNearby instanceof LivingEntity)
                .findFirst()
                .orElse(null);
    }


    /* Necromancer Arrow */


    public static void spawnOneOfSelected(List<EntityType> selectedEntities, Location location) {
        if (location.getWorld() == null) return;

        Random randomInstance = new Random();
        int random = randomInstance.nextInt(selectedEntities.size());

        location.getWorld().spawnEntity(location, selectedEntities.get(random));
    }

    public static void convertToUndead(Villager villager) {
        Location location = villager.getLocation();
        World world = villager.getWorld();

        villager.remove();

        ZombieVillager zombieVillager = (ZombieVillager) world.spawnEntity(location, EntityType.ZOMBIE_VILLAGER);

        zombieVillager.setVillagerProfession(villager.getProfession());
        if (villager.isAdult()) zombieVillager.setAdult();
    }


    /* Flash-bang Arrow */

    public static void detonateFlashBang(Entity itemDisplay, long delay) {
        Bukkit.getScheduler().runTaskLater(CustomArrows.getInstance(), () -> {
            itemDisplay.remove();
            ArrowSpecificUtil.detonateFirework(itemDisplay.getLocation(), FireworkEffect.Type.BALL, Color.WHITE);

            for (Entity onLooker : ArrowSpecificUtil.getEntitiesLookingAt(itemDisplay, 8)) {
                ArrowSpecificUtil.applyFlashBangEffect(onLooker);
            }
        }, delay);
    }


    public static void applyFlashBangEffect(Entity entity) {
        if (!(entity instanceof LivingEntity livingEntity)) return;

        Random randomInstance = new Random();
        int flashBangDuration = (randomInstance.nextInt(4, 9) + 1) * 20;

        ArrayList<PotionEffect> flashBangEffects = new ArrayList<>(List.of
                (new PotionEffect(PotionEffectType.NAUSEA, flashBangDuration, 2, true),
                        new PotionEffect(PotionEffectType.SLOWNESS, flashBangDuration, 2, true),
                        new PotionEffect(PotionEffectType.BLINDNESS, flashBangDuration, 2, true)));

        for (PotionEffect effect : flashBangEffects) {
            livingEntity.addPotionEffect(effect);
        }
    }

    public static List<Entity> getEntitiesLookingAt(Entity targetEntity, int maxRadius) {
        return targetEntity.getNearbyEntities(maxRadius, maxRadius, maxRadius)
                .stream()
                .filter(entity -> entity instanceof LivingEntity)
                .filter(livingEntity -> ((LivingEntity) livingEntity).hasLineOfSight(targetEntity))
                .filter(livingEntity -> isLookingAt((LivingEntity) livingEntity, targetEntity.getLocation()))
                .toList();
    }

    public static Entity spawnDisplayItem(Material material, Location location, @Nullable String displayName) {
        if (material == null || location == null) return null;

        World world = location.getWorld();
        if (world == null) return null;

        ItemStack item = new ItemStack(material);
        ItemDisplay itemDisplay = location.getWorld().spawn(location, ItemDisplay.class);

        itemDisplay.setItemStack(item);
        itemDisplay.setInvulnerable(true);

        if (displayName != null) {
            itemDisplay.setCustomName(GeneralUtil.color(displayName));
            itemDisplay.setCustomNameVisible(true);
        }

        return itemDisplay;
    }

    public static boolean isLookingAt(LivingEntity checkedEntity, Location targetLocation) {
        if (checkedEntity == null || targetLocation == null) return false;

        Location eyeLocation = checkedEntity.getEyeLocation();

        Vector directionToLocation = targetLocation.toVector().subtract(eyeLocation.toVector()).normalize();
        Vector entityDirection = eyeLocation.getDirection().normalize();

        double angle = entityDirection.angle(directionToLocation);
        double angleDegrees = Math.toDegrees(angle);
        double maxAngle = 85.0;

        return angleDegrees <= maxAngle;
    }


    /* Thunder Arrow */


    public static Location randomizeLocation(Location location, int maxOffset) {
        ThreadLocalRandom random = ThreadLocalRandom.current();

        return new Location(
                location.getWorld(),
                location.getX() + random.nextDouble(-maxOffset, maxOffset),
                location.getY() + random.nextDouble(-maxOffset, maxOffset),
                location.getZ() + random.nextDouble(-maxOffset, maxOffset)
        );
    }

    public static void createThunderStrike(Location location, int thunderAmount, int maxOffset, long strikePeriod) {
        LightningStrikeTask task = new LightningStrikeTask(thunderAmount, location, maxOffset);

        Bukkit.getScheduler().runTaskTimer(CustomArrows.getInstance(), task, 0, strikePeriod);
    }


    /* Honey Trap Arrow */


    public static void placeTemporaryBlocks(Map<Location, Material> blockLocations, int deleteAfter, Material material) {
        if (blockLocations.isEmpty()) return;

        for (Location location : blockLocations.keySet()) {
            location.getBlock().setType(material);
        }

        Bukkit.getScheduler().runTaskLater(CustomArrows.getInstance(), () -> {
            for (Location location : blockLocations.keySet()) {
                Material originalMaterial = blockLocations.get(location);

                location.getBlock().setType(originalMaterial);
            }
        }, deleteAfter * 20L);
    }

    public static Map<Location, Material> getAHollowSphereAround(Location center, int radius) {
        Map<Location, Material> sphere = new HashMap<>();
        World world = center.getWorld();

        double pi = Math.PI;
        double doublePi = 2 * pi;

        for (double theta = 0; theta <= pi; theta += pi / (radius * 3)) {
            double sinTheta = Math.sin(theta);
            double cosTheta = Math.cos(theta);

            for (double phi = 0; phi <= doublePi; phi += pi / (radius * 3)) {
                double sinPhi = Math.sin(phi);
                double cosPhi = Math.cos(phi);

                int x = (int) Math.round(center.getX() + radius * sinTheta * cosPhi);
                int y = (int) Math.round(center.getY() + radius * cosTheta);
                int z = (int) Math.round(center.getZ() + radius * sinTheta * sinPhi);

                Location loc = new Location(world, x, y, z);

                Material material = loc.getBlock().getType();

                if (material == Material.AIR) {
                    sphere.put(loc, material);
                }
            }
        }

        return sphere;
    }


    /* .50 cal Arrow */


    public static void applyEffectsIfShotRapidly(Player shooter) {
        PotionEffect existingNausea = shooter.getPotionEffect(PotionEffectType.NAUSEA);
        int amplifier = existingNausea != null ? existingNausea.getAmplifier() + 1 : 0;

        PotionEffect nausea = new PotionEffect(PotionEffectType.NAUSEA, 4 * 20, amplifier, true);
        shooter.addPotionEffect(nausea);

        if (amplifier < 3) return;
        PotionEffect blindness = new PotionEffect(PotionEffectType.BLINDNESS, 60, 1, true);
        double damage = amplifier * 1.25;

        shooter.addPotionEffect(blindness);
        shooter.damage(damage);
    }


    /* Ghost Arrow */

    public static void temporarilyConvertToDisplayItem(Block block) {
        BlockData originalBlockData = block.getBlockData();
        Location blockLocation = block.getLocation();
        assert blockLocation.getWorld() != null;

        block.setType(Material.AIR);

        BlockDisplay blockDisplay = blockLocation.getWorld().spawn(blockLocation, BlockDisplay.class, (display) -> {
            display.setBlock(originalBlockData);
            display.setInvulnerable(true);
            display.setGravity(false);
        });

        new BukkitRunnable() {
            @Override
            public void run() {
                blockDisplay.remove();
                block.setBlockData(originalBlockData);
            }
        }.runTaskLater(CustomArrows.getInstance(), 4);
    }

    public static void shootFakeArrow(EntityShootBowEvent event, Player shooter) {
        Arrow arrow = shooter.getWorld().spawnArrow(event.getProjectile().getLocation(),
                event.getProjectile().getVelocity(), 3.0f, 0.0f, Arrow.class);

        arrow.setVelocity(event.getProjectile().getVelocity().multiply(1.04));
        arrow.setVisualFire(true);
        arrow.setVisibleByDefault(false);
        arrow.setGravity(false);
        arrow.setPersistent(true);

        GeneralUtil.removeArrowAfter(arrow, 300);
    }

    public static boolean isFakeArrow(Entity entity) {
        return entity instanceof Arrow arrow
                && (!arrow.isVisibleByDefault())
                && arrow.isVisualFire();
    }


    /* Smoke Arrow */


    public static void createProgressiveSmokeCloud(Location location) {
        // I KNOW THE CODE'S UGLY BUT HEY IT LOOKS AWESOME IN-GAME SO SHUT UP
        int firstSmokeAmount = 400;
        int secondSmokeAmount = 350;
        int thirdSmokeAmount = 250;
        int fourthSmokeAmount = 225;
        int fifthSmokeAmount = 200;

        int period = 1;

        SmokeCloudTask firstIteration = new SmokeCloudTask(firstSmokeAmount, location, 2, 10);
        SmokeCloudTask secondIteration = new SmokeCloudTask(secondSmokeAmount, location, 3, 15);
        SmokeCloudTask thirdIteration = new SmokeCloudTask(thirdSmokeAmount, location, 4, 20);
        SmokeCloudTask fourthIteration = new SmokeCloudTask(fourthSmokeAmount, location, 4, 25);
        SmokeCloudTask fifthIteration = new SmokeCloudTask(fifthSmokeAmount, location, 4, 25);

        Bukkit.getScheduler().runTaskTimer(CustomArrows.getInstance(), firstIteration, 2, 1);
        Bukkit.getScheduler().runTaskTimer(CustomArrows.getInstance(), secondIteration,firstSmokeAmount/8,period);
        Bukkit.getScheduler().runTaskTimer(CustomArrows.getInstance(), thirdIteration,firstSmokeAmount/5, period);
        Bukkit.getScheduler().runTaskTimer(CustomArrows.getInstance(), fourthIteration,firstSmokeAmount/3,period);
        Bukkit.getScheduler().runTaskTimer(CustomArrows.getInstance(), fifthIteration,firstSmokeAmount/2, period);
    }


    /* Magnet Arrow */


    /**
     * @return true if there was at least one item in the vicinity
     */
    public static boolean teleportNearbyItemsTo(Entity target, int radius) {
        boolean itemsNearby = false;

        for (Entity item : target.getNearbyEntities(radius, radius, radius)) {
            if (!(item instanceof Item)) continue;

            item.teleport(target.getLocation().add(new Vector(0, 1.25, 0)));
            itemsNearby = true;
        }

        return itemsNearby;
    }
}
