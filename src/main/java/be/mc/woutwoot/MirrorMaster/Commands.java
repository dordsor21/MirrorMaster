package be.mc.woutwoot.MirrorMaster;

import org.bukkit.entity.Player;

public class Commands {

    static void Teleport(Player player) {
        if (UsersManager.GetUser(player).mirrorPoint != null) {
            org.bukkit.Location tp = UsersManager.GetUser(player).mirrorPoint.getLocation();
            tp.setY(UsersManager.GetUser(player).mirrorPoint.getY() + 1);
            tp.setX(UsersManager.GetUser(player).mirrorPoint.getX() + 0.5D);
            tp.setZ(UsersManager.GetUser(player).mirrorPoint.getZ() + 0.5D);

            UsersManager.GetUser(player).player.sendMessage("Teleporting to the mirrorpoint!");

            UsersManager.GetUser(player).player.teleport(tp);
        }
    }

    static void Change(String arg, Player player) {
        if ((arg.equals("x")) || (arg.equals("mirrorx")) || (arg.equals("mx"))) {
            UsersManager.GetUser(player).player.sendMessage("Changed mirror type to x mirroring!");
            UsersManager.GetUser(player).mirror = Mirroring.XMirroring;
        }
        if ((arg.equals("z")) || (arg.equals("mirrorz")) || (arg.equals("mz"))) {
            UsersManager.GetUser(player).mirror = Mirroring.ZMirroring;
            UsersManager.GetUser(player).player.sendMessage("Changed mirror type to z mirroring!");
        }
        if ((arg.equals("c")) || (arg.equals("mirrorcross")) || (arg.equals("cross"))) {
            UsersManager.GetUser(player).mirror = Mirroring.CrossMirroring;
            UsersManager.GetUser(player).player.sendMessage("Changed mirror type to cross mirroring!");
        }
        if ((arg.equals("rot180")) || (arg.equals("rotate180")) || (arg.equals("r180")) || (arg.equals("rotating180"))) {
            UsersManager.GetUser(player).mirror = Mirroring.Rotating180;
            UsersManager.GetUser(player).player.sendMessage("Changed type to rotating 180 degrees!");
        }
    }

    static void CrossMirroring(Player player) {
        if (UsersManager.GetUser(player).mirrorPoint != null) {
            if (!UsersManager.GetUser(player).mirrorBlockDestroyed)
                UsersManager.GetUser(player).mirrorPoint.setType(UsersManager.GetUser(player).mirrorPointMat);

            UsersManager.GetUser(player).mirrorBlockDestroyed = false;

            UsersManager.GetUser(player).mirror = Mirroring.None;
        }

        UsersManager.GetUser(player).mirrorPoint = Variables.loc.getBlock();
        UsersManager.GetUser(player).mirrorPointMat = UsersManager.GetUser(player).mirrorPoint.getType();
        UsersManager.GetUser(player).mirrorPoint.setType(org.bukkit.Material.GLOWSTONE);

        UsersManager.GetUser(player).player.sendMessage("Placed mirror cross! Use '/mm stop' to stop mirroring.");

        UsersManager.GetUser(player).mirror = Mirroring.CrossMirroring;
    }

    static void XMirroring(Player player) {
        if (UsersManager.GetUser(player).mirrorPoint != null) {
            if (!UsersManager.GetUser(player).mirrorBlockDestroyed)
                UsersManager.GetUser(player).mirrorPoint.setType(UsersManager.GetUser(player).mirrorPointMat);

            UsersManager.GetUser(player).mirrorBlockDestroyed = false;

            UsersManager.GetUser(player).mirror = Mirroring.None;
        }

        UsersManager.GetUser(player).mirrorPoint = Variables.loc.getBlock();
        UsersManager.GetUser(player).mirrorPointMat = UsersManager.GetUser(player).mirrorPoint.getType();
        UsersManager.GetUser(player).mirrorPoint.setType(org.bukkit.Material.GLOWSTONE);

        UsersManager.GetUser(player).player.sendMessage("Mirroring on x-axis! Use '/mm stop' to stop mirroring.");

        UsersManager.GetUser(player).mirror = Mirroring.XMirroring;
    }

