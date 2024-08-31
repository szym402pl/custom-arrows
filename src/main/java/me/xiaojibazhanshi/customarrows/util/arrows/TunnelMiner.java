package me.xiaojibazhanshi.customarrows.util.arrows;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import java.util.ArrayList;
import java.util.List;

public class TunnelMiner {

    private TunnelMiner() {

    }

    public static List<Block> getTunnelOfBlocks(Block origin, BlockFace direction, int tunnelLength) {
        List<Block> tunnelBlocks = new ArrayList<>();
        tunnelBlocks.add(origin);

        Block nextBlock = returnNextBlockIfSolid(origin, direction);

        for (int i = 1; i < tunnelLength; i++) {
            if (nextBlock == null) break;

            tunnelBlocks.add(nextBlock);
            nextBlock = returnNextBlockIfSolid(nextBlock, direction);
        }

        return tunnelBlocks;
    }

    public static Block returnNextBlockIfSolid(Block origin, BlockFace direction) {
        Block nextBlock = origin.getRelative(direction);

        return nextBlock.getType().isSolid() ? nextBlock : null;
    }

}
