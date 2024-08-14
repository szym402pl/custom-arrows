package me.xiaojibazhanshi.customarrows.guis;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.GuiItem;
import me.xiaojibazhanshi.customarrows.util.Util;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.stream.Collectors;

public class GuiHelper {

    /* Arrow Selection Gui */

    protected GuiItem getASGFrameFiller() {
        ItemStack filler = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta fillerMeta = filler.getItemMeta();
        assert fillerMeta != null;

        fillerMeta.setDisplayName(Util.color("&7..."));
        fillerMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        filler.setItemMeta(fillerMeta);

        return ItemBuilder.from(filler).asGuiItem(event -> event.setCancelled(true));
    }

    protected GuiItem getNoMoreArrowsFiller() {
        ItemStack filler = new ItemStack(Material.BARRIER);
        ItemMeta fillerMeta = filler.getItemMeta();
        assert fillerMeta != null;

        fillerMeta.setDisplayName(Util.color("&cI'm not an arrow"));
        fillerMeta.setLore(List.of("", Util.color("&7Look elsewhere")));
        fillerMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        filler.setItemMeta(fillerMeta);

        return ItemBuilder.from(filler).asGuiItem(event -> event.setCancelled(true));
    }

    protected GuiItem getGuiCloseButton() {
        ItemStack closeButton = new ItemStack(Material.NETHER_STAR);
        ItemMeta closeButtonMeta = closeButton.getItemMeta();
        assert closeButtonMeta != null;

        closeButtonMeta.setDisplayName(Util.color("&cClose the menu"));
        closeButtonMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        closeButton.setItemMeta(closeButtonMeta);

        return ItemBuilder.from(closeButton).asGuiItem(event -> event.setCancelled(true));
    }

    /* Arrow Amount Gui */

    protected List<GuiItem> getArrowsAsGuiItems(List<ItemStack> arrowList, Player player) {
        return arrowList.stream()
                .map(item -> ItemBuilder.from(item).asGuiItem(event ->  {
                    event.setCancelled(true);
                    ArrowAmountGui.openGui(player, item, 1); // default arrow amount
                }))
                .collect(Collectors.toList());
    }

    protected static GuiItem getAmountSetterButton(int amountToAdd, int currentAmount, ItemStack arrow, Player player) {
        boolean isAmountNegative = amountToAdd < 0;

        ItemStack amountSetter = new ItemStack
                (isAmountNegative ? Material.RED_STAINED_GLASS_PANE : Material.GREEN_STAINED_GLASS_PANE);

        int targetAmount = Math.max(1, Math.min(64, currentAmount + amountToAdd));
        String displayName = Util.color((isAmountNegative ? "&c&l-" : "&a&l-") + amountToAdd);

        ItemMeta aSMeta = amountSetter.getItemMeta();
        assert aSMeta != null;

        aSMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        aSMeta.setDisplayName(displayName);

        amountSetter.setItemMeta(aSMeta);

        return ItemBuilder.from(amountSetter).asGuiItem(event -> {
            event.setCancelled(true);
            ArrowAmountGui.openGui(player, arrow, targetAmount);
        });
    }

    protected static GuiItem getAAGFrameFiller() {
        ItemStack filler = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta fillerMeta = filler.getItemMeta();
        assert fillerMeta != null;

        fillerMeta.setDisplayName(Util.color("&7..."));
        fillerMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        filler.setItemMeta(fillerMeta);

        return ItemBuilder.from(filler).asGuiItem(event -> event.setCancelled(true));
    }

    protected static void handleArrowRedeem(Player player, ItemStack arrow) {
        Inventory playerInventory = player.getInventory();
        String arrowDisplayName;

        try {
            arrowDisplayName = arrow.getItemMeta().getDisplayName();
        } catch (NullPointerException npe) {
            arrowDisplayName = Util.color("&cUknown Arrow");
        }

        player.sendMessage(Util.color
                ("&aYou've received " + arrowDisplayName + " &ax&b" + arrow.getAmount() + "&a!"));

        if (Util.isInventoryFull(playerInventory)) {
            playerInventory.addItem(arrow);
        } else {
            player.getWorld().dropItem(player.getLocation(), arrow);
            player.sendMessage(Util.color("&7&oInventory full, dropping the item(s)..."));
        }
    }

}
