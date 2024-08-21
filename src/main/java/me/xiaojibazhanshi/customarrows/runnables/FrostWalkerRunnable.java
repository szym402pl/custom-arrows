package me.xiaojibazhanshi.customarrows.runnables;

import me.xiaojibazhanshi.customarrows.util.arrows.FrostWalker;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.scheduler.BukkitTask;

import java.util.function.Consumer;

import static me.xiaojibazhanshi.customarrows.util.arrows.Thunder.randomizeLocation;

public class FrostWalkerRunnable implements Consumer<BukkitTask> {

    private final Arrow arrow;

    public FrostWalkerRunnable(Arrow arrow) {
        this.arrow = arrow;
    }

    @Override
    public void accept(BukkitTask bukkitTask) {
        if (arrow.isDead() || arrow.isOnGround()) {
            bukkitTask.cancel();
            return;
        }

        Location location = arrow.getLocation();
        Block blockAtLocation = location.getBlock();

        if (FrostWalker.applyFrostWalkerEffect(blockAtLocation)) {
            arrow.remove();
            bukkitTask.cancel();
        };
    }

}
