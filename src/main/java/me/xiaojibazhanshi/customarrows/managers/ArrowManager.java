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
        registerCustomArrow("flash_bang_arrow", new FlashBangArrow());
        registerCustomArrow("thunder_arrow", new ThunderArrow());
        registerCustomArrow("adrenaline_arrow", new AdrenalineArrow());
        registerCustomArrow("invisible_arrow", new InvisibleArrow());
        registerCustomArrow("honey_trap_arrow", new HoneyPotArrow());
        registerCustomArrow("fifty_cal_arrow", new FiftyCalArrow());
        registerCustomArrow("ghost_arrow", new GhostArrow());
        registerCustomArrow("smoke_arrow", new SmokeArrow());
        registerCustomArrow("magnet_arrow", new MagnetArrow());
        registerCustomArrow("inversion_arrow", new InversionArrow());
        registerCustomArrow("hedgehog_arrow", new HedgehogArrow());
        registerCustomArrow("weather_arrow", new WeatherArrow());
        registerCustomArrow("day_cycle_arrow", new DayCycleArrow());
        registerCustomArrow("meteor_arrow", new MeteorArrow());
        registerCustomArrow("crystal_heal_arrow", new CrystalHealArrow());
        registerCustomArrow("grappling_hook_arrow", new GrapplingHookArrow());
        registerCustomArrow("helicopter_arrow", new HelicopterArrow());
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
