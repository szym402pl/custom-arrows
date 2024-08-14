package me.xiaojibazhanshi.customarrows.util;

import me.xiaojibazhanshi.customarrows.CustomArrows;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class Util {

    public static String color(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static NamespacedKey createStringNSKey(String key) {
        return new NamespacedKey(CustomArrows.getInstance(), key);
    }

    public static boolean isInventoryFull(Inventory inventory) {
        return inventory.firstEmpty() == -1;
    }

    public static ItemStack setLore(ItemStack item, List<String> newLore) {
        if (!item.hasItemMeta()) return item;

        ItemMeta meta = item.getItemMeta();
        meta.setLore(newLore);

        item.setItemMeta(meta);

        return item;
    }
}
