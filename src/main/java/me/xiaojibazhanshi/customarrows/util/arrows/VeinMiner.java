package me.xiaojibazhanshi.customarrows.util.arrows;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import java.util.*;

public class VeinMiner {

    public static boolean isOre(Block block) {
        return block != null
                && block.getType().isSolid()
                && block.getType().toString().toLowerCase().contains("ore");
    }

    public static List<Block> getSurroundingBlocks(Block block) {
        return List.of(block.getRelative(BlockFace.DOWN), block.getRelative(BlockFace.UP),
                block.getRelative(BlockFace.NORTH), block.getRelative(BlockFace.SOUTH),
                block.getRelative(BlockFace.EAST), block.getRelative(BlockFace.WEST));
    }

    public static List<Block> filterToOnlyOreBlocks(List<Block> blocks) {
        return blocks.stream().filter(VeinMiner::isOre).toList();
    }

    /**Origin can just be a single block*/
    public static List<Block> getFullOreVein(List<Block> origin) {
        if (origin.isEmpty()) return List.of();

        Set<Block> oresInVein = new HashSet<>(origin);
        Queue<Block> toProcess = new LinkedList<>(origin);

        while (!toProcess.isEmpty()) {
            Block current = toProcess.poll();
            List<Block> surroundingOres = filterToOnlyOreBlocks(getSurroundingBlocks(current));

            for (Block ore : surroundingOres) {
                if (oresInVein.add(ore)) {
                    toProcess.add(ore);
                }
            }
        }

        return new ArrayList<>(oresInVein);
    }

    public static List<Block> sortByDistanceTo(List<Block> blocks, Block origin) {

        blocks.sort((block1, block2) -> {
            double distance1 = block1.getLocation().distance(origin.getLocation());
            double distance2 = block2.getLocation().distance(origin.getLocation());

            return Double.compare(distance1, distance2);
        });

        return blocks;
    }

}
