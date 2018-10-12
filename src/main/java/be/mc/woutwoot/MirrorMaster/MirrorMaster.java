package be.mc.woutwoot.MirrorMaster;

import com.intellectualcrafters.plot.api.PlotAPI;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
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
            UsersManager.Set((Player) sender);

            if (args.length > 0) {
                Variables.loc = UsersManager.user.player.getLocation();
                Variables.loc.setY(Variables.loc.getY() - 1.0D);

                if ((args[0].equals("tp")) || (args[0].equals("teleport"))) {
                    Commands.Teleport();
                    return true;
                }

                if ((args[0].equals("change")) || (args[0].equals("ch"))) {
                    Commands.Change(args[1]);
                    return true;
                }

                if ((args[0].equals("mirrorcross")) || (args[0].equals("c")) || (args[0].equals("cross"))) {
                    Commands.CrossMirroring();
                    return true;
                }

                if ((args[0].equals("mirrorx")) || (args[0].equals("mx")) || (args[0].equals("x"))) {
                    Commands.XMirroring();
                    return true;
                }

                if ((args[0].equals("mirrorz")) || (args[0].equals("mz")) || (args[0].equals("z"))) {
                    Commands.ZMirroring();
                    return true;
                }

                if ((args[0].equals("rot180")) || (args[0].equals("rotate180")) || (args[0].equals("r180")) || (args[0].equals("rotating180"))) {
                    Commands.Rotation180();
                    return true;
                }

                if ((args[0].equals("rot90")) || (args[0].equals("rotate90")) || (args[0].equals("r90")) || (args[0].equals("rotating90"))) {
                    Commands.Rotation90();
                    return true;
                }

                if ((args[0].equals("mirrorstop")) || (args[0].equals("stop")) || (args[0].equals("s"))) {
                    Commands.StopMirroring();
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
        UsersManager.Set(event.getPlayer());

        Variables.currentBlock = event.getBlock();
        Variables.materialCopy = event.getBlock().getType();
        Variables.dataCopy = event.getBlock().getData();
        Variables.touchingBlock = event.getBlockAgainst();


        if (UsersManager.user.mirrorPoint != null) {
            if (!UsersManager.user.player.isSneaking()) {
                if (event.getPlayer().getGameMode() == GameMode.CREATIVE) {
                    switch (UsersManager.user.mirror) {
                        case None:
                            break;

                        case CrossMirroring:
                            Variables.xDif = Variables.currentBlock.getX() - UsersManager.user.mirrorPoint.getX();
                            Variables.yDif = Variables.currentBlock.getY();
                            Variables.zDif = Variables.currentBlock.getZ() - UsersManager.user.mirrorPoint.getZ();

                            CrossMirroring.Mirror();
                            break;

                        case Rotating180:
                            Variables.xDif = Variables.currentBlock.getX() - UsersManager.user.mirrorPoint.getX();
                            Variables.yDif = Variables.currentBlock.getY();
                            Variables.zDif = Variables.currentBlock.getZ() - UsersManager.user.mirrorPoint.getZ();

                            XMirroring.Mirror();
                            break;

                        case Rotating90:
                            Variables.xDif = Variables.currentBlock.getX() - UsersManager.user.mirrorPoint.getX();
                            Variables.yDif = Variables.currentBlock.getY();
                            Variables.zDif = Variables.currentBlock.getZ() - UsersManager.user.mirrorPoint.getZ();

                            ZMirroring.Mirror();
                            break;

                        case XMirroring:
                            Variables.xDif = Variables.currentBlock.getX() - UsersManager.user.mirrorPoint.getX();
                            Variables.yDif = Variables.currentBlock.getY();
                            Variables.zDif = Variables.currentBlock.getZ() - UsersManager.user.mirrorPoint.getZ();

                            Rotating180.Mirror();
                            break;

                        case ZMirroring:
                            Variables.xDif = Variables.currentBlock.getX() - UsersManager.user.mirrorPoint.getX();
                            Variables.yDif = Variables.currentBlock.getY();
                            Variables.zDif = Variables.currentBlock.getZ() - UsersManager.user.mirrorPoint.getZ();

                            Rotating90.Mirror();

                    }

                } else if (event.getPlayer().getGameMode() == GameMode.SURVIVAL) {
                    switch (UsersManager.user.mirror) {
                        case CrossMirroring:
                            break;

                        case None:
                            Variables.xDif = Variables.currentBlock.getX() - UsersManager.user.mirrorPoint.getX();
                            Variables.yDif = Variables.currentBlock.getY();
                            Variables.zDif = Variables.currentBlock.getZ() - UsersManager.user.mirrorPoint.getZ();

                            CrossMirroring.RemoveResources(event);
                            CrossMirroring.Mirror();
                            break;

                        case Rotating180:
                            Variables.xDif = Variables.currentBlock.getX() - UsersManager.user.mirrorPoint.getX();
                            Variables.yDif = Variables.currentBlock.getY();
                            Variables.zDif = Variables.currentBlock.getZ() - UsersManager.user.mirrorPoint.getZ();

                            XMirroring.RemoveResources(event);
                            XMirroring.Mirror();
                            break;

                        case Rotating90:
                            Variables.xDif = Variables.currentBlock.getX() - UsersManager.user.mirrorPoint.getX();
                            Variables.yDif = Variables.currentBlock.getY();
                            Variables.zDif = Variables.currentBlock.getZ() - UsersManager.user.mirrorPoint.getZ();

                            ZMirroring.RemoveResources(event);
                            ZMirroring.Mirror();
                            break;

                        case XMirroring:
                            Variables.xDif = Variables.currentBlock.getX() - UsersManager.user.mirrorPoint.getX();
                            Variables.yDif = Variables.currentBlock.getY();
                            Variables.zDif = Variables.currentBlock.getZ() - UsersManager.user.mirrorPoint.getZ();

                            Rotating180.RemoveResources(event);
                            Rotating180.Mirror();
                            break;

                        case ZMirroring:
                            Variables.xDif = Variables.currentBlock.getX() - UsersManager.user.mirrorPoint.getX();
                            Variables.yDif = Variables.currentBlock.getY();
                            Variables.zDif = Variables.currentBlock.getZ() - UsersManager.user.mirrorPoint.getZ();

                            Rotating90.RemoveResources(event);
                            Rotating90.Mirror();
                    }
                }
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        UsersManager.Set(event.getPlayer());

        Variables.currentBlock = event.getBlock();

        if (UsersManager.user.mirrorPoint != null) {
            CheckBlock(event);

            if (!UsersManager.user.player.isSneaking()) {
                switch (UsersManager.user.mirror) {
                    case None:
                        break;

                    case CrossMirroring:
                        Variables.xDif = Variables.currentBlock.getX() - UsersManager.user.mirrorPoint.getX();
                        Variables.yDif = Variables.currentBlock.getY();
                        Variables.zDif = Variables.currentBlock.getZ() - UsersManager.user.mirrorPoint.getZ();

                        CrossMirroring.Remove();
                        break;

                    case Rotating180:
                        Variables.xDif = Variables.currentBlock.getX() - UsersManager.user.mirrorPoint.getX();
                        Variables.yDif = Variables.currentBlock.getY();
                        Variables.zDif = Variables.currentBlock.getZ() - UsersManager.user.mirrorPoint.getZ();

                        XMirroring.Remove();
                        break;

                    case Rotating90:
                        Variables.xDif = Variables.currentBlock.getX() - UsersManager.user.mirrorPoint.getX();
                        Variables.yDif = Variables.currentBlock.getY();
                        Variables.zDif = Variables.currentBlock.getZ() - UsersManager.user.mirrorPoint.getZ();

                        ZMirroring.Remove();
                        break;

                    case XMirroring:
                        Variables.xDif = Variables.currentBlock.getX() - UsersManager.user.mirrorPoint.getX();
                        Variables.yDif = Variables.currentBlock.getY();
                        Variables.zDif = Variables.currentBlock.getZ() - UsersManager.user.mirrorPoint.getZ();

                        Rotating180.Remove();
                        break;

                    case ZMirroring:
                        Variables.xDif = Variables.currentBlock.getX() - UsersManager.user.mirrorPoint.getX();
                        Variables.yDif = Variables.currentBlock.getY();
                        Variables.zDif = Variables.currentBlock.getZ() - UsersManager.user.mirrorPoint.getZ();

                        Rotating90.Remove();
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
        if ((Variables.currentBlock.getLocation().getBlockX() == UsersManager.user.mirrorPoint.getLocation().getBlockX()) &&
                (Variables.currentBlock.getLocation().getBlockY() == UsersManager.user.mirrorPoint.getLocation().getBlockY()) &&
                (Variables.currentBlock.getLocation().getBlockZ() == UsersManager.user.mirrorPoint.getLocation().getBlockZ())) {
            event.setCancelled(true);
            UsersManager.user.mirrorPoint.setType(Material.AIR);
            UsersManager.user.mirrorBlockDestroyed = true;
        }
    }
}