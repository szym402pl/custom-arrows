package me.xiaojibazhanshi.customarrows.util.arrows;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TreeMiner {

    private TreeMiner() {

    }

    private static final Set<BlockFace> BLOCK_FACES = Set.of(
            BlockFace.NORTH, BlockFace.SOUTH,
            BlockFace.EAST, BlockFace.WEST,
            BlockFace.NORTH_EAST, BlockFace.SOUTH_EAST,
            BlockFace.NORTH_WEST, BlockFace.SOUTH_WEST
    );

    private static final Set<Material> LOG_MATERIALS = Set.of(
            Material.OAK_LOG, Material.BIRCH_LOG,
            Material.SPRUCE_LOG, Material.JUNGLE_LOG,
            Material.ACACIA_LOG, Material.DARK_OAK_LOG
    );

    private static final Set<Material> LEAF_MATERIALS = Set.of(
            Material.OAK_LEAVES, Material.BIRCH_LEAVES,
            Material.SPRUCE_LEAVES, Material.JUNGLE_LEAVES,
            Material.ACACIA_LEAVES, Material.DARK_OAK_LEAVES
    );

    public static List<Block> getAllTreeLogs(Block startBlock) {
        if (!LOG_MATERIALS.contains(startBlock.getType())) {
            return new ArrayList<>();
        }

        List<Block> treeStumps = new ArrayList<>();
        Set<Block> visited = new HashSet<>();

        identifyTreeStumps(startBlock, treeStumps, visited);

        List<Block> validTreeLogs = new ArrayList<>();

        for (Block log : treeStumps) {
            identifyUpwardTreeLogs(log, validTreeLogs);
        }

        validTreeLogs.addAll(treeStumps);
        return validTreeLogs;
    }

    private static void identifyTreeStumps(Block block, List<Block> treeLogs, Set<Block> visited) {
        if (visited.contains(block) || !LOG_MATERIALS.contains(block.getType())) {
            return;
        }

        visited.add(block);
        treeLogs.add(block);

        for (BlockFace face : BLOCK_FACES) {
            Block neighbor = block.getRelative(face);
            if (LOG_MATERIALS.contains(neighbor.getType()) && !visited.contains(neighbor)) {
                identifyTreeStumps(neighbor, treeLogs, visited);
            }
        }
    }

    private static void identifyUpwardTreeLogs(Block block, List<Block> validTreeLogs) {
        Block current = block.getRelative(BlockFace.UP);
        boolean hasLeafAtTop = false;

        while (LOG_MATERIALS.contains(current.getType()) || LEAF_MATERIALS.contains(current.getType())) {
            if (LEAF_MATERIALS.contains(current.getType())) {
                hasLeafAtTop = true;
                break;
            }

            if (LOG_MATERIALS.contains(current.getType())) {
                validTreeLogs.add(current);
            }

            current = current.getRelative(BlockFace.UP);
        }

        if (!hasLeafAtTop) {
            validTreeLogs.clear();
        }
    }

}
