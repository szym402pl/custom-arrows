package me.xiaojibazhanshi.customarrows.util.arrows;

import org.bukkit.Material;
import org.bukkit.block.Block;

import static org.bukkit.block.BlockFace.UP;

public class WaterArrow {

    private WaterArrow() {

    }

    public static void spawnAWaterBlockAbove(Block origin) {
        Block above = origin.getRelative(UP);
        above.setType(Material.WATER);
    }

}
