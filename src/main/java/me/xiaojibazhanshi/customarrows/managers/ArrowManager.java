package me.xiaojibazhanshi.customarrows.managers;

import lombok.Getter;
import me.xiaojibazhanshi.customarrows.arrows.*;
import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.GeneralUtil;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Arrow;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArrowManager {
    @Getter
    private final Map<NamespacedKey, CustomArrow> customArrows = new HashMap<>();

    public ArrowManager() {
        registerCustomArrow("ender_arrow", new EnderArrow());
        registerCustomArrow("homing_arrow", new HomingArrow());
        registerCustomArrow("tnt_arrow", new TNTArrow());
        registerCustomArrow("split_arrow", new SplitArrow());
        registerCustomArrow("lightweight_arrow", new LightweightArrow());
        registerCustomArrow("repulsion_arrow", new RepulsionArrow());
        registerCustomArrow("molotov_arrow", new MolotovArrow());
        registerCustomArrow("illumination_arrow", new IlluminationArrow());
        registerCustomArrow("chained_arrow", new ChainedArrow());
        registerCustomArrow("aim_assist_arrow", new AimAssistArrow());
        registerCustomArrow("seeker_arrow", new SeekerArrow());
        registerCustomArrow("necromancer_arrow", new NecromancerArrow());
        registerCustomArrow("flashbang_arrow", new FlashbangArrow());
        registerCustomArrow("thunder_arrow", new ThunderArrow());
        registerCustomArrow("stealth_arrow", new StealthArrow());
    }

    public void registerCustomArrow(String id, CustomArrow customArrow) {
        customArrows.put(GeneralUtil.createStringNSKey(id), customArrow);
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
}
