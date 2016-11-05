package me.andrew28.machines.commands;

import me.andrew28.machines.commands.defaults.HelpCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

/**
 * @author Andrew Tran
 */
public class DefaultExecutor implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String matched, String[] arguments) {
        if (arguments.length == 0){
            new HelpCommand().handle(commandSender, matched, null);
            return true;
        }
        for (SubCommand subCommand : CommandManager.getAllCommands()){
            for (String subArgument : subCommand.getSubArguments()){
                if (subArgument.equals(arguments[0])){
                    ArrayList<String> argumentsList = new ArrayList<>();
                    for (String s : arguments){
                        argumentsList.add(s);
                    }

                    argumentsList.remove(0); //Only

                    subCommand.handle(commandSender, matched, argumentsList.toArray(new String[argumentsList.size()]));
                    return true;
                }
            }
        }
        return true;
    }
}
