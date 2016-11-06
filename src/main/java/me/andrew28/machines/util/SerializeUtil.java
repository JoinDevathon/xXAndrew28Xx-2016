package me.andrew28.machines.util;

import org.bukkit.Location;

/**
 * @author Andrew Tran
 */
public class SerializeUtil {
    public static String serialize(Location loc){
        return loc.getBlockX() + ";" + loc.getBlockY() + ";" + loc.getBlockZ() + ";" + loc.getWorld().getName();
    }
}
