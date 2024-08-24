package me.xiaojibazhanshi.customarrows.util.arrows;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.Random;

public class Molotov {

    private Molotov() {

    }

    public static void setFiresAround(Block centerBlock, int radius) {
        World world = centerBlock.getWorld();

        int centerX = centerBlock.getX();
        int centerZ = centerBlock.getZ();

        for (int x = centerX - radius; x <= centerX + radius; x++) {

            for (int z = centerZ - radius; z <= centerZ + radius; z++) {

                Block highestBlock = world.getHighestBlockAt(x, z);

                // this makes sure it won't just fire up something 50 blocks above/below
                if (highestBlock.getY() > centerBlock.getY() + 3) continue;
                if (highestBlock.getY() < centerBlock.getY() - 3) continue;

                Block blockAbove = highestBlock.getRelative(0, 1, 0);

                int randomNumber = new Random().nextInt(3) + 1;

                if (blockAbove.getType() != Material.WATER && blockAbove.getType() != Material.LAVA
                        && randomNumber != 3) {
                    blockAbove.setType(Material.FIRE);
                }
            }

        }
    }

}
