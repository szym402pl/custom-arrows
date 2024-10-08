package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.List;

import static me.xiaojibazhanshi.customarrows.util.arrows.Thunder.createThunderStrike;

public class ThunderArrow extends CustomArrow {

    public ThunderArrow() {
        super(ArrowFactory.changeTippedColor // Or you can use #changeTippedEffect if you need the effect
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&bThunder Arrow", "thunder_arrow",
                                List.of("", "This arrow will strike", "thunder upon your enemies")),
                        Color.AQUA));
    }

    @Override
    public void onHitBlock(ProjectileHitEvent event, Player shooter) {
        Location arrowLocation = event.getEntity().getLocation();

        createThunderStrike(arrowLocation, 6, 5, 12);
    }

    @Override
    public void onHitEntity(EntityDamageByEntityEvent event, Player shooter) {
        Location entityLocation = event.getEntity().getLocation();

        createThunderStrike(entityLocation, 3, 1, 8);
    }
}
