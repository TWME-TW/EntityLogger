package dev.twme.entitylogger.command;

import dev.twme.entitylogger.util.ToolUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Main commands for the plugin.
 */
public class MainCommands implements CommandExecutor , TabCompleter {

    /**
     *  Called when a command is executed by a player.
     * @param sender Source of the command
     * @param command Command which was executed
     * @param label Alias of the command which was used
     * @param args Passed command arguments
     * @return true if the command was handled correctly, false otherwise
     */
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            if (!(sender instanceof Player player)) {
                return false;
            }

            if (player.hasPermission("entitylogger.use")) {
                player.getInventory().addItem(ToolUtil.getTool());
                player.sendMessage("You have been given the tool");
                return true;
            }
        } else if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            if (sender.hasPermission("entitylogger.reload")) {
                sender.sendMessage("Reloading plugin");
                //TODO: Reload plugin function
                sender.sendMessage("Plugin reloaded");
                return true;
            }
        } else if (args.length >= 2 && args[0].equalsIgnoreCase("near")) {

        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return List.of();
    }
}
