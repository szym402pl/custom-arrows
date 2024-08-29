package me.xiaojibazhanshi.customarrows.util.arrows;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class Fishing {

    private Fishing() {

    }

    public enum FishingCategories {
        FISH(0, 8),
        TREASURE(9, 19),
        JUNK(20, 30)
        ;

        private final int minRange;
        private final int maxRange;

        FishingCategories(int minRange, int maxRange) {
            this.minRange = minRange;
            this.maxRange = maxRange;
        }

        public int minRange() {
            return minRange;
        }

        public int maxRange() {
            return maxRange;
        }
    }

    /**Fish category -> indexes: 0-8
     * Treasure category -> indexes: 9-19
     * Junk category -> indexes: 20- */
    public static Map<ItemStack, Double> getFishingLootTable() {
        Map<ItemStack, Double> lootTable = new HashMap<>();

        // Fish category
        lootTable.put(new ItemStack(Material.COD), 0.4);
        lootTable.put(new ItemStack(Material.SALMON), 0.25);
        lootTable.put(new ItemStack(Material.TROPICAL_FISH), 0.2);
        lootTable.put(new ItemStack(Material.PUFFERFISH), 0.3);
        lootTable.put(new ItemStack(Material.COOKED_COD), 0.15);
        lootTable.put(new ItemStack(Material.COOKED_SALMON), 0.15);
        lootTable.put(new ItemStack(Material.GLISTERING_MELON_SLICE), 0.13);
        lootTable.put(new ItemStack(Material.NAUTILUS_SHELL), 0.12);
        lootTable.put(new ItemStack(Material.PRISMARINE_CRYSTALS), 0.15);

        // Treasure category
        lootTable.put(new ItemStack(Material.ENCHANTED_BOOK), 0.02);
        lootTable.put(new ItemStack(Material.NAME_TAG), 0.02);
        lootTable.put(new ItemStack(Material.SADDLE), 0.02);
        lootTable.put(new ItemStack(Material.BOW), 0.02);
        lootTable.put(new ItemStack(Material.FISHING_ROD), 0.02);
        lootTable.put(new ItemStack(Material.EMERALD), 0.03);
        lootTable.put(new ItemStack(Material.DIAMOND), 0.01);
        lootTable.put(new ItemStack(Material.GOLDEN_APPLE), 0.05);
        lootTable.put(new ItemStack(Material.HEART_OF_THE_SEA), 0.005);
        lootTable.put(new ItemStack(Material.TOTEM_OF_UNDYING), 0.003);
        lootTable.put(new ItemStack(Material.NETHER_STAR), 0.001);

        // Junk category
        lootTable.put(new ItemStack(Material.LEATHER_BOOTS), 0.05);
        lootTable.put(new ItemStack(Material.ROTTEN_FLESH), 0.05);
        lootTable.put(new ItemStack(Material.STRING), 0.1);
        lootTable.put(new ItemStack(Material.BOWL), 0.05);
        lootTable.put(new ItemStack(Material.BONE), 0.1);
        lootTable.put(new ItemStack(Material.LILY_PAD), 0.05);
        lootTable.put(new ItemStack(Material.TRIPWIRE_HOOK), 0.02);
        lootTable.put(new ItemStack(Material.INK_SAC), 0.05);
        lootTable.put(new ItemStack(Material.SPIDER_EYE), 0.05);
        lootTable.put(new ItemStack(Material.GLASS_BOTTLE), 0.05);
        lootTable.put(new ItemStack(Material.POISONOUS_POTATO), 0.03);

        return lootTable;
    }

    public static boolean isItYourLuckyDay(double chance) {
        ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
        double random = threadLocalRandom.nextDouble(0, 1);

        return random < chance;
    }

    public static ItemStack randomFishingTableItem() {
        ItemStack item;


    }



}
