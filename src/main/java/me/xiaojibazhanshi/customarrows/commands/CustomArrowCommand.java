package me.xiaojibazhanshi.customarrows.commands;

import me.xiaojibazhanshi.customarrows.guis.ArrowSelectionGui;
import me.xiaojibazhanshi.customarrows.managers.ArrowManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CustomArrowCommand implements CommandExecutor {

    private final ArrowManager arrowManager;

    public CustomArrowCommand(ArrowManager arrowManager) {
        this.arrowManager = arrowManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) {
            Bukkit.getLogger().info("Only a player can execute this command!");
            return true;
        }

        if (!player.hasPermission("customarrows.command")) {
            player.sendMessage(ChatColor.RED + "No permission!");
            return true;
        }

        new ArrowSelectionGui(arrowManager).openGui(player);

        return true;
    }
}
