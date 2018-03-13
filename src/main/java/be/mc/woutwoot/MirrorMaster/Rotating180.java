package be.mc.woutwoot.MirrorMaster;

public class Rotating180 {

    static void Mirror(User user) {
        if (Functions.CheckBlockMaterialLists(MaterialLists.Stairs, user))
            Stairs(user);
        else if (Functions.CheckBlockMaterialLists(MaterialLists.Levers, user))
            Levers(user);
        else if (Functions.CheckBlockMaterialLists(MaterialLists.Torches, user))
            Torches(user);
        else if (Functions.CheckBlockMaterialLists(MaterialLists.Halfslabs, user))
            Halfslabs(user);
        else if (Functions.CheckBlockMaterialLists(MaterialLists.Buttons, user))
            Buttons(user);
        else if (Functions.CheckBlockMaterialLists(MaterialLists.Doors, user))
            Doors(user);
        else
            Default(user);
    }

    private static void Stairs(User user) {
        if (!Functions.Down(user)) {
            switch (Functions.LookDirection(user)) {
                case 0:
                    Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, -user.variables.zDif, (byte) 3, user);
                    break;
                case 1:
                    Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, -user.variables.zDif, (byte) 0, user);
                    break;
                case 2:
                    Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, -user.variables.zDif, (byte) 2, user);
                    break;
                case 3:
                    Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, -user.variables.zDif, (byte) 1, user);
                    break;
            }

        } else {
            switch (Functions.LookDirection(user)) {
                case 0:
                    Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, -user.variables.zDif, (byte) 7, user);
                    break;
                case 1:
                    Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, -user.variables.zDif, (byte) 4, user);
                    break;
                case 2:
                    Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, -user.variables.zDif, (byte) 6, user);
                    break;
                case 3:
                    Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, -user.variables.zDif, (byte) 5, user);
                    break;
            }

        }
    }

    private static void Levers(User user) {
        if (Functions.Up(user))
            Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, -user.variables.zDif, (byte) 6, user);
        if (Functions.East(user))
            Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, -user.variables.zDif, (byte) 3, user);
        if (Functions.West(user))
            Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, -user.variables.zDif, (byte) 4, user);
        if (Functions.South(user))
            Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, -user.variables.zDif, (byte) 2, user);
        if (Functions.North(user))
            Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, -user.variables.zDif, (byte) 1, user);
    }

    private static void Torches(User user) {
        if (Functions.Up(user))
            Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, -user.variables.zDif, (byte) 5, user);
        if (Functions.East(user))
            Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, -user.variables.zDif, (byte) 3, user);
        if (Functions.West(user))
            Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, -user.variables.zDif, (byte) 4, user);
        if (Functions.South(user))
            Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, -user.variables.zDif, (byte) 2, user);
        if (Functions.North(user))
            Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, -user.variables.zDif, (byte) 1, user);
    }

    private static void Halfslabs(User user) {
        if (Functions.Down(user))
            Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, -user.variables.zDif, (byte) (user.variables.dataCopy + 8), user);
        else
            Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, -user.variables.zDif, user);
    }

    private static void Buttons(User user) {
        if (Functions.East(user))
            Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, -user.variables.zDif, (byte) 3, user);
        if (Functions.West(user))
            Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, -user.variables.zDif, (byte) 4, user);
        if (Functions.South(user))
            Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, -user.variables.zDif, (byte) 2, user);
        if (Functions.North(user))
            Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, -user.variables.zDif, (byte) 1, user);
    }

    private static void Doors(User user) {
        switch (Functions.LookDirection(user)) {
            case 0:
                Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, -user.variables.zDif, (byte) 3, user);
                Functions.PlaceBlock(-user.variables.xDif, ++user.variables.yDif, -user.variables.zDif, (byte) 11, user);
                break;
            case 1:
                Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, -user.variables.zDif, (byte) 0, user);
                Functions.PlaceBlock(-user.variables.xDif, ++user.variables.yDif, -user.variables.zDif, (byte) 8, user);
                break;
            case 2:
                Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, -user.variables.zDif, (byte) 1, user);
                Functions.PlaceBlock(-user.variables.xDif, ++user.variables.yDif, -user.variables.zDif, (byte) 9, user);
                break;
            case 3:
                Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, -user.variables.zDif, (byte) 2, user);
                Functions.PlaceBlock(-user.variables.xDif, ++user.variables.yDif, -user.variables.zDif, (byte) 10, user);
                break;
        }

    }

    private static void Default(User user) {
        Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, -user.variables.zDif, user);
    }

    static void Remove(User user) {
        Functions.RemoveBlock(-user.variables.xDif, user.variables.yDif, -user.variables.zDif, user);
    }
}