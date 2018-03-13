package be.mc.woutwoot.MirrorMaster;

import com.intellectualcrafters.plot.api.PlotAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class MirrorMaster extends JavaPlugin implements Listener {

    private static boolean p2;
    public static PlotAPI api;

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("mm")) {
            Player player = (Player) sender;
            UsersManager.Set(player);
            User user = UsersManager.GetUser(player);

            if (args.length > 0) {
                user.variables.loc = user.player.getLocation();
                user.variables.loc.setY(user.variables.loc.getY() - 1.0D);

                if ((args[0].equals("tp")) || (args[0].equals("teleport"))) {
                    Commands.Teleport(user);
                    return true;
                }

                if ((args[0].equals("change")) || (args[0].equals("ch"))) {
                    Commands.Change(args[1], user);
                    return true;
                }

                if ((args[0].equals("mirrorcross")) || (args[0].equals("c")) || (args[0].equals("cross"))) {
                    Commands.CrossMirroring(user);
                    return true;
                }

                if ((args[0].equals("mirrorx")) || (args[0].equals("mx")) || (args[0].equals("x"))) {
                    Commands.XMirroring(user);
                    return true;
                }

                if ((args[0].equals("mirrorz")) || (args[0].equals("mz")) || (args[0].equals("z"))) {
                    Commands.ZMirroring(user);
                    return true;
                }

                if ((args[0].equals("rot180")) || (args[0].equals("rotate180")) || (args[0].equals("r180")) || (args[0].equals("rotating180"))) {
                    Commands.Rotation180(user);
                    return true;
                }

                if ((args[0].equals("rot90")) || (args[0].equals("rotate90")) || (args[0].equals("r90")) || (args[0].equals("rotating90"))) {
                    Commands.Rotation90(user);
                    return true;
                }

                if ((args[0].equals("mirrorstop")) || (args[0].equals("stop")) || (args[0].equals("s"))) {
                    Commands.StopMirroring(user);
                    return true;
                }

                if ((args[0].equals("help")) || (args[0].equals("h")) || (args[0].equals("?"))) {
                    Commands.Help(user);
                    return true;
                }
            }
        }
        return false;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        UsersManager.Set(player);
        User user = UsersManager.GetUser(player);

        user.variables.currentBlock = event.getBlock();
        user.variables.materialCopy = event.getBlock().getType();
        user.variables.dataCopy = event.getBlock().getData();
        user.variables.touchingBlock = event.getBlockAgainst();

        if (user.mirrorPoint != null) {
            if (!user.player.isSneaking()) {
                switch (user.mirror) {
                    case CrossMirroring:
                        break;

                    case None:
                        user.variables.xDif = user.variables.currentBlock.getX() - user.mirrorPoint.getX();
                        user.variables.yDif = user.variables.currentBlock.getY();
                        user.variables.zDif = user.variables.currentBlock.getZ() - user.mirrorPoint.getZ();

                        CrossMirroring.Mirror(user);
                        break;

                    case Rotating180:
                        user.variables.xDif = user.variables.currentBlock.getX() - user.mirrorPoint.getX();
                        user.variables.yDif = user.variables.currentBlock.getY();
                        user.variables.zDif = user.variables.currentBlock.getZ() - user.mirrorPoint.getZ();

                        XMirroring.Mirror(user);
                        break;

                    case Rotating90:
                        user.variables.xDif = user.variables.currentBlock.getX() - user.mirrorPoint.getX();
                        user.variables.yDif = user.variables.currentBlock.getY();
                        user.variables.zDif = user.variables.currentBlock.getZ() - user.mirrorPoint.getZ();

                        ZMirroring.Mirror(user);
                        break;

                    case XMirroring:
                        user.variables.xDif = user.variables.currentBlock.getX() - user.mirrorPoint.getX();
                        user.variables.yDif = user.variables.currentBlock.getY();
                        user.variables.zDif = user.variables.currentBlock.getZ() - user.mirrorPoint.getZ();

                        Rotating180.Mirror(user);
                        break;

                    case ZMirroring:
                        user.variables.xDif = user.variables.currentBlock.getX() - user.mirrorPoint.getX();
                        user.variables.yDif = user.variables.currentBlock.getY();
                        user.variables.zDif = user.variables.currentBlock.getZ() - user.mirrorPoint.getZ();

                        Rotating90.Mirror(user);

                }
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        UsersManager.Set(player);
        User user = UsersManager.GetUser(player);

        user.variables.currentBlock = event.getBlock();

        if (user.mirrorPoint != null) {
            CheckBlock(event);

            if (!user.player.isSneaking()) {
                switch (user.mirror) {
                    case CrossMirroring:
                        break;

                    case None:
                        user.variables.xDif = user.variables.currentBlock.getX() - user.mirrorPoint.getX();
                        user.variables.yDif = user.variables.currentBlock.getY();
                        user.variables.zDif = user.variables.currentBlock.getZ() - user.mirrorPoint.getZ();

                        CrossMirroring.Remove(user);
                        break;

                    case Rotating180:
                        user.variables.xDif = user.variables.currentBlock.getX() - user.mirrorPoint.getX();
                        user.variables.yDif = user.variables.currentBlock.getY();
                        user.variables.zDif = user.variables.currentBlock.getZ() - user.mirrorPoint.getZ();

                        XMirroring.Remove(user);
                        break;

                    case Rotating90:
                        user.variables.xDif = user.variables.currentBlock.getX() - user.mirrorPoint.getX();
                        user.variables.yDif = user.variables.currentBlock.getY();
                        user.variables.zDif = user.variables.currentBlock.getZ() - user.mirrorPoint.getZ();

                        ZMirroring.Remove(user);
                        break;

                    case XMirroring:
                        user.variables.xDif = user.variables.currentBlock.getX() - user.mirrorPoint.getX();
                        user.variables.yDif = user.variables.currentBlock.getY();
                        user.variables.zDif = user.variables.currentBlock.getZ() - user.mirrorPoint.getZ();

                        Rotating180.Remove(user);
                        break;

                    case ZMirroring:
                        user.variables.xDif = user.variables.currentBlock.getX() - user.mirrorPoint.getX();
                        user.variables.yDif = user.variables.currentBlock.getY();
                        user.variables.zDif = user.variables.currentBlock.getZ() - user.mirrorPoint.getZ();

                        Rotating90.Remove(user);
                }
            }
        }
    }

    public static boolean P2() {
        return p2;
    }

    public void onEnable() {
        Init();
    }

    private void Init() {
        getServer().getPluginManager().registerEvents(this, this);

        MaterialLists.Init();

        PluginManager manager = Bukkit.getServer().getPluginManager();
        final Plugin plotsquared = manager.getPlugin("PlotSquared");
        p2 = !(plotsquared != null && !plotsquared.isEnabled());
        if (p2)
            api = new PlotAPI();
    }

    private void CheckBlock(BlockBreakEvent event) {
        User user = UsersManager.GetUser(event.getPlayer());
        if ((user.variables.currentBlock.getLocation().getBlockX() == user.mirrorPoint.getLocation().getBlockX()) &&
                (user.variables.currentBlock.getLocation().getBlockY() == user.mirrorPoint.getLocation().getBlockY()) &&
                (user.variables.currentBlock.getLocation().getBlockZ() == user.mirrorPoint.getLocation().getBlockZ())) {
            event.setCancelled(true);
            user.mirrorPoint.setType(Material.AIR);
            user.mirrorBlockDestroyed = true;
        }
    }
}