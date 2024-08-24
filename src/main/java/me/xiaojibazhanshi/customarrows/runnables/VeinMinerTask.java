package me.xiaojibazhanshi.customarrows.runnables;

import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;
import java.util.function.Consumer;

public class VeinMinerTask implements Consumer<BukkitTask> {

    private final List<Block> vein;

    public VeinMinerTask(List<Block> vein) {
        this.vein = vein;
    }

    @Override
    public void accept(BukkitTask bukkitTask) {
        Block toBeBroken = vein.getFirst();
        World world = toBeBroken.getWorld();

        toBeBroken.breakNaturally();
        vein.removeFirst();

        if (vein.isEmpty()) {
            world.playSound(toBeBroken.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.5F, 1.5F);
            bukkitTask.cancel();
            return;
        }

        world.playSound(toBeBroken.getLocation(), Sound.BLOCK_STONE_BREAK, 1.0F, 1.0F);
    }

}
