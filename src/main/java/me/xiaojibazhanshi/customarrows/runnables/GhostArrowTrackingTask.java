package me.xiaojibazhanshi.customarrows.runnables;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LightningStrike;
import org.bukkit.scheduler.BukkitTask;

import java.util.function.Consumer;

import static me.xiaojibazhanshi.customarrows.util.ArrowSpecificUtil.randomizeLocation;

public class GhostArrowTrackingTask implements Consumer<BukkitTask> {

    private final long trackingDuration;
    private final Entity arrow;
    private final long delayInTicks;

    private long counter = 1;

    public GhostArrowTrackingTask(Entity arrow, long trackingDuration, long delayInTicks) {
        this.arrow = arrow;
        this.trackingDuration = trackingDuration;
        this.delayInTicks = delayInTicks;
    }

    @Override
    public void accept(BukkitTask bukkitTask) {
        if (counter >= trackingDuration || arrow.isDead() || arrow.isFrozen()) return;



        counter += delayInTicks;
    }

}
