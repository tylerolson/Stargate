package me.tylerolson.stargate.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.tylerolson.stargate.Main;
import me.tylerolson.stargate.Stargate;

public class CommandStargates implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (Main.stargateManager.getStargates().size() > 0) {
			String gates = "";
			for (Stargate stargate : Main.stargateManager.getStargates()) {
				gates += stargate.getName() + ", ";
			}
			Main.sendMessageWithPrefix(sender, gates.substring(0, gates.length() - 2));
		} else {
			Main.sendMessageWithPrefix(sender, "There are currently no Stargates created.");
		}
		return true;
	}

}
