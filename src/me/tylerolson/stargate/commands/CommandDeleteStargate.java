package me.tylerolson.stargate.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.tylerolson.stargate.Main;

public class CommandDeleteStargate implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (args.length == 1) {
				String stargate = args[0];
				if (Main.stargateManager.doesStargateExist(stargate)) {
					Main.stargateManager.removeStargate(Main.stargateManager.getStargateByName(args[0]));
					player.sendMessage("Stargate '" + stargate + "' has been removed.");
					return true;
				} else {
					player.sendMessage("The Stargate '" + stargate + "' does not exist");
					return true;
				}
			}
		}
		return false;
	}

}
