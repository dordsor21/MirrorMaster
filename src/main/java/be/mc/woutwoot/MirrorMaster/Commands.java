package be.mc.woutwoot.MirrorMaster;

import org.bukkit.entity.Player;

public class Commands  {

	static void Teleport() {
		if (UsersManager.user.mirrorPoint != null) {
			org.bukkit.Location tp = UsersManager.user.mirrorPoint.getLocation();
			tp.setY(UsersManager.user.mirrorPoint.getY() + 1);
			tp.setX(UsersManager.user.mirrorPoint.getX() + 0.5D);
			tp.setZ(UsersManager.user.mirrorPoint.getZ() + 0.5D);

			UsersManager.user.player.sendMessage("Teleporting to the mirrorpoint!");

			UsersManager.user.player.teleport(tp);
		}
	}

	static void Change(String arg) {
		if ((arg.equals("x")) || (arg.equals("mirrorx")) || (arg.equals("mx"))) {
			UsersManager.user.player.sendMessage("Changed mirror type to x mirroring!");
			UsersManager.user.mirror = Mirroring.XMirroring;
		}
		if ((arg.equals("z")) || (arg.equals("mirrorz")) || (arg.equals("mz"))) {
			UsersManager.user.mirror = Mirroring.ZMirroring;
			UsersManager.user.player.sendMessage("Changed mirror type to z mirroring!");
		}
		if ((arg.equals("c")) || (arg.equals("mirrorcross")) || (arg.equals("cross"))) {
			UsersManager.user.mirror = Mirroring.CrossMirroring;
			UsersManager.user.player.sendMessage("Changed mirror type to cross mirroring!");
		}
		if ((arg.equals("rot180")) || (arg.equals("rotate180")) || (arg.equals("r180")) || (arg.equals("rotating180"))) {
			UsersManager.user.mirror = Mirroring.Rotating180;
			UsersManager.user.player.sendMessage("Changed type to rotating 180 degrees!");
		}
	}

	static void CrossMirroring() {
		if (UsersManager.user.mirrorPoint != null) {
			if (!UsersManager.user.mirrorBlockDestroyed.booleanValue())
				UsersManager.user.mirrorPoint.setType(UsersManager.user.mirrorPointMat);

			UsersManager.user.mirrorBlockDestroyed = Boolean.valueOf(false);

			UsersManager.user.mirror = Mirroring.None;
		}

		UsersManager.user.mirrorPoint = Variables.loc.getBlock();
		UsersManager.user.mirrorPointMat = UsersManager.user.mirrorPoint.getType();
		UsersManager.user.mirrorPoint.setType(org.bukkit.Material.GLOWSTONE);

		UsersManager.user.player.sendMessage("Placed mirror cross! Use '/mm stop' to stop mirroring.");

		UsersManager.user.mirror = Mirroring.CrossMirroring;
	}

	static void XMirroring() {
		if (UsersManager.user.mirrorPoint != null) {
			if (!UsersManager.user.mirrorBlockDestroyed.booleanValue())
				UsersManager.user.mirrorPoint.setType(UsersManager.user.mirrorPointMat);

			UsersManager.user.mirrorBlockDestroyed = Boolean.valueOf(false);

			UsersManager.user.mirror = Mirroring.None;
		}

		UsersManager.user.mirrorPoint = Variables.loc.getBlock();
		UsersManager.user.mirrorPointMat = UsersManager.user.mirrorPoint.getType();
		UsersManager.user.mirrorPoint.setType(org.bukkit.Material.GLOWSTONE);

		UsersManager.user.player.sendMessage("Mirroring on x-axis! Use '/mm stop' to stop mirroring.");

		UsersManager.user.mirror = Mirroring.XMirroring;
	}

	static void ZMirroring() {
		if (UsersManager.user.mirrorPoint != null) {
			if (!UsersManager.user.mirrorBlockDestroyed.booleanValue())
				UsersManager.user.mirrorPoint.setType(UsersManager.user.mirrorPointMat);

			UsersManager.user.mirrorBlockDestroyed = Boolean.valueOf(false);

			UsersManager.user.mirror = Mirroring.None;
		}

		UsersManager.user.mirrorPoint = Variables.loc.getBlock();
		UsersManager.user.mirrorPointMat = UsersManager.user.mirrorPoint.getType();
		UsersManager.user.mirrorPoint.setType(org.bukkit.Material.GLOWSTONE);

		UsersManager.user.player.sendMessage("Mirroring on z-axis! Use '/mm stop' to stop mirroring.");

		UsersManager.user.mirror = Mirroring.ZMirroring;
	}

	static void StopMirroring() {
		UsersManager.user.player.sendMessage("Stopped mirroring/rotating!");

		if (!UsersManager.user.mirrorBlockDestroyed.booleanValue())
			UsersManager.user.mirrorPoint.setType(UsersManager.user.mirrorPointMat);

		UsersManager.user.mirrorBlockDestroyed = Boolean.valueOf(false);

		UsersManager.user.mirror = Mirroring.None;
	}

	static void Rotation180() {
		if (UsersManager.user.mirrorPoint != null) {
			if (!UsersManager.user.mirrorBlockDestroyed.booleanValue())
				UsersManager.user.mirrorPoint.setType(UsersManager.user.mirrorPointMat);

			UsersManager.user.mirrorBlockDestroyed = Boolean.valueOf(false);

			UsersManager.user.mirror = Mirroring.None;
		}

		UsersManager.user.mirrorPoint = Variables.loc.getBlock();
		UsersManager.user.mirrorPointMat = UsersManager.user.mirrorPoint.getType();
		UsersManager.user.mirrorPoint.setType(org.bukkit.Material.GLOWSTONE);

		UsersManager.user.player.sendMessage("Rotating 180 degrees! Use '/mm stop' to stop rotating.");

		UsersManager.user.mirror = Mirroring.Rotating180;
	}

	static void Rotation90() {
		if (UsersManager.user.mirrorPoint != null) {
			if (!UsersManager.user.mirrorBlockDestroyed.booleanValue())
				UsersManager.user.mirrorPoint.setType(UsersManager.user.mirrorPointMat);

			UsersManager.user.mirrorBlockDestroyed = Boolean.valueOf(false);

			UsersManager.user.mirror = Mirroring.None;
		}

		UsersManager.user.mirrorPoint = Variables.loc.getBlock();
		UsersManager.user.mirrorPointMat = UsersManager.user.mirrorPoint.getType();
		UsersManager.user.mirrorPoint.setType(org.bukkit.Material.GLOWSTONE);

		UsersManager.user.player.sendMessage("Rotating 90 degrees! Use '/mm stop' to stop rotating.");

		UsersManager.user.mirror = Mirroring.Rotating90;
	}

	static void Help(Player player) {
		player.sendMessage("MirrorMaster command list:");
		player.sendMessage("/mm [x/z/c] (Uses the axis as a mirror, c uses both)");
		player.sendMessage("/mm [r180] (Rotates around the center)");
		player.sendMessage("/mm change [x/z/c/r180] (Change mirrotype)");
		player.sendMessage("/mm tp (Teleport you to the center)");
	}
}