package me.xiaojibazhanshi.customarrows.util.arrows;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.Bisected;
import org.bukkit.event.block.BlockGrowEvent;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class BoneMeal {

    private BoneMeal() {
    }

    private static final int GROWTH_RADIUS = 5;
    private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();

    private static final List<Material> PLANT_TYPES = Arrays.asList(
            Material.TALL_GRASS,
            Material.TALL_GRASS,
            Material.TALL_GRASS,
            Material.TALL_GRASS,
            Material.TALL_GRASS,
            Material.TALL_GRASS, // sorry, it's just to increase likelihood of tall grass being in the mix
            Material.DANDELION,
            Material.POPPY,
            Material.AZURE_BLUET,
            Material.ALLIUM,
            Material.CORNFLOWER,
            Material.OXEYE_DAISY,
            Material.ROSE_BUSH,
            Material.LILAC,
            Material.SUNFLOWER,
            Material.PEONY
    );

    public static boolean applyBonemealEffect(Block block) {
        if (applyAgeableGrowth(block)) {
            return true;
        }

        if (applyGrassBlockGrowth(block)) {
            return true;
        }

        return false;
    }

    private static boolean applyAgeableGrowth(Block block) {
        if (block.getBlockData() instanceof Ageable ageable) {
            if (ageable.getAge() < ageable.getMaximumAge()) {
                ageable.setAge(ageable.getAge() + 1);
                block.setBlockData(ageable);

                triggerBlockGrowEvent(block);
                return true;
            }
        }
        return false;
    }

    private static boolean applyGrassBlockGrowth(Block block) {
        if (block.getType() == Material.GRASS_BLOCK) {
            spawnBonemealParticles(block);
            growSurroundingPlants(block);
            return true;
        }
        return false;
    }

    private static void spawnBonemealParticles(Block block) {
        block.getWorld().spawnParticle(Particle.HAPPY_VILLAGER,
                block.getLocation().clone().add(0.5, 1.5, 0.5),
                10,
                0.5, 0.5, 0.5);
    }

    private static void growSurroundingPlants(Block block) {
        for (int x = -GROWTH_RADIUS; x <= GROWTH_RADIUS; x++) {
            for (int z = -GROWTH_RADIUS; z <= GROWTH_RADIUS; z++) {
                Block relativeBlock = block.getRelative(x, 0, z);
                if (relativeBlock.getType() == Material.GRASS_BLOCK) {
                    growPlantAbove(relativeBlock);
                }
            }
        }
    }

    private static void growPlantAbove(Block block) {
        Block topBlock = block.getRelative(0, 1, 0);
        if (topBlock.getType() == Material.AIR && RANDOM.nextInt(3) == 0) {
            Material growType = getRandomPlantType();
            topBlock.setType(growType);

            if (growType == Material.TALL_GRASS || isTallPlant(growType)) {
                setTallPlantData(topBlock);
            }

            triggerBlockGrowEvent(topBlock);
        }
    }

    private static Material getRandomPlantType() {
        return PLANT_TYPES.get(RANDOM.nextInt(PLANT_TYPES.size()));
    }

    private static boolean isTallPlant(Material material) {
        return material == Material.ROSE_BUSH || material == Material.LILAC ||
                material == Material.SUNFLOWER || material == Material.PEONY;
    }

    private static void setTallPlantData(Block block) {
        if (block.getBlockData() instanceof Bisected grass) {
            grass.setHalf(Bisected.Half.TOP);
            block.setBlockData(grass);
        }
    }

    private static void triggerBlockGrowEvent(Block block) {
        BlockState newState = block.getState();
        BlockGrowEvent growEvent = new BlockGrowEvent(block, newState);
        Bukkit.getPluginManager().callEvent(growEvent);
    }

}
