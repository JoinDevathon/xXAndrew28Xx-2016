package me.andrew28.machines.machines;

import org.bukkit.block.Block;

/**
 * @author Andrew Tran
 */
public interface TickableMachine extends Machine{
    int tickInterval();
    void tick(Block block);
}
