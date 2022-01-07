package com.github.crafttoegether.simplemoderation.commands;

import com.github.crafttoegether.simplemoderation.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class Mod implements CommandExecutor {
    /**
     * Executes the given command, returning its success.
     * <br>
     * If false is returned, then the "usage" plugin.yml entry for this command
     * (if defined) will be sent to the player.
     *
     * @param sender  Source of the command
     * @param command Command which was executed
     * @param label   Alias of the command which was used
     * @param args    Passed command arguments
     * @return true if a valid command, otherwise false
     */
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        final Player player = (Player) sender;
        if (sender.isOp()) {
            try {
                if (args.length == 0) {
                    player.sendMessage(ChatColor.RED + "You didn't provide any player...");
                    return true;
                }
                final UUID target;
                try {
                    target = Bukkit.getPlayer(args[0]).getUniqueId();
                } catch (NullPointerException e) {
                    player.sendMessage(ChatColor.RED + "This player doesn't exist!");
                    return true;
                }

                final FileConfiguration config = Plugin.INSTANCE.getConfig();
                final List<String> mods = config.getStringList("mods");
                if (mods.contains(target.toString())) {
                    mods.remove(target.toString());
                    config.set("mods", mods);
                    player.sendMessage(ChatColor.GREEN + "Revoked " + args[0] + " his moderator permissions!");
                } else {
                    mods.add(target.toString());
                    config.set("mods", mods);
                    player.sendMessage(ChatColor.GREEN + "Made " + args[0] + " a moderator! Keep the server safe...");
                }
                Plugin.INSTANCE.saveConfig();

            } catch (NullPointerException e) {
                player.sendMessage(ChatColor.RED + args[0] + " is not a player!");
                return true;
            }
        } else {
            player.sendMessage(ChatColor.RED + "You don't have permissions to do that!");
        }
        return true;
    }
}
