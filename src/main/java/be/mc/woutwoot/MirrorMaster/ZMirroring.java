package be.mc.woutwoot.MirrorMaster;

import org.bukkit.entity.Player;

public class ZMirroring {

    static void Mirror(Player player) {
        if (Functions.CheckBlockMaterialLists(MaterialLists.Stairs))
            Stairs(player);
        else if (Functions.CheckBlockMaterialLists(MaterialLists.Levers))
            Levers(player);
        else if (Functions.CheckBlockMaterialLists(MaterialLists.Torches))
            Torches(player);
        else if (Functions.CheckBlockMaterialLists(MaterialLists.Halfslabs))
            Halfslabs(player);
        else if (Functions.CheckBlockMaterialLists(MaterialLists.Buttons))
            Buttons(player);
        else if (Functions.CheckBlockMaterialLists(MaterialLists.Doors))
            Doors(player);
        else
            Default(player);
    }

    private static void Stairs(Player player) {
        if (!Functions.Down()) {
            switch (Functions.LookDirection(player)) {
                case 0:
                    Functions.PlaceBlock(-Variables.xDif, Variables.yDif, Variables.zDif, (byte) 2, player);
                    break;
                case 1:
                    Functions.PlaceBlock(-Variables.xDif, Variables.yDif, Variables.zDif, (byte) 0, player);
                    break;
                case 2:
                    Functions.PlaceBlock(-Variables.xDif, Variables.yDif, Variables.zDif, (byte) 3, player);
                    break;
                case 3:
                    Functions.PlaceBlock(-Variables.xDif, Variables.yDif, Variables.zDif, (byte) 1, player);
                    break;
            }
        } else {
            switch (Functions.LookDirection(player)) {
                case 0:
                    Functions.PlaceBlock(-Variables.xDif, Variables.yDif, Variables.zDif, (byte) 6, player);
                    break;
                case 1:
                    Functions.PlaceBlock(-Variables.xDif, Variables.yDif, Variables.zDif, (byte) 4, player);
                    break;
                case 2:
                    Functions.PlaceBlock(-Variables.xDif, Variables.yDif, Variables.zDif, (byte) 7, player);
                    break;
                case 3:
                    Functions.PlaceBlock(-Variables.xDif, Variables.yDif, Variables.zDif, (byte) 5, player);
                    break;
            }

        }
    }

    private static void Levers(Player player) {
        if (Functions.Up())
            Functions.PlaceBlock(-Variables.xDif, Variables.yDif, Variables.zDif, (byte) 6, player);
        if (Functions.East())
            Functions.PlaceBlock(-Variables.xDif, Variables.yDif, Variables.zDif, (byte) 4, player);
        if (Functions.West())
            Functions.PlaceBlock(-Variables.xDif, Variables.yDif, Variables.zDif, (byte) 3, player);
        if (Functions.South())
            Functions.PlaceBlock(-Variables.xDif, Variables.yDif, Variables.zDif, (byte) 2, player);
        if (Functions.North())
            Functions.PlaceBlock(-Variables.xDif, Variables.yDif, Variables.zDif, (byte) 1, player);
    }

    private static void Torches(Player player) {
        if (Functions.Up())
            Functions.PlaceBlock(-Variables.xDif, Variables.yDif, Variables.zDif, (byte) 5, player);
        if (Functions.East())
            Functions.PlaceBlock(-Variables.xDif, Variables.yDif, Variables.zDif, (byte) 4, player);
        if (Functions.West())
            Functions.PlaceBlock(-Variables.xDif, Variables.yDif, Variables.zDif, (byte) 3, player);
        if (Functions.South())
            Functions.PlaceBlock(-Variables.xDif, Variables.yDif, Variables.zDif, (byte) 2, player);
        if (Functions.North())
            Functions.PlaceBlock(-Variables.xDif, Variables.yDif, Variables.zDif, (byte) 1, player);
    }

    private static void Halfslabs(Player player) {
        if (Functions.Down())
            Functions.PlaceBlock(-Variables.xDif, Variables.yDif, Variables.zDif, (byte) (Variables.dataCopy + 8), player);
        else
            Functions.PlaceBlock(-Variables.xDif, Variables.yDif, Variables.zDif, player);
    }

    private static void Buttons(Player player) {
        if (Functions.East())
            Functions.PlaceBlock(-Variables.xDif, Variables.yDif, Variables.zDif, (byte) 4, player);
        if (Functions.West())
            Functions.PlaceBlock(-Variables.xDif, Variables.yDif, Variables.zDif, (byte) 3, player);
        if (Functions.South())
            Functions.PlaceBlock(-Variables.xDif, Variables.yDif, Variables.zDif, (byte) 2, player);
        if (Functions.North())
            Functions.PlaceBlock(-Variables.xDif, Variables.yDif, Variables.zDif, (byte) 1, player);
    }

    private static void Doors(Player player) {
        switch (Functions.LookDirection(player)) {
            case 0:
                Functions.PlaceBlock(-Variables.xDif, Variables.yDif, Variables.zDif, (byte) 1, player);
                Functions.PlaceBlock(-Variables.xDif, ++Variables.yDif, Variables.zDif, (byte) 9, player);
                break;
            case 1:
                Functions.PlaceBlock(-Variables.xDif, Variables.yDif, Variables.zDif, (byte) 0, player);
                Functions.PlaceBlock(-Variables.xDif, ++Variables.yDif, Variables.zDif, (byte) 8, player);
                break;
            case 2:
                Functions.PlaceBlock(-Variables.xDif, Variables.yDif, Variables.zDif, (byte) 3, player);
                Functions.PlaceBlock(-Variables.xDif, ++Variables.yDif, Variables.zDif, (byte) 11, player);
                break;
            case 3:
                Functions.PlaceBlock(-Variables.xDif, Variables.yDif, Variables.zDif, (byte) 2, player);
                Functions.PlaceBlock(-Variables.xDif, ++Variables.yDif, Variables.zDif, (byte) 10, player);
                break;
        }
    }

    private static void Default(Player player) {
        Functions.PlaceBlock(-Variables.xDif, Variables.yDif, Variables.zDif, player);
    }

    static void Remove(Player player) {
        Functions.RemoveBlock(-Variables.xDif, Variables.yDif, Variables.zDif, player);
    }
}