package me.xiaojibazhanshi.customarrows.util.arrows;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import java.util.ArrayList;
import java.util.List;

public class Bridge {

    private Bridge(){

    }

    public static List<Block> getALineOfAirBlocks(Block origin, BlockFace direction, int maxBridgeLength) {
        List<Block> line = new ArrayList<>();

        Block nextBlock = returnNextBlockIfNotSolid(origin, direction);

        for (int i = 1; i < maxBridgeLength; i++) {
            if (nextBlock == null) break;

            line.add(nextBlock);
            nextBlock = returnNextBlockIfNotSolid(nextBlock, direction);
        }

        return line;
    }

    public static Block returnNextBlockIfNotSolid(Block origin, BlockFace direction) {
        Block nextBlock = origin.getRelative(direction);

        return nextBlock.getType().isSolid() ? null : nextBlock;
    }

}
