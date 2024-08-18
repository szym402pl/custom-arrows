package me.xiaojibazhanshi.customarrows.runnables;

import me.xiaojibazhanshi.customarrows.util.ArrowSpecificUtil;
import org.bukkit.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.Map;
import java.util.function.Consumer;

import static me.xiaojibazhanshi.customarrows.util.ArrowSpecificUtil.randomizeLocation;

public class BlackHoleTask implements Consumer<BukkitTask> {

    private final Location location;
    private final int durationInSeconds;
    private final int chosenPeriod;
    private boolean areBlocksBroken = false;

    private int counter = 1;

    public BlackHoleTask(Location location, int durationInSeconds, int chosenPeriod) {
        this.durationInSeconds = durationInSeconds;
        this.chosenPeriod = chosenPeriod;
        this.location = location;
    }

    @Override
    public void accept(BukkitTask bukkitTask) {
        int ticksInSecond = 20;
        if (counter * chosenPeriod >= durationInSeconds * ticksInSecond) bukkitTask.cancel();

        World world = location.getWorld();
        assert world != null;

        for (Location particleALocation : ArrowSpecificUtil.generateSphere(location, 0.5, 3)) {
            world.spawnParticle(Particle.DUST,
                    particleALocation,
                    1,
                    0.0, 0.0, 0.0,
                    0.0,
                    new Particle.DustOptions(Color.BLACK, 1.0F), true);
        }

        for (Location particleBLocation : ArrowSpecificUtil.generateOneHighCylinder(location, 2.5, 10)) {
            world.spawnParticle(Particle.DUST,
                    particleBLocation,
                    1,
                    0.0, 0.0, 0.0,
                    0.0,
                    new Particle.DustOptions(Color.YELLOW, 1.0F), true);
        }

        if (!areBlocksBroken) {
            ArrowSpecificUtil.breakBlocksAround(location, 2);

            areBlocksBroken = true;
        }

        counter++;
    }

}
