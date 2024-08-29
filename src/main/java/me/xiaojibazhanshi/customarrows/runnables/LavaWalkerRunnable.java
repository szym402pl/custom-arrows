package me.xiaojibazhanshi.customarrows.runnables;

import me.xiaojibazhanshi.customarrows.util.arrows.FrostWalker;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.scheduler.BukkitTask;

import java.util.function.Consumer;

import static me.xiaojibazhanshi.customarrows.util.arrows.LavaWalker.applyLavaWalkerEffect;

public class LavaWalkerRunnable implements Consumer<BukkitTask> {

    private final Arrow arrow;

    public LavaWalkerRunnable(Arrow arrow) {
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

        if (applyLavaWalkerEffect(blockAtLocation)) {
            arrow.remove();
            bukkitTask.cancel();
        }
    }

}
