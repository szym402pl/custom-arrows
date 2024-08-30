package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.CustomArrows;
import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.runnables.RainbowCloudTask;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

import static me.xiaojibazhanshi.customarrows.util.arrows.Rainbow.filterLocationsToAbove;
import static me.xiaojibazhanshi.customarrows.util.arrows.Rainbow.generateVerticalRing;

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

        World world = hitLocation.getWorld();
        assert world != null;

        int particles = 128;
        double radius = 3.5;
        int period = 1;
        int smokeAmount = 95;
        int smokeIterations = 30;
        int offset = 1;

        double radiusStep = 0.2;
        double middleRadiusOffset = radius + (double) colorsOfRainbow.size() / 2 * radiusStep;
        Location hitLocationClone = hitLocation.clone().add(0, -0.25, 0);

        List<Location> middleRingLocations = generateVerticalRing(hitLocationClone, middleRadiusOffset, particles, shooter);
        List<Location> finalRingLocations = filterLocationsToAbove(middleRingLocations, hitLocationClone);

        Location firstCloudLocation = finalRingLocations.getFirst();
        Location secondCloudLocation = finalRingLocations.getLast();

        RainbowCloudTask firstCloud = new RainbowCloudTask(smokeAmount, firstCloudLocation, offset, smokeIterations);
        RainbowCloudTask secondCloud = new RainbowCloudTask(smokeAmount, secondCloudLocation, offset, smokeIterations);

        Bukkit.getScheduler().runTaskTimer(CustomArrows.getInstance(), firstCloud, 2, period);
        Bukkit.getScheduler().runTaskTimer(CustomArrows.getInstance(), secondCloud, 2, period);

        new BukkitRunnable() {
            int ticksElapsed = 0;
            final int maxTicks = 100;

            @Override
            public void run() {
                double newRadius = radius;

                for (Color color : colorsOfRainbow) {
                    newRadius += radiusStep;

                    List<Location> currentRingLocations = generateVerticalRing(hitLocation, newRadius, particles, shooter);
                    currentRingLocations = filterLocationsToAbove(currentRingLocations, hitLocation);

                    for (Location location : currentRingLocations) {

                        world.spawnParticle(Particle.DUST,
                                location,
                                1,
                                0.0, 0.0, 0.0,
                                0.0,
                                new Particle.DustOptions(color, 1.0F),
                                true);

                    }
                }

                ticksElapsed += 2;

                if (ticksElapsed >= maxTicks) {
                    cancel();
                }
            }
        }.runTaskTimer(CustomArrows.getInstance(), 1, 2);

    }
}
