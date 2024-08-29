package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.runnables.TunnelMinerTask;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static me.xiaojibazhanshi.customarrows.util.GeneralUtil.color;
import static me.xiaojibazhanshi.customarrows.util.arrows.TunnelMiner.getTunnelOfBlocks;

public class TunnelMinerArrow extends CustomArrow {

    private final Map<UUID, TunnelMinerTask> activeTunnelTasks = new HashMap<>();
    private final HashMap<BlockFace, BlockFace> oppositeBlockFaces = new HashMap<>();
    private final List<Material> pickaxes;

    public TunnelMinerArrow() {
        super(ArrowFactory.changeTippedColor
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&7Tunnel Miner Arrow", "tunnel_miner_arrow",
                                List.of("", "This arrow will mine a tunnel in front of you",
                                        "", "NOTE: You need a pickaxe for it to work",
                                        "and it will drain 2 durability per block,",
                                        "plus it will stop once you leave the game or",
                                        "if it mines 100 blocks or encounters air blocks")),
                        Color.GRAY));

        oppositeBlockFaces.put(BlockFace.DOWN, BlockFace.UP);
        oppositeBlockFaces.put(BlockFace.UP, BlockFace.DOWN);

        oppositeBlockFaces.put(BlockFace.SOUTH, BlockFace.NORTH);
        oppositeBlockFaces.put(BlockFace.NORTH, BlockFace.SOUTH);

        oppositeBlockFaces.put(BlockFace.EAST, BlockFace.WEST);
        oppositeBlockFaces.put(BlockFace.WEST, BlockFace.EAST);

        pickaxes = List.of(Material.WOODEN_PICKAXE,
                Material.STONE_PICKAXE,
                Material.IRON_PICKAXE,
                Material.GOLDEN_PICKAXE,
                Material.DIAMOND_PICKAXE,
                Material.NETHERITE_PICKAXE);
    }

    @Override
    public void onPlayerLeave(PlayerQuitEvent event, Player player) {

        for (UUID uuid : activeTunnelTasks.keySet()) {
            if (player.getUniqueId() == uuid) {
                activeTunnelTasks.get(uuid).cancel();
                activeTunnelTasks.remove(uuid, activeTunnelTasks.get(uuid));
            }
        }
    }

    @Override
    public void onHitBlock(ProjectileHitEvent event, Player shooter) {
        Block hitBlock = event.getHitBlock();

        if (hitBlock == null || !hitBlock.getType().isSolid()) {
            shooter.sendTitle("", color("&7I can't mine here..."), 5, 25, 5);
            return;
        }

        ItemStack[] inventoryItems = shooter.getInventory().getContents();

        if (inventoryItems.length == 0) {
            shooter.sendTitle("", color("&7I need a pickaxe for it to work..."), 5, 25, 5);
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
            shooter.sendTitle("", color("&7I need a pickaxe for it to work..."), 5, 25, 5);
            return;
        }

        event.getEntity().remove();

        int tunnelLength = 10;
        BlockFace oppositeFace = oppositeBlockFaces.get(event.getHitBlockFace());

        List<Block> tunnelBlocks = getTunnelOfBlocks(hitBlock, oppositeFace, tunnelLength);

        TunnelMinerTask task = new TunnelMinerTask(tunnelBlocks, pickaxe);
        task.start();

        activeTunnelTasks.put(shooter.getUniqueId(), task);
    }

}
