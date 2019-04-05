package me.dordsor21.MirrorMaster.util;

import com.github.intellectualsites.plotsquared.bukkit.util.BukkitUtil;
import com.github.intellectualsites.plotsquared.plot.config.Captions;
import com.github.intellectualsites.plotsquared.plot.config.Settings;
import com.github.intellectualsites.plotsquared.plot.flag.Flags;
import com.github.intellectualsites.plotsquared.plot.object.Plot;
import com.github.intellectualsites.plotsquared.plot.object.PlotPlayer;
import com.github.intellectualsites.plotsquared.plot.util.Permissions;
import me.dordsor21.MirrorMaster.MirrorMaster;
import me.dordsor21.MirrorMaster.mirrors.Mirroring;
import me.dordsor21.MirrorMaster.objects.AdjacentBlock;
import me.dordsor21.MirrorMaster.objects.User;
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
        Bukkit.getScheduler().runTask(MirrorMaster.get(), () -> {
            Location l = new Location(user.player.getWorld(), user.mirrorPoint.getX() + xDif, yDif, user.mirrorPoint.getZ() + zDif);
            if (!p2CheckPlace(l, user.player)) return;
            l.getBlock().setType(user.variables.materialCopy, false);
            l.getBlock().setBlockData(user.variables.dataCopy, false);
        });
    }

    public static void PlaceBlock(int xDif, int yDif, int zDif, BlockData data, User user) {
        Bukkit.getScheduler().runTask(MirrorMaster.get(), () -> {
            Location l = new Location(user.player.getWorld(), user.mirrorPoint.getX() + xDif, yDif, user.mirrorPoint.getZ() + zDif);
            if (!p2CheckPlace(l, user.player)) return;
            l.getBlock().setType(user.variables.materialCopy, false);
            l.getBlock().setBlockData(data, false);
        });
    }

    public static void PlaceBlock(Block b, BlockData data, Material mat, User user) {
        Bukkit.getScheduler().runTask(MirrorMaster.get(), () -> {
            if (!p2CheckPlace(b.getLocation(), user.player)) return;
            b.setType(mat, true);
            b.setBlockData(data, true);
        });
    }

    public static void PlaceBlockRelative(int xDif, int yDif, int zDif, BlockData data, Material mat, User user, BlockFace bf) {
        Bukkit.getScheduler().runTask(MirrorMaster.get(), () -> {
            Location l = new Location(user.player.getWorld(), user.mirrorPoint.getX() + xDif, yDif, user.mirrorPoint.getZ() + zDif);
            if (!p2CheckPlace(l, user.player)) return;
            l.getBlock().getRelative(bf).setType(mat, false);
            l.getBlock().getRelative(bf).setBlockData(data, false);
        });
    }

    public static void RemoveBlock(int xDif, int yDif, int zDif, User user) {
        Bukkit.getScheduler().runTask(MirrorMaster.get(), () -> {
            Location l = new Location(user.player.getWorld(), user.mirrorPoint.getX() + xDif, yDif, user.mirrorPoint.getZ() + zDif);
            if (!p2CheckRemove(l, user.player)) return;
            l.getBlock().setType(Material.AIR);
        });
    }

    private static boolean p2CheckRemove(Location l, Player pl) {
        if (MirrorMaster.P2()) {
            Plot p = BukkitUtil.getPlot(l);
            PlotPlayer pp = PlotPlayer.wrap(pl);
            if (p != null) {
                if (!p.hasOwner())
                    return Permissions.hasPermission(pp, Captions.PERMISSION_ADMIN_DESTROY_UNOWNED);
                if (Settings.Done.RESTRICT_BUILDING && p.getFlags().containsKey(Flags.DONE))
                    return Permissions.hasPermission(pp, Captions.PERMISSION_ADMIN_DESTROY_OTHER);
                if (!p.isAdded(pp.getUUID()))
                    return Permissions.hasPermission(pp, Captions.PERMISSION_ADMIN_DESTROY_OTHER);
            } else {
                return Permissions.hasPermission(pp, Captions.PERMISSION_ADMIN_DESTROY_ROAD);
            }
        }
        return true;
    }

    private static boolean p2CheckPlace(Location l, Player pl) {
        if (MirrorMaster.P2()) {
            Plot p = BukkitUtil.getPlot(l);
            PlotPlayer pp = PlotPlayer.wrap(pl);
            if (p != null) {
                if (!p.hasOwner())
                    return Permissions.hasPermission(pp, Captions.PERMISSION_ADMIN_BUILD_UNOWNED);
                if (Settings.Done.RESTRICT_BUILDING && p.getFlags().containsKey(Flags.DONE))
                    return Permissions.hasPermission(pp, Captions.PERMISSION_ADMIN_BUILD_OTHER);
                if (!p.isAdded(pp.getUUID()))
                    return Permissions.hasPermission(pp, Captions.PERMISSION_ADMIN_BUILD_OTHER);
            } else {
                return Permissions.hasPermission(pp, Captions.PERMISSION_ADMIN_BUILD_ROAD);
            }
        }
        return true;
    }

    public static void Mirror(User user) {
        Mirroring mirroring = user.mirrorClass;
        String block = user.variables.currentBlock.getType().name().toLowerCase();
        if (block.contains("stairs"))
            mirroring.Stairs();
        else if (block.equalsIgnoreCase("lever") || block.contains("button"))
            mirroring.ButtonLevers();
        else if (block.contains("torch"))
            mirroring.Torches();
        else if (block.contains("slab"))
            mirroring.Halfslabs();
        else if (block.contains("_door"))
            mirroring.Doors();
        else if (block.contains("gate"))
            mirroring.Gates();
        else if (block.contains("fence") || block.contains("cobblestone_wall") || block.equalsIgnoreCase("iron_bars"))
            mirroring.Fences();
        else if (block.contains("trapdoor"))
            mirroring.Trapdoors();
        else if (block.contains("piston"))
            mirroring.Pistons();
        else if (block.equalsIgnoreCase("end_rod"))
            mirroring.EndRods();
        else if (block.endsWith("chest"))
            mirroring.Chests();
        else if (block.equalsIgnoreCase("ladder") || block.equalsIgnoreCase("carved_pumpkin") || block.equalsIgnoreCase("jack_o_lantern") || block.equalsIgnoreCase("observer") ||
                block.equalsIgnoreCase("dispenser") || block.equalsIgnoreCase("dropper") || block.equalsIgnoreCase("comparator") || block.equalsIgnoreCase("repeater"))
            mirroring.RotateXZ();
        else if (block.equalsIgnoreCase("vine"))
            mirroring.Vines();
        else if (block.contains("glass_pane"))
            mirroring.GlassPanes();
        else if (block.equalsIgnoreCase("tripwire_hook"))
            mirroring.TripWire();
        else if (block.equalsIgnoreCase("hopper"))
            mirroring.Hopper();
        else if (block.equalsIgnoreCase("sunflower") || block.equalsIgnoreCase("lilac") || block.equalsIgnoreCase("rose_bush") || block.equalsIgnoreCase("peony") ||
                block.equalsIgnoreCase("tall_grass") || block.equalsIgnoreCase("large_fern"))
            mirroring.TallFlower();
        else if (block.contains("terracotta"))
            mirroring.Terracotta();
        else
            mirroring.Default();
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
