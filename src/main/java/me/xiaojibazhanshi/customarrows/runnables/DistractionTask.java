package me.xiaojibazhanshi.customarrows.runnables;

import org.bukkit.*;
import org.bukkit.entity.Slime;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

import static me.xiaojibazhanshi.customarrows.util.GeneralUtil.removeEntityAfter;
import static me.xiaojibazhanshi.customarrows.util.arrows.Repulsion.detonateFirework;
import static me.xiaojibazhanshi.customarrows.util.arrows.Thunder.randomizeLocation;

public class DistractionTask implements Consumer<BukkitTask> {

    private final int fireworkAmount;
    private final Location location;

    private int counter = 1;

    public DistractionTask(int fireworkAmount, Location location) {
        this.location = location;
        this.fireworkAmount = fireworkAmount;
    }

    @Override
    public void accept(BukkitTask bukkitTask) {
        if (counter >= fireworkAmount) bukkitTask.cancel();
        int maxOffset = 5;

        World world = location.getWorld();
        assert world != null;

        Location yAdjustedLocation = location.clone().add(0, 20, 0);
        Location fireworkLocation = randomizeLocation(yAdjustedLocation, maxOffset);

        world.playSound(location, Sound.ENTITY_GENERIC_EXPLODE, 12.5F, 12.5F);
        detonateFirework(fireworkLocation, FireworkEffect.Type.BALL_LARGE, Color.BLACK);

        counter++;
    }

}
