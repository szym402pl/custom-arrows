package me.xiaojibazhanshi.customarrows.runnables;

import me.xiaojibazhanshi.customarrows.util.ArrowSpecificUtil;
import org.bukkit.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

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

        if (!areBlocksBroken) {
            ArrowSpecificUtil.executeBlackHoleAnimation(location, 2.5);
            areBlocksBroken = true;
        }

        World world = location.getWorld();
        assert world != null;

        generateBlackHole(world);
        suckInNearbyEntities(location);

        counter++;
    }

    private void generateBlackHole(World world) {
        List<Location> ringLocations = ArrowSpecificUtil.generateOneHighRing(location, 3, 9.0);
        List<Location> blackHoleLocations = ArrowSpecificUtil.generateSphere(location, 0.75, 4.0);

        for (Location particleBLocation : ringLocations) {
            world.spawnParticle(Particle.DUST,
                    particleBLocation,
                    1,
                    0.0, 0.0, 0.0,
                    0.0,
                    new Particle.DustOptions(Color.YELLOW, 0.6F), true);
        }

        for (Location particleALocation : blackHoleLocations) {
            world.spawnParticle(Particle.DUST,
                    particleALocation,
                    1,
                    0.0, 0.0, 0.0,
                    0.0,
                    new Particle.DustOptions(Color.BLACK, 1.35F), true);
        }
    }

    private void suckInNearbyEntities(Location location) {
        World world = location.getWorld();
        assert world != null;

        for (Entity entity : world.getNearbyEntities(location, 6, 6, 6)) {
            if (!(entity instanceof LivingEntity livingEntity)) return;

            if (livingEntity.getLocation().distance(location) < 1.5) {
                livingEntity.damage(0.75);
            }

            Location livingEntityLocation = livingEntity.getLocation();
            Vector direction = location.toVector().subtract(livingEntityLocation.toVector());

            direction.normalize();
            direction.multiply(0.5);

            livingEntity.setVelocity(livingEntity.getVelocity().add(direction));
        }
    }

}
