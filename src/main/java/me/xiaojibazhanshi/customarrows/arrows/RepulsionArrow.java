package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import me.xiaojibazhanshi.customarrows.util.ArrowSpecificUtil;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
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
                                List.of("", "This arrow repels all nearby", "players from it's landing spot",
                                        "", "It additionally deals a bit of damage")),
                        Color.AQUA));
    }

    @Override
    public void onHitGround(ProjectileHitEvent event, Player shooter) {
        handleArrowHit(event.getEntity().getLocation());
    }

    @Override
    public void onHitEntity(EntityDamageByEntityEvent event, Player shooter) {
        handleArrowHit(event.getDamager().getLocation());
    }

    private void handleArrowHit(Location arrowLocation) {
        ArrowSpecificUtil.detonateFirework(arrowLocation, FireworkEffect.Type.BALL_LARGE, Color.AQUA);
        ArrowSpecificUtil.repelEntitiesNearby(arrowLocation);
    }
}
