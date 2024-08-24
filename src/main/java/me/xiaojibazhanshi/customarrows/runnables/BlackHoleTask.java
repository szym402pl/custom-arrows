package me.xiaojibazhanshi.customarrows.runnables;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

import static me.xiaojibazhanshi.customarrows.util.arrows.BlackHole.*;

public class BlackHoleTask implements Consumer<BukkitTask> {

    private final Location location;
    private final int durationInSeconds;
    private final int chosenPeriod;
    private boolean areBlocksBroken;

    private int counter = 1;

    public BlackHoleTask(Location location, int durationInSeconds, int chosenPeriod) {
        this.durationInSeconds = durationInSeconds;
        this.chosenPeriod = chosenPeriod;
        this.location = location;
        areBlocksBroken = false;
    }

    @Override
    public void accept(BukkitTask bukkitTask) {
        int ticksInSecond = 20;
        if (counter * chosenPeriod >= durationInSeconds * ticksInSecond) bukkitTask.cancel();

        World world = location.getWorld();
        assert world != null;

        suckInNearbyEntities(location);
        generateBlackHole(world);

        if (!areBlocksBroken) {
            executeBlackHoleAnimation(location, 2.4);
            areBlocksBroken = true;
        }

        counter++;
    }

    private void generateBlackHole(World world) {
        Location adjustedLocation = location.clone().add(-1.1, 0, -1.1);

        List<Location> ringLocations = generateOneHighRing(adjustedLocation, 1.75, 9.0);
        List<Location> blackHoleLocations = generateSphere(adjustedLocation, 0.3, 4.0);

        boolean isOtherColor = ThreadLocalRandom.current().nextInt(10) == 1;

        for (Location particleBLocation : ringLocations) {
            world.spawnParticle(Particle.DUST,
                    particleBLocation,
                    1,
                    0.0, 0.0, 0.0,
                    0.0,
                    new Particle.DustOptions(isOtherColor ? Color.RED : Color.YELLOW, 0.5F),
                    true);
        }

        for (Location particleALocation : blackHoleLocations) {
            world.spawnParticle(Particle.DUST,
                    particleALocation,
                    1,
                    0.0, 0.0, 0.0,
                    0.0,
                    new Particle.DustOptions(Color.BLACK, 1.35F),
                    true);
        }
    }

    private void suckInNearbyEntities(Location location) {
        World world = location.getWorld();
        assert world != null;

        for (Entity entity : world.getNearbyEntities(location, 8, 6, 8)) {
            if (!(entity instanceof LivingEntity livingEntity)) return;

            double speed = livingEntity.getLocation().distance(location) > 5.0 ? 0.2 : 0.35;
            Location livingEntityLocation = livingEntity.getLocation();

            Vector direction = location.toVector().subtract(livingEntityLocation.toVector());

            direction.normalize();
            direction.multiply(speed);

            livingEntity.setVelocity(livingEntity.getVelocity().add(direction));

            if (livingEntity.getLocation().distance(location) < 2.0) livingEntity.damage(0.75);
        }
    }

}
