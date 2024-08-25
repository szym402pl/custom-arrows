package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

import static me.xiaojibazhanshi.customarrows.util.GeneralUtil.color;
import static me.xiaojibazhanshi.customarrows.util.arrows.AreaHeal.spawnALingeringPotion;

public class TunnelMinerArrow extends CustomArrow {

    private final Map<UUID, BukkitTask> activeTunnelTasks = new HashMap<>();
    private final HashMap<BlockFace, BlockFace> oppositeSides = new HashMap<>();
    private final List<Material> pickaxes;

    public TunnelMinerArrow() {
        super(ArrowFactory.changeTippedColor
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&7Tunnel Miner Arrow", "tunnel_miner_arrow",
                                List.of("", "This arrow will mine a tunnel in front of you",
                                        "", "NOTE: You need a pickaxe for it to work,",
                                        "it will drain 2 durability per block",
                                        "& it will stop once you shoot again or",
                                        "if it mines 100 blocks/encounters air blocks")),
                                Color.GRAY));

        oppositeSides.put(BlockFace.DOWN, BlockFace.UP);
        oppositeSides.put(BlockFace.UP, BlockFace.DOWN);

        oppositeSides.put(BlockFace.SOUTH, BlockFace.NORTH);
        oppositeSides.put(BlockFace.NORTH, BlockFace.SOUTH);

        oppositeSides.put(BlockFace.EAST, BlockFace.WEST);
        oppositeSides.put(BlockFace.WEST, BlockFace.EAST);

        pickaxes = List.of(Material.WOODEN_PICKAXE,
                           Material.STONE_PICKAXE,
                           Material.IRON_PICKAXE,
                           Material.GOLDEN_PICKAXE,
                           Material.DIAMOND_PICKAXE,
                           Material.NETHERITE_PICKAXE);
    }

    @Override
    public void onHitBlock(ProjectileHitEvent event, Player shooter) {
        Block hitBlock = event.getHitBlock();
        if (hitBlock == null) return;

        ItemStack[] inventoryItems = shooter.getInventory().getContents();

        if (inventoryItems.length == 0) {
            shooter.sendTitle("", color("&7I need a pickaxe for it to work..."));
            return;
        }

        ItemStack pickaxe = null;

        for (ItemStack item : shooter.getInventory().getContents()) {
            if (item == null) continue;

            if (pickaxes.contains(item.getType())) {
                pickaxe = item;
                break;
            }
        }

        if (pickaxe == null) {
            shooter.sendTitle("", color("&7I need a pickaxe for it to work..."));
            return;
        }

        event.getEntity().remove();

        BlockFace oppositeFace = oppositeSides.get(event.getHitBlockFace());
        Block oppositeBlock = hitBlock.getRelative(oppositeFace);

        System.out.println("Opposite block: " + oppositeBlock);
    }

}
