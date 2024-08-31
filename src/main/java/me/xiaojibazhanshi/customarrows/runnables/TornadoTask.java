package me.xiaojibazhanshi.customarrows.runnables;

import me.xiaojibazhanshi.customarrows.CustomArrows;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.function.Consumer;

import static me.xiaojibazhanshi.customarrows.util.arrows.BlackHole.generateOneHighRing;

public class TornadoTask implements Consumer<BukkitTask> {

    private final Location currentLocation;
    private final int durationInSeconds;
    private final int chosenPeriod;
    private final World world;

    private int counter = 1;

    public TornadoTask(Location currentLocation, int durationInSeconds, int chosenPeriod) {
        this.durationInSeconds = durationInSeconds;
        this.chosenPeriod = chosenPeriod;
        this.currentLocation = currentLocation;
        this.world = currentLocation.getWorld();
    }

    @Override
    public void accept(BukkitTask bukkitTask) {
        int ticksInSecond = 20;
        if (counter * chosenPeriod >= durationInSeconds * ticksInSecond) bukkitTask.cancel();

        int height = 40;
        double step = 0.75;
        double maxRadius = height * 0.25;

        double counterCopy = counter % 100 == 0 ? -1 * counter : counter;
        double swirlSpeed = 0.02;
        double swirlAmplitude = 0.4;
        double swirlOffsetX = Math.sin(counterCopy * swirlSpeed) * swirlAmplitude;
        double swirlOffsetZ = Math.cos(counterCopy * swirlSpeed) * swirlAmplitude;

        Vector offsetVector = new Vector(swirlOffsetX, 0, swirlOffsetZ);
        currentLocation.add(offsetVector);
        currentLocation.setY(world.getHighestBlockYAt(currentLocation));

        generateTornado(currentLocation, world, height, step);
        createInnerVortex(currentLocation, world, maxRadius, height);
        createOuterVortex(currentLocation, world, maxRadius * 3, height / 2);

        if (counter % 5 == 0) createTopSmokeClouds(currentLocation.add(offsetVector), height, maxRadius);

        counter++;
    }

    private void createTopSmokeClouds(Location location, int height, double radius) {
        Location topOfTheTornado = location.clone().add(new Vector(0, height, 0));
        List<Location> smokeCloudLocations = generateOneHighRing(topOfTheTornado, radius, 0.2);

        for (Location smokeLocation : smokeCloudLocations) {
            createProgressiveSmokeCloud(smokeLocation);
        }
    }

    private void createInnerVortex(Location location, World world, double radius, int height) {
        List<Entity> nearbyEntities = world.getNearbyEntities(location, radius, height, radius).stream()
                .filter(entity -> entity instanceof LivingEntity)
                .toList();

        for (Entity entity : nearbyEntities) {
            entity.setVelocity(entity.getVelocity().clone().add(new Vector(0, 0.25, 0)));
        }
    }

    private void createOuterVortex(Location location, World world, double radius, int height) {
        List<Entity> nearbyEntities = world.getNearbyEntities(location, radius, height, radius).stream()
                .filter(entity -> entity instanceof LivingEntity)
                .toList();

        for (Entity entity : nearbyEntities) {
            Location entityLocation = entity.getLocation();
            Location tornadoAtEntityY = location.clone();
            tornadoAtEntityY.setY(entityLocation.getY());

            Vector directionToTornado = tornadoAtEntityY.toVector().subtract(entityLocation.toVector()).normalize();
            double distanceToTornado = entityLocation.distance(tornadoAtEntityY);

            if (distanceToTornado < 4) return;

            double velocityMultiplier = 1 - (distanceToTornado / radius);
            velocityMultiplier = velocityMultiplier < 0.05 ? 0.15 : velocityMultiplier;

            Vector pullVelocity = directionToTornado.multiply(velocityMultiplier * 1.2);
            entity.setVelocity(entity.getVelocity().add(pullVelocity));
        }
    }

    private void generateTornado(Location location, World world, int height, double step) {

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
        int thirdSmokeAmount = 50;
        int fourthSmokeAmount = 37;

        int period = 1;

        SmokeCloudTask thirdIteration = new SmokeCloudTask(thirdSmokeAmount, location, 4, 15, Color.GRAY);
        SmokeCloudTask fourthIteration = new SmokeCloudTask(fourthSmokeAmount, location, 4, 20, Color.GRAY);

        Bukkit.getScheduler().runTaskTimer(CustomArrows.getInstance(), thirdIteration, 2, period);
        Bukkit.getScheduler().runTaskTimer(CustomArrows.getInstance(), fourthIteration, fourthSmokeAmount / 3, period);
    }

}
