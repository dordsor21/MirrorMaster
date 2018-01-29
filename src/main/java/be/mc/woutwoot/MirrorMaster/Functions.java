package be.mc.woutwoot.MirrorMaster;

import org.bukkit.block.BlockFace;

public class Functions {

	static int LookDirection() {
		float yaw = UsersManager.user.player.getLocation().getYaw();

		while (yaw <= 0.0F)
			yaw += 360.0F;
		if ((yaw < 45.0F) || (yaw > 315.0F))
			return 0;
		if ((yaw > 45.0F) && (yaw < 135.0F))
			return 1;
		if ((yaw > 135.0F) && (yaw < 225.0F))
			return 2;
		if ((yaw > 225.0F) && (yaw < 315.0F))
			return 3;
		return 4;
	}

	static boolean Up() {
		if (Variables.touchingBlock.getFace(Variables.currentBlock) == BlockFace.UP)
			return true;
		return false;
	}

	static boolean Down() {
		if (Variables.touchingBlock.getFace(Variables.currentBlock) == BlockFace.DOWN)
			return true;
		return false;
	}

	static boolean East() {
		if (Variables.touchingBlock.getFace(Variables.currentBlock) == BlockFace.EAST)
			return true;
		return false;
	}

	static boolean West() {
		if (Variables.touchingBlock.getFace(Variables.currentBlock) == BlockFace.WEST)
			return true;
		return false;
	}

	static boolean North() {
		if (Variables.touchingBlock.getFace(Variables.currentBlock) == BlockFace.NORTH)
			return true;
		return false;
	}

	static boolean South() {
		if (Variables.touchingBlock.getFace(Variables.currentBlock) == BlockFace.SOUTH)
			return true;
		return false;
	}

	static void PlaceBlock(int xDif, int yDif, int zDif) {
		UsersManager.user.player.getWorld().getBlockAt(UsersManager.user.mirrorPoint.getX() + xDif, yDif, UsersManager.user.mirrorPoint.getZ() + zDif).setType(Variables.materialCopy);
		UsersManager.user.player.getWorld().getBlockAt(UsersManager.user.mirrorPoint.getX() + xDif, yDif, UsersManager.user.mirrorPoint.getZ() + zDif).setData(Variables.dataCopy);
	}

	static void PlaceBlock(int xDif, int yDif, int zDif, byte data) {
		UsersManager.user.player.getWorld().getBlockAt(UsersManager.user.mirrorPoint.getX() + xDif, yDif, UsersManager.user.mirrorPoint.getZ() + zDif).setType(Variables.materialCopy);
		UsersManager.user.player.getWorld().getBlockAt(UsersManager.user.mirrorPoint.getX() + xDif, yDif, UsersManager.user.mirrorPoint.getZ() + zDif).setData(data);
	}

	static void RemoveBlock(int xDif, int yDif, int zDif) {
		UsersManager.user.player.getWorld().getBlockAt(UsersManager.user.mirrorPoint.getX() + xDif, yDif, UsersManager.user.mirrorPoint.getZ() + zDif).setType(org.bukkit.Material.AIR);
	}

	static void RemoveBlock(int xDif, int yDif, int zDif, boolean normal) {
		UsersManager.user.player.getWorld().getBlockAt(UsersManager.user.mirrorPoint.getX() + xDif, yDif, UsersManager.user.mirrorPoint.getZ() + zDif).breakNaturally();
	}

	static void CheckRemovedBlock() {
		if ((Variables.currentBlock.getX() == UsersManager.user.mirrorPoint.getX()) && (Variables.currentBlock.getY() == UsersManager.user.mirrorPoint.getY()) && (Variables.currentBlock.getZ() == UsersManager.user.mirrorPoint.getZ())) {
			UsersManager.user.mirrorBlockDestroyed = Boolean.valueOf(true);
		}
	}




























	static boolean CheckBlockMaterialLists(java.util.ArrayList<org.bukkit.Material> list)
	{
		for (org.bukkit.Material material : list) {
			if (material == Variables.currentBlock.getType())
				return true;
		}
		return false;
	}
}