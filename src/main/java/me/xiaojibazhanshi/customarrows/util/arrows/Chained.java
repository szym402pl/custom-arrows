package me.xiaojibazhanshi.customarrows.util.arrows;

import me.xiaojibazhanshi.customarrows.util.ArrowSpecificUtil;
import me.xiaojibazhanshi.customarrows.util.Util;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import java.util.List;

public class Chained {

    public static void chainTargets(List<LivingEntity> targetList, LivingEntity hitEntity) {
        for (LivingEntity target : targetList) {
            if (!hitEntity.hasLineOfSight(target)) continue;

            Vector hitEntityToTarget = ArrowSpecificUtil.getDirectionFromEntityToTarget(hitEntity, target);
            Vector clampedDirection = hitEntityToTarget.multiply(0.3);
            double yCopy = hitEntityToTarget.getY();

            Arrow newArrow = hitEntity.getWorld().spawn(hitEntity.getEyeLocation().add(clampedDirection), Arrow.class);

            newArrow.setPickupStatus(AbstractArrow.PickupStatus.DISALLOWED);
            newArrow.setVelocity(hitEntityToTarget.multiply(5.0).setY(yCopy));

            Util.removeArrowAfter(newArrow, 30L);
        }
    }

}
