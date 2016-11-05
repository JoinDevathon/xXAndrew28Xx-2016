package me.andrew28.machines;

import me.andrew28.machines.commands.CommandManager;
import me.andrew28.machines.commands.DefaultExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public class Machines extends JavaPlugin {
    private static Machines instance;

    public static Machines getInstance(){
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        System.out.println("A Devathon Project by xXAndrew28Xx -- andrewtran312@gmail.com");

        //INITIALIZATION
        CommandManager.init();
        MachineManager.init();

        //COMMANDS
        getCommand("machines").setExecutor(new DefaultExecutor());
    }

    @Override
    public void onDisable() {
        // put your disable code here
    }
}

