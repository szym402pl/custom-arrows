package me.xiaojibazhanshi.customarrows.util.arrows;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public class Tree {

    private Tree() {
    }

    public static boolean isSaplingEligible(Block block) {
        return !(block == null
                || block.getRelative(BlockFace.UP).getType() != Material.AIR
                || (block.getType() != Material.GRASS_BLOCK
                && block.getType() != Material.COARSE_DIRT
                && block.getType() != Material.DIRT_PATH
                && block.getType() != Material.DIRT));
    }

    public static void generateTree(Block block, TreeType treeType) {
        Location location = block.getLocation();
        World world = location.getWorld();
        assert world != null;

        world.generateTree(location, treeType);
    }

}
