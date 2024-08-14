package me.xiaojibazhanshi.customarrows.util;

import me.xiaojibazhanshi.customarrows.CustomArrows;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionType;

import java.util.List;
import java.util.stream.Collectors;


public class ArrowFactory {

    public static ItemStack createArrowItemStack(Material material, String name, String id, List<String> lore) {
        ItemStack arrow = new ItemStack(material);

        ItemMeta arrowMeta = arrow.getItemMeta();
        assert arrowMeta != null;

        List<String> finalLore = lore.stream().map(Util::color).toList();

        arrowMeta.setDisplayName(Util.color(name));
        arrowMeta.getPersistentDataContainer()
                .set(new NamespacedKey(CustomArrows.getInstance(), id), PersistentDataType.STRING, id);
        arrowMeta.setLore(finalLore);

        arrow.setItemMeta(arrowMeta);

        return arrow;
    }

    public static ItemStack changeTippedEffect(ItemStack arrow, PotionType effect) {
        if (arrow.getType() != Material.TIPPED_ARROW) return arrow;

        PotionMeta arrowMeta = (PotionMeta) arrow.getItemMeta();
        assert arrowMeta != null;

        arrowMeta.setBasePotionType(effect);
        arrow.setItemMeta(arrowMeta);

        return arrow;
    }

}
