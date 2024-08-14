package me.xiaojibazhanshi.customarrows.util;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

public class ArrowSpecificUtil {

    /* HOMING ARROW */

    public static LivingEntity findEntityInSight(Player player, int maxDistance) {
        Location eyeLocation = player.getEyeLocation();
        Vector direction = eyeLocation.getDirection();

        RayTraceResult target = player.getWorld().rayTraceEntities
                (eyeLocation, direction, maxDistance, entity -> entity instanceof LivingEntity && entity != player);

        if (target != null) {
            return (LivingEntity) target.getHitEntity();
        }

        return null;
    }

    public static boolean isTargetWithinDegrees(Entity homingEntity, LivingEntity target, int maxDegrees) {
        Vector homingDirection = homingEntity.getLocation().getDirection();
        Vector toTarget = target.getLocation().toVector().subtract(homingEntity.getLocation().toVector());

        double angle = homingDirection.angle(toTarget);
        double angleDegrees = Math.toDegrees(angle);

        return angleDegrees <= maxDegrees;
    }

    

    /* OTHER ARROW */

}
