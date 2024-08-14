package me.xiaojibazhanshi.customarrows.guis;

import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import me.xiaojibazhanshi.customarrows.managers.ArrowManager;
import me.xiaojibazhanshi.customarrows.util.Util;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.List;

public class ArrowSelectionGui extends GuiHelper {

    private final ArrowManager arrowManager;

    public ArrowSelectionGui(ArrowManager arrowManager) {
        this.arrowManager = arrowManager;
    }

    final int[] BOTTOM_FRAME_SLOTS = {1, 2, 3, 4, 6, 7, 8, 9};
    final int CLOSE_BUTTON_SLOT = 5;

    public void openGui(Player player) {
        Gui gui = Gui.gui()
                .title(Component.text(Util.color("&aChoose a custom arrow")))
                .rows(4)
                .create();

        gui.setItem(1, CLOSE_BUTTON_SLOT, getGuiCloseButton());

        for (int slot : BOTTOM_FRAME_SLOTS) {
            gui.setItem(4, slot, getASGFrameFiller());
        }

        List<GuiItem> arrowList = getArrowsAsGuiItems(arrowManager.getItemStacks(), player);

        for (GuiItem arrow : arrowList) {
            gui.addItem(arrow);
        }

        gui.getFiller().fill(getNoMoreArrowsFiller());

        gui.open(player);
    }

}
