package me.xiaojibazhanshi.customarrows.util.arrows;

import me.xiaojibazhanshi.customarrows.util.GeneralUtil;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public class Trap {

    /**
     * @return true if block above isn't solid and there
     * isn't air around the to-be tnt block (the one below the main block)
     */
    public static boolean isValidTrapLocation(Block block) {
        if (block == null) return false;

        Material type = block.getType();
        if (!type.isSolid() || !GeneralUtil.isNotPlant(block)) return false;

        Block blockAbove = block.getRelative(BlockFace.UP);
        if (blockAbove.getType() != Material.AIR && GeneralUtil.isNotPlant(blockAbove)) return false;

        Block blockBelow = block.getRelative(BlockFace.DOWN);

        for (BlockFace face : BlockFace.values()) {
            if (blockBelow.getRelative(face).getType() == Material.AIR) return false;
        }

        return true;
    }

}
