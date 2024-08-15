package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.runnables.HomingArrowRunnable;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import me.xiaojibazhanshi.customarrows.util.ArrowSpecificUtil;
import me.xiaojibazhanshi.customarrows.util.GeneralUtil;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChainedArrow extends CustomArrow {

    public ChainedArrow() {
        super(ArrowFactory.changeTippedColor // Or you can use #changeTippedEffect if you need the effect
                (ArrowFactory.createArrowItemStack(
                                 Material.TIPPED_ARROW, "&8Chained Arrow", "chained_arrow",
                                 List.of("", "This arrow will 'chain' and hit", "all entities around the one you hit")),
                        Color.fromRGB(169, 169, 169))); // Dark gray
    }

    @Override
    public void onHitEntity(EntityDamageByEntityEvent event, Player shooter) {
        if (!(event.getEntity() instanceof LivingEntity hitEntity)) return;

        List<LivingEntity> targetList = new ArrayList<>(hitEntity.getNearbyEntities(8, 4, 8)
                .stream()
                .filter(entity -> entity instanceof LivingEntity)
                .map(entity -> (LivingEntity) entity)
                .toList());

        if (targetList.isEmpty()) return;

        // failsafe
        targetList.remove(shooter);
        targetList.remove(hitEntity);

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
}
