package be.mc.woutwoot.MirrorMaster;

import be.mc.woutwoot.MirrorMaster.mirrors.Mirroring;
import be.mc.woutwoot.MirrorMaster.objects.AdjacentBlock;
import be.mc.woutwoot.MirrorMaster.objects.User;
import com.intellectualcrafters.plot.config.C;
import com.intellectualcrafters.plot.config.Settings;
import com.intellectualcrafters.plot.flag.Flags;
import com.intellectualcrafters.plot.object.Plot;
import com.intellectualcrafters.plot.object.PlotPlayer;
import com.intellectualcrafters.plot.util.Permissions;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;

public class Functions {

    public static String LookDirection(User user) {
        float yaw = user.player.getLocation().getYaw();

        while (yaw <= 0.0F)
            yaw += 360.0F;
        if ((yaw < 45.0F) || (yaw >= 315.0F))
            return "south";
        if ((yaw >= 45.0F) && (yaw < 135.0F))
            return "west";
        if ((yaw >= 135.0F) && (yaw < 225.0F))
            return "north";
        return "east";
    }

    public static boolean Up(User user) {
        return user.variables.touchingBlock.getFace(user.variables.currentBlock) == BlockFace.UP;
    }

    public static boolean Down(User user) {
        return user.variables.touchingBlock.getFace(user.variables.currentBlock) == BlockFace.DOWN;
    }

    public static boolean East(User user) {
        return user.variables.touchingBlock.getFace(user.variables.currentBlock) == BlockFace.EAST;
    }

    public static boolean West(User user) {
        return user.variables.touchingBlock.getFace(user.variables.currentBlock) == BlockFace.WEST;
    }

    public static boolean North(User user) {
        return user.variables.touchingBlock.getFace(user.variables.currentBlock) == BlockFace.NORTH;
    }

    public static boolean South(User user) {
        return user.variables.touchingBlock.getFace(user.variables.currentBlock) == BlockFace.SOUTH;
    }

    public static void PlaceBlock(int xDif, int yDif, int zDif, User user) {
        Bukkit.getScheduler().runTask(MirrorMaster.instance, () -> {
            Location l = new Location(user.player.getWorld(), user.mirrorPoint.getX() + xDif, yDif, user.mirrorPoint.getZ() + zDif);
            if (!p2CheckPlace(l, user.player)) return;
            l.getBlock().setType(user.variables.materialCopy, false);
            l.getBlock().setBlockData(user.variables.dataCopy, false);
        });
    }

    public static void PlaceBlock(int xDif, int yDif, int zDif, BlockData data, User user) {
        Bukkit.getScheduler().runTask(MirrorMaster.instance, () -> {
            Location l = new Location(user.player.getWorld(), user.mirrorPoint.getX() + xDif, yDif, user.mirrorPoint.getZ() + zDif);
            if (!p2CheckPlace(l, user.player)) return;
            l.getBlock().setType(user.variables.materialCopy, false);
            l.getBlock().setBlockData(data, false);
        });
    }

    public static void PlaceBlockRelative(int xDif, int yDif, int zDif, BlockData data, Material mat, User user, BlockFace bf) {
        Bukkit.getScheduler().runTask(MirrorMaster.instance, () -> {
            Location l = new Location(user.player.getWorld(), user.mirrorPoint.getX() + xDif, yDif, user.mirrorPoint.getZ() + zDif);
            if (!p2CheckPlace(l, user.player)) return;
            l.getBlock().getRelative(bf).setType(mat, false);
            l.getBlock().getRelative(bf).setBlockData(data, false);
        });
    }

    public static void RemoveBlock(int xDif, int yDif, int zDif, User user) {
        Bukkit.getScheduler().runTask(MirrorMaster.instance, () -> {
            Location l = new Location(user.player.getWorld(), user.mirrorPoint.getX() + xDif, yDif, user.mirrorPoint.getZ() + zDif);
            if (!p2CheckRemove(l, user.player)) return;
            l.getBlock().setType(Material.AIR);
        });
    }

