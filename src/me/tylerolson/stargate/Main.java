package me.tylerolson.stargate;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

import me.tylerolson.stargate.bstats.Metrics;
import me.tylerolson.stargate.commands.CommandCreateStargate;

public class Main extends JavaPlugin implements Listener {

	public FileConfiguration config;
	public StargateManager stargateManager;

	@Override
	public void onEnable() {

		config = getConfig();
		stargateManager = new StargateManager(this, ChatColor.AQUA + "Stargate Network");
		getServer().getPluginManager().registerEvents(this, this);
		config.options().copyDefaults(true);
		saveConfig();

		this.getCommand("createstargate").setExecutor(new CommandCreateStargate(this));
		@SuppressWarnings("unused")
		Metrics metrics = new Metrics(this);
		getLogger().info("Enabled bStats!");
	}
	
	@Override
	public void onDisable() {

	}
	
	public boolean findGate(Location dhdLocation) {
		Location dhdLocationZ = new Location(dhdLocation.getWorld(), dhdLocation.getX(), dhdLocation.getY(), dhdLocation.getZ());
		Location dhdLocationZ_ = new Location(dhdLocation.getWorld(), dhdLocation.getX(), dhdLocation.getY(), dhdLocation.getZ());
		dhdLocationZ.setZ(dhdLocationZ.getZ() + 3);
		dhdLocationZ_.setZ(dhdLocationZ_.getZ() - 3);
		for (int i = 0; i < 8; i++) {
			if (stargateManager.isGateStructure(dhdLocationZ) != 0) {
				return true;
			} else if (stargateManager.isGateStructure(dhdLocationZ_) != 0) {
				return true;
			}else {
				dhdLocationZ.setZ(dhdLocationZ.getZ() + 1);
				dhdLocationZ_.setZ(dhdLocationZ_.getZ() - 1);
			}
		}
		return false;
	}

	public int gatePlayer(Player player, String gate) {
		if (!getConfig().contains("Stargate." + gate)) {
			player.sendMessage("The Stargate '" + gate + "' does not exist.");
			return 0;
		}
		stargateManager.updateStargateActive(stargateManager.getStargateByName(gate));
		if (stargateManager.getStargateByName(gate).isActive()) {
			player.playSound(player.getLocation(), Sound.AMBIENT_UNDERWATER_ENTER, 1, 1);
			player.teleport(stargateManager.getStargateByName(gate).getSpawnLocation());
			return 1;
		} else {
			player.sendMessage("The Stargate '" + gate + "' is not active. (possibly destroyed)");
			return 2;
		}

	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		if (getConfig().getConfigurationSection("Stargate") != null) {
			for (String string : getConfig().getConfigurationSection("Stargate").getKeys(false)) {
				Location tempGateLocation = new Location(event.getPlayer().getWorld(), getConfig().getDouble("Stargate." + string + ".x"), getConfig().getDouble("Stargate." + string + ".y"), getConfig().getDouble("Stargate." + string + ".z"));

				Block tempGateBlock = tempGateLocation.getBlock().getRelative(BlockFace.DOWN);
				if (event.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getX() == tempGateBlock.getX()) {
					if (event.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getY() == tempGateBlock.getY()) {
						if (event.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getZ() == tempGateBlock.getZ()) {
							//if (isGateStructure(event.getPlayer().getLocation()) != 0) {
								// teleport
							//}
						}
					}
				}
			}
		}
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (event.getClickedBlock().getType().equals(Material.STONE_BUTTON)) {
				if (event.getClickedBlock().getRelative(BlockFace.DOWN).getType().equals(Material.MOSSY_COBBLESTONE)) {
					if (findGate(event.getClickedBlock().getLocation())) {
						stargateManager.openInventory(event.getPlayer());
					} else {
						event.getPlayer().sendMessage("No Stargate found.");
					}	
				}
			}
		}

	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		
		if (event.getInventory() == null) {
			return;
		}
		if (event.getInventory().getName().equals(stargateManager.getMenuName())) {
			event.setCancelled(true);

			if (event.getCurrentItem() == null || event.getCurrentItem().getType().equals(Material.AIR))
				return;

			String tempGate = event.getCurrentItem().getItemMeta().getDisplayName();
			if (!tempGate.equals("")) {
				tempGate = ChatColor.stripColor(tempGate);

				if (getConfig().contains("Stargate." + tempGate)) {
					gatePlayer(player, tempGate);
				}
			}
		}

	}

}
