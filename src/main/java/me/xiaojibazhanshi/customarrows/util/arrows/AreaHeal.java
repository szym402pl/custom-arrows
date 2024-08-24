package me.xiaojibazhanshi.customarrows.util.arrows;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LingeringPotion;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class AreaHeal {

    private AreaHeal() {

    }

    @SuppressWarnings("deprecation") // IDC that LingeringPotion.class is deprecated, there's no other way to do it
    public static void spawnALingeringPotion(Entity thrower) {
        ItemStack potion = new ItemStack(Material.LINGERING_POTION);
        PotionMeta potionMeta = (PotionMeta) potion.getItemMeta();
        assert potionMeta != null;

        potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.REGENERATION, 200, 1), true);
        potion.setItemMeta(potionMeta);

        ThrownPotion thrownPotion = thrower.getWorld().spawn(thrower.getLocation(), LingeringPotion.class);
        thrownPotion.setItem(potion);
    }

}
