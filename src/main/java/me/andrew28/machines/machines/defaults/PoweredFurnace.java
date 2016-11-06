package me.andrew28.machines.machines.defaults;

import me.andrew28.machines.core.EnergyManager;
import me.andrew28.machines.machines.PoweredMachine;
import me.andrew28.machines.machines.TickableMachine;
import me.andrew28.machines.recipes.ShapedRecipe;
import me.andrew28.machines.util.ItemStackUtils;
import net.minecraft.server.v1_10_R1.*;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_10_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_10_R1.block.CraftBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

/**
 * @author Andrew Tran
 */
public class PoweredFurnace implements PoweredMachine, TickableMachine {
    HashMap<Block, Integer> energy = new HashMap<>();
    @Override
    public void onEnergyChange(Integer level, Block block) {
        energy.put(block, level);
    }

    @Override
    public String getCodeName() {
        return "POWER_FURNACE";
    }

    @Override
    public String getDisplayName() {
        return "Powered Furnace";
    }

    @Override
    public ItemStack getBaseItem() {
        return ItemStackUtils.glow(new ItemStack(Material.FURNACE));
    }

    @Override
    public ShapedRecipe getRecipe() {
        ShapedRecipe shapedRecipe = new ShapedRecipe("XXX","XYX","XXX");
        shapedRecipe.setItem('X', new ItemStack(Material.IRON_INGOT));
        shapedRecipe.setItem('Y', new ItemStack(Material.FURNACE));
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
        return 15;
    }

    @Override
    public void tick(Block block) {
        if (energy.containsKey(block) && energy.get(block) > 20){
            energy.put(block, energy.get(block) - 20);
            EnergyManager.addEnergy(block, -20);
            NBTTagCompound NBT = new NBTTagCompound();
            WorldServer nmsWorld = ((CraftWorld) block.getWorld()).getHandle();
            TileEntity tileEntity = nmsWorld.getTileEntity(new BlockPosition(block.getX(), block.getY(), block.getZ()));
            if (tileEntity == null) {
                System.out.println("HAI");
                return;
            }
            tileEntity.save(NBT);
            NBT.set("BurnTime", new NBTTagShort((short)250));
            NBT.set("CookTimeTotal", new NBTTagShort((short)10));
            tileEntity.a(NBT);
            tileEntity.update();
            IBlockData tileEntType = nmsWorld.getType(new BlockPosition(block.getX(), block.getY(), block.getZ()));
            nmsWorld.notify(tileEntity.getPosition(), tileEntType, tileEntType, 3);

        }
    }

}
