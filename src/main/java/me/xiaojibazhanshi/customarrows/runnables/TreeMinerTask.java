package me.xiaojibazhanshi.customarrows.runnables;

import me.xiaojibazhanshi.customarrows.CustomArrows;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class TreeMinerTask extends BukkitRunnable {

    private final List<Block> logsToBreak;

    public TreeMinerTask(List<Block> logsToBreak) {
        this.logsToBreak = logsToBreak;
    }

    @Override
    public void run() {
        Block block = logsToBreak.getFirst();
        logsToBreak.remove(block);

        World world = block.getWorld();

        if (logsToBreak.isEmpty()) {
            world.playSound(block.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);

            cancel();
        }

        Sound breakSound;

        try {
            breakSound = Sound.valueOf(("BLOCK_" + block.getType() + "_BREAK").replace("LOG", "WOOD"));
        } catch (IllegalArgumentException ex) {
            breakSound = Sound.BLOCK_CHERRY_WOOD_BREAK;
        }

        world.playSound(block.getLocation(), breakSound, 1.0F, 1.0F);

        double offset = 0.5;
        double yOffset = 0.2;
        int count = 5;

        world.spawnParticle(Particle.BLOCK, block.getLocation(), count, offset, yOffset, offset, block.getBlockData());
        block.breakNaturally();
    }

    public void start() {
        runTaskTimer(CustomArrows.getInstance(), 10, 10);
    }
}