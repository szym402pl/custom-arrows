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
        registerCustomArrow("honeypot_arrow", new HoneypotArrow());
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
        registerCustomArrow("black_hole_arrow", new BlackHoleArrow());
        registerCustomArrow("area_heal_arrow", new AreaHealArrow());
        registerCustomArrow("dividing_arrow", new DividingArrow());
        registerCustomArrow("hive_arrow", new HiveArrow());
        registerCustomArrow("vampire_arrow", new VampireArrow());
        registerCustomArrow("trap_arrow", new TrapArrow());
        registerCustomArrow("marker_arrow", new MarkerArrow());
        registerCustomArrow("place_swap_arrow", new PlaceSwapArrow());
        registerCustomArrow("anti_gravity_arrow", new AntiGravityArrow());
        registerCustomArrow("frost_walker_arrow", new FrostWalkerArrow());
        registerCustomArrow("dimension_arrow", new DimensionArrow());
        registerCustomArrow("laser_arrow", new LaserArrow());
        registerCustomArrow("vein_miner_arrow", new VeinMinerArrow());
        registerCustomArrow("corruption_arrow", new CorruptionArrow());
        registerCustomArrow("smelter_arrow", new SmelterArrow());
        registerCustomArrow("armor_breaker_arrow", new ArmorBreakerArrow());
        registerCustomArrow("lava_arrow", new LavaArrow());
        registerCustomArrow("explosive_decoy_arrow", new ExplosiveDecoyArrow());
        registerCustomArrow("time_freeze_arrow", new TimeFreezeArrow());
        registerCustomArrow("tunnel_miner_arrow", new TunnelMinerArrow());
        registerCustomArrow("immunity_bubble_arrow", new ImmunityBubbleArrow());
        registerCustomArrow("rider_arrow", new RiderArrow());
        registerCustomArrow("growth_arrow", new GrowthArrow());
        registerCustomArrow("tree_arrow", new TreeArrow());
        registerCustomArrow("mob_aggro_arrow", new MobAggroArrow());
        registerCustomArrow("tornado_arrow", new TornadoArrow());
        registerCustomArrow("turbo_mine_arrow", new TurboMineArrow());
        registerCustomArrow("creeper_arrow", new CreeperArrow());
        registerCustomArrow("trident_arrow", new TridentArrow());
        registerCustomArrow("scanner_arrow", new ScannerArrow());
        registerCustomArrow("attraction_arrow", new AttractionArrow());
        registerCustomArrow("naming_arrow", new NamingArrow());
        registerCustomArrow("betrayal_arrow", new BetrayalArrow());
        registerCustomArrow("slime_army_arrow", new SlimeArmyArrow());
        registerCustomArrow("personal_chest_arrow", new PersonalChestArrow());
        registerCustomArrow("rainbow_arrow", new RainbowArrow());
        registerCustomArrow("piglin_arrow", new PiglinArrow());
        registerCustomArrow("bridge_arrow", new BridgeArrow());
        registerCustomArrow("lava_walker_arrow", new LavaWalkerArrow());
        registerCustomArrow("snow_trap_arrow", new SnowTrapArrow());
        registerCustomArrow("fishing_arrow", new FishingArrow());
        registerCustomArrow("guided_arrow", new GuidedArrow());
        registerCustomArrow("chorus_arrow", new ChorusArrow());
        registerCustomArrow("redstone_arrow", new RedstoneArrow());
        registerCustomArrow("ore_locator_arrow", new OreLocatorArrow());
        registerCustomArrow("tree_miner_arrow", new TreeMinerArrow());
        registerCustomArrow("distraction_arrow", new DistractionArrow());
        registerCustomArrow("signal_arrow", new SignalArrow());
        registerCustomArrow("bone_meal_arrow", new BoneMealArrow());
        registerCustomArrow("taming_arrow", new TamingArrow());
        registerCustomArrow("bouncing_arrow", new BouncingArrow());
        registerCustomArrow("poison_cloud_arrow", new PoisonCloudArrow());
        registerCustomArrow("displacement_arrow", new DisplacementArrow());
        registerCustomArrow("torch_arrow", new TorchArrow());
        registerCustomArrow("rideable_arrow", new RideableArrow());
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
