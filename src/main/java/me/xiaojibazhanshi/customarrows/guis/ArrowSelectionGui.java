package me.xiaojibazhanshi.customarrows.guis;

import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import dev.triumphteam.gui.guis.PaginatedGui;
import me.xiaojibazhanshi.customarrows.managers.ArrowManager;
import me.xiaojibazhanshi.customarrows.util.GeneralUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

public class ArrowSelectionGui extends GuiHelper {

    private final ArrowManager arrowManager;

    public ArrowSelectionGui(ArrowManager arrowManager) {
        this.arrowManager = arrowManager;
    }

    final int[] BOTTOM_FRAME_SLOTS = {1, 3, 4, 6, 7, 9};
    final int CLOSE_BUTTON_SLOT = 5;
    final int PREVIOUS_PAGE_SLOT = 2;
    final int NEXT_PAGE_SLOT = 8;

    public void openGui(Player player) {
        PaginatedGui gui = Gui.paginated()
                .title(Component.text(GeneralUtil.color("&aChoose a custom arrow")))
                .rows(4)
                .pageSize(27)
                .create();

        gui.setItem(4, CLOSE_BUTTON_SLOT, getGuiCloseButton(player));

        gui.setItem(4, PREVIOUS_PAGE_SLOT, getCustomButton(Material.PAPER, "&cPrevious", event -> {
            event.setCancelled(true);
            gui.previous();
        }));

        gui.setItem(4, NEXT_PAGE_SLOT, getCustomButton(Material.PAPER, "&cNext", event -> {
            event.setCancelled(true);
            gui.next();
        }));

        for (int slot : BOTTOM_FRAME_SLOTS) {
            gui.setItem(4, slot, getASGFrameFiller());
        }

        List<GuiItem> arrowList = getArrowsAsGuiItems
                (GeneralUtil.sortAlphabeticallyByNames(arrowManager.getItemStacks()), player);

        int itemAmount = 0;

        for (GuiItem arrow : arrowList) {
            itemAmount++;
            gui.addItem(arrow);
        }

        GuiItem filler = getNoMoreArrowsFiller();
        int maxSlot = gui.getPagesNum() * (gui.getRows() - 1) * 9; // Rows -1 since there's 1 frame row

        for (int i = itemAmount; i < maxSlot; i++) {
            gui.addItem(filler);
        }

        gui.open(player);
    }

}
