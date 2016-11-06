package me.andrew28.machines.machines.listeners;

import me.andrew28.machines.MachineManager;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

/**
 * @author Andrew Tran
 */
public class BreakListener implements Listener {
    @EventHandler
    public void onBreak(BlockBreakEvent e){
        Block b = e.getBlock();
        Player p = e.getPlayer();
        if (MachineManager.getBlockMachine(b) != null){
            e.setCancelled(true);
            b.setType(Material.AIR);
            p.getInventory().addItem(MachineManager.getItem(MachineManager.getBlockMachine(b)));
            MachineManager.getBlockMachine(b).destroy(e.getPlayer(), b);
            MachineManager.removeBlockMachine(b);
        }
    }
}
