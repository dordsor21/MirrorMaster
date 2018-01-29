package be.mc.woutwoot.MirrorMaster;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class User  {
	Player player;
	Mirroring mirror;
	Boolean mirrorBlockDestroyed;
	Boolean enabled;
	Block mirrorPoint;
	Material mirrorPointMat;

	User(Player player) {
		this.player = player;
		this.mirror = Mirroring.None;
		this.mirrorBlockDestroyed = Boolean.valueOf(false);
		this.enabled = Boolean.valueOf(true);
	}

	User() {
		this.enabled = Boolean.valueOf(false);
	}
}