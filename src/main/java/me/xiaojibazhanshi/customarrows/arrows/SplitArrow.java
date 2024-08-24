package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.List;

import static me.xiaojibazhanshi.customarrows.util.arrows.Split.initiateFourWayArrowsOn;

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

        if (event.getEntity() instanceof LivingEntity)
            initiateFourWayArrowsOn((LivingEntity) event.getEntity());
    }
}
