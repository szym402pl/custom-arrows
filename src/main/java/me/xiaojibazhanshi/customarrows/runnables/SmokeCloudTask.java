package me.xiaojibazhanshi.customarrows.runnables;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.scheduler.BukkitTask;

import java.util.function.Consumer;

import static me.xiaojibazhanshi.customarrows.util.arrows.Thunder.randomizeLocation;

public class SmokeCloudTask implements Consumer<BukkitTask> {

    private final int smokeAmount;
    private final Location location;
    private final int maxOffset;
    private final float particleSize;
    private int smokeIterations;
    private final Color color;

    private int counter = 1;

    public SmokeCloudTask(int smokeAmount, Location location, int maxOffset, int smokeIterations, Color color) {
        particleSize = 4.0F; // max value
        this.maxOffset = maxOffset;
        this.location = location.clone();
        this.smokeAmount = smokeAmount;
        this.smokeIterations = smokeIterations;
        this.color = color;
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
                    new Particle.DustOptions(color, particleSize), true);

            smokeIterations--;
        } while (smokeIterations > 0);

        counter++;
    }

}
