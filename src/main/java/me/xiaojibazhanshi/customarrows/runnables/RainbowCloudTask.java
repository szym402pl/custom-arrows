package me.xiaojibazhanshi.customarrows.runnables;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.scheduler.BukkitTask;

import java.util.function.Consumer;

import static me.xiaojibazhanshi.customarrows.util.arrows.Thunder.randomizeLocation;

public class RainbowCloudTask implements Consumer<BukkitTask> {

    private final int smokeAmount;
    private final Location location;
    private final int maxOffset;
    private final float particleSize;
    private int smokeIterations;

    private int counter = 1;

    public RainbowCloudTask(int smokeAmount, Location location, int maxOffset, int smokeIterations) {
        particleSize = 4.0F;
        this.maxOffset = maxOffset;
        this.location = location.clone();
        this.smokeAmount = smokeAmount;
        this.smokeIterations = smokeIterations;
    }

    @Override
    public void accept(BukkitTask bukkitTask) {
        if (counter == smokeAmount) bukkitTask.cancel();
        if (location.getWorld() == null) return;

        do {
            location.getWorld().spawnParticle(Particle.DUST,
                    randomizeLocation(location.clone(), maxOffset),
                    2,
                    0.0, 0.0, 0.0,
                    0.0,
                    new Particle.DustOptions(Color.WHITE, particleSize), true);

            smokeIterations--;
        } while (smokeIterations > 0);

        counter++;
    }

}
