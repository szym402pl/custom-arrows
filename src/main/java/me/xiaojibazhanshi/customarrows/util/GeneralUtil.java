package me.xiaojibazhanshi.customarrows.util;

import me.xiaojibazhanshi.customarrows.CustomArrows;
import org.bukkit.*;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GeneralUtil {

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

    public static void removeArrowAfter(Arrow arrow, long delay) {
        new BukkitRunnable() {
            @Override
            public void run() {
                arrow.remove();
            }
        }.runTaskLater(CustomArrows.getInstance(), delay);
    }

    public static void shootLikeABullet(Entity arrow, double dustOffset) {
        Location location = arrow.getLocation();
        assert location.getWorld() != null;

        location.getWorld().spawnParticle(Particle.DUST,
                location,
                15,
                dustOffset, dustOffset, dustOffset,
                new Particle.DustOptions(Color.GRAY, 1.25F));

        arrow.setVelocity(arrow.getVelocity().multiply(3.5));
    }

    public static void damageWeapon(ItemStack weapon, int damage) {
        if (!(weapon instanceof Damageable)) return;

        Damageable meta = (Damageable) weapon.getItemMeta();
        assert meta != null;

        meta.setDamage(meta.getDamage() - damage);

        weapon.setItemMeta(meta);
    }

    /**
     * @return true if the use was restricted
     */
    public static boolean restrictUseIfWeaponIsNot(EntityShootBowEvent event, Player shooter, Material crossbowOrBow) {
        assert event.getBow() != null;

        if (event.getBow().getType() != crossbowOrBow) {
            shooter.playSound(shooter, Sound.ENTITY_VILLAGER_NO, 1.0F ,1.0F);
            shooter.getInventory().addItem(event.getConsumable());
            event.setCancelled(true);
            return true;
        }

        return false;
    }
}
