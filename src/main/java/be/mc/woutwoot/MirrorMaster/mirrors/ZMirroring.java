package be.mc.woutwoot.MirrorMaster.mirrors;

import be.mc.woutwoot.MirrorMaster.Functions;
import be.mc.woutwoot.MirrorMaster.Mirroring;
import be.mc.woutwoot.MirrorMaster.objects.AdjacentBlock;
import be.mc.woutwoot.MirrorMaster.objects.User;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.*;

public class ZMirroring implements Mirroring {

    public void Stairs(User user) {
        Stairs data = (Stairs) user.variables.dataCopy;
        data.setHalf(Bisected.Half.BOTTOM);
        if (Functions.Down(user))
            data.setHalf(Bisected.Half.TOP);
        String shape = data.getShape().name();
        data.setShape(Stairs.Shape.valueOf(shape.contains("LEFT") ? shape.replace("LEFT", "RIGHT") : shape.replace("RIGHT", "LEFT")));
        switch (Functions.LookDirection(user)) {
            case "south":
                data.setFacing(BlockFace.SOUTH);
                Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, user.variables.zDif, data, user);
                break;
            case "north":
                data.setFacing(BlockFace.NORTH);
                Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, user.variables.zDif, data, user);
                break;
            case "east":
                data.setFacing(BlockFace.WEST);
                Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, user.variables.zDif, data, user);
                break;
            case "west":
                data.setFacing(BlockFace.EAST);
                Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, user.variables.zDif, data, user);
                break;
        }

        for (BlockFace face : data.getFaces())
            if (user.variables.currentBlock.getRelative(face).getBlockData() instanceof Fence) {
                Block b = user.variables.currentBlock.getRelative(face);
                Fence fence = doFence(b, user);
                fence.setFace(doBlockFace(face).getOppositeFace(), true);
                Functions.PlaceBlockRelative(-user.variables.xDif, user.variables.yDif, user.variables.zDif, fence, b.getType(), user, doBlockFace(face));
            }
    }
    
    public void Fences(User user) {
        Fence data = (Fence) user.variables.dataCopy;
        for (AdjacentBlock ab : Functions.getRelatives(user.variables.currentBlock)) {
            if (user.variables.currentBlock.getType().equals(Material.NETHER_BRICK_FENCE)) {
                if (ab.block.getType().name().toLowerCase().contains("gate")) {
                    Gate gate = (Gate) ab.block.getBlockData();
                    if (!(gate.getFacing().equals(ab.blockFace) || gate.getFacing().getOppositeFace().equals(ab.blockFace)))
                        data.setFace(doBlockFace(ab.blockFace), true);
                    else
                        data.setFace(doBlockFace(ab.blockFace), false);
                } else if (ab.block.getType().equals(Material.NETHER_BRICK_FENCE)) {
                    data.setFace(doBlockFace(ab.blockFace), true);
                    Fence fence = doFence(ab.block, user);
                    Functions.PlaceBlockRelative(-user.variables.xDif, user.variables.yDif, user.variables.zDif, fence, ab.block.getType(), user, doBlockFace(ab.blockFace));
                } else
                    data = genericFence(data, ab);
            } else if (ab.block.getType().name().toLowerCase().contains("cobblestone_wall")) {
                if (ab.block.getType().name().toLowerCase().contains("gate")) {
                    Gate gate = (Gate) ab.block.getBlockData();
                    if ((gate.getFacing().equals(ab.blockFace) || gate.getFacing().getOppositeFace().equals(ab.blockFace)))
                        data.setFace(doBlockFace(ab.blockFace), false);
                    else {
                        data.setFace(doBlockFace(ab.blockFace), true);
                        gate.setInWall(true);
                        Functions.PlaceBlockRelative(-user.variables.xDif, user.variables.yDif, user.variables.zDif, gate, ab.block.getType(), user, doBlockFace(ab.blockFace));
                    }
                } else if (ab.block.getType().name().toLowerCase().contains("cobblestone_wall")) {
                    data.setFace(doBlockFace(ab.blockFace), true);
                    Fence wall = doFence(ab.block, user);
                    if (((wall.getFaces().contains(BlockFace.EAST) && wall.getFaces().contains(BlockFace.WEST)) && !(wall.getFaces().contains(BlockFace.NORTH) || wall.getFaces().contains(BlockFace.SOUTH)))
                            || (!(wall.getFaces().contains(BlockFace.EAST) || wall.getFaces().contains(BlockFace.WEST)) && (wall.getFaces().contains(BlockFace.NORTH) && wall.getFaces().contains(BlockFace.SOUTH))))
                        wall.setFace(BlockFace.UP, false);
                    else
                        wall.setFace(BlockFace.UP, true);
                    Functions.PlaceBlockRelative(-user.variables.xDif, user.variables.yDif, user.variables.zDif, wall, ab.block.getType(), user, doBlockFace(ab.blockFace));
                } else
                    data = genericFence(data, ab);
            } else {
                if (ab.block.getType().name().toLowerCase().contains("gate")) {
                    Gate gate = (Gate) ab.block.getBlockData();
                    if (!(gate.getFacing().equals(ab.blockFace) || gate.getFacing().getOppositeFace().equals(ab.blockFace)))
                        data.setFace(doBlockFace(ab.blockFace), true);
                    else
                        data.setFace(doBlockFace(ab.blockFace), false);
                } else if (ab.block.getType().name().toLowerCase().contains("fence")) {
                    data.setFace(doBlockFace(ab.blockFace), true);
                    Fence fence = doFence(ab.block, user);
                    Functions.PlaceBlockRelative(-user.variables.xDif, user.variables.yDif, user.variables.zDif, fence, ab.block.getType(), user, doBlockFace(ab.blockFace));
                } else {
                    genericFence(data, ab);
                }
            }
        }
        if (data.getMaterial().name().toLowerCase().contains("cobblestone_wall"))
            if ((data.getFaces().contains(BlockFace.EAST) && data.getFaces().contains(BlockFace.WEST)) && !(data.getFaces().contains(BlockFace.NORTH) || data.getFaces().contains(BlockFace.SOUTH))
                    || !(data.getFaces().contains(BlockFace.EAST) || data.getFaces().contains(BlockFace.WEST)) && (data.getFaces().contains(BlockFace.NORTH) && data.getFaces().contains(BlockFace.SOUTH)))
                data.setFace(BlockFace.UP, false);
            else
                data.setFace(BlockFace.UP, true);
        Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, user.variables.zDif, data, user);
    }

    private Fence doFence(Block b, User user) {
        Fence data = (Fence) b.getBlockData();
        for (AdjacentBlock ab : Functions.getRelatives(b)) {
            if (b.getType().equals(Material.AIR)) {
                data.setFace(doBlockFace(ab.blockFace), true);
                continue;
            }
            if (b.getType().equals(Material.NETHER_BRICK_FENCE)) {
                if (ab.block.getType().name().toLowerCase().contains("gate")) {
                    Gate gate = (Gate) ab.block.getBlockData();
                    if (!(gate.getFacing().equals(ab.blockFace) || gate.getFacing().getOppositeFace().equals(ab.blockFace)))
                        data.setFace(doBlockFace(ab.blockFace), true);
                    else
                        data.setFace(doBlockFace(ab.blockFace), false);
                } else if (ab.block.getType().equals(Material.NETHER_BRICK_FENCE))
                    data.setFace(doBlockFace(ab.blockFace), true);
                else
                    data = genericFence(data, ab);
            } else if (b.getType().name().toLowerCase().contains("cobblestone_wall")) {
                if (ab.block.getType().name().toLowerCase().contains("cobblestone_wall"))
                    data.setFace(doBlockFace(ab.blockFace), true);
                else if (ab.block.getType().name().toLowerCase().contains("gate")) {
                    Gate gate = (Gate) ab.block.getBlockData();
                    if (!(gate.getFacing().equals(ab.blockFace) || gate.getFacing().getOppositeFace().equals(ab.blockFace))) {
                        data.setFace(doBlockFace(ab.blockFace), true);
                        gate.setInWall(true);
                        Functions.PlaceBlockRelative(-user.variables.xDif, user.variables.yDif, user.variables.zDif, gate, ab.block.getType(), user, doBlockFace(ab.blockFace));
                    } else
                        data.setFace(doBlockFace(ab.blockFace), false);
                } else
                    data = genericFence(data, ab);
            } else {
                if (ab.block.getType().name().toLowerCase().contains("gate")) {
                    Gate gate = (Gate) ab.block.getBlockData();
                    if (!(gate.getFacing().equals(ab.blockFace) || gate.getFacing().getOppositeFace().equals(ab.blockFace)))
                        data.setFace(doBlockFace(ab.blockFace), true);
                    else
                        data.setFace(doBlockFace(ab.blockFace), false);
                } else if (ab.block.getType().name().toLowerCase().contains("fence"))
                    data.setFace(doBlockFace(ab.blockFace), true);
                else
                    data = genericFence(data, ab);
            }
        }
        return data;
    }

    private BlockFace doBlockFace(BlockFace bf) {
        if (bf.equals(BlockFace.SOUTH) || bf.equals(BlockFace.NORTH))
            return bf;
        return bf.equals(BlockFace.EAST) ? BlockFace.WEST : BlockFace.EAST;
    }

    private Fence genericFence(Fence data, AdjacentBlock ab) {
        if (ab.block.getType().isSolid() && ab.block.getType().isOccluding())
            data.setFace(doBlockFace(ab.blockFace), true);
        else if (ab.block.getBlockData() instanceof Slab && ((Slab) ab.block.getBlockData()).getType().equals(Slab.Type.DOUBLE))
            data.setFace(doBlockFace(ab.blockFace), true);
        else if (ab.block.getBlockData() instanceof Stairs) {
            if (((Stairs) ab.block.getBlockData()).getFacing().getOppositeFace().equals(ab.blockFace))
                data.setFace(doBlockFace(ab.blockFace), true);
        } else
            data.setFace(doBlockFace(ab.blockFace), false);
        return data;
    }

    public void Gates(User user) {
        Gate data = (Gate) user.variables.dataCopy;
        data.setFacing(doBlockFace(data.getFacing()));
        Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, user.variables.zDif, data, user);
        for (AdjacentBlock ab : Functions.getRelatives(user.variables.currentBlock))
            if (ab.block.getBlockData() instanceof Fence) {
                Fence fence = doFence(ab.block, user);
                Functions.PlaceBlockRelative(-user.variables.xDif, user.variables.yDif, user.variables.zDif, fence, ab.block.getType(), user, doBlockFace(ab.blockFace));
            }
    }

    public void ButtonLevers(User user) {
        Switch data = (Switch) user.variables.dataCopy;
        if (Functions.Up(user)) {
            switch (Functions.LookDirection(user)) {
                case "east":
                    data.setFacing(BlockFace.WEST);
                    Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, user.variables.zDif, data, user);
                    break;
                case "west":
                    data.setFacing(BlockFace.EAST);
                    Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, user.variables.zDif, data, user);
                    break;
                case "south":
                    data.setFacing(BlockFace.SOUTH);
                    Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, user.variables.zDif, data, user);
                    break;
                case "north":
                    data.setFacing(BlockFace.NORTH);
                    Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, user.variables.zDif, data, user);
                    break;
            }
            data.setFace(Switch.Face.FLOOR);
        } else if (Functions.Down(user))
            data.setFace(Switch.Face.CEILING);
        if (Functions.East(user))
            data.setFacing(BlockFace.WEST);
        else if (Functions.West(user))
            data.setFacing(BlockFace.EAST);
        else if (Functions.South(user))
            data.setFacing(BlockFace.SOUTH);
        else if (Functions.North(user))
            data.setFacing(BlockFace.NORTH);
        Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, user.variables.zDif, data, user);
    }

    public void Torches(User user) {
        String mat = user.variables.materialCopy.name();
        if (mat.equalsIgnoreCase("REDSTONE_WALL_TORCH")) {
            RedstoneWallTorch data = (RedstoneWallTorch) user.variables.dataCopy;
            if (Functions.East(user))
                data.setFacing(BlockFace.WEST);
            else
                data.setFacing(BlockFace.EAST);
            if (Functions.South(user))
                data.setFacing(BlockFace.SOUTH);
            else
                data.setFacing(BlockFace.NORTH);
            Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, user.variables.zDif, data, user);
        } else if (mat.equalsIgnoreCase("WALL_TORCH")) {
            Directional data = (Directional) user.variables.dataCopy;
            if (Functions.East(user))
                data.setFacing(BlockFace.WEST);
            else
                data.setFacing(BlockFace.EAST);
            if (Functions.South(user))
                data.setFacing(BlockFace.SOUTH);
            else
                data.setFacing(BlockFace.NORTH);
            Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, user.variables.zDif, data, user);
        }
    }

    public void Halfslabs(User user) {
        Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, user.variables.zDif, user.variables.dataCopy, user);
        Slab data = (Slab) user.variables.dataCopy;
        if (data.getType().equals(Slab.Type.DOUBLE)) {
            for (AdjacentBlock ab : Functions.getRelatives(user.variables.currentBlock))
                if (ab.block.getBlockData() instanceof Fence) {
                    Fence fence = doFence(ab.block, user);
                    Functions.PlaceBlockRelative(-user.variables.xDif, user.variables.yDif, user.variables.zDif, fence, ab.block.getType(), user, doBlockFace(ab.blockFace));
                }
        }
    }

    public void Doors(User user) {
        Door data = (Door) user.variables.dataCopy;
        Door.Hinge hinge = data.getHinge();
        switch (Functions.LookDirection(user)) {
            case "south":
                data.setFacing(BlockFace.SOUTH);
                data.setHinge(hinge == Door.Hinge.LEFT ? Door.Hinge.RIGHT : Door.Hinge.LEFT);
                data.setHalf(Bisected.Half.BOTTOM);
                Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, user.variables.zDif, data, user);
                data.setHalf(Bisected.Half.TOP);
                Functions.PlaceBlock(-user.variables.xDif, ++user.variables.yDif, user.variables.zDif, data, user);
                break;
            case "north":
                data.setFacing(BlockFace.NORTH);
                data.setHinge(hinge == Door.Hinge.LEFT ? Door.Hinge.RIGHT : Door.Hinge.LEFT);
                data.setHalf(Bisected.Half.BOTTOM);
                Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, user.variables.zDif, data, user);
                data.setHalf(Bisected.Half.TOP);
                Functions.PlaceBlock(-user.variables.xDif, ++user.variables.yDif, user.variables.zDif, data, user);
                break;
            case "east":
                data.setFacing(BlockFace.WEST);
                data.setHalf(Bisected.Half.BOTTOM);
                Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, user.variables.zDif, data, user);
                data.setHalf(Bisected.Half.TOP);
                Functions.PlaceBlock(-user.variables.xDif, ++user.variables.yDif, user.variables.zDif, data, user);
                break;
            case "west":
                data.setFacing(BlockFace.EAST);
                data.setHalf(Bisected.Half.BOTTOM);
                Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, user.variables.zDif, data, user);
                data.setHalf(Bisected.Half.TOP);
                Functions.PlaceBlock(-user.variables.xDif, ++user.variables.yDif, user.variables.zDif, data, user);
                break;
        }
    }

    public void Default(User user) {
        Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, user.variables.zDif, user);
        if (user.variables.currentBlock.getType().isSolid())
            for (AdjacentBlock ab : Functions.getRelatives(user.variables.currentBlock))
                if (ab.block.getBlockData() instanceof Fence) {
                    Fence fence = doFence(ab.block, user);
                    Functions.PlaceBlockRelative(-user.variables.xDif, user.variables.yDif, user.variables.zDif, fence, ab.block.getType(), user, doBlockFace(ab.blockFace));
                }
    }

    public void Remove(User user) {
        Functions.RemoveBlock(-user.variables.xDif, user.variables.yDif, user.variables.zDif, user);
        for (AdjacentBlock ab : Functions.getRelatives(user.variables.currentBlock))
            if (ab.block.getBlockData() instanceof Fence) {
                Fence fence = doFence(ab.block, user);
                Functions.PlaceBlockRelative(-user.variables.xDif, user.variables.yDif, user.variables.zDif, fence, ab.block.getType(), user, doBlockFace(ab.blockFace));
            }
    }
}