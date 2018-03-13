package be.mc.woutwoot.MirrorMaster;

import org.bukkit.block.Block;

public class Commands {

    static void Teleport(User user) {
        if (user.mirrorPoint != null) {
            org.bukkit.Location tp = user.mirrorPoint.getLocation();
            tp.setY(user.mirrorPoint.getY() + 1);
            tp.setX(user.mirrorPoint.getX() + 0.5D);
            tp.setZ(user.mirrorPoint.getZ() + 0.5D);

            user.player.sendMessage("Teleporting to the mirrorpoint!");

            user.player.teleport(tp);
        }
    }

    static void Change(String arg, User user) {
        if ((arg.equals("x")) || (arg.equals("mirrorx")) || (arg.equals("mx"))) {
            user.player.sendMessage("Changed mirror type to x mirroring!");
            user.mirror = Mirroring.XMirroring;
        }
        if ((arg.equals("z")) || (arg.equals("mirrorz")) || (arg.equals("mz"))) {
            user.mirror = Mirroring.ZMirroring;
            user.player.sendMessage("Changed mirror type to z mirroring!");
        }
        if ((arg.equals("c")) || (arg.equals("mirrorcross")) || (arg.equals("cross"))) {
            user.mirror = Mirroring.CrossMirroring;
            user.player.sendMessage("Changed mirror type to cross mirroring!");
        }
        if ((arg.equals("rot180")) || (arg.equals("rotate180")) || (arg.equals("r180")) || (arg.equals("rotating180"))) {
            user.mirror = Mirroring.Rotating180;
            user.player.sendMessage("Changed type to rotating 180 degrees!");
        }
        UsersManager.Set(user);
    }

    static void CrossMirroring(User user) {
        norNullMirrorPoint(user);

        user.mirrorPoint(user.variables.loc.getBlock());
        user.mirrorPointMat(user.mirrorPoint.getType());
        Block b = user.mirrorPoint;
        b.setType(user.mirrorPointMat);
        user.mirrorPoint(b);

        user.player.sendMessage("Placed mirror cross! Use '/mm stop' to stop mirroring.");

        user.mirror(Mirroring.CrossMirroring);
        UsersManager.Set(user);
    }

    static void XMirroring(User user) {
        norNullMirrorPoint(user);

        user.mirrorPoint(user.variables.loc.getBlock());
        user.mirrorPointMat(user.mirrorPoint.getType());
        Block b = user.mirrorPoint;
        b.setType(user.mirrorPointMat);
        user.mirrorPoint(b);

        user.player.sendMessage("Mirroring on x-axis! Use '/mm stop' to stop mirroring.");

        user.mirror(Mirroring.XMirroring);
        UsersManager.Set(user);
    }

    static void ZMirroring(User user) {
        norNullMirrorPoint(user);

        user.mirrorPoint(user.variables.loc.getBlock());
        user.mirrorPointMat(user.mirrorPoint.getType());
        Block b = user.mirrorPoint;
        b.setType(user.mirrorPointMat);
        user.mirrorPoint(b);

        user.player.sendMessage("Mirroring on z-axis! Use '/mm stop' to stop mirroring.");

        user.mirror(Mirroring.ZMirroring);
        UsersManager.Set(user);
    }

    static void StopMirroring(User user) {
        user.player.sendMessage("Stopped mirroring/rotating!");

        if (!user.mirrorBlockDestroyed) {
            Block b = user.mirrorPoint;
            b.setType(user.mirrorPointMat);
            user.mirrorPoint(b);
        }

        user.mirrorBlockDestroyed(false);

        user.mirror(Mirroring.None);
        UsersManager.Set(user);
    }

    static void Rotation180(User user) {
        norNullMirrorPoint(user);

        user.mirrorPoint(user.variables.loc.getBlock());
        user.mirrorPointMat(user.mirrorPoint.getType());
        Block b = user.mirrorPoint;
        b.setType(user.mirrorPointMat);
        user.mirrorPoint(b);

        user.player.sendMessage("Rotating 180 degrees! Use '/mm stop' to stop rotating.");

        user.mirror(Mirroring.Rotating180);
        UsersManager.Set(user);
    }

    static void Rotation90(User user) {
        norNullMirrorPoint(user);

        user.mirrorPoint(user.variables.loc.getBlock());
        user.mirrorPointMat(user.mirrorPoint.getType());
        Block b = user.mirrorPoint;
        b.setType(user.mirrorPointMat);
        user.mirrorPoint(b);

        user.player.sendMessage("Rotating 90 degrees! Use '/mm stop' to stop rotating.");

        user.mirror(Mirroring.Rotating90);
        UsersManager.Set(user);
    }

    private static void norNullMirrorPoint(User user) {
        if (user.mirrorPoint != null) {
            if (!user.mirrorBlockDestroyed) {
                Block b = user.mirrorPoint;
                b.setType(user.mirrorPointMat);
                user.mirrorPoint(b);
            }

            user.mirrorBlockDestroyed(false);

            user.mirror(Mirroring.None);
        }
    }

    static void Help(User user) {
        user.player.sendMessage("MirrorMaster command list:");
        user.player.sendMessage("/mm [x/z/c] (Uses the axis as a mirror, c uses both)");
        user.player.sendMessage("/mm [r180] (Rotates around the center)");
        user.player.sendMessage("/mm change [x/z/c/r180] (Change mirrotype)");
        user.player.sendMessage("/mm tp (Teleport you to the center)");
    }
}