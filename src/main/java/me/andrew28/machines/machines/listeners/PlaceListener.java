package me.andrew28.machines.machines.listeners;

import me.andrew28.machines.MachineManager;
import me.andrew28.machines.machines.Machine;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * @author Andrew Tran
 */
public class PlaceListener implements Listener{
    @EventHandler
    public void onPlace(BlockPlaceEvent e){
        Player p = e.getPlayer();
        ItemStack is = e.getItemInHand();
        if (is == null){
            return;
        }
        ItemMeta im = is.getItemMeta();
        if (im.hasDisplayName()){
            String display = im.getDisplayName();
            for (Machine machine : MachineManager.getAllMachines()){
                if (machine.getDisplayName().equals(display) && machine.getBaseItem().getType() == is.getType()){
                    p.sendMessage("You have placed a " + machine.getDisplayName());
                    MachineManager.setBlockMachine(e.getBlock(), machine);
                }
            }
        }
    }
}
