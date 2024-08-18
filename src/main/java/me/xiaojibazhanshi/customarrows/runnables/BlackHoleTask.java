package me.xiaojibazhanshi.customarrows.runnables;

import me.xiaojibazhanshi.customarrows.util.ArrowSpecificUtil;
import org.bukkit.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.scheduler.BukkitTask;

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

        World world = location.getWorld();
        assert world != null;

        List<Location> ringLocations = ArrowSpecificUtil.generateOneHighRing(location, 3, 8.0);
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

        if (!areBlocksBroken) {
            ArrowSpecificUtil.executeBlackHoleAnimation(location, 2);
            areBlocksBroken = true;
        }

        counter++;
    }

}
