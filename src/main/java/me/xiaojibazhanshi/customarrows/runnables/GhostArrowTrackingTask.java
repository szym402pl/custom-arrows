package me.xiaojibazhanshi.customarrows.runnables;

import me.xiaojibazhanshi.customarrows.util.ArrowSpecificUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.function.Consumer;

import static me.xiaojibazhanshi.customarrows.util.ArrowSpecificUtil.randomizeLocation;

public class GhostArrowTrackingTask implements Consumer<BukkitTask> {

    private final long trackingDuration;
    private final Entity arrow;
    private final long delayInTicks;

    private long counter = 1;

    public GhostArrowTrackingTask(Entity arrow, int trackingDurationInSec, long delayInTicks) {
        this.arrow = arrow;
        this.trackingDuration = trackingDurationInSec * 20L;
        this.delayInTicks = delayInTicks;
    }

    @Override
    public void accept(BukkitTask bukkitTask) {
        counter += delayInTicks;

        if (counter >= trackingDuration || arrow.isDead())  {
            bukkitTask.cancel();
            return;
        }

        Location currentLocation = arrow.getLocation().clone();
        Block nextBlock = currentLocation.clone().add(currentLocation.getDirection().normalize().multiply(2.0)).getBlock();

        if (!nextBlock.getType().isSolid()) return;
        System.out.println("Got through the first check - the next block distance: "
                + currentLocation.distance(nextBlock.getLocation()));

        Block nextNextBlock = nextBlock.getLocation().add(currentLocation.getDirection().normalize()).getBlock();

        if (nextNextBlock.getType() != Material.AIR) return;
        System.out.println("Got through the second check - turning the next block into a display");

        ArrowSpecificUtil.temporarilyConvertToDisplayItem(nextBlock);
        bukkitTask.cancel();
    }

}
