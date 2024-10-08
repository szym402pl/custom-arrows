package me.xiaojibazhanshi.customarrows;

import lombok.Getter;
import me.xiaojibazhanshi.customarrows.commands.CustomArrowCommand;
import me.xiaojibazhanshi.customarrows.listeners.*;
import me.xiaojibazhanshi.customarrows.managers.ArrowManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class CustomArrows extends JavaPlugin {

    /*                                OVERVIEW                               */

    /*  This plugin just adds as many custom arrows as I can possibly think of

        Upon entering a command, a GUI with all available arrows will open.

        When you click on a certain arrow, another GUI will open;
        that GUI will have incremental elements to let you choose
        how many arrows you want to grab for further usage                   */

    @Getter
    private static CustomArrows instance;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        instance = this;

        ArrowManager arrowManager = new ArrowManager();

        Bukkit.getPluginManager().registerEvents(new ArrowHitEntityListener(arrowManager), this);
        Bukkit.getPluginManager().registerEvents(new ArrowHitBlockListener(arrowManager), this);
        Bukkit.getPluginManager().registerEvents(new ArrowFireListener(arrowManager), this);
        Bukkit.getPluginManager().registerEvents(new PlayerLeaveListener(arrowManager), this);
        Bukkit.getPluginManager().registerEvents(new PlayerChatListener(arrowManager), this);

        getCommand("customarrows").setExecutor(new CustomArrowCommand(arrowManager));
    }
}
