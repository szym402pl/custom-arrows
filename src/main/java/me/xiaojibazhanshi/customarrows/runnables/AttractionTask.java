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

public class AttractionTask implements Consumer<BukkitTask> {

    private final Location currentLocation;
    private final int durationInSeconds;
    private final int chosenPeriod;
    private final World world;

    private int counter = 1;

    public AttractionTask(Location attractionPoint, int durationInSeconds, int chosenPeriod) {
        this.durationInSeconds = durationInSeconds;
        this.chosenPeriod = chosenPeriod;
        this.currentLocation = attractionPoint;
        this.world = attractionPoint.getWorld();
    }

    @Override
    public void accept(BukkitTask bukkitTask) {
        int ticksInSecond = 20;
        if (counter * chosenPeriod >= durationInSeconds * ticksInSecond) bukkitTask.cancel();

        int radius = 8;

        suckEntitiesInto(currentLocation, world, radius);

        counter++;
    }

    private void suckEntitiesInto(Location location, World world, double radius) {
        List<Entity> nearbyEntities = world.getNearbyEntities(location, radius, radius, radius).stream()
                .filter(entity -> entity instanceof LivingEntity)
                .toList();

        for (Entity entity : nearbyEntities) {
            Location entityLocation = entity.getLocation();
            Location center = location.clone();

            Vector direction = center.toVector().subtract(entityLocation.toVector()).normalize();
            double distance = entityLocation.distance(center);

            double velocityMultiplier = 1 - (distance / radius);
            velocityMultiplier = velocityMultiplier < 0.05 ? 0.15 : velocityMultiplier;

            Vector pullVelocity = direction.multiply(velocityMultiplier * 1.2);
            entity.setVelocity(entity.getVelocity().add(pullVelocity));
        }
    }

}
