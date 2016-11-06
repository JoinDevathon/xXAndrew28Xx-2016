package me.andrew28.machines.commands.defaults;

import me.andrew28.machines.MachineManager;
import me.andrew28.machines.commands.SubCommand;
import me.andrew28.machines.machines.Machine;
import me.andrew28.machines.util.ColorUtility;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Set;

/**
 * @author Andrew Tran
 */
public class InfoBlockCommand implements SubCommand {
    @Override
    public String[] getSubArguments() {
        return new String[]{"infoblock", "iblock"};
    }

    @Override
    public String getUsage() {
        return "/infoblock | LOOK AT A BLOCK";
    }

    @Override
    public void handle(CommandSender sender, String matchedCommand, String[] arguments) {
        if (!(sender instanceof Player)){
            return;
        }
        Player player = (Player) sender;
        Block b = player.getTargetBlock((Set<Material>) null, Integer.MAX_VALUE);
        if (b == null){
            player.sendMessage("Please look at a block.");
            return;
        }
        Machine machine = MachineManager.getBlockMachine(b);
        if (machine == null){
            player.sendMessage("The block you are looking is not a machine.");
        }else{
            player.sendMessage(ColorUtility.color("The block you are looking at is a &e" + machine.getDisplayName()));
        }
    }
}
