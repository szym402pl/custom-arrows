package me.xiaojibazhanshi.customarrows.managers;

import lombok.Getter;
import me.xiaojibazhanshi.customarrows.arrows.EnderArrow;
import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.Util;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Arrow;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArrowManager {
    @Getter private final Map<NamespacedKey, CustomArrow> customArrows = new HashMap<>();

    public ArrowManager() {
        registerCustomArrow("ender_arrow", new EnderArrow());
    }

    public void registerCustomArrow(String id, CustomArrow customArrow) {
        customArrows.put(Util.createStringNSKey(id), customArrow);
    }

    public List<ItemStack> getItemStacks() {
        List<ItemStack> items = new ArrayList<>();

        for (CustomArrow customArrow : customArrows.values()) {
            items.add(customArrow.getArrowItem());
        }

        return items;
    }

    public CustomArrow getCustomArrow(Arrow arrow) {
        PersistentDataContainer dataContainer;

        try {
            dataContainer = arrow.getItem().getItemMeta().getPersistentDataContainer();
        } catch (NullPointerException npe) {
            return null;
        }

        for (NamespacedKey key : customArrows.keySet()) {
            if (dataContainer.has(key)) {
                return customArrows.get(key);
            }
        }

        return null;
    }

    public CustomArrow getCustomArrow(ItemStack item) {
        for (CustomArrow arrow : customArrows.values()) {
            if (arrow.getArrowItem().isSimilar(item))
                return arrow;
        }

        return null;
    }
}
