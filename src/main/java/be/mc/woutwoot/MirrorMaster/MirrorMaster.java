package be.mc.woutwoot.MirrorMaster;

import com.intellectualcrafters.plot.api.PlotAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
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
    static PlotAPI api;

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("mm")) {
            Player player = (Player) sender;
            UsersManager.Set(player);
            User user = UsersManager.GetUser(player);

            if (args.length > 0) {
                Variables variables = user.variables;
                variables.loc = user.player.getLocation();
                variables.loc.setY(user.variables.loc.getY() - 1.0D);
                user.variables(variables);
                switch (args[0].toLowerCase()) {
                    case "tp":
                    case "teleport":
                        Commands.Teleport(user);
                        return true;
                    case "change":
                    case "ch":
                        Commands.Change(args[1], user);
                        return true;
                    case "mirrorcross":
                    case "c":
                    case "cross":
                        Commands.CrossMirroring(user);
                        return true;
                    case "mirrorx":
                    case "mx":
                    case "x":
                        Commands.XMirroring(user);
                        return true;
                    case "mirrorz":
                    case "mz":
                    case "z":
                        Commands.ZMirroring(user);
                        return true;
                    case "rot180":
                    case "rotate180":
                    case "r180":
                    case "180":
                        Commands.Rotation180(user);
                        return true;
                    case "rot90":
                    case "rotate90":
                    case "r90":
                    case "90":
                        Commands.Rotation90(user);
                        return true;
                    case "mirrorstop":
                    case "stop":
                    case "s":
                        Commands.StopMirroring(user);
                        return true;
                    case "help":
                    case "h":
                    case "?":
                    default:
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

        Variables variables = user.variables;

        variables.currentBlock = event.getBlock();
        variables.materialCopy = event.getBlock().getType();
        variables.dataCopy = event.getBlock().getData();
        variables.touchingBlock = event.getBlockAgainst();

        if (user.mirrorPoint != null) {
            if (!user.player.isSneaking()) {
                switch (user.mirror) {
                    case None:
                        break;

                    case CrossMirroring:
                        variables.xDif = user.variables.currentBlock.getX() - user.mirrorPoint.getX();
                        variables.yDif = user.variables.currentBlock.getY();
                        variables.zDif = user.variables.currentBlock.getZ() - user.mirrorPoint.getZ();

                        user.variables(variables);
                        CrossMirroring.Mirror(user);
                        break;

                    case Rotating180:
                        variables.xDif = user.variables.currentBlock.getX() - user.mirrorPoint.getX();
                        variables.yDif = user.variables.currentBlock.getY();
                        variables.zDif = user.variables.currentBlock.getZ() - user.mirrorPoint.getZ();

                        user.variables(variables);
                        XMirroring.Mirror(user);
                        break;

                    case Rotating90:
                        variables.xDif = user.variables.currentBlock.getX() - user.mirrorPoint.getX();
                        variables.yDif = user.variables.currentBlock.getY();
                        variables.zDif = user.variables.currentBlock.getZ() - user.mirrorPoint.getZ();

                        user.variables(variables);
                        ZMirroring.Mirror(user);
                        break;

                    case XMirroring:
                        variables.xDif = user.variables.currentBlock.getX() - user.mirrorPoint.getX();
                        variables.yDif = user.variables.currentBlock.getY();
                        variables.zDif = user.variables.currentBlock.getZ() - user.mirrorPoint.getZ();

                        user.variables(variables);
                        Rotating180.Mirror(user);
                        break;

                    case ZMirroring:
                        variables.xDif = user.variables.currentBlock.getX() - user.mirrorPoint.getX();
                        variables.yDif = user.variables.currentBlock.getY();
                        variables.zDif = user.variables.currentBlock.getZ() - user.mirrorPoint.getZ();

                        user.variables(variables);
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

        Variables variables = user.variables;

        variables.currentBlock = event.getBlock();

        if (user.mirrorPoint != null) {
            CheckBlock(event);

            if (!user.player.isSneaking()) {
                switch (user.mirror) {
                    case None:
                        break;

                    case CrossMirroring:
                        variables.xDif = user.variables.currentBlock.getX() - user.mirrorPoint.getX();
                        variables.yDif = user.variables.currentBlock.getY();
                        variables.zDif = user.variables.currentBlock.getZ() - user.mirrorPoint.getZ();

                        user.variables(variables);
                        CrossMirroring.Remove(user);
                        break;

                    case Rotating180:
                        variables.xDif = user.variables.currentBlock.getX() - user.mirrorPoint.getX();
                        variables.yDif = user.variables.currentBlock.getY();
                        variables.zDif = user.variables.currentBlock.getZ() - user.mirrorPoint.getZ();

                        user.variables(variables);
                        XMirroring.Remove(user);
                        break;

                    case Rotating90:
                        variables.xDif = user.variables.currentBlock.getX() - user.mirrorPoint.getX();
                        variables.yDif = user.variables.currentBlock.getY();
                        variables.zDif = user.variables.currentBlock.getZ() - user.mirrorPoint.getZ();

                        user.variables(variables);
                        ZMirroring.Remove(user);
                        break;

                    case XMirroring:
                        variables.xDif = user.variables.currentBlock.getX() - user.mirrorPoint.getX();
                        variables.yDif = user.variables.currentBlock.getY();
                        variables.zDif = user.variables.currentBlock.getZ() - user.mirrorPoint.getZ();

                        user.variables(variables);
                        Rotating180.Remove(user);
                        break;

                    case ZMirroring:
                        variables.xDif = user.variables.currentBlock.getX() - user.mirrorPoint.getX();
                        variables.yDif = user.variables.currentBlock.getY();
                        variables.zDif = user.variables.currentBlock.getZ() - user.mirrorPoint.getZ();

                        user.variables(variables);
                        Rotating90.Remove(user);
                }
            }
        }
    }

    static boolean P2() {
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
            Block b = user.mirrorPoint;
            b.setType(Material.AIR);
            user.mirrorPoint(b);
            user.mirrorBlockDestroyed(true);
        }
    }
}