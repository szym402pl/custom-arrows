package me.xiaojibazhanshi.customarrows.guis;

import dev.triumphteam.gui.guis.Gui;
import me.xiaojibazhanshi.customarrows.util.Util;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public class ArrowSelectionGui extends GuiHelper {

    public void openGui(Player player) {
        Gui gui = Gui.gui()
                .title(Component.text(Util.color("&aChoose a custom arrow")))
                .rows(4)
                .create();


    }

}
