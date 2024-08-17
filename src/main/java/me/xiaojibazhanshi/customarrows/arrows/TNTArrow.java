package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.List;

public class TNTArrow extends CustomArrow {

    public TNTArrow() {
        super(ArrowFactory.changeTippedColor // Or you can use #changeTippedEffect if you need the effect
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&cTNT Arrow", "tnt_arrow",
                                List.of("", "This arrow will spawn a primed", "tnt block wherever it lands")),
                Color.ORANGE));
    }

    @Override
    public void onHitBlock(ProjectileHitEvent event, Player shooter) {
        Location arrowLocation = event.getEntity().getLocation();

        shooter.getWorld().spawn(arrowLocation, TNTPrimed.class);
        event.getEntity().remove();
    }

    @Override
    public void onHitEntity(EntityDamageByEntityEvent event, Player shooter) {
        Location arrowLocation = event.getEntity().getLocation();

        shooter.getWorld().spawn(arrowLocation, TNTPrimed.class);
        event.getDamager().remove();
    }
}
