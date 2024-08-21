package me.xiaojibazhanshi.customarrows.util.arrows;

import me.xiaojibazhanshi.customarrows.util.ArrowSpecificUtil;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

public class AimAssist {

    public static void provideAimAssist(Entity projectile, LivingEntity target) {
        Vector initialSpeed = projectile.getVelocity();

        Vector directionToTarget = ArrowSpecificUtil.getDirectionFromEntityToTarget(projectile, target);
        Vector finalVelocity = directionToTarget.multiply(initialSpeed.length());

        projectile.setVelocity(finalVelocity.multiply(1.15).multiply(new Vector(1, 1.1, 1)));
    }

}
