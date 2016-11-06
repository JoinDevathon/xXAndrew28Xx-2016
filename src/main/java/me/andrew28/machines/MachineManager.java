package me.andrew28.machines;

import me.andrew28.machines.core.WorldManager;
import me.andrew28.machines.machines.Machine;
import me.andrew28.machines.machines.defaults.SteamGenerator;
import me.andrew28.machines.recipes.RecipeManager;
import me.andrew28.machines.util.SerializeUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Andrew Tran
 */
public class MachineManager {
    private static HashMap<String, Machine> machines = new HashMap<>();
    private static HashMap<Block, Machine> blockMachines = new HashMap<>();

    public static void init(){
        registerMachine("STEAM_GENERATOR", new SteamGenerator());

        for (World world : Bukkit.getWorlds()){
            YamlConfiguration config = WorldManager.getWorldMachineConfiguration(world);
            if (config.contains("machines")){
                ConfigurationSection mainSection = config.getConfigurationSection("machines");
                for (String key : mainSection.getKeys(false)){
                    String machineCodeName = mainSection.getString(key);

                    String[] split = key.split(";");
                    Integer x = Integer.valueOf(split[0]);
                    Integer y = Integer.valueOf(split[1]);
                    Integer z = Integer.valueOf(split[2]);
                    String worldName = split[3];
                    Block block = new Location(Bukkit.getWorld(worldName),x,y,z).getBlock();
                    setBlockMachine(block, getMachine(machineCodeName));
                }
            }
        }
    }


    public static void registerMachine(String codeName, Machine machine){
        if (codeNameRegistered(codeName)){
            throw new IllegalArgumentException("Machine " + codeName + " is already registered");
        }
        machines.put(codeName, machine);
        ItemStack is = machine.getBaseItem();
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(machine.getDisplayName());
        is.setItemMeta(im);
        machine.getRecipe().registerBukkit(is);
        RecipeManager.registerRecipe(is, machine.getRecipe());
    }
    public static void setBlockMachine(Block b, Machine machine){
        blockMachines.put(b, machine);
    }
    public static void removeBlockMachine(Block b){
        blockMachines.remove(b);
    }
    public static Machine getBlockMachine(Block b){
        if (blockMachines.containsKey(b)){
            return blockMachines.get(b);
        }
        return null;
    }
    public static void saveAll(){
        for (World world : Bukkit.getWorlds()){
            YamlConfiguration config = WorldManager.getWorldMachineConfiguration(world);
            if (!config.contains("machines")){
                config.createSection("machines");
            }
            ConfigurationSection mainSection = config.getConfigurationSection("machines");
            for (Map.Entry<Block, Machine> entry : blockMachines.entrySet()){
                if(!entry.getKey().getWorld().equals(world)){
                    continue;
                }
                String serializedLocation = SerializeUtil.serialize(entry.getKey().getLocation());
                mainSection.set(serializedLocation, entry.getValue().getCodeName());
            }
            WorldManager.saveWorldMachineConfiguration(world, config);
            System.out.println("[Machines] Saved machines in world " + world.getName());
        }
    }
    public static Boolean codeNameRegistered(String codeName){
        return machines.containsKey(codeName);
    }
    public static Machine getMachine(String codeName){
        if (!codeNameRegistered(codeName)){
            throw new IllegalArgumentException("Machine " + codeName + " is not registered!");
        }
        return machines.get(codeName);
    }
    public static Machine[] getAllMachines(){
        return machines.values().toArray(new Machine[machines.values().size()]);
    }
}
