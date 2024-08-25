package me.xiaojibazhanshi.customarrows.runnables;

import me.xiaojibazhanshi.customarrows.CustomArrows;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.List;

import static me.xiaojibazhanshi.customarrows.util.arrows.Homing.getDirectionFromEntityToTarget;
import static me.xiaojibazhanshi.customarrows.util.arrows.Homing.isDistanceGreaterThan;
import static me.xiaojibazhanshi.customarrows.util.arrows.Seeker.findFirstEntityBelow;

public class TunnelMinerTask extends BukkitRunnable {

    private final List<Block> tunnelBlocks;
    private final ItemStack pickaxe;

    public TunnelMinerTask(List<Block> tunnelBlocks, ItemStack pickaxe) {
        this.tunnelBlocks = tunnelBlocks;
        this.pickaxe = pickaxe;
    }

    @Override
    public void run() {
        Block block = tunnelBlocks.getFirst();
        tunnelBlocks.remove(block);

        World world = block.getWorld();

        if (tunnelBlocks.isEmpty()) {
            world.playSound(block.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);

            cancel();
        }

        Damageable pickaxeMeta = (Damageable) pickaxe.getItemMeta();
        assert pickaxeMeta != null;

        boolean willPickaxeBreak = pickaxeMeta.getDamage() + 2 > pickaxe.getType().getMaxDurability();

        if (willPickaxeBreak) {
            pickaxe.setAmount(0);
            world.playSound(block.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0F, 1.0F);

            cancel();
            return;
        }

        pickaxeMeta.setDamage(pickaxeMeta.getDamage() + 2);
        pickaxe.setItemMeta(pickaxeMeta);

        Sound breakSound;

        try {
            breakSound = Sound.valueOf("BLOCK_" + block.getType() + "_BREAK");
        } catch (IllegalArgumentException ex) {
            breakSound = Sound.BLOCK_STONE_BREAK;
        }

        world.playSound(block.getLocation(), breakSound, 1.0F, 1.0F);

        double offset = 0.5;
        double yOffset = 0.2;
        int count = 5;

        world.spawnParticle(Particle.BLOCK, block.getLocation(), count, offset, yOffset, offset, block.getBlockData());

        block.breakNaturally();
    }

    public void start() {
        runTaskTimer(CustomArrows.getInstance(), 10, 40);
    }
}