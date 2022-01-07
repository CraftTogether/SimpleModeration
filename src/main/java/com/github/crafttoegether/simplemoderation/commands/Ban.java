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

public class Ban implements CommandExecutor {
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
        final FileConfiguration config = Plugin.INSTANCE.getConfig();
        if (config.getStringList("mods").contains(player.getUniqueId().toString())) {
            if (args.length == 0) {
                player.sendMessage(ChatColor.RED + "You didn't provide any player...");
                return true;
            }
            final Player target;
            try {
                target = Bukkit.getPlayer(args[0]);
            } catch (NullPointerException e) {
                player.sendMessage(ChatColor.RED + "This player doesn't exist!");
                return true;
            }

            final StringBuilder reason = new StringBuilder();
            if (args.length > 1) {
                for (int i = 1; i < args.length; i++) {
                    final String arg = args[i];
                    if (i == 1) reason.append(arg);
                    else reason.append(" ").append(arg);
                }
            } else {
                reason.append("Seems like the moderator was too dumb to tell you a reason... Anyways I'm sure you know why you're banned (:");
            }

            target.banPlayer(reason.toString());
            player.sendMessage(ChatColor.GREEN + "Done! I hope the world is now a better place...");
        } else {
            player.sendMessage(ChatColor.RED + "You don't have permissions to do that!");
        }
        return true;
    }
}
