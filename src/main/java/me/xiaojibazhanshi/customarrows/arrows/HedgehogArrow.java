package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.List;

public class HedgehogArrow extends CustomArrow {

    public HedgehogArrow() {
        super(ArrowFactory.changeTippedColor
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&dHedgehog Arrow", "hedgehog_arrow",
                                List.of("", "This arrow will turn the", "target into a hedgehog")),
                        Color.PURPLE));
    }

    @Override
    public void onHitEntity(EntityDamageByEntityEvent event, Player shooter) {
        if (!(event.getEntity() instanceof Player target)) return;

        target.setArrowsInBody(500);
    }
}
