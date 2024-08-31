package me.xiaojibazhanshi.customarrows.util.arrows;

import org.bukkit.Material;
import org.bukkit.block.Block;

public class Smelter {

    private Smelter() {}

    public static Material getSmeltedMaterial(Block block) {
        String newName = block.getType().name()
                .replace("ORE", "INGOT")
                .replace("DEEPSLATE_", "")
                .replace("NETHER_", "");

        Material newMaterial;

        // I made a double try clause, if the stuff can't be smelted into
        // an ingot then it must be an item, but if not, it must be lapis
        try {
            try {
                newMaterial = Material.valueOf(newName);
            } catch (IllegalArgumentException ex) {
                newMaterial = Material.valueOf(newName.replace("_INGOT", ""));
            }
        } catch (IllegalArgumentException ex) {
            newMaterial = Material.LAPIS_LAZULI;
        }

        return newMaterial;
    }

}
