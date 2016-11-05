package me.andrew28.machines.recipes;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Andrew Tran
 */
public class ShapedRecipe extends Recipe{
    String[] layout;
    HashMap<Character, ItemStack> associations = new HashMap<>();
    public ShapedRecipe(String... layout) {
        this.layout = layout;
    }
    public void setItem(Character c, ItemStack is){
        associations.put(c, is);
    }
    public List<ItemStack> build(){
        List<ItemStack> built = new ArrayList<>();
        for (String row : layout){
            ArrayList<ItemStack> rowItems = new ArrayList<>();
            for (Character c : row.toCharArray()){
                if (!associations.containsKey(c)){
                    rowItems.add(new ItemStack(Material.AIR));
                }
                rowItems.add(associations.get(c));
            }
            built.addAll(rowItems);
        }
        return built;
    }
    public void registerBukkit(ItemStack is){
        org.bukkit.inventory.ShapedRecipe shapedRecipe = new org.bukkit.inventory.ShapedRecipe(is);
        shapedRecipe.shape(layout);
        for (Map.Entry<Character, ItemStack> entry : associations.entrySet()){
            shapedRecipe.setIngredient(entry.getKey(), entry.getValue().getType());
        }
        Bukkit.getServer().addRecipe(shapedRecipe);
    }
}
