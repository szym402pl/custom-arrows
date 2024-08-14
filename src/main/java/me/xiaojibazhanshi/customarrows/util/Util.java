package me.xiaojibazhanshi.customarrows.util;

import me.xiaojibazhanshi.customarrows.CustomArrows;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Comparator;
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

    public static ItemStack getItemCopyWithNewLore(ItemStack item, List<String> lore) {
        if (!item.hasItemMeta()) return item;

        ItemStack copy = item.clone();
        ItemMeta meta = copy.getItemMeta();

        assert meta != null;
        meta.setLore(lore);
        copy.setItemMeta(meta);

        return copy;
    }

    public static List<ItemStack> sortAlphabeticallyByNames(List<ItemStack> unsortedList) {
        List<ItemStack> sortedList = new ArrayList<>(List.copyOf(unsortedList));

        sortedList.sort(Comparator.comparing(itemStack -> {
            ItemMeta meta = itemStack.getItemMeta();
            return (meta != null && meta.hasDisplayName())
                    ? ChatColor.stripColor(meta.getDisplayName()) : "zzz"; // "zzz" ensures this is the last item
        }));

        return sortedList;
    }

    public static List<String> extractLore(ItemStack item) {
        try {
            return new ArrayList<>(List.copyOf(item.getItemMeta().getLore()));
        } catch (NullPointerException ignored) {
            return new ArrayList<>();
        }
    }
}
