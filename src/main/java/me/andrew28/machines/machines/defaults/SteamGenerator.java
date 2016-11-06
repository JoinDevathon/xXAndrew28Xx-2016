package me.andrew28.machines.machines.defaults;

import me.andrew28.machines.machines.PowerOutputtingMachine;
import me.andrew28.machines.recipes.ShapedRecipe;
import me.andrew28.machines.util.ItemStackUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

/**
 * @author Andrew Tran
 */
public class SteamGenerator implements PowerOutputtingMachine {

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
        return ItemStackUtils.glow(new ItemStack(Material.FURNACE));
    }

    @Override
    public ShapedRecipe getRecipe() {
        ShapedRecipe recipe = new ShapedRecipe("XXX","XYX","XXX");
        recipe.setItem('X', ItemStackUtils.name(new ItemStack(Material.REDSTONE), "ENERGY"));
        recipe.setItem('Y', new ItemStack(Material.FURNACE));
        return recipe;
    }



    @Override
    public void interact(Action action, Player player) {

    }

    @Override
    public void place(Player player, Block block) {

    }

    @Override
    public void destroy(Player player) {

    }

    @Override
    public void wrench(BlockFace side, Player player, Boolean shift) {

    }
}
