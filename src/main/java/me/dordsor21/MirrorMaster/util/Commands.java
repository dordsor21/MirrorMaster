package me.dordsor21.MirrorMaster.util;

import me.dordsor21.MirrorMaster.objects.Mirrors;
import me.dordsor21.MirrorMaster.objects.User;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class Commands {

    public static void Teleport(User user) {
        if (user.mirrorPoint != null) {
            org.bukkit.Location tp = user.mirrorPoint.getLocation();
            tp.setY(user.mirrorPoint.getY() + 1);
            tp.setX(user.mirrorPoint.getX() + 0.5D);
            tp.setZ(user.mirrorPoint.getZ() + 0.5D);

            user.player.sendMessage("Teleporting to the mirrorpoint!");

            user.player.teleport(tp);
        }
    }

    public static void Change(String arg, User user) {
        if ((arg.equals("x")) || (arg.equals("mirrorx")) || (arg.equals("mx"))) {
            user.player.sendMessage("Changed mirror type to x mirroring!");
            user.mirror = Mirrors.XMirroring;
        }
        if ((arg.equals("z")) || (arg.equals("mirrorz")) || (arg.equals("mz"))) {
            user.mirror = Mirrors.ZMirroring;
            user.player.sendMessage("Changed mirror type to z mirroring!");
        }
        if ((arg.equals("c")) || (arg.equals("mirrorcross")) || (arg.equals("cross"))) {
            user.mirror = Mirrors.CrossMirroring;
            user.player.sendMessage("Changed mirror type to cross mirroring!");
        }
        if ((arg.equals("rot180")) || (arg.equals("rotate180")) || (arg.equals("r180")) || (arg.equals("rotating180"))) {
            user.mirror = Mirrors.Rotating180;
            user.player.sendMessage("Changed type to rotating 180 degrees!");
        }
        UsersManager.Set(user);
    }

    public static void CrossMirroring(User user) {
        notNullMirrorPoint(user);

        user.mirrorPoint(user.variables.loc.getBlock());
        user.mirrorPointMat(user.mirrorPoint.getType());
        user.mirrorPoint.setType(Material.GLOWSTONE);
        user.player.sendMessage("Placed mirror cross! Use '/mm stop' to stop Mirrors.");

        user.mirror(Mirrors.CrossMirroring);
        UsersManager.Set(user);
    }

    public static void XMirroring(User user) {
        notNullMirrorPoint(user);

        user.mirrorPoint(user.variables.loc.getBlock());
        user.mirrorPointMat(user.mirrorPoint.getType());
        user.mirrorPoint.setType(Material.GLOWSTONE);
        Block b = user.mirrorPoint;
        b.setType(user.mirrorPointMat);
        user.mirrorPoint(b);

        user.player.sendMessage("Mirroring on x-axis! Use '/mm stop' to stop Mirrors.");

        user.mirror(Mirrors.XMirroring);
        UsersManager.Set(user);
    }

    public static void ZMirroring(User user) {
        notNullMirrorPoint(user);

        user.mirrorPoint(user.variables.loc.getBlock());
        user.mirrorPointMat(user.mirrorPoint.getType());
        user.mirrorPoint.setType(Material.GLOWSTONE);

        user.player.sendMessage("Mirroring on z-axis! Use '/mm stop' to stop Mirrors.");

        user.mirror(Mirrors.ZMirroring);
        UsersManager.Set(user);
    }

    public static void StopMirroring(User user) {
        user.player.sendMessage("Stopped mirroring/rotating!");

        if (!user.mirrorBlockDestroyed) {
            Block b = user.mirrorPoint;
            b.setType(user.mirrorPointMat);
            user.mirrorPoint(b);
        }

        user.mirrorBlockDestroyed(false);

        user.mirror(Mirrors.None);
        UsersManager.Set(user);
    }

    public static void Rotation180(User user) {
        notNullMirrorPoint(user);

        user.mirrorPoint(user.variables.loc.getBlock());
        user.mirrorPointMat(user.mirrorPoint.getType());
        user.mirrorPoint.setType(Material.GLOWSTONE);

        user.player.sendMessage("Rotating 180 degrees! Use '/mm stop' to stop rotating.");

        user.mirror(Mirrors.Rotating180);
        UsersManager.Set(user);
    }

    public static void Rotation90(User user) {
        notNullMirrorPoint(user);

        user.mirrorPoint(user.variables.loc.getBlock());
        user.mirrorPointMat(user.mirrorPoint.getType());
        user.mirrorPoint.setType(Material.GLOWSTONE);

        user.player.sendMessage("Rotating 90 degrees! Use '/mm stop' to stop rotating.");

        user.mirror(Mirrors.Rotating90);
        UsersManager.Set(user);
    }

    private static void notNullMirrorPoint(User user) {
        if (user.mirrorPoint != null) {
            if (!user.mirrorBlockDestroyed) {
                Block b = user.mirrorPoint;
                b.setType(user.mirrorPointMat);
                user.mirrorPoint(b);
            }

            user.mirrorBlockDestroyed(false);

            user.mirror(Mirrors.None);
        }
    }

    public static void Help(User user) {
        user.player.sendMessage("MirrorMaster command list:");
        user.player.sendMessage("/mm [x/z/c] (Uses the axis as a mirror, c uses both)");
        user.player.sendMessage("/mm [r180] (Rotates around the center)");
        user.player.sendMessage("/mm change [x/z/c/r180] (Change mirrotype)");
        user.player.sendMessage("/mm tp (Teleport you to the center)");
    }
}