package me.xiaojibazhanshi.customarrows.util;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
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

    /* OTHER ARROW */

}
