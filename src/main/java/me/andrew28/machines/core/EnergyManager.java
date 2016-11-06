package me.andrew28.machines.core;

import me.andrew28.machines.MachineManager;
import me.andrew28.machines.machines.Machine;
import me.andrew28.machines.machines.PoweredMachine;
import me.andrew28.machines.util.SerializeUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Andrew Tran
 */
public class EnergyManager {
    private static HashMap<Block, Integer> blocks = new HashMap<>();
    public static void addReceiveEnergyBlock(Block b){
        blocks.put(b, 0);
    }
    public static Boolean canReceiveEnergyBlock(Block b){
        return blocks.containsKey(b);
    }
    public static void removeReceiveEnergyBlock(Block b){
        blocks.remove(b);
    }

    public static void init(){
        for (World world : Bukkit.getWorlds()){
            YamlConfiguration config = WorldManager.getWorldMachineConfiguration(world);
            if (config.contains("energy")){
                ConfigurationSection mainSection = config.getConfigurationSection("energy");
                for (String key : mainSection.getKeys(false)){
                    Integer energyLevel = mainSection.getInt(key);

                    String[] split = key.split(";");
                    Integer x = Integer.valueOf(split[0]);
                    Integer y = Integer.valueOf(split[1]);
                    Integer z = Integer.valueOf(split[2]);
                    String worldName = split[3];
                    Block block = new Location(Bukkit.getWorld(worldName),x,y,z).getBlock();
                    addReceiveEnergyBlock(block);
                    addEnergy(block, energyLevel);
                }
            }
        }
    }

    public static void saveAll(){
        for (World world : Bukkit.getWorlds()){
            YamlConfiguration config = WorldManager.getWorldMachineConfiguration(world);
            if (!config.contains("energy")){
                config.createSection("energy");
            }
            ConfigurationSection mainSection = config.getConfigurationSection("energy");
            for (Map.Entry<Block, Integer> entry : blocks.entrySet()){
                if(!entry.getKey().getWorld().equals(world)){
                    continue;
                }
                String serializedLocation = SerializeUtil.serialize(entry.getKey().getLocation());
                mainSection.set(serializedLocation, entry.getValue());
            }
            WorldManager.saveWorldMachineConfiguration(world, config);
            System.out.println("[Machines] Saved energy values in world " + world.getName());
        }
    }

    public static Integer getEnergy(Block b){
        if (blocks.containsKey(b)){
            return blocks.get(b);
        }
        return null;
    }
    public static void addEnergy(Block b, int amount){
        if(canReceiveEnergyBlock(b)){
            Integer current = blocks.get(b);
            current += amount;
            blocks.put(b, current);
            if (MachineManager.getBlockMachine(b) != null){
                Machine machine = MachineManager.getBlockMachine(b);
                if (machine instanceof PoweredMachine){
                    ((PoweredMachine) machine).onEnergyChange(current, b);
                }
            }
        }
    }
    public static void setEnergy(Block b, int amount){
        if (canReceiveEnergyBlock(b)){
            blocks.put(b, amount);
        }
    }
    public static List<Block> getTouchingCanPowered(Block b){
        ArrayList<Block> canPower = new ArrayList<Block>();
        BlockFace[] bfs = new BlockFace[]{
                BlockFace.NORTH,
                BlockFace.SOUTH,
                BlockFace.WEST,
                BlockFace.EAST,
                BlockFace.UP,
                BlockFace.DOWN
        };
        for (BlockFace bf : bfs){
            if (canReceiveEnergyBlock(b.getRelative(bf))){
                canPower.add(b.getRelative(bf));
            }
        }
        return canPower;
    }
}
