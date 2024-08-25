package me.xiaojibazhanshi.customarrows.util.arrows;

import me.xiaojibazhanshi.customarrows.CustomArrows;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Transformation;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import javax.annotation.Nullable;

public class Growth {

    private Growth() {

    }

    public static void temporarilyConvertToBiggerBlock(Block block, int delay, double sizeMultiplier) {
        BlockData originalBlockData = block.getBlockData();
        Location blockLocation = block.getLocation();
        assert blockLocation.getWorld() != null;

        block.setType(Material.AIR);

        BlockDisplay blockDisplay = blockLocation.getWorld().spawn(blockLocation, BlockDisplay.class, (display) -> {
            display.setBlock(originalBlockData);
            display.setInvulnerable(true);
            display.setGravity(false);

            Transformation transformation = new Transformation(
                    new Vector3f(0, 0, 0),
                    new Quaternionf(),
                    new Vector3f((float) sizeMultiplier, (float) sizeMultiplier, (float) sizeMultiplier),
                    new Quaternionf()
            );

            display.setTransformation(transformation);
        });

        new BukkitRunnable() {
            @Override
            public void run() {
                blockDisplay.remove();
                block.setBlockData(originalBlockData);
            }
        }.runTaskLater(CustomArrows.getInstance(), delay);
    }

}
