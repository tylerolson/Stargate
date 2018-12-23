package me.tylerolson.stargate;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import me.tylerolson.stargate.path.StargatePath;

public class StargateManager {

	private String menuName;
	private Inventory stargateInventory;

	public StargateManager(String menuName) {
		this.menuName = menuName;

		if (this.getStargates().size() > 0) {
			for (Stargate stargate : this.getStargates()) {
				Main.plugin.getLogger().info("Found Stargate '" + stargate.getName() + "'");
			}
		} else {
			Main.plugin.getLogger().info("No Stargates found");
		}
	}

	public List<Stargate> getStargates() {
		List<Stargate> stargates = new ArrayList<Stargate>();

		Main.plugin.reloadConfig();
		if (Main.plugin.getConfig().getConfigurationSection("Stargate") != null) {
			if (Main.plugin.getConfig().getConfigurationSection("Stargate").getKeys(false) != null) {
				for (String name : Main.plugin.getConfig().getConfigurationSection("Stargate").getKeys(false)) {
					World tempWorld = Main.plugin.getServer().getWorld(Main.plugin.getConfig().getString("Stargate." + name + ".spawn.world"));
					double tempSpawnX = Main.plugin.getConfig().getDouble("Stargate." + name + ".spawn.x");
					double tempSpawnY = Main.plugin.getConfig().getDouble("Stargate." + name + ".spawn.y");
					double tempSpawnZ = Main.plugin.getConfig().getDouble("Stargate." + name + ".spawn.z");
					Location tempSpawnLocation = new Location(tempWorld, tempSpawnX, tempSpawnY, tempSpawnZ);
					boolean tempActive = Main.plugin.getConfig().getBoolean("Stargate." + name + ".isActive");
					stargates.add(new Stargate(name, tempSpawnLocation, tempActive));
				}
			}
		}
		return stargates;
	}

	public Stargate getStargateByName(String name) {
		for (Stargate stargate : this.getStargates()) {
			if (stargate.getName().equals(name)) {
				return stargate;
			}
		}
		return null;
	}

	public void addStargate(Stargate stargate) {
		updateStargateConfig(stargate);
	}

	public void removeStargate(Stargate stargate) {
		Main.plugin.getConfig().set("Stargate." + stargate.getName(), null);
		Main.plugin.saveConfig();
	}

	public boolean doesStargateExist(String stargateName) {
		for (Stargate stargate : this.getStargates()) {
			if (stargate.getName().equals(stargateName)) {
				return true;
			}
		}
		return false;
	}

	public void updateStargateConfig(Stargate stargate) {
		Main.plugin.getConfig().set("Stargate." + stargate.getName() + ".spawn.world", stargate.getSpawnLocation().getWorld().getName());
		Main.plugin.getConfig().set("Stargate." + stargate.getName() + ".spawn.x", stargate.getSpawnLocation().getX());
		Main.plugin.getConfig().set("Stargate." + stargate.getName() + ".spawn.y", stargate.getSpawnLocation().getY());
		Main.plugin.getConfig().set("Stargate." + stargate.getName() + ".spawn.z", stargate.getSpawnLocation().getZ());
		Main.plugin.getConfig().set("Stargate." + stargate.getName() + ".block.world", stargate.getSpawnLocation().getWorld().getName());
		Main.plugin.getConfig().set("Stargate." + stargate.getName() + ".block.x", stargate.getGateBlockLocation().getX());
		Main.plugin.getConfig().set("Stargate." + stargate.getName() + ".block.y", stargate.getGateBlockLocation().getY());
		Main.plugin.getConfig().set("Stargate." + stargate.getName() + ".block.z", stargate.getGateBlockLocation().getZ());
		Main.plugin.getConfig().set("Stargate." + stargate.getName() + ".isActive", stargate.isActive());
		Main.plugin.saveConfig();
	}

	public void updateStargateActive(Stargate stargate) {
		if (StargatePath.foundStargate(stargate.getSpawnLocation())) {
			stargate.setActive(true);
		} else {
			stargate.setActive(false);
		}
		updateStargateConfig(stargate);
	}

	public String getMenuName() {
		return menuName;
	}

	public void openInventory(Player player) {
		this.stargateInventory = Bukkit.createInventory(null, 18, menuName);
		for (Stargate stargate : this.getStargates()) {
			stargateInventory.addItem(stargate.getInvItemStack());
		}
		player.openInventory(stargateInventory);
	}

}
