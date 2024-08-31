package me.xiaojibazhanshi.customarrows.util.arrows;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import java.util.List;

public class SnowTrap {

    private SnowTrap() {}

    public static void setUpTrap(Block origin) {
        Location location = origin.getLocation();
        World world = location.getWorld();
        assert world != null;

        int trapSize = 5;

        for (int i = 0; i < trapSize; i++) {
            Block blockToBreak = world.getBlockAt(location.clone().subtract(0, i, 0));
            blockToBreak.setType(Material.AIR);
        }

        Block bottom = world.getBlockAt(location.clone().subtract(0, trapSize, 0));
        bottom.setType(Material.GRASS_BLOCK);

        Block berryBushBlock = bottom.getRelative(BlockFace.UP);
        berryBushBlock.setType(Material.SWEET_BERRY_BUSH);

        origin.setType(Material.POWDER_SNOW);

        Block above = origin.getRelative(BlockFace.UP);
        above.setType(Material.SNOW);
    }

    public static boolean isEligibleForTrap(Block block, List<BlockFace> blockFacesToCheck) {
        for (BlockFace blockFace : blockFacesToCheck) {
            Block blockAtFace = block.getRelative(blockFace);
            Material material = blockAtFace.getType();

            if (material == Material.AIR) {
                return false;
            }
        }

        Block above = block.getRelative(BlockFace.UP);
        Block below = block.getRelative(BlockFace.DOWN);

        return above.getType() == Material.SNOW && below.getType().isSolid();
    }
}
