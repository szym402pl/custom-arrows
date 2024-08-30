package me.xiaojibazhanshi.customarrows.util.arrows;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import static me.xiaojibazhanshi.customarrows.util.GeneralUtil.color;

public class Fishing {

    private Fishing() {

    }

    public enum FishingCategories {
        FISH(0, 8),
        TREASURE(9, 19),
        JUNK(20, 30);

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

    /**
     * Fish category -> indexes: 0-8
     * Treasure category -> indexes: 9-19
     * Junk category -> indexes: 20-
     */
    public static Map<ItemStack, Double> getFishingLootTable() {
        Map<ItemStack, Double> lootTable = new HashMap<>();

        // Fish category
        lootTable.put(new ItemStack(Material.COD), 0.5);
        lootTable.put(new ItemStack(Material.SALMON), 0.55);
        lootTable.put(new ItemStack(Material.TROPICAL_FISH), 0.6);
        lootTable.put(new ItemStack(Material.PUFFERFISH), 0.4);
        lootTable.put(new ItemStack(Material.COOKED_COD), 0.15);
        lootTable.put(new ItemStack(Material.COOKED_SALMON), 0.15);
        lootTable.put(new ItemStack(Material.GLISTERING_MELON_SLICE), 0.23);
        lootTable.put(new ItemStack(Material.NAUTILUS_SHELL), 0.16);
        lootTable.put(new ItemStack(Material.PRISMARINE_CRYSTALS), 0.17);

        // Treasure category
        lootTable.put(new ItemStack(Material.ENCHANTED_BOOK), 0.05);
        lootTable.put(new ItemStack(Material.NAME_TAG), 0.04);
        lootTable.put(new ItemStack(Material.SADDLE), 0.03);
        lootTable.put(new ItemStack(Material.BOW), 0.04);
        lootTable.put(new ItemStack(Material.FISHING_ROD), 0.02);
        lootTable.put(new ItemStack(Material.EMERALD), 0.03);
        lootTable.put(new ItemStack(Material.DIAMOND), 0.01);
        lootTable.put(new ItemStack(Material.GOLDEN_APPLE), 0.05);
        lootTable.put(new ItemStack(Material.HEART_OF_THE_SEA), 0.005);
        lootTable.put(new ItemStack(Material.TOTEM_OF_UNDYING), 0.003);
        lootTable.put(new ItemStack(Material.NETHER_STAR), 0.001);

        // Junk category
        lootTable.put(new ItemStack(Material.LEATHER_BOOTS), 0.08);
        lootTable.put(new ItemStack(Material.ROTTEN_FLESH), 0.09);
        lootTable.put(new ItemStack(Material.STRING), 0.1);
        lootTable.put(new ItemStack(Material.BOWL), 0.05);
        lootTable.put(new ItemStack(Material.BONE), 0.1);
        lootTable.put(new ItemStack(Material.LILY_PAD), 0.05);
        lootTable.put(new ItemStack(Material.TRIPWIRE_HOOK), 0.06);
        lootTable.put(new ItemStack(Material.INK_SAC), 0.08);
        lootTable.put(new ItemStack(Material.SPIDER_EYE), 0.08);
        lootTable.put(new ItemStack(Material.GLASS_BOTTLE), 0.08);
        lootTable.put(new ItemStack(Material.POISONOUS_POTATO), 0.06);

        return lootTable;
    }

    public static boolean isItYourLuckyDay(double chance) {
        ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
        double random = threadLocalRandom.nextDouble(0, 1);

        return random < chance;
    }

    public static ItemStack randomFishingLootTableItem() {
        Map<ItemStack, Double> lootTable = getFishingLootTable();
        ThreadLocalRandom random = ThreadLocalRandom.current();

        int minFishRange = FishingCategories.FISH.minRange;
        int maxFishRange = FishingCategories.FISH.maxRange;

        int minJunkRange = FishingCategories.JUNK.minRange;
        int maxJunkRange = FishingCategories.JUNK.maxRange;

        int minTreasureRange = FishingCategories.TREASURE.minRange;
        int maxTreasureRange = FishingCategories.TREASURE.maxRange;

        int fishCategoryRandom = random.nextInt(minFishRange, maxFishRange);
        int junkCategoryRandom = random.nextInt(minJunkRange, maxJunkRange);
        int treasureCategoryRandom = random.nextInt(minTreasureRange, maxTreasureRange);

        int currentIndex = 0;

        for (Map.Entry<ItemStack, Double> entry : lootTable.entrySet()) {

            if (currentIndex == fishCategoryRandom
                    && currentIndex >= minFishRange && currentIndex <= maxFishRange) {

                if (isItYourLuckyDay(entry.getValue())) {
                    return entry.getKey();
                }
            } else if (currentIndex == treasureCategoryRandom
                    && currentIndex >= minTreasureRange && currentIndex <= maxTreasureRange) {

                if (isItYourLuckyDay(entry.getValue())) {
                    return entry.getKey();
                }
            } else if (currentIndex == junkCategoryRandom
                    && currentIndex >= FishingCategories.JUNK.minRange
                    && currentIndex <= FishingCategories.JUNK.maxRange) {

                if (isItYourLuckyDay(entry.getValue())) {
                    return entry.getKey();
                }
            }

            currentIndex++;
        }

        return null;
    }

    public static void notifyPlayer(@Nullable ItemStack item, Player player) {
        String message = color("&7Aww, no fish :(");

        if (item != null) {
            String unprocessed = item.getType().toString();
            String name = unprocessed.toLowerCase().replace("_", " ");

            message = color("&7You've fished out a &a" + name + "&7!");
            player.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
        } else {
            player.playSound(player, Sound.ENTITY_WANDERING_TRADER_NO, 1.0F, 1.0F);
        }

        player.sendTitle("", message, 5, 25, 5);
    }

    public static void pushItemTowardsPlayer(Item item, Player player) {
        Location playerLocation = player.getLocation();
        Location itemLocation = item.getLocation();

        Vector direction = playerLocation.toVector().subtract(itemLocation.toVector());
        double distance = direction.length();

        direction.normalize();
        Vector velocity = direction; // .multiply(distance);

        velocity.setY(Math.max(velocity.getY(), 0.1));
        item.setVelocity(velocity);
    }

}
