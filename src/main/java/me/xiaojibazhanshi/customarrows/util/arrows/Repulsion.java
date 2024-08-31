package me.xiaojibazhanshi.customarrows.util.arrows;

import me.xiaojibazhanshi.customarrows.CustomArrows;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Firework;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.util.Vector;

public class Repulsion {

    private Repulsion() {
    }

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
                            ? repulsionForce.multiply(onGroundMultiplier)
                            : repulsionForce.multiply(inAirMultiplier)), delay);
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

}
