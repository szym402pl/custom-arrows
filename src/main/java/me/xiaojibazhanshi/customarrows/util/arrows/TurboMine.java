package me.xiaojibazhanshi.customarrows.util.arrows;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import java.util.ArrayList;
import java.util.List;

public class TurboMine {

    private TurboMine() {}

    public static List<Block> getAdjacentBlocks(Block block, List<BlockFace> allowedBlockFaces) {
        List<Block> list = new ArrayList<>();

        for (BlockFace blockFace : allowedBlockFaces) {
            Block adjacentBlock = block.getRelative(blockFace);
            Material blockType = block.getType();

            if (!blockType.isSolid() ||
                    blockType == Material.AIR ||
                    blockType == Material.BEDROCK ||
                    blockType == Material.OBSIDIAN) continue;

            list.add(adjacentBlock);
        }

        return list;
    }

}
