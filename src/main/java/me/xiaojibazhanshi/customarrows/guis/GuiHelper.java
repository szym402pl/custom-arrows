package me.xiaojibazhanshi.customarrows.guis;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import me.xiaojibazhanshi.customarrows.managers.ArrowManager;
import me.xiaojibazhanshi.customarrows.util.Util;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.stream.Collectors;

public class GuiHelper {

    protected GuiItem getFrameFiller() {
        ItemStack filler = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta fillerMeta = filler.getItemMeta();

        fillerMeta.setDisplayName(Util.color("&7..."));
        fillerMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        filler.setItemMeta(fillerMeta);

        return ItemBuilder.from(filler).asGuiItem(event -> event.setCancelled(true));
    }

    protected GuiItem getNoMoreArrowsFiller() {
        ItemStack filler = new ItemStack(Material.BARRIER);
        ItemMeta fillerMeta = filler.getItemMeta();

        fillerMeta.setDisplayName(Util.color("&cI'm not an arrow"));
        fillerMeta.setLore(List.of("", Util.color("&7Look elsewhere")));
        fillerMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        filler.setItemMeta(fillerMeta);

        return ItemBuilder.from(filler).asGuiItem(event -> event.setCancelled(true));
    }

    protected List<GuiItem> translateIntoGuiItems(List<ItemStack> itemStackList) {
        return itemStackList.stream()
                .map(item -> ItemBuilder.from(item).asGuiItem(event ->  {
                    event.setCancelled(true);

                }))
                .collect(Collectors.toList());
    }

}
