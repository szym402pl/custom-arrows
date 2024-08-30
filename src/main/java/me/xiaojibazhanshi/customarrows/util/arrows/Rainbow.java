package me.xiaojibazhanshi.customarrows.util.arrows;

import me.xiaojibazhanshi.customarrows.CustomArrows;
import me.xiaojibazhanshi.customarrows.runnables.RainbowCloudTask;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class Rainbow {

    private Rainbow() {

    }

    public static void makeARainbow(Player shooter, Location hitLocation, List<Color> colorsOfRainbow) {
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
                if (ticksElapsed >= maxTicks) cancel();
            }
        }.runTaskTimer(CustomArrows.getInstance(), 1, 2);
    }

    public static List<Location> filterLocationsToAbove(List<Location> originalLocations, Location center) {
        return originalLocations
                .stream()
                .filter(location -> location.getY() > center.getY())
                .toList();
    }

    public static List<Location> generateVerticalRing(Location center, double radius, int segments, Player player) {
        List<Location> locations = new ArrayList<>();
        World world = center.getWorld();

        if (world == null) {
            throw new IllegalArgumentException("Center location's world cannot be null");
        }

        double angleStep = 2 * Math.PI / segments;

        float yaw = player.getLocation().getYaw();
        boolean isFacingNorthSouth = (yaw >= -45 && yaw < 45) || (yaw >= 135 || yaw < -135);

        for (int i = 0; i < segments; i++) {
            double angle = i * angleStep;
            double x, z;

            if (isFacingNorthSouth) {
                x = center.getX() + radius * Math.cos(angle);
                z = center.getZ();
            } else {
                x = center.getX();
                z = center.getZ() + radius * Math.cos(angle);
            }

            double y = center.getY() + radius * Math.sin(angle);

            Location location = new Location(world, x, y, z);
            locations.add(location);
        }

        return locations;
    }


}
