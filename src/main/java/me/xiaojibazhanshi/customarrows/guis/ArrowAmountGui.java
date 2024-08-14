package me.xiaojibazhanshi.customarrows.guis;

import dev.triumphteam.gui.guis.Gui;
import me.xiaojibazhanshi.customarrows.util.Util;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public class ArrowAmountGui extends GuiHelper {

    public void openGui(Player player) {
        Gui gui = Gui.gui()
                .title(Component.text(Util.color("&aChoose the desired amount")))
                .rows(3)
                .create();



    }


}
