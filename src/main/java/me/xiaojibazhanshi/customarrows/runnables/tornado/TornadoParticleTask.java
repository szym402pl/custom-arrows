package me.xiaojibazhanshi.customarrows.runnables.tornado;

import me.xiaojibazhanshi.customarrows.CustomArrows;
import me.xiaojibazhanshi.customarrows.runnables.SmokeCloudTask;
import org.bukkit.*;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.function.Consumer;

import static me.xiaojibazhanshi.customarrows.util.arrows.BlackHole.generateOneHighRing;

public class TornadoParticleTask implements Consumer<BukkitTask> {

    private final Location location;
    private final int durationInSeconds;
    private final int chosenPeriod;
    private boolean createdClouds = false;

    private int counter = 1;

    public TornadoParticleTask(Location location, int durationInSeconds, int chosenPeriod) {
        this.durationInSeconds = durationInSeconds;
        this.chosenPeriod = chosenPeriod;
        this.location = location;
    }

    @Override
    public void accept(BukkitTask bukkitTask) {
        int ticksInSecond = 20;
        if (counter * chosenPeriod >= durationInSeconds * ticksInSecond) bukkitTask.cancel();
        int height = 50;
        double step = 0.75;
        double maxRadius = height * 0.25;

        generateTornado(location.getWorld(), height, step);

        if (!createdClouds) {
            Location currentLocation = location.clone().add(new Vector(0, height, 0));
            List<Location> smokeCloudLocations = generateOneHighRing(currentLocation, maxRadius, 0.2);

            for (Location smokeLocation : smokeCloudLocations) {
                createProgressiveSmokeCloud(smokeLocation);
            }

            createdClouds = true;
        }

        generateOutwardRings(location.getWorld(), height, step, 1);

        counter++;
    }

    private void generateOutwardRings(World world, int height, double step, int rings) {
        Location currentLocation = location.clone().add(0, height, 0);

        for (double i = 1; i < rings + 1; i++) {
            double radius = (height * 0.25) + (step * i * 1.1);
            double density = radius * 0.25;

            List<Location> ringLocations = generateOneHighRing(currentLocation, radius, density);

            for (Location particleLocation : ringLocations) {
                world.spawnParticle(Particle.DUST,
                        particleLocation,
                        1,
                        0.0, 0.0, 0.0,
                        0.0,
                        new Particle.DustOptions(Color.GRAY, 4.0F),
                        true);
            }
        }

    }

    private void generateTornado(World world, int height, double step) {

        for (double i = 0; i < height; i += step) { // iterate through tornado height
            double minRadius = 0.3;
            double radius = i * 0.25;

            radius = Math.max(radius, minRadius);

            double minDensity = 0.8;
            double densityMultiplier = 0.25;
            double density = radius * densityMultiplier;

            density = Math.max(density, minDensity);

            Location currentLocation = location.clone().add(new Vector(0, i, 0));
            List<Location> ringLocations = generateOneHighRing(currentLocation, radius, density);

            for (Location particleLocation : ringLocations) { // spawning particles themselves
                world.spawnParticle(Particle.DUST,
                        particleLocation,
                        1,
                        0.0, 0.0, 0.0,
                        0.0,
                        new Particle.DustOptions(Color.GRAY, 4.0F),
                        true);
            }
        }

    }

    private void createProgressiveSmokeCloud(Location location) {
        int firstSmokeAmount = 350;
        int secondSmokeAmount = 300;
        int thirdSmokeAmount = 250;
        int fourthSmokeAmount = 225;

        int period = 1;

        SmokeCloudTask firstIteration = new SmokeCloudTask(firstSmokeAmount, location, 2, 10);
        SmokeCloudTask secondIteration = new SmokeCloudTask(secondSmokeAmount, location, 3, 15);
        SmokeCloudTask thirdIteration = new SmokeCloudTask(thirdSmokeAmount, location, 4, 20);
        SmokeCloudTask fourthIteration = new SmokeCloudTask(fourthSmokeAmount, location, 4, 30);

        Bukkit.getScheduler().runTaskTimer(CustomArrows.getInstance(), firstIteration, 2, 1);
        Bukkit.getScheduler().runTaskTimer(CustomArrows.getInstance(), secondIteration, firstSmokeAmount / 8, period);
        Bukkit.getScheduler().runTaskTimer(CustomArrows.getInstance(), thirdIteration, firstSmokeAmount / 5, period);
        Bukkit.getScheduler().runTaskTimer(CustomArrows.getInstance(), fourthIteration, firstSmokeAmount / 3, period);
    }

}
