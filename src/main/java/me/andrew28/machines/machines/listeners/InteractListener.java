package me.andrew28.machines.machines.listeners;

import me.andrew28.machines.MachineManager;
import me.andrew28.machines.machines.Machine;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * @author Andrew Tran
 */
public class InteractListener implements Listener{
    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        if (e.getAction() == Action.LEFT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_BLOCK){
            Block b = e.getClickedBlock();
            if (b == null){
                return;
            }
            if (MachineManager.getBlockMachine(b) != null){
                Machine machine = MachineManager.getBlockMachine(b);
                machine.interact(e.getAction(), e.getPlayer(), b);
            }
        }
    }
}
