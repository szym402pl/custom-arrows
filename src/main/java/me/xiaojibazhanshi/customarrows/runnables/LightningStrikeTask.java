package me.xiaojibazhanshi.customarrows.runnables;

import org.bukkit.Location;
import org.bukkit.entity.LightningStrike;
import org.bukkit.scheduler.BukkitTask;

import java.util.function.Consumer;

import static me.xiaojibazhanshi.customarrows.util.arrows.Thunder.randomizeLocation;

public class LightningStrikeTask implements Consumer<BukkitTask> {

    private final int thunderAmount;
    private final Location location;
    private final int maxOffset;

    private int counter = 1;

    public LightningStrikeTask(int thunderAmount, Location location, int maxOffset) {
        this.maxOffset = maxOffset;
        this.location = location;
        this.thunderAmount = thunderAmount;
    }

    @Override
    public void accept(BukkitTask bukkitTask) {
        if (counter == thunderAmount) bukkitTask.cancel();
        if (location.getWorld() == null) return;

        location.getWorld().spawn(randomizeLocation(location.clone(), maxOffset), LightningStrike.class);

        counter++;
    }

}
