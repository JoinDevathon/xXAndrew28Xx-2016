package me.andrew28.machines.recipes;

import me.andrew28.machines.util.ColorUtility;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

/**
 * @author Andrew Tran
 */
public class RecipeManager {
    private static HashMap<ItemStack, Recipe> recipes = new HashMap<>();
    public static void registerRecipe(ItemStack result, Recipe recipe){
        recipes.put(result, recipe);
    }
    public static Set<Map.Entry<ItemStack, Recipe>> getAllRecipes(){
        return recipes.entrySet();
    }
    public static class CustomCrafting implements Listener {
        /*@EventHandler
        public void onInventoryClick(InventoryClickEvent e){
            Inventory clicked = e.getClickedInventory();
            if (clicked == null){
                return;
            }
            //System.out.println(clicked.getType() + " " + e.getSlot());
            HumanEntity p = e.getWhoClicked();
            if (clicked.getType() == InventoryType.WORKBENCH){
                Integer slot = e.getSlot();
                if (slot != 0){
                    outer:
                    for (Map.Entry<ItemStack, Recipe> entry : getAllRecipes()){
                        System.out.println("HAI 1");
                        ItemStack result = entry.getKey();
                        Recipe recipe = entry.getValue();
                        if (recipe instanceof ShapedRecipe){
                            System.out.println("Hai2");
                            ShapedRecipe shapedRecipe = (ShapedRecipe) recipe;
                            List<ItemStack> recipeList = shapedRecipe.build();
                            for (int i = 1; i <= 9; i++){
                                ItemStack recipeItem = recipeList.get(i - 1);
                                if (recipeItem == null){
                                    recipeItem = new ItemStack(Material.AIR);
                                }
                                ItemStack craftingItem;
                                if (e.getSlot() == i){
                                    System.out.println("LAST, so i replacety withty " + e.getCursor().getType().name());
                                    craftingItem = e.getCursor();
                                }else{
                                    craftingItem = clicked.getItem(i);
                                }
                                if (craftingItem == null){
                                    craftingItem = new ItemStack(Material.AIR);
                                }

                                // recipeItem is in the recipe
                                // craftingItem is in the crafting table

                                //TYPE TEST
                                if (recipeItem.getType() != craftingItem.getType()){
                                    System.out.println("FAIL 1 on slot " + i + " because the recipe requires "
                                    + recipeItem.getType().name() + " but you gave it a " + craftingItem.getType().name());
                                    break outer;
                                }

                                //DISPLAY NAME TEST
                                ItemMeta recipeItemMeta = recipeItem.getItemMeta();
                                ItemMeta craftingItemMeta = craftingItem.getItemMeta();
                                if (recipeItemMeta.hasDisplayName() && !craftingItemMeta.hasDisplayName()){
                                    // RECIPE HAS DISPLAY
                                    // CRAFTING DOES NOT HAVE DISPLAY
                                    System.out.println("FAIL 2 on slot " + i);
                                    break outer;
                                }else if (recipeItemMeta.hasDisplayName() && craftingItemMeta.hasDisplayName()){
                                    // BOTH HAVE DISPLAY NAME
                                    if (recipeItemMeta.getDisplayName() != craftingItemMeta.getDisplayName()){
                                        System.out.println("FAIL 3 on slot " + i);
                                        break outer;
                                    }
                                }

                                //EVERYTHING WORKED WOO
                                System.out.println("Nothing failed, so I set it to " + result.getType());
                                clicked.setItem(0, result);
                            }
                        }
                    }
                }else{
                    //They just got an item
                    p.sendMessage(":D");
                }
            }
        }*/
        @EventHandler
        public void onCraft(CraftItemEvent e){
            if (e.getRecipe() instanceof org.bukkit.inventory.ShapedRecipe){
                org.bukkit.inventory.ShapedRecipe bukkitShapedRecipe = (org.bukkit.inventory.ShapedRecipe) e.getRecipe();
                List<ItemStack> craftItems = new ArrayList<>();
                for (String row : bukkitShapedRecipe.getShape()){
                    ArrayList<ItemStack> rowItems = new ArrayList<>();
                    for (Character c : row.toCharArray()){
                        if (!bukkitShapedRecipe.getIngredientMap().containsKey(c)){
                            rowItems.add(new ItemStack(Material.AIR));
                        }
                        rowItems.add(bukkitShapedRecipe.getIngredientMap().get(c));
                    }
                    craftItems.addAll(rowItems);
                }
                Boolean valid = false;
                //
                outer:
                for (Map.Entry<ItemStack, Recipe> entry : getAllRecipes()) {
                    ItemStack result = entry.getKey();
                    Recipe recipe = entry.getValue();
                    if (recipe instanceof ShapedRecipe) {
                        ShapedRecipe shapedRecipe = (ShapedRecipe) recipe;
                        List<ItemStack> recipeItems = shapedRecipe.build();
                        // craftItems is in crafting table
                        // recipeItems is the recipe
                        Boolean same = true;
                        for (int i = 0; i<= 8; i++){
                            if (craftItems.get(i).getType() != recipeItems.get(i).getType()){
                                same = false;
                            }
                        }
                        if (same){
                            for (int i = 1; i <= 9; i++){
                                ItemStack recipeItem = recipeItems.get(i - 1);
                                if (recipeItem == null){
                                    recipeItem = new ItemStack(Material.AIR);
                                }
                                ItemStack craftingItem;
                                craftingItem = craftItems.get(i - 1);
                                if (craftingItem == null){
                                    craftingItem = new ItemStack(Material.AIR);
                                }

                                // recipeItem is in the recipe
                                // craftingItem is in the crafting table

                                //TYPE TEST
                                if (recipeItem.getType() != craftingItem.getType()){
                                    break outer;
                                }

                                //DISPLAY NAME TEST
                                ItemMeta recipeItemMeta = recipeItem.getItemMeta();
                                ItemMeta craftingItemMeta = craftingItem.getItemMeta();
                                if ((recipeItemMeta.getDisplayName() != null) && (craftingItemMeta.getDisplayName() == null)){
                                    // RECIPE HAS DISPLAY
                                    // CRAFTING DOES NOT HAVE DISPLAY
                                    break outer;
                                }else if (recipeItemMeta.hasDisplayName() && craftingItemMeta.hasDisplayName()){
                                    // BOTH HAVE DISPLAY NAME
                                    if (recipeItemMeta.getDisplayName() != craftingItemMeta.getDisplayName()){
                                        break outer;
                                    }
                                }
                                valid = true;

                            }
                        }
                    }
                }
                if (!valid){
                    e.setCancelled(true);
                    e.getWhoClicked().sendMessage(ColorUtility.color("&cOne of the items is special, using an ordinary one won't work!"));
                    e.getInventory().setResult(null);
                }
            }
        }
    }
}
