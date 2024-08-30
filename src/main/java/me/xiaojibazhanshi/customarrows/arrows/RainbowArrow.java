package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.ArrayList;
import java.util.List;

import static me.xiaojibazhanshi.customarrows.util.arrows.Rainbow.makeARainbow;

public class RainbowArrow extends CustomArrow {

    private final List<Color> colorsOfRainbow = new ArrayList<>(List.of(
            Color.YELLOW,
            Color.ORANGE,
            Color.RED,
            Color.PURPLE,
            Color.BLUE
    ));

    public RainbowArrow() {
        super(ArrowFactory.changeTippedColor
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&aR&ba&ei&an&bb&eo&aw &bA&er&ar&bo&ew", "rainbow_arrow",
                                List.of("", "This arrow makes a", "little rainbow :)")),
                        Color.WHITE));
    }

    @Override
    public void onHitBlock(ProjectileHitEvent event, Player shooter) {
        Block hitBlock = event.getHitBlock();
        if (hitBlock == null) return;

        Arrow arrow = (Arrow) event.getEntity();
        Location hitLocation = arrow.getLocation();
        arrow.remove();

        makeARainbow(shooter, hitLocation, colorsOfRainbow);
    }

    @Override
    public void onHitEntity(EntityDamageByEntityEvent event, Player shooter) {
        Arrow arrow = (Arrow) event.getDamager();
        Location hitLocation = arrow.getLocation();
        arrow.remove();

        makeARainbow(shooter, hitLocation, colorsOfRainbow);
    }
}
