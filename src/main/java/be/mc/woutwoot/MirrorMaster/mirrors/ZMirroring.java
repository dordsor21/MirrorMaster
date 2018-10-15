package be.mc.woutwoot.MirrorMaster.mirrors;

import be.mc.woutwoot.MirrorMaster.util.Functions;
import be.mc.woutwoot.MirrorMaster.objects.AdjacentBlock;
import be.mc.woutwoot.MirrorMaster.objects.User;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.*;

import static org.bukkit.block.BlockFace.*;

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
                data.setFacing(SOUTH);
                Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, user.variables.zDif, data, user);
                break;
            case "north":
                data.setFacing(NORTH);
                Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, user.variables.zDif, data, user);
                break;
            case "east":
                data.setFacing(WEST);
                Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, user.variables.zDif, data, user);
                break;
            case "west":
                data.setFacing(EAST);
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
            if (user.variables.currentBlock.getType() == Material.NETHER_BRICK_FENCE) {
                if (ab.block.getType().name().toLowerCase().contains("gate")) {
                    Gate gate = (Gate) ab.block.getBlockData().clone();
                    if (!((gate.getFacing() == ab.blockFace) || gate.getFacing().getOppositeFace() == ab.blockFace))
                        data.setFace(doBlockFace(ab.blockFace), true);
                    else
                        data.setFace(doBlockFace(ab.blockFace), false);
                } else if (ab.block.getType() == Material.NETHER_BRICK_FENCE) {
                    data.setFace(doBlockFace(ab.blockFace), true);
                    Fence fence = doFence(ab.block, user);
                    Functions.PlaceBlockRelative(-user.variables.xDif, user.variables.yDif, user.variables.zDif, fence, ab.block.getType(), user, doBlockFace(ab.blockFace));
                } else
                    data = genericFence(data, ab);
            } else if (user.variables.currentBlock.getType().name().toLowerCase().contains("cobblestone_wall")) {
                if (ab.block.getType().name().toLowerCase().contains("gate")) {
                    Gate gate = (Gate) ab.block.getBlockData().clone();
                    if ((gate.getFacing() == ab.blockFace || gate.getFacing().getOppositeFace() == ab.blockFace))
                        data.setFace(doBlockFace(ab.blockFace), false);
                    else {
                        data.setFace(doBlockFace(ab.blockFace), true);
                        gate.setInWall(true);
                        Functions.PlaceBlockRelative(-user.variables.xDif, user.variables.yDif, user.variables.zDif, gate, ab.block.getType(), user, doBlockFace(ab.blockFace));
                    }
                } else if (ab.block.getType().name().toLowerCase().contains("cobblestone_wall")) {
                    data.setFace(doBlockFace(ab.blockFace), true);
                    Fence wall = doFence(ab.block, user);
                    Functions.PlaceBlockRelative(-user.variables.xDif, user.variables.yDif, user.variables.zDif, wall, ab.block.getType(), user, doBlockFace(ab.blockFace));
                } else
                    data = genericFence(data, ab);
            } else {
                if (ab.block.getType().name().toLowerCase().contains("gate")) {
                    Gate gate = (Gate) ab.block.getBlockData().clone();
                    if (!((gate.getFacing() == ab.blockFace) || gate.getFacing().getOppositeFace() == ab.blockFace))
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
            if ((data.getFaces().contains(EAST) && data.getFaces().contains(WEST)) && !(data.getFaces().contains(NORTH) || data.getFaces().contains(SOUTH))
                    || !(data.getFaces().contains(EAST) || data.getFaces().contains(WEST)) && (data.getFaces().contains(NORTH) && data.getFaces().contains(SOUTH)))
                data.setFace(UP, false);
            else
                data.setFace(UP, true);
        Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, user.variables.zDif, data, user);
    }

    private Fence doFence(Block b, User user) {
        Fence data = (Fence) b.getBlockData().clone();
        for (AdjacentBlock ab : Functions.getRelatives(b)) {
            if ((ab.block.getType() == Material.AIR || ab.block == user.variables.currentBlock) && !user.variables.placing) {
                data.setFace(doBlockFace(ab.blockFace), false);
                continue;
            }
            if (b.getType() == Material.NETHER_BRICK_FENCE) {
                if (ab.block.getType().name().toLowerCase().contains("gate")) {
                    Gate gate = (Gate) ab.block.getBlockData().clone();
                    if (!((gate.getFacing() == ab.blockFace) || gate.getFacing().getOppositeFace() == ab.blockFace))
                        data.setFace(doBlockFace(ab.blockFace), true);
                    else
                        data.setFace(doBlockFace(ab.blockFace), false);
                } else if (ab.block.getType() == Material.NETHER_BRICK_FENCE)
                    data.setFace(doBlockFace(ab.blockFace), true);
                else
                    data = genericFence(data, ab);
            } else if (b.getType().name().toLowerCase().contains("cobblestone_wall")) {
                if (ab.block.getType().name().toLowerCase().contains("cobblestone_wall"))
                    data.setFace(doBlockFace(ab.blockFace), true);
                else if (ab.block.getType().name().toLowerCase().contains("gate")) {
                    Gate gate = (Gate) ab.block.getBlockData().clone();
                    if (!((gate.getFacing() == ab.blockFace) || gate.getFacing().getOppositeFace() == ab.blockFace)) {
                        data.setFace(doBlockFace(ab.blockFace), true);
                        gate.setInWall(true);
                        if (ab.block == user.variables.currentBlock) {
                            if (user.variables.placing)
                                Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, user.variables.zDif, gate, user);
                        } else
                            Functions.PlaceBlockRelative(-user.variables.xDif, user.variables.yDif, user.variables.zDif, gate, ab.block.getType(), user, doBlockFace(ab.blockFace));
                    } else
                        data.setFace(doBlockFace(ab.blockFace), false);
                } else
                    data = genericFence(data, ab);
                if (((data.getFaces().contains(EAST) && data.getFaces().contains(WEST)) && !(data.getFaces().contains(NORTH) || data.getFaces().contains(SOUTH)))
                        || (!(data.getFaces().contains(EAST) || data.getFaces().contains(WEST)) && (data.getFaces().contains(NORTH) && data.getFaces().contains(SOUTH))))
                    data.setFace(UP, false);
                else
                    data.setFace(UP, true);
            } else {
                if (ab.block.getType().name().toLowerCase().contains("gate")) {
                    Gate gate = (Gate) ab.block.getBlockData().clone();
                    if (!((gate.getFacing() == ab.blockFace) || gate.getFacing().getOppositeFace() == ab.blockFace))
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
        if (bf == SOUTH || bf == NORTH || bf == UP || bf == DOWN)
            return bf;
        return bf == EAST ? WEST : EAST;
    }

    private Fence genericFence(Fence data, AdjacentBlock ab) {
        if (ab.block.getType().isSolid() && ab.block.getType().isOccluding())
            data.setFace(doBlockFace(ab.blockFace), true);
        else if (ab.block.getBlockData() instanceof Slab && ((Slab) ab.block.getBlockData()).getType() == Slab.Type.DOUBLE)
            data.setFace(doBlockFace(ab.blockFace), true);
        else if (ab.block.getBlockData() instanceof Stairs) {
            if (((Stairs) ab.block.getBlockData()).getFacing().getOppositeFace() == ab.blockFace)
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
                    data.setFacing(WEST);
                    Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, user.variables.zDif, data, user);
                    break;
                case "west":
                    data.setFacing(EAST);
                    Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, user.variables.zDif, data, user);
                    break;
                case "south":
                    data.setFacing(SOUTH);
                    Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, user.variables.zDif, data, user);
                    break;
                case "north":
                    data.setFacing(NORTH);
                    Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, user.variables.zDif, data, user);
                    break;
            }
            data.setFace(Switch.Face.FLOOR);
        } else if (Functions.Down(user))
            data.setFace(Switch.Face.CEILING);
        if (Functions.East(user))
            data.setFacing(WEST);
        else if (Functions.West(user))
            data.setFacing(EAST);
        else if (Functions.South(user))
            data.setFacing(SOUTH);
        else if (Functions.North(user))
            data.setFacing(NORTH);
        Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, user.variables.zDif, data, user);
    }

    public void Torches(User user) {
        String mat = user.variables.materialCopy.name();
        if (mat.equalsIgnoreCase("REDSTONE_WALL_TORCH")) {
            RedstoneWallTorch data = (RedstoneWallTorch) user.variables.dataCopy;
            if (Functions.East(user))
                data.setFacing(WEST);
            else
                data.setFacing(EAST);
            if (Functions.South(user))
                data.setFacing(SOUTH);
            else
                data.setFacing(NORTH);
            Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, user.variables.zDif, data, user);
        } else if (mat.equalsIgnoreCase("WALL_TORCH")) {
            Directional data = (Directional) user.variables.dataCopy;
            if (Functions.East(user))
                data.setFacing(WEST);
            else
                data.setFacing(EAST);
            if (Functions.South(user))
                data.setFacing(SOUTH);
            else
                data.setFacing(NORTH);
            Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, user.variables.zDif, data, user);
        }
    }

    public void Halfslabs(User user) {
        Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, user.variables.zDif, user.variables.dataCopy, user);
        Slab data = (Slab) user.variables.dataCopy;
        if (data.getType() == Slab.Type.DOUBLE) {
            for (AdjacentBlock ab : Functions.getRelatives(user.variables.currentBlock))
                if (ab.block.getBlockData() instanceof Fence) {
                    Fence fence = doFence(ab.block, user);
                    Functions.PlaceBlockRelative(-user.variables.xDif, user.variables.yDif, user.variables.zDif, fence, ab.block.getType(), user, doBlockFace(ab.blockFace));
                }
        }
    }

    public void Doors(User user) {
        Door datab = (Door) user.variables.currentBlock.getType().createBlockData();
        Door datat = (Door) user.variables.currentBlock.getType().createBlockData();
        Door.Hinge hinge = ((Door) user.variables.dataCopy).getHinge();
        switch (Functions.LookDirection(user)) {
            case "south":
                datab.setFacing(SOUTH);
                datab.setHinge(hinge == Door.Hinge.LEFT ? Door.Hinge.RIGHT : Door.Hinge.LEFT);
                datab.setHalf(Bisected.Half.BOTTOM);
                Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, user.variables.zDif, datab, user);

                datat.setFacing(SOUTH);
                datat.setHinge(hinge == Door.Hinge.LEFT ? Door.Hinge.RIGHT : Door.Hinge.LEFT);
                datat.setHalf(Bisected.Half.TOP);
                Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif + 1, user.variables.zDif, datat, user);
                break;
            case "north":
                datab.setFacing(NORTH);
                datab.setHinge(hinge == Door.Hinge.LEFT ? Door.Hinge.RIGHT : Door.Hinge.LEFT);
                datab.setHalf(Bisected.Half.BOTTOM);
                Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, user.variables.zDif, datab, user);

                datat.setFacing(NORTH);
                datat.setHinge(hinge == Door.Hinge.LEFT ? Door.Hinge.RIGHT : Door.Hinge.LEFT);
                datat.setHalf(Bisected.Half.TOP);
                Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif + 1, user.variables.zDif, datat, user);
                break;
            case "east":
                datab.setFacing(WEST);
                datab.setHinge(hinge == Door.Hinge.LEFT ? Door.Hinge.RIGHT : Door.Hinge.LEFT);
                datab.setHalf(Bisected.Half.BOTTOM);
                Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, user.variables.zDif, datab, user);

                datat.setFacing(WEST);
                datat.setHinge(hinge == Door.Hinge.LEFT ? Door.Hinge.RIGHT : Door.Hinge.LEFT);
                datat.setHalf(Bisected.Half.TOP);
                Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif + 1, user.variables.zDif, datat, user);
                break;
            case "west":
                datab.setFacing(EAST);
                datab.setHinge(hinge == Door.Hinge.LEFT ? Door.Hinge.RIGHT : Door.Hinge.LEFT);
                datab.setHalf(Bisected.Half.BOTTOM);
                Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, user.variables.zDif, datab, user);

                datat.setFacing(EAST);
                datat.setHinge(hinge == Door.Hinge.LEFT ? Door.Hinge.RIGHT : Door.Hinge.LEFT);
                datat.setHalf(Bisected.Half.TOP);
                Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif + 1, user.variables.zDif, datat, user);
                break;
        }
    }

    public void Trapdoors(User user) {
        TrapDoor data = (TrapDoor) user.variables.dataCopy;
        data.setFacing(doBlockFace(data.getFacing()));
        Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, user.variables.zDif, data, user);
    }

    public void Pistons(User user) {
        Piston data = (Piston) user.variables.dataCopy;
        data.setFacing(doBlockFace(data.getFacing()));
        Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, user.variables.zDif, data, user);
    }

    public void EndRods(User user) {
        Directional data = (Directional) user.variables.dataCopy;
        data.setFacing(doBlockFace(data.getFacing()));
        Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, user.variables.zDif, data, user);
    }

    public void Chests(User user) {
        if (user.variables.currentBlock.getType() == Material.ENDER_CHEST) {
            EnderChest data = (EnderChest) user.variables.dataCopy;
            data.setFacing(doBlockFace(data.getFacing()));
            Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, user.variables.zDif, data, user);
        } else {
            Chest data = (Chest) user.variables.dataCopy.clone();
            data.setFacing(doBlockFace(data.getFacing()));
            if (data.getType() != Chest.Type.SINGLE) {
                data.setType(data.getType() == Chest.Type.LEFT ? Chest.Type.RIGHT : Chest.Type.LEFT);
                switch (((Chest) user.variables.dataCopy).getFacing()) {
                    case NORTH:
                        if (user.variables.currentBlock.getRelative(BlockFace.EAST).getType() == user.variables.currentBlock.getType()) {
                            Chest chest = (Chest) user.variables.currentBlock.getRelative(BlockFace.EAST).getBlockData().clone();
                            if (chest.getFacing() == NORTH && chest.getType() == Chest.Type.SINGLE) {
                                chest.setType(Chest.Type.LEFT);
                                data.setType(Chest.Type.RIGHT);
                                Functions.PlaceBlockRelative(-user.variables.xDif, user.variables.yDif, user.variables.zDif, chest, chest.getMaterial(), user, BlockFace.WEST);
                            } else
                                data.setType(Chest.Type.SINGLE);
                        } else if (user.variables.currentBlock.getRelative(BlockFace.WEST).getType() == user.variables.currentBlock.getType()) {
                            Chest chest = (Chest) user.variables.currentBlock.getRelative(BlockFace.WEST).getBlockData().clone();
                            if (chest.getFacing() == NORTH && chest.getType() == Chest.Type.SINGLE) {
                                chest.setType(Chest.Type.RIGHT);
                                data.setType(Chest.Type.LEFT);
                                Functions.PlaceBlockRelative(-user.variables.xDif, user.variables.yDif, user.variables.zDif, chest, chest.getMaterial(), user, BlockFace.EAST);
                            } else
                                data.setType(Chest.Type.SINGLE);
                        }
                        break;
                    case EAST:
                        if (user.variables.currentBlock.getRelative(BlockFace.SOUTH).getType() == user.variables.currentBlock.getType()) {
                            Chest chest = (Chest) user.variables.currentBlock.getRelative(BlockFace.SOUTH).getBlockData().clone();
                            if (chest.getFacing() == EAST && chest.getType() == Chest.Type.SINGLE) {
                                Bukkit.getLogger().info(chest.getAsString() + "a");
                                chest.setType(Chest.Type.LEFT);
                                data.setType(Chest.Type.RIGHT);
                                chest.setFacing(BlockFace.WEST);
                                Functions.PlaceBlockRelative(-user.variables.xDif, user.variables.yDif, user.variables.zDif, chest, chest.getMaterial(), user, BlockFace.SOUTH);
                            } else {
                                data.setType(Chest.Type.SINGLE);
                                chest.setType(Chest.Type.LEFT);
                                Functions.PlaceBlockRelative(-user.variables.xDif, user.variables.yDif, user.variables.zDif, chest, chest.getMaterial(), user, BlockFace.SOUTH);
                            }
                        } else if (user.variables.currentBlock.getRelative(BlockFace.NORTH).getType() == user.variables.currentBlock.getType()) {
                            Chest chest = (Chest) user.variables.currentBlock.getRelative(BlockFace.NORTH).getBlockData().clone();
                            if (chest.getFacing() == EAST && chest.getType() == Chest.Type.SINGLE) {
                                Bukkit.getLogger().info(chest.getAsString() + "b");
                                chest.setType(Chest.Type.RIGHT);
                                data.setType(Chest.Type.LEFT);
                                chest.setFacing(BlockFace.WEST);
                                Functions.PlaceBlockRelative(-user.variables.xDif, user.variables.yDif, user.variables.zDif, chest, chest.getMaterial(), user, BlockFace.NORTH);
                            } else
                                data.setType(Chest.Type.SINGLE);
                        }
                        break;
                    case SOUTH:
                        if (user.variables.currentBlock.getRelative(BlockFace.WEST).getType() == user.variables.currentBlock.getType()) {
                            Chest chest = (Chest) user.variables.currentBlock.getRelative(BlockFace.WEST).getBlockData().clone();
                            if (chest.getFacing() == SOUTH && chest.getType() == Chest.Type.SINGLE) {
                                chest.setType(Chest.Type.LEFT);
                                data.setType(Chest.Type.RIGHT);
                                Functions.PlaceBlockRelative(-user.variables.xDif, user.variables.yDif, user.variables.zDif, chest, chest.getMaterial(), user, BlockFace.EAST);
                            } else
                                data.setType(Chest.Type.SINGLE);
                        } else if (user.variables.currentBlock.getRelative(BlockFace.EAST).getType() == user.variables.currentBlock.getType()) {
                            Chest chest = (Chest) user.variables.currentBlock.getRelative(BlockFace.EAST).getBlockData().clone();
                            if (chest.getFacing() == SOUTH && chest.getType() == Chest.Type.SINGLE) {
                                chest.setType(Chest.Type.RIGHT);
                                data.setType(Chest.Type.LEFT);
                                Functions.PlaceBlockRelative(-user.variables.xDif, user.variables.yDif, user.variables.zDif, chest, chest.getMaterial(), user, BlockFace.WEST);
                            } else
                                data.setType(Chest.Type.SINGLE);
                        }
                        break;
                    case WEST:
                        if (user.variables.currentBlock.getRelative(BlockFace.NORTH).getType() == user.variables.currentBlock.getType()) {
                            Chest chest = (Chest) user.variables.currentBlock.getRelative(BlockFace.NORTH).getBlockData().clone();
                            if (chest.getFacing() == WEST && chest.getType() == Chest.Type.SINGLE) {
                                chest.setType(Chest.Type.LEFT);
                                data.setType(Chest.Type.RIGHT);
                                chest.setFacing(BlockFace.EAST);
                                Functions.PlaceBlockRelative(-user.variables.xDif, user.variables.yDif, user.variables.zDif, chest, chest.getMaterial(), user, BlockFace.NORTH);
                            } else
                                data.setType(Chest.Type.SINGLE);
                        } else if (user.variables.currentBlock.getRelative(BlockFace.SOUTH).getType() == user.variables.currentBlock.getType()) {
                            Chest chest = (Chest) user.variables.currentBlock.getRelative(BlockFace.SOUTH).getBlockData().clone();
                            if (chest.getFacing() == WEST && chest.getType() == Chest.Type.SINGLE) {
                                chest.setType(Chest.Type.RIGHT);
                                data.setType(Chest.Type.LEFT);
                                chest.setFacing(BlockFace.EAST);
                                Functions.PlaceBlockRelative(-user.variables.xDif, user.variables.yDif, user.variables.zDif, chest, chest.getMaterial(), user, BlockFace.SOUTH);
                            } else
                                data.setType(Chest.Type.SINGLE);
                        }
                        break;
                }
            }
            Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, user.variables.zDif, data, user);
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
            } else if (ab.block.getBlockData() instanceof Gate && user.variables.currentBlock.getType().name().toLowerCase().contains("cobblestone_wall")) {
                Gate gate = (Gate) ab.block.getBlockData().clone();
                if (!((gate.getFacing() == ab.blockFace) || gate.getFacing().getOppositeFace() == ab.blockFace))
                    if (!ab.block.getRelative(ab.blockFace).getType().name().toLowerCase().contains("cobblestone_wall")) {
                        gate.setInWall(false);
                        Functions.PlaceBlockRelative(-user.variables.xDif, user.variables.yDif, user.variables.zDif, gate, ab.block.getType(), user, doBlockFace(ab.blockFace));
                    }
            } else if (ab.block.getBlockData() instanceof Door) {
                Door door = (Door) ab.block.getBlockData();
                if(door.getHalf().equals(Bisected.Half.BOTTOM))
                    Functions.RemoveBlock(-user.variables.xDif, user.variables.yDif + 1, user.variables.zDif, user);
                else
                    Functions.RemoveBlock(-user.variables.xDif, user.variables.yDif - 1, user.variables.zDif, user);
            } else if (ab.block.getBlockData() instanceof Chest && user.variables.currentBlock.getBlockData() instanceof Chest) {
                Chest chest = (Chest) ab.block.getBlockData();
                if (chest.getType() != Chest.Type.SINGLE && chest.getFacing() == ((Chest) user.variables.currentBlock.getBlockData()).getFacing())
                    switch (chest.getFacing()) {
                        case NORTH:
                            if (chest.getType().equals(Chest.Type.LEFT)) {
                                Block b = ab.block.getRelative(BlockFace.WEST);
                                if (b.getType() == ab.block.getType()) {
                                    Chest chest2 = (Chest) b.getBlockData();
                                    chest2.setType(Chest.Type.SINGLE);
                                    Functions.PlaceBlockRelative(-user.variables.xDif, user.variables.yDif, user.variables.zDif, chest2, b.getType(), user, doBlockFace(ab.blockFace));
                                }
                            } else {
                                Block b = ab.block.getRelative(BlockFace.EAST);
                                if (b.getType() == ab.block.getType()) {
                                    Chest chest2 = (Chest) b.getBlockData();
                                    chest2.setType(Chest.Type.SINGLE);
                                    Functions.PlaceBlockRelative(-user.variables.xDif, user.variables.yDif, user.variables.zDif, chest2, b.getType(), user, doBlockFace(ab.blockFace));
                                }
                            }
                            break;
                        case EAST:
                            if (chest.getType().equals(Chest.Type.LEFT)) {
                                Block b = ab.block.getRelative(BlockFace.NORTH);
                                if (b.getType() == ab.block.getType()) {
                                    Chest chest2 = (Chest) b.getBlockData();
                                    chest2.setType(Chest.Type.SINGLE);
                                    Functions.PlaceBlockRelative(-user.variables.xDif, user.variables.yDif, user.variables.zDif, chest2, b.getType(), user, doBlockFace(ab.blockFace));
                                }
                            } else {
                                Block b = ab.block.getRelative(BlockFace.SOUTH);
                                if (b.getType() == ab.block.getType()) {
                                    Chest chest2 = (Chest) b.getBlockData();
                                    chest2.setType(Chest.Type.SINGLE);
                                    Functions.PlaceBlockRelative(-user.variables.xDif, user.variables.yDif, user.variables.zDif, chest2, b.getType(), user, doBlockFace(ab.blockFace));
                                }
                            }
                            break;
                        case SOUTH:
                            if (chest.getType().equals(Chest.Type.LEFT)) {
                                Block b = ab.block.getRelative(BlockFace.EAST);
                                if (b.getType() == ab.block.getType()) {
                                    Chest chest2 = (Chest) b.getBlockData();
                                    chest2.setType(Chest.Type.SINGLE);
                                    Functions.PlaceBlockRelative(-user.variables.xDif, user.variables.yDif, user.variables.zDif, chest2, b.getType(), user, doBlockFace(ab.blockFace));
                                }
                            } else {
                                Block b = ab.block.getRelative(BlockFace.WEST);
                                if (b.getType() == ab.block.getType()) {
                                    Chest chest2 = (Chest) b.getBlockData();
                                    chest2.setType(Chest.Type.SINGLE);
                                    Functions.PlaceBlockRelative(-user.variables.xDif, user.variables.yDif, user.variables.zDif, chest2, b.getType(), user, doBlockFace(ab.blockFace));
                                }
                            }
                            break;
                        case WEST:
                            if (chest.getType().equals(Chest.Type.LEFT)) {
                                Block b = ab.block.getRelative(BlockFace.SOUTH);
                                if (b.getType() == ab.block.getType()) {
                                    Chest chest2 = (Chest) b.getBlockData();
                                    chest2.setType(Chest.Type.SINGLE);
                                    Functions.PlaceBlockRelative(-user.variables.xDif, user.variables.yDif, user.variables.zDif, chest2, b.getType(), user, doBlockFace(ab.blockFace));
                                }
                            } else {
                                Block b = ab.block.getRelative(BlockFace.NORTH);
                                if (b.getType() == ab.block.getType()) {
                                    Chest chest2 = (Chest) b.getBlockData();
                                    chest2.setType(Chest.Type.SINGLE);
                                    Functions.PlaceBlockRelative(-user.variables.xDif, user.variables.yDif, user.variables.zDif, chest2, b.getType(), user, doBlockFace(ab.blockFace));
                                }
                            }
                            break;
                    }
            }
    }
}