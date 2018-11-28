package me.tylerolson.stargate;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Stargate {

	private String name;
	private Location spawnLocation;
	private Block blockLocation;
	private boolean active;
	private ItemStack invItemStack;
	private ItemMeta invItemMeta;

	public Stargate(String name, Location spawnLocation, boolean active) {
		this.setName(name);
		this.setSpawnLocation(spawnLocation);
		this.setActive(active);
		this.setInvItemStack(new ItemStack(Material.MOSSY_COBBLESTONE, 1));
		this.setInvItemMeta(name);
	}

	// Name

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// Spawn location

	public Location getSpawnLocation() {
		return spawnLocation;
	}

	public void setSpawnLocation(Location spawnLocation) {
		this.spawnLocation = spawnLocation;
		this.blockLocation = spawnLocation.getBlock().getRelative(BlockFace.DOWN);
	}

	// Block location

	public Block getGateBlockLocation() {
		return blockLocation;
	}

	// Status

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	// Item stack

	public ItemStack getInvItemStack() {
		return invItemStack;
	}

	public void setInvItemStack(ItemStack invItemStack) {
		this.invItemStack = invItemStack;
	}

	// Item Meta

	public ItemMeta getInvItemMeta() {
		return invItemMeta;
	}

	public void setInvItemMeta(String invItemMetaName) {
		this.invItemMeta = getInvItemStack().getItemMeta();
		this.invItemMeta.setDisplayName(ChatColor.DARK_AQUA + invItemMetaName);
		this.invItemStack.setItemMeta(this.getInvItemMeta());
	}

}
