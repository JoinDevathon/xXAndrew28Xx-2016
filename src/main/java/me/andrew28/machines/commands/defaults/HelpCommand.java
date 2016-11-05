package me.andrew28.machines.commands.defaults;

import me.andrew28.machines.commands.CommandManager;
import me.andrew28.machines.commands.SubCommand;
import me.andrew28.machines.util.ColorUtility;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

/**
 * @author Andrew Tran
 */
public class HelpCommand implements SubCommand{
    @Override
    public String[] getSubArguments() {
        return new String[]{"what", "help", "?"};
    }

    @Override
    public String getUsage() {
        return "/machines help";
    }

    @Override
    public void handle(CommandSender sender, String matchedCommand, String[] arguments) {
        ArrayList<String> help = new ArrayList<>();
        help.add("&aMachines&e by &candrew28");
        for (SubCommand command : CommandManager.getAllCommands()){
            help.add("&a" + command.getUsage());
        }
        sender.sendMessage(ColorUtility.color(help.toArray(new String[help.size()])));
    }
}
