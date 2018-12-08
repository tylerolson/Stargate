package me.tylerolson.stargate;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class StargateManager {

	private Main instance;
	private List<Stargate> stargates = new ArrayList<Stargate>();
	private String menuName;
	private Inventory stargateInventory;

	public StargateManager(Main instance, String menuName) {
		this.instance = instance;
		this.menuName = menuName;

		if (instance.getConfig().getConfigurationSection("Stargate") != null) {
			if (instance.getConfig().getConfigurationSection("Stargate").getKeys(false) != null) {
				for (String name : instance.getConfig().getConfigurationSection("Stargate").getKeys(false)) {
					World tempWorld = instance.getServer().getWorld(instance.getConfig().getString("Stargate." + name + ".spawn.world"));
					double tempSpawnX = instance.getConfig().getDouble("Stargate." + name + ".spawn.x");
					double tempSpawnY = instance.getConfig().getDouble("Stargate." + name + ".spawn.y");
					double tempSpawnZ = instance.getConfig().getDouble("Stargate." + name + ".spawn.z");
					Location tempSpawnLocation = new Location(tempWorld, tempSpawnX, tempSpawnY, tempSpawnZ);
					boolean tempActive = instance.getConfig().getBoolean("Stargate." + name + ".isActive");
					addStargate(new Stargate(name, tempSpawnLocation, tempActive));
					instance.getLogger().info("Added Stargate '" + name + "'");
				}
			}
		} else {
			instance.getLogger().info("No Stargates found");
		}
	}

	public List<Stargate> getStargates() {
		return stargates;
	}

	public Stargate getStargateByName(String name) {
		for (Stargate stargate : stargates) {
			if (stargate.getName().equals(name)) {
				return stargate;
			}
		}
		return null;
	}

	public void addStargate(Stargate stargate) {
		updateStargateConfig(stargate);
		this.stargates.add(stargate);
	}

	public void removeStargate(Stargate stargate) {
		instance.getConfig().set("Stargate." + stargate.getName(), "");
		this.stargates.remove(stargate);
	}

	public void updateStargateConfig(Stargate stargate) {
		instance.getConfig().set("Stargate." + stargate.getName() + ".spawn.world", stargate.getSpawnLocation().getWorld().getName());
		instance.getConfig().set("Stargate." + stargate.getName() + ".spawn.x", stargate.getSpawnLocation().getX());
		instance.getConfig().set("Stargate." + stargate.getName() + ".spawn.y", stargate.getSpawnLocation().getY());
		instance.getConfig().set("Stargate." + stargate.getName() + ".spawn.z", stargate.getSpawnLocation().getZ());
		instance.getConfig().set("Stargate." + stargate.getName() + ".block.world", stargate.getSpawnLocation().getWorld().getName());
		instance.getConfig().set("Stargate." + stargate.getName() + ".block.x", stargate.getGateBlockLocation().getX());
		instance.getConfig().set("Stargate." + stargate.getName() + ".block.y", stargate.getGateBlockLocation().getY());
		instance.getConfig().set("Stargate." + stargate.getName() + ".block.z", stargate.getGateBlockLocation().getZ());
		instance.getConfig().set("Stargate." + stargate.getName() + ".isActive", stargate.isActive());
		instance.saveConfig();
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
		for (Stargate stargate : stargates) {
			stargateInventory.addItem(stargate.getInvItemStack());
		}
		player.openInventory(stargateInventory);
	}

}