    private static boolean p2CheckRemove(Location l, Player pl) {
        if (MirrorMaster.P2()) {
            Plot p = MirrorMaster.api.getPlot(l);
            PlotPlayer pp = PlotPlayer.wrap(pl);
            if (p != null) {
                if (!p.hasOwner())
                    return Permissions.hasPermission(pp, C.PERMISSION_ADMIN_DESTROY_UNOWNED);
                if (Settings.Done.RESTRICT_BUILDING && p.getFlags().containsKey(Flags.DONE))
                    return Permissions.hasPermission(pp, C.PERMISSION_ADMIN_DESTROY_OTHER);
                if (!p.isAdded(pp.getUUID()))
                    return Permissions.hasPermission(pp, C.PERMISSION_ADMIN_DESTROY_OTHER);
            } else {
                return Permissions.hasPermission(pp, C.PERMISSION_ADMIN_DESTROY_ROAD);
            }
        }
        return true;
    }

    private static boolean p2CheckPlace(Location l, Player pl) {
        if (MirrorMaster.P2()) {
            Plot p = MirrorMaster.api.getPlot(l);
            PlotPlayer pp = PlotPlayer.wrap(pl);
            if (p != null) {
                if (!p.hasOwner())
                    return Permissions.hasPermission(pp, C.PERMISSION_ADMIN_BUILD_UNOWNED);
                if (Settings.Done.RESTRICT_BUILDING && p.getFlags().containsKey(Flags.DONE))
                    return Permissions.hasPermission(pp, C.PERMISSION_ADMIN_BUILD_OTHER);
                if (!p.isAdded(pp.getUUID()))
                    return Permissions.hasPermission(pp, C.PERMISSION_ADMIN_BUILD_OTHER);
            } else {
                return Permissions.hasPermission(pp, C.PERMISSION_ADMIN_BUILD_ROAD);
            }
        }
        return true;
    }

    static void Mirror(User user, Mirroring mirroring) {
        if (user.variables.currentBlock.getType().name().toLowerCase().contains("stairs"))
            mirroring.Stairs(user);
        else if (user.variables.currentBlock.getType().name().equalsIgnoreCase("lever") || user.variables.currentBlock.getType().name().toLowerCase().contains("button"))
            mirroring.ButtonLevers(user);
        else if (user.variables.currentBlock.getType().name().toLowerCase().contains("torch"))
            mirroring.Torches(user);
        else if (user.variables.currentBlock.getType().name().toLowerCase().contains("slab"))
            mirroring.Halfslabs(user);
        else if (user.variables.currentBlock.getType().name().toLowerCase().contains("_door"))
            mirroring.Doors(user);
        else if (user.variables.currentBlock.getType().name().toLowerCase().contains("gate"))
            mirroring.Gates(user);
        else if (user.variables.currentBlock.getType().name().toLowerCase().contains("fence") || user.variables.currentBlock.getType().name().toLowerCase().contains("cobblestone_wall"))
            mirroring.Fences(user);
        else if (user.variables.currentBlock.getType().name().toLowerCase().contains("trapdoor"))
            mirroring.Trapdoors(user);
        else if (user.variables.currentBlock.getType().name().toLowerCase().contains("piston"))
            mirroring.Pistons(user);
        else if (user.variables.currentBlock.getType().name().equalsIgnoreCase("end_rod"))
            mirroring.EndRods(user);
        else if(user.variables.currentBlock.getType().name().toLowerCase().endsWith("chest"))
            mirroring.Chests(user);
        else
            mirroring.Default(user);
    }

    public static AdjacentBlock[] getRelatives(Block b) {
        AdjacentBlock[] bs = new AdjacentBlock[4];
        bs[0] = new AdjacentBlock(BlockFace.NORTH, b.getRelative(BlockFace.NORTH));
        bs[1] = new AdjacentBlock(BlockFace.EAST, b.getRelative(BlockFace.EAST));
        bs[2] = new AdjacentBlock(BlockFace.SOUTH, b.getRelative(BlockFace.SOUTH));
        bs[3] = new AdjacentBlock(BlockFace.WEST, b.getRelative(BlockFace.WEST));
        return bs;
    }
}