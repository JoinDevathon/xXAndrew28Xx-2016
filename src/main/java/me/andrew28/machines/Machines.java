package me.andrew28.machines;

import me.andrew28.machines.commands.CommandManager;
import me.andrew28.machines.commands.DefaultExecutor;
import me.andrew28.machines.core.EnergyManager;
import me.andrew28.machines.core.WorldManager;
import me.andrew28.machines.machines.defaults.SteamGenerator;
import me.andrew28.machines.machines.listeners.BreakListener;
import me.andrew28.machines.machines.listeners.InteractListener;
import me.andrew28.machines.machines.listeners.PlaceListener;
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
        WorldManager.init();
        CommandManager.init();
        EnergyManager.init();
        MachineManager.init();

        //COMMANDS
        getCommand("machines").setExecutor(new DefaultExecutor());

        //EVENTS
        Bukkit.getPluginManager().registerEvents(new RecipeManager.CustomCrafting(), this);
        Bukkit.getPluginManager().registerEvents(new PlaceListener(), this);
        Bukkit.getPluginManager().registerEvents(new BreakListener(), this);
        Bukkit.getPluginManager().registerEvents(new InteractListener(), this);
    }

    @Override
    public void onDisable() {
        MachineManager.saveAll();
        EnergyManager.saveAll();
    }
}

