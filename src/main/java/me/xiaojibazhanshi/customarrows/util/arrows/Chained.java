package me.xiaojibazhanshi.customarrows.util.arrows;

import me.xiaojibazhanshi.customarrows.util.GeneralUtil;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import java.util.List;

import static me.xiaojibazhanshi.customarrows.util.arrows.Homing.getDirectionFromEntityToTarget;

public class Chained {

    private Chained() {}

    public static void chainTargets(List<LivingEntity> targetList, LivingEntity hitEntity) {
        long delay = 30L;
        double velocityMultiplier = 5.0;
        double directionClamp = 0.3;


        for (LivingEntity target : targetList) {
            if (!hitEntity.hasLineOfSight(target)) continue;

            Vector hitEntityToTarget = getDirectionFromEntityToTarget(hitEntity, target);
            Vector clampedDirection = hitEntityToTarget.multiply(directionClamp);
            double yCopy = hitEntityToTarget.getY();

            Arrow newArrow = hitEntity.getWorld().spawn(hitEntity.getEyeLocation().add(clampedDirection), Arrow.class);

            newArrow.setPickupStatus(AbstractArrow.PickupStatus.DISALLOWED);
            newArrow.setVelocity(hitEntityToTarget.multiply(velocityMultiplier).setY(yCopy));

            GeneralUtil.removeArrowAfter(newArrow, delay);
        }
    }

}
