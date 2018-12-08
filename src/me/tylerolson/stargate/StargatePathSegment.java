package me.tylerolson.stargate;

import org.bukkit.block.BlockFace;

public class StargatePathSegment {

	private BlockFace face;
	private boolean blank;

	public StargatePathSegment(BlockFace face, boolean blank) {
		this.face = face;
		this.blank = blank;
	}

	public BlockFace getFace() {
		return face;
	}

	public boolean isBlank() {
		return blank;
	}

}
