package be.mc.woutwoot.MirrorMaster.mirrors;

import be.mc.woutwoot.MirrorMaster.Mirroring;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.*;

public class CrossMirroring {

    /*void Stairs(User user) {
        Stairs data = (Stairs) user.variables.dataCopy;
        data.setFacing(BlockFace.UP);
        if (Functions.Down(user))
            data.setFacing(BlockFace.DOWN);
        switch (Functions.LookDirection(user)) {
            case 0x3:
                Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, user.variables.zDif, data, user);
                data.setFacing(BlockFace.SOUTH);
                Functions.PlaceBlock(user.variables.xDif, user.variables.yDif, -user.variables.zDif, data, user);
                Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, -user.variables.zDif, data, user);
                break;
            case 0x0:
                Functions.PlaceBlock(user.variables.xDif, user.variables.yDif, -user.variables.zDif, data, user);
                data.setFacing(BlockFace.WEST);
                Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, user.variables.zDif, data, user);
                Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, -user.variables.zDif, data, user);
                break;
            case 0x2:
                Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, user.variables.zDif, data, user);
                data.setFacing(BlockFace.NORTH);
                Functions.PlaceBlock(user.variables.xDif, user.variables.yDif, -user.variables.zDif, data, user);
                Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, -user.variables.zDif, data, user);
                break;
            case 0x1:
                Functions.PlaceBlock(user.variables.xDif, user.variables.yDif, -user.variables.zDif, data, user);
                data.setFacing(BlockFace.EAST);
                Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, user.variables.zDif, data, user);
                Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, -user.variables.zDif, data, user);
                break;
        }
    }


    void ButtonLevers(User user) {
        Switch data = (Switch) user.variables.dataCopy;
        doUDNESW(user, data);
    }

    void Torches(User user) {
        String mat = user.variables.materialCopy.name();
        if (mat.equalsIgnoreCase("REDSTONE_WALL_TORCH")) {
            RedstoneWallTorch data = (RedstoneWallTorch) user.variables.dataCopy;
            doUDNESW(user, data);
        } else if (mat.equalsIgnoreCase("WALL_TORCH")) {
            Directional data = (Directional) user.variables.dataCopy;
            doUDNESW(user, data);
        }
    }

    void Halfslabs(User user) {
        Slab data = (Slab) user.variables.dataCopy;
        if (Functions.Down(user))
            data.setType(Slab.Type.TOP);
        else
            data.setType(Slab.Type.BOTTOM);
        Functions.PlaceBlock(user.variables.xDif, user.variables.yDif, -user.variables.zDif, data, user);
        Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, user.variables.zDif, data, user);
        Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, -user.variables.zDif, data, user);
    }

    void Doors(User user) {
        Door data = (Door) user.variables.dataCopy;
        data.setHalf(Bisected.Half.BOTTOM);
        Door.Hinge hinge = data.getHinge();
        switch (Functions.LookDirection(user)) {
            case 0x3:
                data.setFacing(BlockFace.SOUTH);
                Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, user.variables.zDif, data, user);

                data.setHinge(hinge == Door.Hinge.LEFT ? Door.Hinge.RIGHT : Door.Hinge.LEFT);
                Functions.PlaceBlock(user.variables.xDif, user.variables.yDif, -user.variables.zDif, data, user);
                Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, -user.variables.zDif, data, user);

                data.setHinge(hinge == Door.Hinge.LEFT ? Door.Hinge.RIGHT : Door.Hinge.LEFT);
                data.setHalf(Bisected.Half.TOP);
                Functions.PlaceBlock(-user.variables.xDif, ++user.variables.yDif, user.variables.zDif, data, user);

                data.setHinge(hinge == Door.Hinge.LEFT ? Door.Hinge.RIGHT : Door.Hinge.LEFT);
                Functions.PlaceBlock(user.variables.xDif, user.variables.yDif, -user.variables.zDif, data, user);
                Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, -user.variables.zDif, data, user);
                break;
            case 0x0:
                Functions.PlaceBlock(user.variables.xDif, user.variables.yDif, -user.variables.zDif, data, user);

                data.setHinge(hinge == Door.Hinge.LEFT ? Door.Hinge.RIGHT : Door.Hinge.LEFT);
                Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, user.variables.zDif, data, user);
                Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, -user.variables.zDif, data, user);

                data.setHinge(hinge == Door.Hinge.LEFT ? Door.Hinge.RIGHT : Door.Hinge.LEFT);
                data.setHalf(Bisected.Half.TOP);
                Functions.PlaceBlock(user.variables.xDif, ++user.variables.yDif, -user.variables.zDif, data, user);

                data.setHinge(hinge == Door.Hinge.LEFT ? Door.Hinge.RIGHT : Door.Hinge.LEFT);
                Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, user.variables.zDif, data, user);
                Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, -user.variables.zDif, data, user);
                break;
            case 0x2:
                Functions.PlaceBlock(user.variables.xDif, user.variables.yDif, -user.variables.zDif, data, user);

                data.setHinge(hinge == Door.Hinge.LEFT ? Door.Hinge.RIGHT : Door.Hinge.LEFT);
                Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, user.variables.zDif, data, user);
                Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, -user.variables.zDif, data, user);

                data.setHinge(hinge == Door.Hinge.LEFT ? Door.Hinge.RIGHT : Door.Hinge.LEFT);
                data.setHalf(Bisected.Half.TOP);
                Functions.PlaceBlock(user.variables.xDif, ++user.variables.yDif, -user.variables.zDif, data, user);

                data.setHinge(hinge == Door.Hinge.LEFT ? Door.Hinge.RIGHT : Door.Hinge.LEFT);
                Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, user.variables.zDif, data, user);
                Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, -user.variables.zDif, data, user);
                break;
            case 0x1:
                Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, user.variables.zDif, data, user);

                data.setHinge(hinge == Door.Hinge.LEFT ? Door.Hinge.RIGHT : Door.Hinge.LEFT);
                Functions.PlaceBlock(user.variables.xDif, user.variables.yDif, -user.variables.zDif, data, user);
                Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, -user.variables.zDif, data, user);

                data.setHinge(hinge == Door.Hinge.LEFT ? Door.Hinge.RIGHT : Door.Hinge.LEFT);
                data.setHalf(Bisected.Half.TOP);
                Functions.PlaceBlock(-user.variables.xDif, ++user.variables.yDif, user.variables.zDif, data, user);

                data.setHinge(hinge == Door.Hinge.LEFT ? Door.Hinge.RIGHT : Door.Hinge.LEFT);
                Functions.PlaceBlock(user.variables.xDif, user.variables.yDif, -user.variables.zDif, data, user);
                Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, -user.variables.zDif, data, user);
                break;
        }

    }


    void Default(User user) {
        Functions.PlaceBlock(user.variables.xDif, user.variables.yDif, -user.variables.zDif, user);
        Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, user.variables.zDif, user);
        Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, -user.variables.zDif, user);
    }

    void Remove(User user) {
        Functions.RemoveBlock(user.variables.xDif, user.variables.yDif, -user.variables.zDif, user);
        Functions.RemoveBlock(-user.variables.xDif, user.variables.yDif, user.variables.zDif, user);
        Functions.RemoveBlock(-user.variables.xDif, user.variables.yDif, -user.variables.zDif, user);
    }

    private void doUDNESW(User user, Directional data) {
        if (Functions.Up(user))
            data.setFacing(BlockFace.UP);
        else
            data.setFacing(BlockFace.DOWN);
        if (Functions.East(user)) {
            Functions.PlaceBlock(user.variables.xDif, user.variables.yDif, -user.variables.zDif, data, user);
            data.setFacing(BlockFace.WEST);
            Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, user.variables.zDif, data, user);
            Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, -user.variables.zDif, data, user);
        } else {
            Functions.PlaceBlock(user.variables.xDif, user.variables.yDif, -user.variables.zDif, data, user);
            data.setFacing(BlockFace.EAST);
            Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, user.variables.zDif, data, user);
            Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, -user.variables.zDif, data, user);
        }
        if (Functions.South(user)) {
            Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, user.variables.zDif, data, user);
            data.setFacing(BlockFace.NORTH);
            Functions.PlaceBlock(user.variables.xDif, user.variables.yDif, -user.variables.zDif, data, user);
            Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, -user.variables.zDif, data, user);
        } else {
            Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, user.variables.zDif, data, user);
            data.setFacing(BlockFace.NORTH);
            Functions.PlaceBlock(user.variables.xDif, user.variables.yDif, -user.variables.zDif, data, user);
            Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, -user.variables.zDif, data, user);
        }

    }*/
}