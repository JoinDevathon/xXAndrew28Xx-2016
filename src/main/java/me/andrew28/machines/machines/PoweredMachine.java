package me.andrew28.machines.machines;

/**
 * @author Andrew Tran
 */
public interface PoweredMachine extends Machine{
    void onEnergyChange(Integer level);
}
