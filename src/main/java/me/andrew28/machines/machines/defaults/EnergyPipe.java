package me.andrew28.machines.machines.defaults;

import me.andrew28.machines.core.EnergyManager;
import me.andrew28.machines.machines.PowerOutputtingMachine;
import me.andrew28.machines.machines.PoweredMachine;
import me.andrew28.machines.machines.TickableMachine;
import me.andrew28.machines.recipes.ShapedRecipe;
import me.andrew28.machines.util.ItemStackUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

/**
 * @author Andrew Tran
 */
public class EnergyPipe implements PowerOutputtingMachine, PoweredMachine, TickableMachine {
    HashMap<Block, Integer> energy = new HashMap<>();
    @Override
    public void onEnergyChange(Integer level, Block block) {
        energy.put(block, level);
    }

    @Override
    public String getCodeName() {
        return "ENERGY_PIPE";
    }

    @Override
    public String getDisplayName() {
        return "Energy Pipe";
    }

    @Override
    public ItemStack getBaseItem() {
        return new ItemStackUtils().glow(new ItemStack(Material.GLASS));
    }

    @Override
    public ShapedRecipe getRecipe() {
        ShapedRecipe shapedRecipe = new ShapedRecipe("XOX","OXO","XOX");
        shapedRecipe.setItem('X', new ItemStack(Material.REDSTONE));
        shapedRecipe.setItem('O', new ItemStack(Material.GLASS));
        return shapedRecipe;
    }

    @Override
    public void interact(Action action, Player player, Block block) {
        player.sendMessage("ENERGY: " + (energy.containsKey(block) ? energy.get(block) : "0"));
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
        if (energy.containsKey(block)){
            List<Block> canPower = EnergyManager.getTouchingCanPowered(block);
            if(canPower.size() == 0){
                return;
            }
            Integer powerLevel = energy.get(block) / canPower.size();
            for (Block b : canPower){
                EnergyManager.addEnergy(b, powerLevel);
            }
            energy.put(block, 0);
            EnergyManager.setEnergy(block, 0);
        }
    }
}
