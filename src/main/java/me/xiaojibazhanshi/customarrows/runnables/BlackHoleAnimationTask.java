package me.xiaojibazhanshi.customarrows.runnables;

import me.xiaojibazhanshi.customarrows.CustomArrows;
import me.xiaojibazhanshi.customarrows.util.ArrowSpecificUtil;
import org.bukkit.Location;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.sql.SQLOutput;
import java.util.*;
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
