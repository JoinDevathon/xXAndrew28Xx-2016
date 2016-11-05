package me.andrew28.machines;

import me.andrew28.machines.machines.Machine;
import me.andrew28.machines.machines.defaults.SteamGenerator;
import me.andrew28.machines.recipes.RecipeManager;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;

/**
 * @author Andrew Tran
 */
public class MachineManager {
    private static HashMap<String, Machine> machines = new HashMap<>();
    private static HashMap<Block, Machine> blockMachines = new HashMap<>();

    public static void init(){
        registerMachine("STEAM_GENERATOR", new SteamGenerator());
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
