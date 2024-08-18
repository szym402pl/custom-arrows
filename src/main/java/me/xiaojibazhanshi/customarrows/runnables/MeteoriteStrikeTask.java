package me.xiaojibazhanshi.customarrows.runnables;

import org.bukkit.Location;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LightningStrike;
import org.bukkit.scheduler.BukkitTask;

import java.util.function.Consumer;

import static me.xiaojibazhanshi.customarrows.util.ArrowSpecificUtil.randomizeLocation;

public class MeteoriteStrikeTask implements Consumer<BukkitTask> {

    private final int meteoriteAmount;
    private final Location location;
    private final int maxOffset;

    private int counter = 1;

    public MeteoriteStrikeTask(int meteoriteAmount, Location location, int maxOffset) {
        this.maxOffset = maxOffset;
        this.location = location;
        this.meteoriteAmount = meteoriteAmount;
    }

    @Override
    public void accept(BukkitTask bukkitTask) {
        if (counter == meteoriteAmount) bukkitTask.cancel();
        if (location.getWorld() == null) return;

        Location randomLocation = randomizeLocation(location.clone(), maxOffset);

        float angle = 35.0F;
        randomLocation.setPitch(angle);

        location.getWorld().spawn(randomLocation, Fireball.class);

        counter++;
    }

}
