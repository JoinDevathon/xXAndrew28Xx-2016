package me.andrew28.machines.machines.defaults;

import me.andrew28.machines.MachineManager;
import me.andrew28.machines.core.EnergyManager;
import me.andrew28.machines.machines.PoweredMachine;
import me.andrew28.machines.machines.TickableMachine;
import me.andrew28.machines.recipes.ShapedRecipe;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * @author Andrew Tran
 */
public class PowerBank implements PoweredMachine, TickableMachine {
    HashMap<Block, Integer> energy = new HashMap<>();
    @Override
    public void onEnergyChange(Integer level, Block block) {
        energy.put(block, level);
    }

    @Override
    public String getCodeName() {
        return "POWER_BANK";
    }

    @Override
    public String getDisplayName() {
        return "Infinite Power Bank";
    }

    @Override
    public ItemStack getBaseItem() {
        return new ItemStack(Material.EMERALD_BLOCK);
    }

    @Override
    public ShapedRecipe getRecipe() {
        ShapedRecipe recipe = new ShapedRecipe("XXX", "XYX", "XXX");
        recipe.setItem('X', new ItemStack(Material.EMERALD));
        recipe.setItem('Y', new ItemStack(Material.REDSTONE_BLOCK));
        return recipe;
    }

    @Override
    public void interact(Action action, Player player, Block block) {
        Integer energyLevel = 0;
        if (energy.containsKey(block)){
            energyLevel = energy.get(block);
        }
        player.sendMessage("ENERGY: " + energyLevel);
    }

    @Override
    public void place(Player player, Block block) {

    }

    @Override
    public void destroy(Player player, Block block) {

    }

    @Override
    public void wrench(BlockFace side, Player player, Boolean shifting, Block block) {

    }

    @Override
    public Listener getListener() {
        return null;
    }

    @Override
    public int tickInterval() {
        return 1;
    }

    @Override
    public void tick(Block block) {
        if (block == null){
            return;
        }
        List<Block> canPower = EnergyManager.getTouchingCanPowered(block);
        if (canPower.size() == 0){
            return;
        }
        Iterator<Block> it = canPower.iterator();
        while(it.hasNext()){
            Block b = it.next();
            if (MachineManager.getBlockMachine(b) == null){
                continue;
            }
            if (MachineManager.getBlockMachine(b).getCodeName().equals("POWER_BANK")){
                it.remove();
            }
        }
        if (!energy.containsKey(block)){
            return;
        }
        Integer powerLevel = energy.get(block) / canPower.size();
        for (Block b : canPower){
            EnergyManager.addEnergy(b, powerLevel);
        }
        energy.put(block, 0); //DRAINED
        EnergyManager.setEnergy(block, 0); //DRAINED
    }
}
