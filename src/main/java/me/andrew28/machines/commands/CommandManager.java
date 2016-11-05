package me.andrew28.machines.commands;

import me.andrew28.machines.commands.defaults.HelpCommand;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarFile;

/**
 * @author Andrew Tran
 */
public class CommandManager {
    private static HashMap<String, SubCommand> subCommands = new HashMap<>();

    public static void init() {
        registerSubCommand("help", new HelpCommand());
    }
    public static void registerSubCommand(String subArgument, SubCommand subCommand){
        if (subArgumentRegistered(subArgument)){
            throw new IllegalArgumentException("Subcommand: " + subArgument + " is already registered");
        }
        subCommands.put(subArgument, subCommand);
    }
    public static Boolean subArgumentRegistered(String subArgument){
        return subCommands.containsKey(subArgument);
    }

    public static SubCommand getSubCommand(String subArgument){
        if (!subArgumentRegistered(subArgument)){
            throw new IllegalArgumentException("Subcommand: " + subArgument + " is not registered!");
        }
        return subCommands.get(subArgument);
    }
    public static SubCommand[] getAllCommands(){
        return subCommands.values().toArray(new SubCommand[subCommands.values().size()]);
    }
}
