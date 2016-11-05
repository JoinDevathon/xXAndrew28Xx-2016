package me.andrew28.machines.util;

import org.bukkit.ChatColor;

/**
 * @author Andrew Tran
 */
public class ColorUtility {
    public static String[] color(String... strings){
        int i = 0;
        for (String s : strings){
            strings[i] = color(s);
            i++;
        }
        return strings;
    }
    public static String color(String s){
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
