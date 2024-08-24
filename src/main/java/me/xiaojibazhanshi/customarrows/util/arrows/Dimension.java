package me.xiaojibazhanshi.customarrows.util.arrows;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import static me.xiaojibazhanshi.customarrows.util.GeneralUtil.color;
import static me.xiaojibazhanshi.customarrows.util.arrows.Thunder.randomizeLocation;

public class Dimension {

    private Dimension() {

    }

    /**
     * @return false if it couldn't find a safe location in 10 attempts
     */
    public static boolean teleportToOneOfDimensions(LivingEntity entity, boolean end) {
        World targetWorld = end ? Bukkit.getWorld("world_the_end") : Bukkit.getWorld("world_nether");
        Location location = new Location(targetWorld, 0, -1, 0);
        assert targetWorld != null;

        int attempts = 0;

        while (location.getY() == -1) {
            if (attempts == 10) break;

            location = (randomizeLocation(location, 10));
            location = end ? returnHighestYLocation(location) : findHighestYInNether(location);

            attempts++;
        }

        if (location.getY() == -1) return false;

        String message = color("&7Where am I? Is this " + (end ? "&0The End" : "&cThe Nether") + "&7?!");

        if (entity instanceof Player) {
            ((Player) entity).sendTitle("", message, 5, 20, 5);
        }

        entity.teleport(location);
        return true;
    }

    public static Location findHighestYInNether(Location location) {
        int startY = 10;
        int maxY = 124;

        int x = location.getBlockX();
        int z = location.getBlockZ();

        World world = location.getWorld();
        assert world != null;

        for (int y = startY; y < maxY; y += 2) {
            Block block = world.getBlockAt(x, y, z);
            Block below = block.getRelative(BlockFace.DOWN);

            if (block.getType() == Material.AIR && below.getType().isSolid()) {
                return new Location(world, x, y, z);
            }
        }

        return new Location(world, x, -1, z);
    }

    private static Location returnHighestYLocation(Location location) {
        World world = location.getWorld();
        assert world != null;

        return new Location(location.getWorld(),
                location.getBlockX(),
                location.getWorld().getHighestBlockYAt(location),
                location.getBlockZ());
    }

}
