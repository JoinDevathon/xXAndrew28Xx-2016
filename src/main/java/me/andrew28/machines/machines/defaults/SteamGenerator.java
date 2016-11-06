package me.andrew28.machines.machines.defaults;

import me.andrew28.machines.core.EnergyManager;
import me.andrew28.machines.machines.PowerOutputtingMachine;
import me.andrew28.machines.machines.TickableMachine;
import me.andrew28.machines.recipes.ShapedRecipe;
import me.andrew28.machines.util.ColorUtility;
import me.andrew28.machines.util.ItemStackUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrew Tran
 */
public class SteamGenerator implements PowerOutputtingMachine, TickableMachine {
    public static final int POWER_PER_TICK = 40;
    @Override
    public String getCodeName() {
        return "STEAM_GENERATOR";
    }

    @Override
    public String getDisplayName() {
        return "Steam Generator";
    }

    @Override
    public ItemStack getBaseItem() {
        return ItemStackUtils.glow(new ItemStack(Material.PISTON_BASE));
    }

    @Override
    public ShapedRecipe getRecipe() {
        ShapedRecipe recipe = new ShapedRecipe("XXX","XYX","XXX");
        recipe.setItem('X', new ItemStack(Material.REDSTONE));
        recipe.setItem('Y', new ItemStack(Material.FURNACE));
        return recipe;
    }



    @Override
    public void interact(Action action, Player player, Block block) {
        if (action == Action.RIGHT_CLICK_BLOCK) {
            Inventory inv = Bukkit.createInventory(null, 36, ColorUtility.color("&eSteam Gen: "));
        }
    }

    @Override
    public void place(Player player, Block block) {

    }

    @Override
    public void destroy(Player player, Block block) {

    }

    @Override
    public void wrench(BlockFace side, Player player, Boolean shift, Block block) {

    }

    @Override
    public Listener getListener() {
        return null;
    }

    @Override
    public int tickInterval() {
        return 5;
    }

    @Override
    public void tick(Block block) {
        Block above = block.getRelative(BlockFace.UP);
        ItemStack toRemove = null;
        Inventory inv;
        Integer slotToRemove = null;
        if (above.getType() == Material.CHEST){
            inv = ((Chest) above.getState()).getBlockInventory();
            int i = 0;
            for (ItemStack is : inv.getContents()){
                if (is == null){
                    i++;
                    continue;
                }
                if (ItemStackUtils.isFurnaceFuel(is.getType())){
                    ItemStack clone = is.clone();
                    clone.setAmount(clone.getAmount() - 1);
                    toRemove = clone;
                    slotToRemove = i;
                    break;
                }
                i++;
            }
        }else{
            return;
        }
        if (toRemove == null || slotToRemove == null){
            return;
        }

        List<Block> canPower = EnergyManager.getTouchingCanPowered(block);
        if (canPower.size() == 0){
            return;
        }
        for (Block b : canPower){
            EnergyManager.addEnergy(b, POWER_PER_TICK / canPower.size()); //Split power evenly
        }

        if (toRemove.getAmount() < 1){
            inv.setItem(slotToRemove, null);
        }else{
            inv.setItem(slotToRemove, toRemove);
        }

    }
}
