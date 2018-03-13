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

            if (args.length > 0) {
                Variables.loc = UsersManager.GetUser(player).player.getLocation();
                Variables.loc.setY(Variables.loc.getY() - 1.0D);

                if ((args[0].equals("tp")) || (args[0].equals("teleport"))) {
                    Commands.Teleport(player);
                    return true;
                }

                if ((args[0].equals("change")) || (args[0].equals("ch"))) {
                    Commands.Change(args[1], player);
                    return true;
                }

                if ((args[0].equals("mirrorcross")) || (args[0].equals("c")) || (args[0].equals("cross"))) {
                    Commands.CrossMirroring(player);
                    return true;
                }

                if ((args[0].equals("mirrorx")) || (args[0].equals("mx")) || (args[0].equals("x"))) {
                    Commands.XMirroring(player);
                    return true;
                }

                if ((args[0].equals("mirrorz")) || (args[0].equals("mz")) || (args[0].equals("z"))) {
                    Commands.ZMirroring(player);
                    return true;
                }

                if ((args[0].equals("rot180")) || (args[0].equals("rotate180")) || (args[0].equals("r180")) || (args[0].equals("rotating180"))) {
                    Commands.Rotation180(player);
                    return true;
                }

                if ((args[0].equals("rot90")) || (args[0].equals("rotate90")) || (args[0].equals("r90")) || (args[0].equals("rotating90"))) {
                    Commands.Rotation90(player);
                    return true;
                }

                if ((args[0].equals("mirrorstop")) || (args[0].equals("stop")) || (args[0].equals("s"))) {
                    Commands.StopMirroring(player);
                    return true;
                }

                if ((args[0].equals("help")) || (args[0].equals("h")) || (args[0].equals("?"))) {
                    Commands.Help((Player) sender);
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

        Variables.currentBlock = event.getBlock();
        Variables.materialCopy = event.getBlock().getType();
        Variables.dataCopy = event.getBlock().getData();
        Variables.touchingBlock = event.getBlockAgainst();

        if (!UsersManager.GetUser(player).player.isSneaking()) {
            switch (UsersManager.GetUser(player).mirror) {
                case CrossMirroring:
                    break;

                case None:
                    Variables.xDif = Variables.currentBlock.getX() - UsersManager.GetUser(player).mirrorPoint.getX();
                    Variables.yDif = Variables.currentBlock.getY();
                    Variables.zDif = Variables.currentBlock.getZ() - UsersManager.GetUser(player).mirrorPoint.getZ();

                    CrossMirroring.Mirror(player);
                    break;

                case Rotating180:
                    Variables.xDif = Variables.currentBlock.getX() - UsersManager.GetUser(player).mirrorPoint.getX();
                    Variables.yDif = Variables.currentBlock.getY();
                    Variables.zDif = Variables.currentBlock.getZ() - UsersManager.GetUser(player).mirrorPoint.getZ();

                    XMirroring.Mirror(player);
                    break;

                case Rotating90:
                    Variables.xDif = Variables.currentBlock.getX() - UsersManager.GetUser(player).mirrorPoint.getX();
                    Variables.yDif = Variables.currentBlock.getY();
                    Variables.zDif = Variables.currentBlock.getZ() - UsersManager.GetUser(player).mirrorPoint.getZ();

                    ZMirroring.Mirror(player);
                    break;

                case XMirroring:
                    Variables.xDif = Variables.currentBlock.getX() - UsersManager.GetUser(player).mirrorPoint.getX();
                    Variables.yDif = Variables.currentBlock.getY();
                    Variables.zDif = Variables.currentBlock.getZ() - UsersManager.GetUser(player).mirrorPoint.getZ();

                    Rotating180.Mirror(player);
                    break;

                case ZMirroring:
                    Variables.xDif = Variables.currentBlock.getX() - UsersManager.GetUser(player).mirrorPoint.getX();
                    Variables.yDif = Variables.currentBlock.getY();
                    Variables.zDif = Variables.currentBlock.getZ() - UsersManager.GetUser(player).mirrorPoint.getZ();

                    Rotating90.Mirror(player);

            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        UsersManager.Set(player);

        Variables.currentBlock = event.getBlock();

        if (UsersManager.GetUser(player).mirrorPoint != null) {
            CheckBlock(event);

            if (!UsersManager.GetUser(player).player.isSneaking()) {
                switch (UsersManager.GetUser(player).mirror) {
                    case CrossMirroring:
                        break;

                    case None:
                        Variables.xDif = Variables.currentBlock.getX() - UsersManager.GetUser(player).mirrorPoint.getX();
                        Variables.yDif = Variables.currentBlock.getY();
                        Variables.zDif = Variables.currentBlock.getZ() - UsersManager.GetUser(player).mirrorPoint.getZ();

                        CrossMirroring.Remove(player);
                        break;

                    case Rotating180:
                        Variables.xDif = Variables.currentBlock.getX() - UsersManager.GetUser(player).mirrorPoint.getX();
                        Variables.yDif = Variables.currentBlock.getY();
                        Variables.zDif = Variables.currentBlock.getZ() - UsersManager.GetUser(player).mirrorPoint.getZ();

                        XMirroring.Remove(player);
                        break;

                    case Rotating90:
                        Variables.xDif = Variables.currentBlock.getX() - UsersManager.GetUser(player).mirrorPoint.getX();
                        Variables.yDif = Variables.currentBlock.getY();
                        Variables.zDif = Variables.currentBlock.getZ() - UsersManager.GetUser(player).mirrorPoint.getZ();

                        ZMirroring.Remove(player);
                        break;

                    case XMirroring:
                        Variables.xDif = Variables.currentBlock.getX() - UsersManager.GetUser(player).mirrorPoint.getX();
                        Variables.yDif = Variables.currentBlock.getY();
                        Variables.zDif = Variables.currentBlock.getZ() - UsersManager.GetUser(player).mirrorPoint.getZ();

                        Rotating180.Remove(player);
                        break;

                    case ZMirroring:
                        Variables.xDif = Variables.currentBlock.getX() - UsersManager.GetUser(player).mirrorPoint.getX();
                        Variables.yDif = Variables.currentBlock.getY();
                        Variables.zDif = Variables.currentBlock.getZ() - UsersManager.GetUser(player).mirrorPoint.getZ();

                        Rotating90.Remove(player);
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

        Variables.logger = getLogger();

        MaterialLists.Init();


        PluginManager manager = Bukkit.getServer().getPluginManager();
        final Plugin plotsquared = manager.getPlugin("PlotSquared");
        p2 = !(plotsquared != null && !plotsquared.isEnabled());
        if (p2)
            api = new PlotAPI();
    }

    private void CheckBlock(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if ((Variables.currentBlock.getLocation().getBlockX() == UsersManager.GetUser(player).mirrorPoint.getLocation().getBlockX()) &&
                (Variables.currentBlock.getLocation().getBlockY() == UsersManager.GetUser(player).mirrorPoint.getLocation().getBlockY()) &&
                (Variables.currentBlock.getLocation().getBlockZ() == UsersManager.GetUser(player).mirrorPoint.getLocation().getBlockZ())) {
            event.setCancelled(true);
            UsersManager.GetUser(player).mirrorPoint.setType(Material.AIR);
            UsersManager.GetUser(player).mirrorBlockDestroyed = true;
        }
    }
}