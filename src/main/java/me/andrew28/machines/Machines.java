package me.andrew28.machines;

import me.andrew28.machines.commands.CommandManager;
import me.andrew28.machines.commands.DefaultExecutor;
import me.andrew28.machines.core.WorldManager;
import me.andrew28.machines.machines.defaults.SteamGenerator;
import me.andrew28.machines.recipes.RecipeManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

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
        WorldManager.init();

        //COMMANDS
        getCommand("machines").setExecutor(new DefaultExecutor());

        //EVENTS
        Bukkit.getPluginManager().registerEvents(new RecipeManager.CustomCrafting(), this);
    }

    @Override
    public void onDisable() {
        // put your disable code here
    }
}

