package me.xiaojibazhanshi.customarrows.guis;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import me.xiaojibazhanshi.customarrows.util.Util;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ArrowAmountGui extends GuiHelper {

    private static final int[] setterSlotsInOrder = {2, 3, 7, 8};
    private static final int[] setterAmountsInOrder = {-16, -1, 1, 16};

    public static void openGui(Player player, ItemStack desiredArrow, int arrowAmount) {
        Gui gui = Gui.gui()
                .title(Component.text(Util.color("&aChoose the desired amount")))
                .rows(3)
                .create();

        List<String> newLore = new ArrayList<>();

        try {
            newLore = new ArrayList<>(List.copyOf(desiredArrow.getItemMeta().getLore()));
        } catch (NullPointerException ignored) {}

        newLore.addAll(getArrowInfo(arrowAmount));

        desiredArrow.setAmount(arrowAmount);
        ItemStack nLDesiredArrow = Util.setLore(desiredArrow, newLore);

        for (int i = 0; i < setterSlotsInOrder.length; i++) {
            gui.setItem(2,
                    setterSlotsInOrder[i],
                    getAmountSetterButton(setterAmountsInOrder[i], arrowAmount, desiredArrow, player));
        }

        GuiItem arrowAsGuiItem = ItemBuilder.from(nLDesiredArrow).asGuiItem(event -> {
            event.setCancelled(true);
            player.closeInventory();

            handleArrowRedeem(player, desiredArrow);
        });

        gui.setItem(2, 5, arrowAsGuiItem);
        gui.getFiller().fill(getAAGFrameFiller());

        gui.open(player);
    }


}
