package me.xiaojibazhanshi.customarrows.util.arrows;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.HashMap;
import java.util.Map;

import static me.xiaojibazhanshi.customarrows.util.arrows.Honeypot.placeTemporaryBlocks;

public class Corruption {

    private Corruption() {

    }

    public static void temporarilyCorruptBlocksInRadius(Block center, int radius, int deleteAfterSeconds) {
        World world = center.getWorld();
        Location centerLocation = center.getLocation();
        Map<Location, Material> blockLocations = new HashMap<>();

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    Location loc = centerLocation.clone().add(x, y, z);
                    Block block = world.getBlockAt(loc);

                    if (block.getType().isSolid())
                        blockLocations.put(block.getLocation(), block.getType());
                }
            }
        }

        placeTemporaryBlocks(blockLocations, deleteAfterSeconds, Material.SOUL_SAND);
    }

}
