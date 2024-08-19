package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import me.xiaojibazhanshi.customarrows.util.ArrowSpecificUtil;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.List;

public class SplitArrow extends CustomArrow {

    public SplitArrow() {
        super(ArrowFactory.changeTippedColor // Or you can use #changeTippedEffect if you need the effect
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&7Split Arrow", "split_arrow",
                                List.of("", "This arrow will split into 4 and", "hit the target time and time again")),
                        Color.GRAY));
    }

    @Override
    public void onHitEntity(EntityDamageByEntityEvent event, Player shooter) {
        event.getDamager().remove();

        if (event.getEntity() instanceof LivingEntity)
            ArrowSpecificUtil.initiateFourWayArrowsOn((LivingEntity) event.getEntity());
    }
}
