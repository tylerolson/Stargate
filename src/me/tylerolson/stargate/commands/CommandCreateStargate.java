package me.tylerolson.stargate.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.tylerolson.stargate.Main;
import me.tylerolson.stargate.Stargate;

public class CommandCreateStargate implements CommandExecutor {

	private Main instance;

	public CommandCreateStargate(Main main) {
		this.instance = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (args.length == 1) {
				String stargate = args[0];
				if (instance.getConfig().contains(stargate)) {
					sender.sendMessage("The Stargate '" + stargate + "' already exsists.");
				} else if (instance.stargateManager.isGateStructure(player.getLocation()) != 0) {
					Location tempSpawnLocation = new Location(player.getLocation().getWorld(), player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());
					Stargate tempStargate = new Stargate(stargate, tempSpawnLocation, true);
					instance.stargateManager.addStargate(tempStargate);
					sender.sendMessage("Created Stargate '" + args[0] + "'");
					return true;
				} else {
					sender.sendMessage("The Stargate must be made of mossy cobblestone.");
					return true;
				}
			}
		}
		return false;
	}

}
