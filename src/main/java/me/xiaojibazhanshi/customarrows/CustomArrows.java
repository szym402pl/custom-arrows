package me.xiaojibazhanshi.customarrows;

import org.bukkit.plugin.java.JavaPlugin;

public final class CustomArrows extends JavaPlugin {

    /*                                OVERVIEW                               */

    /*     This plugin just adds as many custom arrows as I can think of

        Upon entering a command, a GUI with all available arrows will open.

        When you click on a certain arrow, another GUI will open;
        that GUI will have incremental elements to let you choose
        how many arrows you want to grab for further usage                   */


    @Override
    public void onEnable() {
        saveDefaultConfig();

    }

    @Override
    public void onDisable() {

    }
}
