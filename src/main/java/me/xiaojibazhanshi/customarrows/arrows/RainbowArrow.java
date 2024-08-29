package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.List;

import static me.xiaojibazhanshi.customarrows.util.arrows.Rainbow.generateVerticalRing;

public class RainbowArrow extends CustomArrow {

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

        World world = hitLocation.getWorld();
        assert world != null;

        int pointDensity = 128;
        double radius = 3.0;

        List<Location> ringLocations = generateVerticalRing(hitLocation, radius, pointDensity, shooter);

        for (Location location : ringLocations) {
            world.spawnParticle(Particle.DUST,
                    location,
                    1,
                    0.0, 0.0, 0.0,
                    0.0,
                    new Particle.DustOptions(Color.YELLOW, 1.5F),
                    true);
        }

    }

    @Override
    public void onHitEntity(EntityDamageByEntityEvent event, Player shooter) {

    }
}
