package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import me.xiaojibazhanshi.customarrows.util.ArrowSpecificUtil;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.List;

public class RepulsionArrow extends CustomArrow {

    public RepulsionArrow() {
        super(ArrowFactory.changeTippedColor // Or you can use #changeTippedEffect if you need the effect
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&bRepulsion Arrow", "repulsion_arrow",
                                List.of("", "This arrow repels all nearby", "players from it's landing spot")),
                        Color.AQUA));
    }

    @Override
    public void onHitGround(ProjectileHitEvent event, Player shooter) {
        Location arrowLocation = event.getEntity().getLocation();

        ArrowSpecificUtil.detonateRepulsionFirework(arrowLocation);
        ArrowSpecificUtil.repelEntitiesNearby(arrowLocation);
    }

    @Override
    public void onHitEntity(EntityDamageByEntityEvent event, Player shooter) {
        Location arrowLocation = event.getDamager().getLocation();

        ArrowSpecificUtil.detonateRepulsionFirework(arrowLocation);
        ArrowSpecificUtil.repelEntitiesNearby(arrowLocation);
    }
}
