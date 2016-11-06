  Wow, hi judges! Anyways to start you can go in creative and get yourself a **crafting bench** and inside it surround **a furnace** with **8 redstone**. Now place it down on the floor and put a **chest** on top! Now put **coal** in to the chest. It won't generate power yet because it doesn't have a buffer. Once you place down a **power bank** though, it will start making energy. Make a **power bank** by crafting **8 emeralds** around **a redstone block** and place it right next to the **steam generator**. The **steam generator** will now pump energy in to the **power bank**. If you put **two or more powerbanks** it will split the power evenly. You can craft an **energy pipe** by making an **X with glass blocks** in a **crafting bench** and filling the other **4 slots with redstone**. Break the **powered furnace** and move it further away then place pipes connecting the **power bank** and the **powered furnace** together. If you restart everything will still work because all of this is **saved directly** in to the world file in **machines.dat** (isn't actually a data file, its YML. Just a .dat so people won't mess with it). So you can send the world to other friends, and if they don't have the plugin; it will not do anything to prevent the word from loading. If they do though, the machines will still work. A good alternative to NBT.

  The **API** allows for any plugin to register machines easily. Machines can be powered or even have an action ran on them **every x ticks**, so stuff like energy can be done easily. Talking about energy, blocks can be registered to **receive/give** energy. Machines can change their **recipe, action on interact with left/right click in any way, placement, removement, and custom listener**. Most of this plugin isn't demonstrated in game, so I'll highlight some code, after I finish (I might not if I don't have time). APIs include a Command/SubCommand API for /machines, EnergyManager (for energy transfers), WorldManager (for saving custom stuff for your machines), huge MachineAPI which has tons of stuff for machines, RecipeManager with ShapedRecipes, ColorUtility, ItemStackUtilities, and a SerializationUtility.

Command/SubCommand API classes:
https://github.com/JoinDevathon/xXAndrew28Xx-2016/blob/master/src/main/java/me/andrew28/machines/commands/CommandManager.java
https://github.com/JoinDevathon/xXAndrew28Xx-2016/blob/master/src/main/java/me/andrew28/machines/commands/DefaultExecutor.java
https://github.com/JoinDevathon/xXAndrew28Xx-2016/blob/master/src/main/java/me/andrew28/machines/commands/SubCommand.java

#EnergyManager:
https://github.com/JoinDevathon/xXAndrew28Xx-2016/blob/master/src/main/java/me/andrew28/machines/core/EnergyManager.java

#WorldManager:
https://github.com/JoinDevathon/xXAndrew28Xx-2016/blob/master/src/main/java/me/andrew28/machines/core/WorldManager.java
#Machines:
https://github.com/JoinDevathon/xXAndrew28Xx-2016/blob/master/src/main/java/me/andrew28/machines/MachineManager.java
https://github.com/JoinDevathon/xXAndrew28Xx-2016/blob/master/src/main/java/me/andrew28/machines/machines/Machine.java
https://github.com/JoinDevathon/xXAndrew28Xx-2016/blob/master/src/main/java/me/andrew28/machines/machines/PowerOutputtingMachine.java
https://github.com/JoinDevathon/xXAndrew28Xx-2016/blob/master/src/main/java/me/andrew28/machines/machines/PoweredMachine.java
https://github.com/JoinDevathon/xXAndrew28Xx-2016/blob/master/src/main/java/me/andrew28/machines/machines/TickableMachine.java
https://github.com/JoinDevathon/xXAndrew28Xx-2016/blob/master/src/main/java/me/andrew28/machines/machines/listeners/BreakListener.java
https://github.com/JoinDevathon/xXAndrew28Xx-2016/blob/master/src/main/java/me/andrew28/machines/machines/listeners/InteractListener.java
https://github.com/JoinDevathon/xXAndrew28Xx-2016/blob/master/src/main/java/me/andrew28/machines/machines/listeners/PlaceListener.java
Default Machines:
https://github.com/JoinDevathon/xXAndrew28Xx-2016/blob/master/src/main/java/me/andrew28/machines/machines/defaults/EnergyPipe.java
https://github.com/JoinDevathon/xXAndrew28Xx-2016/blob/master/src/main/java/me/andrew28/machines/machines/defaults/PowerBank.java
https://github.com/JoinDevathon/xXAndrew28Xx-2016/blob/master/src/main/java/me/andrew28/machines/machines/defaults/PoweredFurnace.java
https://github.com/JoinDevathon/xXAndrew28Xx-2016/blob/master/src/main/java/me/andrew28/machines/machines/defaults/SteamGenerator.java
#Recipe API:
https://github.com/JoinDevathon/xXAndrew28Xx-2016/blob/master/src/main/java/me/andrew28/machines/recipes/RecipeManager.java
https://github.com/JoinDevathon/xXAndrew28Xx-2016/blob/master/src/main/java/me/andrew28/machines/recipes/Recipe.java
https://github.com/JoinDevathon/xXAndrew28Xx-2016/blob/master/src/main/java/me/andrew28/machines/recipes/ShapedRecipe.java
#ColorUtility:
https://github.com/JoinDevathon/xXAndrew28Xx-2016/blob/master/src/main/java/me/andrew28/machines/util/ColorUtility.java
#ItemStackUtils:
https://github.com/JoinDevathon/xXAndrew28Xx-2016/blob/master/src/main/java/me/andrew28/machines/util/ItemStackUtils.java
#SerializeUtils:
https://github.com/JoinDevathon/xXAndrew28Xx-2016/blob/master/src/main/java/me/andrew28/machines/util/SerializeUtil.java