    static void ZMirroring(Player player) {
        if (UsersManager.GetUser(player).mirrorPoint != null) {
            if (!UsersManager.GetUser(player).mirrorBlockDestroyed)
                UsersManager.GetUser(player).mirrorPoint.setType(UsersManager.GetUser(player).mirrorPointMat);

            UsersManager.GetUser(player).mirrorBlockDestroyed = false;

            UsersManager.GetUser(player).mirror = Mirroring.None;
        }

        UsersManager.GetUser(player).mirrorPoint = Variables.loc.getBlock();
        UsersManager.GetUser(player).mirrorPointMat = UsersManager.GetUser(player).mirrorPoint.getType();
        UsersManager.GetUser(player).mirrorPoint.setType(org.bukkit.Material.GLOWSTONE);

        UsersManager.GetUser(player).player.sendMessage("Mirroring on z-axis! Use '/mm stop' to stop mirroring.");

        UsersManager.GetUser(player).mirror = Mirroring.ZMirroring;
    }

    static void StopMirroring(Player player) {
        UsersManager.GetUser(player).player.sendMessage("Stopped mirroring/rotating!");

        if (!UsersManager.GetUser(player).mirrorBlockDestroyed)
            UsersManager.GetUser(player).mirrorPoint.setType(UsersManager.GetUser(player).mirrorPointMat);

        UsersManager.GetUser(player).mirrorBlockDestroyed = false;

        UsersManager.GetUser(player).mirror = Mirroring.None;
    }

    static void Rotation180(Player player) {
        if (UsersManager.GetUser(player).mirrorPoint != null) {
            if (!UsersManager.GetUser(player).mirrorBlockDestroyed)
                UsersManager.GetUser(player).mirrorPoint.setType(UsersManager.GetUser(player).mirrorPointMat);

            UsersManager.GetUser(player).mirrorBlockDestroyed = false;

            UsersManager.GetUser(player).mirror = Mirroring.None;
        }

        UsersManager.GetUser(player).mirrorPoint = Variables.loc.getBlock();
        UsersManager.GetUser(player).mirrorPointMat = UsersManager.GetUser(player).mirrorPoint.getType();
        UsersManager.GetUser(player).mirrorPoint.setType(org.bukkit.Material.GLOWSTONE);

        UsersManager.GetUser(player).player.sendMessage("Rotating 180 degrees! Use '/mm stop' to stop rotating.");

        UsersManager.GetUser(player).mirror = Mirroring.Rotating180;
    }

    static void Rotation90(Player player) {
        if (UsersManager.GetUser(player).mirrorPoint != null) {
            if (!UsersManager.GetUser(player).mirrorBlockDestroyed)
                UsersManager.GetUser(player).mirrorPoint.setType(UsersManager.GetUser(player).mirrorPointMat);

            UsersManager.GetUser(player).mirrorBlockDestroyed = false;

            UsersManager.GetUser(player).mirror = Mirroring.None;
        }

        UsersManager.GetUser(player).mirrorPoint = Variables.loc.getBlock();
        UsersManager.GetUser(player).mirrorPointMat = UsersManager.GetUser(player).mirrorPoint.getType();
        UsersManager.GetUser(player).mirrorPoint.setType(org.bukkit.Material.GLOWSTONE);

        UsersManager.GetUser(player).player.sendMessage("Rotating 90 degrees! Use '/mm stop' to stop rotating.");

        UsersManager.GetUser(player).mirror = Mirroring.Rotating90;
    }

    static void Help(Player player) {
        player.sendMessage("MirrorMaster command list:");
        player.sendMessage("/mm [x/z/c] (Uses the axis as a mirror, c uses both)");
        player.sendMessage("/mm [r180] (Rotates around the center)");
        player.sendMessage("/mm change [x/z/c/r180] (Change mirrotype)");
        player.sendMessage("/mm tp (Teleport you to the center)");
    }
}