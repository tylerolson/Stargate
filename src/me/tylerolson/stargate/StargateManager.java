package me.tylerolson.stargate;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class StargateManager {

	private Main instance;
	private List<Stargate> stargates = new ArrayList<Stargate>();
	private String menuName;
	private Inventory stargateInventory;
	private BlockFace[] gateEW = { BlockFace.DOWN, BlockFace.EAST, BlockFace.UP, BlockFace.UP, BlockFace.UP, BlockFace.WEST, BlockFace.WEST, BlockFace.DOWN, BlockFace.DOWN, BlockFace.DOWN };

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
		if (isGateStructu(stargate.getSpawnLocation()) != 0) {
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

	public char isGateStructu(Location tempLocation) {
		Block tempBlock = tempLocation.getBlock();
		System.out.println(tempBlock.getY());
		for (int i = 0; i < gateEW.length; i++) {
			tempBlock = tempBlock.getRelative(gateEW[i]);
			tempBlock.setType(Material.IRON_BLOCK);
			if (tempBlock.getType().equals(Material.MOSSY_COBBLESTONE)) {
			}
		}
		return 0;
	}

	public char isGateStructure(Location tempLocation) {
		Block blockUnderPlayer = tempLocation.getBlock().getRelative(BlockFace.DOWN);
		Location blockUnderPlayerX1 = blockUnderPlayer.getLocation();
		Location blockUnderPlayerX_1 = blockUnderPlayer.getLocation();
		Location blockUnderPlayerZ1 = blockUnderPlayer.getLocation();
		Location blockUnderPlayerZ_1 = blockUnderPlayer.getLocation();
		blockUnderPlayerX1.setX(blockUnderPlayer.getLocation().getX() - 1);
		blockUnderPlayerX_1.setX(blockUnderPlayer.getLocation().getX() + 1);
		blockUnderPlayerZ1.setZ(blockUnderPlayer.getLocation().getZ() - 1);
		blockUnderPlayerZ_1.setZ(blockUnderPlayer.getLocation().getZ() + 1);

		if (blockUnderPlayer.getType().equals(Material.MOSSY_COBBLESTONE)) {
			if (blockUnderPlayerX1.getBlock().getType().equals(Material.MOSSY_COBBLESTONE) && blockUnderPlayerX_1.getBlock().getType().equals(Material.MOSSY_COBBLESTONE)) {
				Location blockUnderPlayerY1 = blockUnderPlayerX1;
				Location blockUnderPlayerY_1 = blockUnderPlayerX_1;
				blockUnderPlayerY1.setY(blockUnderPlayerY1.getY() + 1);
				blockUnderPlayerY_1.setY(blockUnderPlayerY_1.getY() + 1);
				if (blockUnderPlayerY1.getBlock().getType().equals(Material.MOSSY_COBBLESTONE) && blockUnderPlayerY_1.getBlock().getType().equals(Material.MOSSY_COBBLESTONE)) {
					Location blockUnderPlayerY2 = blockUnderPlayerY1;
					Location blockUnderPlayerY_2 = blockUnderPlayerY_1;
					blockUnderPlayerY2.setY(blockUnderPlayerY2.getY() + 1);
					blockUnderPlayerY_2.setY(blockUnderPlayerY_2.getY() + 1);
					if (blockUnderPlayerY2.getBlock().getType().equals(Material.MOSSY_COBBLESTONE) && blockUnderPlayerY_2.getBlock().getType().equals(Material.MOSSY_COBBLESTONE)) {
						Location blockUnderPlayer2 = blockUnderPlayer.getLocation();
						Location blockUnderPlayerY3 = blockUnderPlayerY2;
						Location blockUnderPlayerY_3 = blockUnderPlayerY_2;
						blockUnderPlayer2.setY(blockUnderPlayer2.getY() + 3);
						blockUnderPlayerY3.setY(blockUnderPlayerY3.getY() + 1);
						blockUnderPlayerY_3.setY(blockUnderPlayerY_3.getY() + 1);
						if (blockUnderPlayerY3.getBlock().getType().equals(Material.MOSSY_COBBLESTONE) && blockUnderPlayerY_3.getBlock().getType().equals(Material.MOSSY_COBBLESTONE) && blockUnderPlayer2.getBlock().getType().equals(Material.MOSSY_COBBLESTONE)) {
							return 'x';
						}
					}
				}
			}
		}
		return 0;
	}

}
