package me.tylerolson.stargate.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.tylerolson.stargate.Main;
import me.tylerolson.stargate.Stargate;
import me.tylerolson.stargate.path.StargatePath;

public class CommandCreateStargate implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (args.length == 1) {
				String stargate = args[0];
				if (Main.stargateManager.doesStargateExist(stargate)) {
					Main.sendMessageWithPrefix(player, "The Stargate '" + stargate + "' already exsists.");
					return true;
				} else if (StargatePath.foundStargate(player.getLocation())) {
					Location tempSpawnLocation = new Location(player.getLocation().getWorld(), player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());
					Stargate tempStargate = new Stargate(stargate, tempSpawnLocation, true);
					Main.stargateManager.addStargate(tempStargate);
					Main.sendMessageWithPrefix(player, "Created Stargate '" + args[0] + "'");
					return true;
				} else {
					Main.sendMessageWithPrefix(player, "The Stargate must be made of mossy cobblestone.");
					return true;
				}
			}
		}
		return false;
	}

}
