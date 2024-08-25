package me.xiaojibazhanshi.customarrows.runnables.tornado;

import me.xiaojibazhanshi.customarrows.CustomArrows;
import me.xiaojibazhanshi.customarrows.runnables.SmokeCloudTask;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.function.Consumer;

import static me.xiaojibazhanshi.customarrows.util.arrows.BlackHole.generateOneHighRing;

public class TornadoTask implements Consumer<BukkitTask> {

    private final Location location;
    private final int durationInSeconds;
    private final int chosenPeriod;
    private final World world;

    private int counter = 1;

    public TornadoTask(Location location, int durationInSeconds, int chosenPeriod) {
        this.durationInSeconds = durationInSeconds;
        this.chosenPeriod = chosenPeriod;
        this.location = location;
        this.world = location.getWorld();
    }

    @Override
    public void accept(BukkitTask bukkitTask) {
        int ticksInSecond = 20;
        if (counter * chosenPeriod >= durationInSeconds * ticksInSecond) bukkitTask.cancel();

        int height = 40;
        double step = 0.75;
        double maxRadius = height * 0.25;

        generateTornado(world, height, step);

        createInnerVortex(world, maxRadius, height);
        createOuterVortex(world, maxRadius * 2.5, height / 2);

        if (counter % 10 == 0) {
            Location topOfTheTornado = location.clone().add(new Vector(0, height, 0));
            List<Location> smokeCloudLocations = generateOneHighRing(topOfTheTornado, maxRadius, 0.2);

            for (Location smokeLocation : smokeCloudLocations) {
                createProgressiveSmokeCloud(smokeLocation);
            }
        }

        counter++;
    }

    private void createInnerVortex(World world, double radius, int height) {
        List<Entity> nearbyEntities = world.getNearbyEntities(location, radius, height, radius).stream()
                .filter(entity -> entity instanceof LivingEntity)
                .toList();

        for (Entity entity : nearbyEntities) {
            entity.setVelocity(entity.getVelocity().clone().add(new Vector(0, 0.5, 0)));
        }
    }

    private void createOuterVortex(World world, double radius, int height) {
        List<Entity> nearbyEntities = world.getNearbyEntities(location, radius, height, radius).stream()
                .filter(entity -> entity instanceof LivingEntity)
                .toList();

        for (Entity entity : nearbyEntities) {
            Location entityLocation = entity.getLocation();
            Vector directionToTornado = location.toVector().subtract(entityLocation.toVector()).normalize();
            double distanceToTornado = entityLocation.distance(location);

            double velocityMultiplier = 1 - (distanceToTornado / radius);
            velocityMultiplier = velocityMultiplier < 0 ? 0.1 : velocityMultiplier;

            Vector pullVelocity = directionToTornado.multiply(velocityMultiplier * 1.5);
            entity.setVelocity(entity.getVelocity().add(pullVelocity));
        }
    }

    private void generateTornado(World world, int height, double step) {

        double baseFrequency = 0.2;
        double baseAmplitude = 2.5;

        double timeFactor = counter * 0.4;

        for (double i = 0; i < height; i += step) {
            double minRadius = 0.3;
            double radius = (i > (double) height / 3) ? i * 0.21 : i * 0.225;
            radius = (i > (double) height / 2) ? i * 0.20 : radius;
            radius = (i > (double) height / 1.5) ? i * 0.215 : radius;
            radius = Math.max(radius, minRadius);

            double minDensity = 0.8;
            double densityMultiplier = 0.25;
            double density = radius * densityMultiplier;
            density = Math.max(density, minDensity);

            double xOffset = Math.sin(i * baseFrequency) * baseAmplitude;
            double zOffset = Math.cos(i * baseFrequency) * baseAmplitude;

            xOffset += Math.sin(i * baseFrequency + timeFactor) * (baseAmplitude * 0.5);
            zOffset += Math.cos(i * baseFrequency + timeFactor) * (baseAmplitude * 0.5);

            Location currentLocation = location.clone().add(new Vector(xOffset, i, zOffset));
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


    private void createProgressiveSmokeCloud(Location location) {
        int thirdSmokeAmount = 100;
        int fourthSmokeAmount = 75;

        int period = 1;

        SmokeCloudTask thirdIteration = new SmokeCloudTask(thirdSmokeAmount, location, 5, 30);
        SmokeCloudTask fourthIteration = new SmokeCloudTask(fourthSmokeAmount, location, 5, 35);

        Bukkit.getScheduler().runTaskTimer(CustomArrows.getInstance(), thirdIteration, 2, period);
        Bukkit.getScheduler().runTaskTimer(CustomArrows.getInstance(), fourthIteration, fourthSmokeAmount / 3, period);
    }

}
