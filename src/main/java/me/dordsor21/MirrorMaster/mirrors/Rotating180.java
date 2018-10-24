package me.dordsor21.MirrorMaster.mirrors;

public class Rotating180 {

    /*void Stairs(User user) {
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

    void Levers(User user) {
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

    void Torches(User user) {
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

    void Halfslabs(User user) {
        if (Functions.Down(user))
            Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, -user.variables.zDif, (byte) (user.variables.dataCopy + 8), user);
        else
            Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, -user.variables.zDif, user);
    }

    void Buttons(User user) {
        if (Functions.East(user))
            Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, -user.variables.zDif, (byte) 3, user);
        if (Functions.West(user))
            Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, -user.variables.zDif, (byte) 4, user);
        if (Functions.South(user))
            Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, -user.variables.zDif, (byte) 2, user);
        if (Functions.North(user))
            Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, -user.variables.zDif, (byte) 1, user);
    }

    void Doors(User user) {
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

    void Default(User user) {
        Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, -user.variables.zDif, user);
    }

    void Remove(User user) {
        Functions.RemoveBlock(-user.variables.xDif, user.variables.yDif, -user.variables.zDif, user);
    }*/
}