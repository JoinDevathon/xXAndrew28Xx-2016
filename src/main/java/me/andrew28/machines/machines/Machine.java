package me.andrew28.machines.machines;

import me.andrew28.machines.recipes.ShapedRecipe;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

/**
 * @author Andrew Tran
 */
public interface Machine {
    String getCodeName();
    String getDisplayName();
    ItemStack getBaseItem();
    ShapedRecipe getRecipe();
    void interact(Action action, Player player, Block block);
    void place(Player player, Block block);
    void destroy(Player player, Block block);
    void wrench(BlockFace side, Player player, Boolean shifting, Block block);
    Listener getListener();
}
