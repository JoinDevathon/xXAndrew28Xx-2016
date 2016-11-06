package me.andrew28.machines;

import me.andrew28.machines.core.WorldManager;
import me.andrew28.machines.machines.Machine;
import me.andrew28.machines.machines.TickableMachine;
import me.andrew28.machines.machines.defaults.EnergyPipe;
import me.andrew28.machines.machines.defaults.PowerBank;
import me.andrew28.machines.machines.defaults.PoweredFurnace;
import me.andrew28.machines.machines.defaults.SteamGenerator;
import me.andrew28.machines.recipes.RecipeManager;
import me.andrew28.machines.util.SerializeUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
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
    private static HashMap<String, TickableMachine> tickableMachines = new HashMap<>();

    private static int tickNumber = 0;
    public static void init(){
        registerMachine("STEAM_GENERATOR", new SteamGenerator());
        registerMachine("POWER_BANK", new PowerBank());
        registerMachine("POWER_FURNACE", new PoweredFurnace());
        registerMachine("ENERGY_PIPE", new EnergyPipe());
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
        Bukkit.getScheduler().runTaskTimer(Machines.getInstance(), () -> {
            tickNumber++;
            for (Map.Entry<Block, Machine> entry : blockMachines.entrySet()){
                Machine machine = entry.getValue();
                if (machine instanceof TickableMachine){
                    try{
                        if (tickNumber % ((TickableMachine) machine).tickInterval() == 0){
                            ((TickableMachine) machine).tick(entry.getKey());
                        }
                    }catch(Exception e){

                        e.printStackTrace();
                    }
                }
            }
        }, 0L, 1L);
    }


    public static void registerMachine(String codeName, Machine machine){
        if (codeNameRegistered(codeName)){
            throw new IllegalArgumentException("Machine " + codeName + " is already registered");
        }
        machines.put(codeName, machine);
        ItemStack is = getItem(machine);
        machine.getRecipe().registerBukkit(is);
        RecipeManager.registerRecipe(is, machine.getRecipe());

        if (machine.getListener() != null){
            Bukkit.getPluginManager().registerEvents(machine.getListener(), Machines.getInstance());
        }

        if (machine instanceof TickableMachine){
            tickableMachines.put(codeName, (TickableMachine) machine);
        }
    }
    public static ItemStack getItem(Machine machine){
        ItemStack is = machine.getBaseItem();
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(machine.getDisplayName());
        is.setItemMeta(im);
        return is;
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
