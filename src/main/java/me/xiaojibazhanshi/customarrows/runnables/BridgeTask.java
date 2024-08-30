package me.xiaojibazhanshi.customarrows.runnables;

import me.xiaojibazhanshi.customarrows.CustomArrows;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class BridgeTask extends BukkitRunnable {

    private final List<Block> blocks;

    public BridgeTask(List<Block> blocks) {
        this.blocks = blocks;
    }

    @Override
    public void run() {
        Block block = blocks.getFirst();
        blocks.remove(block);

        World world = block.getWorld();

        if (blocks.isEmpty()) {
            world.playSound(block.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);

            cancel();
        }

        block.setType(Material.BIRCH_PLANKS);
        world.playSound(block.getLocation(), Sound.BLOCK_CHERRY_WOOD_PLACE, 1.0F, 1.0F);

        double offset = 0.5;
        double yOffset = 0.5;
        int count = 5;

        Location particleLocation = block.getLocation().clone().add(0.5, 0.5, 0.5);
        world.spawnParticle(Particle.BLOCK, particleLocation, count, offset, yOffset, offset, block.getBlockData());
    }

    public void start() {
        runTaskTimer(CustomArrows.getInstance(), 10, 10);
    }
}