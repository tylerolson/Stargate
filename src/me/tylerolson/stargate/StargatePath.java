package me.tylerolson.stargate;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

@SuppressWarnings("serial")
public class StargatePath {

	public static final List<StargatePathSegment> pathCircleEW = new ArrayList<StargatePathSegment>() {
		{
			add(new StargatePathSegment(BlockFace.DOWN, false));
			add(new StargatePathSegment(BlockFace.EAST, false));
			add(new StargatePathSegment(BlockFace.EAST, true));
			add(new StargatePathSegment(BlockFace.UP, false));
			add(new StargatePathSegment(BlockFace.EAST, true));
			add(new StargatePathSegment(BlockFace.UP, false));
			add(new StargatePathSegment(BlockFace.UP, false));
			add(new StargatePathSegment(BlockFace.UP, false));
			add(new StargatePathSegment(BlockFace.WEST, true));
			add(new StargatePathSegment(BlockFace.UP, false));
			add(new StargatePathSegment(BlockFace.UP, true));
			add(new StargatePathSegment(BlockFace.WEST, false));
			add(new StargatePathSegment(BlockFace.WEST, false));
			add(new StargatePathSegment(BlockFace.WEST, false));
			add(new StargatePathSegment(BlockFace.WEST, true));
			add(new StargatePathSegment(BlockFace.DOWN, false));
			add(new StargatePathSegment(BlockFace.WEST, true));
			add(new StargatePathSegment(BlockFace.DOWN, false));
			add(new StargatePathSegment(BlockFace.DOWN, false));
			add(new StargatePathSegment(BlockFace.DOWN, false));
			add(new StargatePathSegment(BlockFace.EAST, true));
			add(new StargatePathSegment(BlockFace.DOWN, false));
			add(new StargatePathSegment(BlockFace.EAST, true));
		}
	};

	public static final List<StargatePathSegment> pathNetherEW = new ArrayList<StargatePathSegment>() {
		{
			add(new StargatePathSegment(BlockFace.DOWN, false));
			add(new StargatePathSegment(BlockFace.EAST, false));
			add(new StargatePathSegment(BlockFace.UP, false));
			add(new StargatePathSegment(BlockFace.UP, false));
			add(new StargatePathSegment(BlockFace.UP, false));
			add(new StargatePathSegment(BlockFace.WEST, false));
			add(new StargatePathSegment(BlockFace.WEST, false));
			add(new StargatePathSegment(BlockFace.DOWN, false));
			add(new StargatePathSegment(BlockFace.DOWN, false));
			add(new StargatePathSegment(BlockFace.DOWN, false));
		}
	};

	public static final List<StargatePathSegment> pathNetherNS = new ArrayList<StargatePathSegment>() {
		{
			add(new StargatePathSegment(BlockFace.DOWN, false));
			add(new StargatePathSegment(BlockFace.NORTH, false));
			add(new StargatePathSegment(BlockFace.UP, false));
			add(new StargatePathSegment(BlockFace.UP, false));
			add(new StargatePathSegment(BlockFace.UP, false));
			add(new StargatePathSegment(BlockFace.SOUTH, false));
			add(new StargatePathSegment(BlockFace.SOUTH, false));
			add(new StargatePathSegment(BlockFace.DOWN, false));
			add(new StargatePathSegment(BlockFace.DOWN, false));
			add(new StargatePathSegment(BlockFace.DOWN, false));
		}
	};

	static boolean isValidPath(Location tempLocation, List<StargatePathSegment> path) {
		Block tempBlock = tempLocation.getBlock();
		for (int i = 0; i < path.size(); i++) {
			tempBlock = tempBlock.getRelative(path.get(i).getFace());
			if (!path.get(i).isBlank()) {
				if (!tempBlock.getType().equals(Material.MOSSY_COBBLESTONE)) {
					return false;
				}
			}
		}
		return true;
	}

	public static boolean foundStargate(Location tempLocation) {
		if (isValidPath(tempLocation, pathNetherEW)) {
			return true;
		}
		if (isValidPath(tempLocation, pathNetherNS)) {
			return true;
		}
		if (isValidPath(tempLocation, pathCircleEW)) {
			return true;
		}
		return false;
	}

}
