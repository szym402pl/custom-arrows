package me.xiaojibazhanshi.customarrows.util.arrows;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import static me.xiaojibazhanshi.customarrows.util.arrows.Homing.getDirectionFromEntityToTarget;

public class AimAssist {

    private AimAssist() {
    }

    public static void provideAimAssist(Entity projectile, LivingEntity target) {
        Vector initialSpeed = projectile.getVelocity();

        Vector directionToTarget = getDirectionFromEntityToTarget(projectile, target);
        Vector finalVelocity = directionToTarget.multiply(initialSpeed.length());

        projectile.setVelocity(finalVelocity.multiply(1.15).multiply(new Vector(1, 1.1, 1)));
    }

}
