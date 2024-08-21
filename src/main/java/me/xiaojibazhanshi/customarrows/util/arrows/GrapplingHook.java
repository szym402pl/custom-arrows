package me.xiaojibazhanshi.customarrows.util.arrows;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

public class GrapplingHook {

    public static void applyGrapplingHookVelocity(LivingEntity target, Location toWhere, double speed) {
        Location playerLocation = target.getLocation();
        Vector direction = toWhere.toVector().subtract(playerLocation.toVector());

        direction.normalize();
        direction.multiply(speed);

        double targetY = direction.getY() + direction.getY() / 2;
        direction.setY(Math.max(targetY, 0.5));

        target.setVelocity(direction);
    }

}
