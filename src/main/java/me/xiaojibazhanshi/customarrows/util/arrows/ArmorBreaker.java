package me.xiaojibazhanshi.customarrows.util.arrows;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ArmorBreaker {

    private ArmorBreaker() {}

    public static Material getBaseMaterialFromArmor(ItemStack armor) {
        String typeName = armor.getType().name()
                .replace("_HELMET", "_INGOT")
                .replace("_CHESTPLATE", "_INGOT")
                .replace("_LEGGINGS", "_INGOT")
                .replace("_BOOTS", "_INGOT")
                .replace("GOLDEN", "GOLD");

        Material baseMaterial;

        try {
            try {
                baseMaterial = Material.valueOf(typeName);
            } catch (IllegalArgumentException ex) {
                baseMaterial = Material.valueOf(typeName.replace("_INGOT", ""));
            }
        } catch (IllegalArgumentException ex) {
            baseMaterial = Material.LEATHER; // fallback to leather, it'll also work for chainmail ig
        }

        return baseMaterial;
    }

    public static boolean hasEnchants(ItemStack item) {
        return item.getItemMeta() != null && item.getItemMeta().hasEnchants();
    }

}
