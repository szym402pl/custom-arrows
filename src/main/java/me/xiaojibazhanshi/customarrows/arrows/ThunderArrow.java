package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import me.xiaojibazhanshi.customarrows.util.ArrowSpecificUtil;
import me.xiaojibazhanshi.customarrows.util.GeneralUtil;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.List;

public class ThunderArrow extends CustomArrow {

    public ThunderArrow() {
        super(ArrowFactory.changeTippedColor // Or you can use #changeTippedEffect if you need the effect
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&bThunder Arrow", "thunder_arrow",
                                List.of("", "This arrow will strike", "thunder upon your enemies")),
                        Color.AQUA));
    }

    @Override
    public void onHitGround(ProjectileHitEvent event, Player shooter) {
        Location arrowLocation = event.getEntity().getLocation();

        ArrowSpecificUtil.createThunderStrike(arrowLocation, 6, 5, 12);
    }

    @Override
    public void onHitEntity(EntityDamageByEntityEvent event, Player shooter) {
        Location entityLocation = event.getEntity().getLocation();

        ArrowSpecificUtil.createThunderStrike(entityLocation, 3, 1, 8);
    }
}
