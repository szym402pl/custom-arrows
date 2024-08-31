package me.xiaojibazhanshi.customarrows.util.arrows;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

public class OreLocator {

    private OreLocator() {
    }

    /**
     * @return null if no ore was found in given length (max distance)
     */
    public static Block getNearestOre(Location startingLocation, int length) {
        Location currentLocation = startingLocation.clone();
        World world = currentLocation.getWorld();
        Vector direction = currentLocation.getDirection().normalize();

        assert world != null;
        double step = 0.2;

        for (double i = 0; i < length; i += step) {
            currentLocation.add(direction.clone().multiply(step));

            Block currentBlock = currentLocation.getBlock();

            Material material = currentBlock.getType();
            String blockName = material.name();

            if (blockName.contains("ORE")) {
                return currentBlock;
            }
        }

        return null;
    }

    /**
     * @return gray if the specified material doesn't match any ore
     */
    public static Color getColorBasedOffOre(Material material) {

        switch (material) {
            case DIAMOND_ORE:
            case DEEPSLATE_DIAMOND_ORE:
                return Color.AQUA;
            case IRON_ORE:
            case DEEPSLATE_IRON_ORE:
                return Color.SILVER;
            case GOLD_ORE:
            case DEEPSLATE_GOLD_ORE:
            case NETHER_GOLD_ORE:
                return Color.YELLOW;
            case REDSTONE_ORE:
            case DEEPSLATE_REDSTONE_ORE:
                return Color.RED;
            case LAPIS_ORE:
            case DEEPSLATE_LAPIS_ORE:
                return Color.BLUE;
            case EMERALD_ORE:
            case DEEPSLATE_EMERALD_ORE:
                return Color.GREEN;
            case COPPER_ORE:
            case DEEPSLATE_COPPER_ORE:
                return Color.ORANGE;
            case COAL_ORE:
            case DEEPSLATE_COAL_ORE:
                return Color.BLACK;
            case NETHER_QUARTZ_ORE:
                return Color.fromRGB(255, 244, 229);
            default:
                return Color.WHITE;
        }

    }

}
