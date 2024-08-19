package me.xiaojibazhanshi.customarrows.runnables;

import me.xiaojibazhanshi.customarrows.util.ArrowSpecificUtil;
import org.bukkit.Location;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.scheduler.BukkitTask;

import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.function.Consumer;

public class BlackHoleAnimationTask implements Consumer<BukkitTask> {

    private final Location blackHoleLocation;
    private TreeSet<BlockDisplay> blockDisplayList;

    public BlackHoleAnimationTask(Location blackHoleLocation, List<BlockDisplay> blockDisplayList) {
        Comparator<BlockDisplay> distanceComparator = Comparator.comparingDouble
                (blockDisplay -> blockDisplay.getLocation().distance(blackHoleLocation));

        TreeSet<BlockDisplay> sortedBlockDisplayList = new TreeSet<>(distanceComparator);
        sortedBlockDisplayList.addAll(blockDisplayList);

        this.blackHoleLocation = blackHoleLocation;
        this.blockDisplayList = sortedBlockDisplayList;
    }

    @Override
    public void accept(BukkitTask bukkitTask) {
        if (blockDisplayList.isEmpty()) {
            bukkitTask.cancel();
            return;
        }

        BlockDisplay workedOn = blockDisplayList.getFirst();
        ArrowSpecificUtil.animateTowardBlackHole(blackHoleLocation, workedOn);

        blockDisplayList.remove(workedOn);
    }

}
