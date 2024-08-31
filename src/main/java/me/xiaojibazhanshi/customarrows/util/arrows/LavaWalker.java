package me.xiaojibazhanshi.customarrows.util.arrows;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public class LavaWalker {

    private LavaWalker() {}

    enum BlockFaces {
        NORTH(BlockFace.NORTH),
        WEST(BlockFace.WEST),
        SOUTH(BlockFace.SOUTH),
        EAST(BlockFace.EAST);

        private final BlockFace blockFace;

        BlockFaces(BlockFace blockFace) {
            this.blockFace = blockFace;
        }
    }

    public static boolean applyLavaWalkerEffect(Block block) {
        if (block == null) return false;
        if (block.getType() != Material.LAVA) return false;

        Block finalBlock = getBlockOnLavaSurface(block);
        finalBlock.setType(Material.OBSIDIAN);

        applyFrostWalkerEffectToNearbyBlocks(finalBlock);

        return true;
    }

    private static Block getBlockOnLavaSurface(Block underLavaBlock) {
        Location location = underLavaBlock.getLocation();
        int blockY = location.getBlockY();

        World world = location.getWorld();
        assert world != null;

        int maxYAdjustment = 10;

        for (int i = blockY; i < underLavaBlock.getLocation().getY() + maxYAdjustment; i++) {
            Block blockI = world.getBlockAt(location.getBlockX(), i, location.getBlockZ());
            Block blockAbove = blockI.getRelative(BlockFace.UP);

            if (blockAbove.getType() != Material.AIR) {
                continue;
            }

            return blockI;
        }

        return underLavaBlock;
    }

    private static void applyFrostWalkerEffectToNearbyBlocks(Block block) {
        for (BlockFaces face1 : BlockFaces.values()) {
            Block block1 = block.getRelative(face1.blockFace);
            if (block1.getType() != Material.LAVA) continue;

            block1.setType(Material.OBSIDIAN);

            for (BlockFaces face2 : BlockFaces.values()) {
                Block block2 = block1.getRelative(face2.blockFace);
                if (block2.getType() != Material.LAVA) continue;

                block2.setType(Material.OBSIDIAN);
            }
        }
    }

}
