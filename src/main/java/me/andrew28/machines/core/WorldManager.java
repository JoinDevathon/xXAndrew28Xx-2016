package me.andrew28.machines.core;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author Andrew Tran
 */
public class WorldManager {
    private static HashMap<String, File> machineSaveFiles = new HashMap<>();
    public static void init(){
        Bukkit.getWorlds().stream().forEach(world -> {
            try {
                loadWorld(world);
            } catch (IOException e) {
                System.out.println("Could not load world: " + (world == null ? "--NULL--" : world.getName()));
                e.printStackTrace();
            }
        });
    }
    public static void loadWorld(World w) throws IOException {
        File folder = w.getWorldFolder();
        File saveFile = new File(folder, "machines.dat"); //Not actually a .dat file, just to seem fancier ::D
        if(!saveFile.exists()){
            saveFile.createNewFile();
        }
        machineSaveFiles.put(w.getName(), saveFile);
    }
    public static File getWorldMachineFile(World w){
        return machineSaveFiles.get(w.getName());
    }
    public static YamlConfiguration getWorldMachineConfiguration(World w){
        return YamlConfiguration.loadConfiguration(getWorldMachineFile(w));
    }
    public static void saveWorldMachineConfiguration(World w, YamlConfiguration config){
        try {
            config.save(getWorldMachineFile(w));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
