package me.andrew28.machines;

import me.andrew28.machines.machines.Machine;

import java.util.HashMap;

/**
 * @author Andrew Tran
 */
public class MachineManager {
    private static HashMap<String, Machine> machines = new HashMap<>();

    public static void init(){

    }


    public static void registerMachine(String codeName, Machine machine){
        if (codeNameRegistered(codeName)){
            throw new IllegalArgumentException("Machine " + codeName + " is already registered");
        }
        machines.put(codeName, machine);
    }
    public static Boolean codeNameRegistered(String codeName){
        return machines.containsKey(codeName);
    }
    public static Machine getMachine(String codeName){
        if (!codeNameRegistered(codeName)){
            throw new IllegalArgumentException("Machine " + codeName + " is not registered!");
        }
        return machines.get(codeName);
    }
    public static Machine[] getAllMachines(){
        return machines.values().toArray(new Machine[machines.values().size()]);
    }
}
