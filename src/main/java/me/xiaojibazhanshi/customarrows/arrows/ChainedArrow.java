package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.ArrayList;
import java.util.List;

import static me.xiaojibazhanshi.customarrows.util.arrows.Chained.chainTargets;

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
        final int radius = 8;
        final int yRadius = 4;

        List<LivingEntity> targetList = new ArrayList<>(hitEntity.getNearbyEntities(radius, yRadius, radius)
                .stream()
                .filter(entity -> entity instanceof LivingEntity)
                .filter(entity -> !entity.equals(shooter) && !entity.equals(hitEntity))
                .map(entity -> (LivingEntity) entity)
                .toList());

        chainTargets(targetList, hitEntity);
    }


}
