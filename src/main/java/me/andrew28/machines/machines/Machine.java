package me.andrew28.machines.machines;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

/**
 * @author Andrew Tran
 */
public interface Machine {
    String getCodeName();
    String getDisplayName();
    ItemStack getBaseItem();
    ShapedRecipe getRecipe();


}
