package me.xiaojibazhanshi.customarrows.util.arrows;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import static me.xiaojibazhanshi.customarrows.util.arrows.Homing.getDirectionFromEntityToTarget;

public class AimAssist {

    private AimAssist() {}

    public static void provideAimAssist(Entity projectile, LivingEntity target) {
        Vector initialSpeed = projectile.getVelocity();

        Vector directionToTarget = getDirectionFromEntityToTarget(projectile, target);
        Vector finalVelocity = directionToTarget.multiply(initialSpeed.length());

        double yAdjustment = 1.1;
        double velocityMultiplier = 1.15;

        projectile.setVelocity(finalVelocity.multiply(velocityMultiplier).multiply(new Vector(1, yAdjustment, 1)));
    }

}
