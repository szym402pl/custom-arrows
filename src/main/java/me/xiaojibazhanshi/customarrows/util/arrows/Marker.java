package me.xiaojibazhanshi.customarrows.util.arrows;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import static me.xiaojibazhanshi.customarrows.util.arrows.Ghost.temporarilyConvertToDisplayItem;

public class Marker {

    private Marker() {

    }

    public static void spawnBeam(Location location, int height, int durationInSeconds, Material beamMaterial) {
        World world = location.getWorld();

        for (int y = (int) location.getY(); y < location.getY() + height; y++) {
            assert world != null;
            Location location2 = new Location(world, location.getX(), y, location.getZ());
            Block block = world.getBlockAt(location2);

            temporarilyConvertToDisplayItem(block, durationInSeconds * 20, beamMaterial);
        }
    }

}
