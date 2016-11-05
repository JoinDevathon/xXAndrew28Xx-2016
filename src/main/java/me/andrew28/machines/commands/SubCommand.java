package me.andrew28.machines.commands;

import org.bukkit.command.CommandSender;

/**
 * @author Andrew Tran
 */
public interface SubCommand {
    String[] getSubArguments();
    String getUsage();
    void handle(CommandSender sender, String matchedCommand, String[] arguments);
}
