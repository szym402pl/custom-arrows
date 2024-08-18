package me.xiaojibazhanshi.customarrows.runnables;

import me.xiaojibazhanshi.customarrows.util.ArrowSpecificUtil;
import org.bukkit.Location;
import org.bukkit.block.data.BlockData;
import org.bukkit.scheduler.BukkitTask;

import java.util.Map;
import java.util.function.Consumer;

public class BlackHoleAnimationTask implements Consumer<BukkitTask> {

    private final Location blackHoleLocation;
    private Map<Location, BlockData> blockDataMap;

    public BlackHoleAnimationTask(Location blackHoleLocation, Map<Location, BlockData> blockDataMap) {
        this.blackHoleLocation = blackHoleLocation;
        this.blockDataMap = blockDataMap;
    }

    @Override
    public void accept(BukkitTask bukkitTask) {
        if (blockDataMap.isEmpty()) {
            bukkitTask.cancel();
            return;
        }

        for (Location blockLocation : blockDataMap.keySet()) {
            ArrowSpecificUtil.animateTowardBlackHole(blackHoleLocation, blockDisplay);
            blockDataMap.remove(blockLocation);
            return;
        }
    }

}
