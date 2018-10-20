package be.mc.woutwoot.MirrorMaster.mirrors;

import be.mc.woutwoot.MirrorMaster.objects.AdjacentBlock;
import be.mc.woutwoot.MirrorMaster.objects.User;
import be.mc.woutwoot.MirrorMaster.util.Functions;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.MultipleFacing;
import org.bukkit.block.data.type.*;

import static org.bukkit.block.BlockFace.*;

@SuppressWarnings("Duplicates")
public class XMirroring implements Mirroring {

    public void Stairs(User user) {
        Stairs data = (Stairs) user.variables.dataCopy;
        data.setHalf(Bisected.Half.BOTTOM);
        if (Functions.Down(user))
            data.setHalf(Bisected.Half.TOP);
        String shape = data.getShape().name();
        data.setShape(Stairs.Shape.valueOf(shape.contains("LEFT") ? shape.replace("LEFT", "RIGHT") : shape.replace("RIGHT", "LEFT")));
        switch (Functions.LookDirection(user)) {
            case "south":
                data.setFacing(NORTH);
                Place(data, user);
                break;
            case "north":
                data.setFacing(SOUTH);
                Place(data, user);
                break;
            case "east":
                data.setFacing(EAST);
                Place(data, user);
                break;
            case "west":
                data.setFacing(WEST);
                Place(data, user);
                break;
        }

        for (BlockFace face : data.getFaces())
            if (user.variables.currentBlock.getRelative(face).getBlockData() instanceof Fence) {
                Block b = user.variables.currentBlock.getRelative(face);
                Fence fence = (Fence) doFencePane(b, user);
                fence.setFace(doBlockFace(face).getOppositeFace(), true);
                Functions.PlaceBlockRelative(user.variables.xDif, user.variables.yDif, -user.variables.zDif, fence, b.getType(), user, doBlockFace(face));
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
                    Fence fence = (Fence) doFencePane(ab.block, user);
                    Functions.PlaceBlockRelative(user.variables.xDif, user.variables.yDif, -user.variables.zDif, fence, ab.block.getType(), user, doBlockFace(ab.blockFace));
                } else
                    data = (Fence) genericFencePane(data, ab);
            } else if (user.variables.currentBlock.getType().name().toLowerCase().contains("cobblestone_wall")) {
                if (ab.block.getType().name().toLowerCase().contains("gate")) {
                    Gate gate = (Gate) ab.block.getBlockData().clone();
                    if ((gate.getFacing() == ab.blockFace || gate.getFacing().getOppositeFace() == ab.blockFace))
                        data.setFace(doBlockFace(ab.blockFace), false);
                    else {
                        data.setFace(doBlockFace(ab.blockFace), true);
                        gate.setInWall(true);
                        Functions.PlaceBlockRelative(user.variables.xDif, user.variables.yDif, -user.variables.zDif, gate, ab.block.getType(), user, doBlockFace(ab.blockFace));
                    }
                } else if (ab.block.getType().name().toLowerCase().contains("cobblestone_wall")) {
                    data.setFace(doBlockFace(ab.blockFace), true);
                    Fence wall = (Fence) doFencePane(ab.block, user);
                    Functions.PlaceBlockRelative(user.variables.xDif, user.variables.yDif, -user.variables.zDif, wall, ab.block.getType(), user, doBlockFace(ab.blockFace));
                } else
                    data = (Fence) genericFencePane(data, ab);
            } else if (user.variables.currentBlock.getType().name().equalsIgnoreCase("iron_bars")) {
                if (ab.block.getType().name().toLowerCase().contains("glass_pane")) {
                    data.setFace(doBlockFace(ab.blockFace), true);
                    GlassPane pane = (GlassPane) doFencePane(ab.block, user);
                    Functions.PlaceBlockRelative(user.variables.xDif, user.variables.yDif, -user.variables.zDif, pane, ab.block.getType(), user, doBlockFace(ab.blockFace));
                } else if (ab.block.getType().equals(user.variables.currentBlock.getType())) {
                    data.setFace(doBlockFace(ab.blockFace), true);
                    Fence bars = (Fence) doFencePane(ab.block, user);
                    Functions.PlaceBlockRelative(user.variables.xDif, user.variables.yDif, -user.variables.zDif, bars, ab.block.getType(), user, doBlockFace(ab.blockFace));
                } else
                    data = (Fence) genericFencePane(data, ab);
            } else {
                if (ab.block.getType().name().toLowerCase().contains("gate")) {
                    Gate gate = (Gate) ab.block.getBlockData().clone();
                    if (!((gate.getFacing() == ab.blockFace) || gate.getFacing().getOppositeFace() == ab.blockFace))
                        data.setFace(doBlockFace(ab.blockFace), true);
                    else
                        data.setFace(doBlockFace(ab.blockFace), false);
                } else if (ab.block.getType().name().toLowerCase().contains("fence")) {
                    data.setFace(doBlockFace(ab.blockFace), true);
                    Fence fence = (Fence) doFencePane(ab.block, user);
                    Functions.PlaceBlockRelative(user.variables.xDif, user.variables.yDif, -user.variables.zDif, fence, ab.block.getType(), user, doBlockFace(ab.blockFace));
                } else {
                    data = (Fence) genericFencePane(data, ab);
                }
            }
        }
        if (data.getMaterial().name().toLowerCase().contains("cobblestone_wall"))
            if ((data.getFaces().contains(NORTH) && data.getFaces().contains(SOUTH)) && !(data.getFaces().contains(EAST) || data.getFaces().contains(WEST))
                    || !(data.getFaces().contains(NORTH) || data.getFaces().contains(SOUTH)) && (data.getFaces().contains(EAST) && data.getFaces().contains(WEST)))
                data.setFace(UP, false);
            else
                data.setFace(UP, true);
        Place(data, user);
    }

    public void GlassPanes(User user) {
        GlassPane data = (GlassPane) user.variables.dataCopy.clone();
        for (AdjacentBlock ab : Functions.getRelatives(user.variables.currentBlock))
            if (ab.block.getType().name().toLowerCase().contains("glass_pane")) {
                data.setFace(doBlockFace(ab.blockFace), true);
                GlassPane pane = (GlassPane) doFencePane(ab.block, user);
                Functions.PlaceBlockRelative(user.variables.xDif, user.variables.yDif, -user.variables.zDif, pane, ab.block.getType(), user, doBlockFace(ab.blockFace));
            } else if (ab.block.getType().name().equalsIgnoreCase("iron_bars")) {
                data.setFace(doBlockFace(ab.blockFace), true);
                Fence bars = (Fence) doFencePane(ab.block, user);
                Functions.PlaceBlockRelative(user.variables.xDif, user.variables.yDif, -user.variables.zDif, bars, ab.block.getType(), user, doBlockFace(ab.blockFace));
            } else
                data = (GlassPane) genericFencePane(data, ab);
        Place(data, user);
        if (user.variables.touchingBlock.getType().name().toLowerCase().contains("glass_pane")) {
            GlassPane pane = (GlassPane) user.variables.touchingBlock.getBlockData().clone();
            for (AdjacentBlock ab : Functions.getRelatives(user.variables.touchingBlock))
                if (ab.block.getType().name().toLowerCase().contains("glass_pane"))
                    pane.setFace(ab.blockFace, true);
                else if (user.variables.currentBlock.getType().name().equalsIgnoreCase("iron_bars"))
                    pane.setFace(ab.blockFace, true);
                else if (ab.block.getType().isSolid() && ab.block.getType().isOccluding())
                    pane.setFace(ab.blockFace, true);
                else if (ab.block.getBlockData() instanceof Slab && ((Slab) ab.block.getBlockData()).getType() == Slab.Type.DOUBLE)
                    pane.setFace(ab.blockFace, true);
                else if (ab.block.getBlockData() instanceof Stairs) {
                    if (((Stairs) ab.block.getBlockData()).getFacing().getOppositeFace() == ab.blockFace)
                        pane.setFace(ab.blockFace, true);
                } else
                    pane.setFace(doBlockFace(ab.blockFace), false);
            Functions.PlaceBlock(user.variables.touchingBlock, pane, pane.getMaterial(), user);
        }
    }

    private MultipleFacing doFencePane(Block b, User user) {
        MultipleFacing data = (MultipleFacing) b.getBlockData().clone();
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
                    data = genericFencePane(data, ab);
            } else if (b.getType().name().toLowerCase().contains("cobblestone_wall")) {
                if (ab.block.getType().name().toLowerCase().contains("cobblestone_wall"))
                    data.setFace(doBlockFace(ab.blockFace), true);
                else if (ab.block.getType().name().toLowerCase().contains("gate")) {
                    Gate gate = (Gate) ab.block.getBlockData().clone();
                    if (!((gate.getFacing() == ab.blockFace) || gate.getFacing().getOppositeFace() == ab.blockFace))
                        data.setFace(doBlockFace(ab.blockFace), true);
                    else
                        data.setFace(doBlockFace(ab.blockFace), false);
                } else
                    data = genericFencePane(data, ab);
                if (((data.getFaces().contains(NORTH) && data.getFaces().contains(SOUTH)) && !(data.getFaces().contains(EAST) || data.getFaces().contains(WEST)))
                        || (!(data.getFaces().contains(NORTH) || data.getFaces().contains(SOUTH)) && (data.getFaces().contains(EAST) && data.getFaces().contains(WEST))))
                    data.setFace(UP, false);
                else
                    data.setFace(UP, true);
            } else if (b.getType().name().equalsIgnoreCase("iron_bars") || b.getType().name().toLowerCase().contains("glass_pane")) {
                if (ab.block.getType().name().equalsIgnoreCase("iron_bars") || ab.block.getType().name().toLowerCase().contains("glass_pane"))
                    data.setFace(doBlockFace(ab.blockFace), true);
                else
                    data = genericFencePane(data, ab);
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
                    data = genericFencePane(data, ab);
            }
        }
        return data;
    }

    private BlockFace doBlockFace(BlockFace bf) {
        if (bf != NORTH && bf != SOUTH)
            return bf;
        return bf == NORTH ? SOUTH : NORTH;
    }

    private MultipleFacing genericFencePane(MultipleFacing data, AdjacentBlock ab) {
        if (ab.block.getType().isSolid() && ab.block.getType().isOccluding())
            data.setFace(doBlockFace(ab.blockFace), true);
        else if (ab.block.getBlockData() instanceof Slab && ((Slab) ab.block.getBlockData()).getType() == Slab.Type.DOUBLE)
            data.setFace(doBlockFace(ab.blockFace), true);
        else if (ab.block.getBlockData() instanceof Stairs) {
            if (((Stairs) ab.block.getBlockData()).getFacing().getOppositeFace() == ab.blockFace)
                data.setFace(doBlockFace(ab.blockFace), true);
            else
                data.setFace(doBlockFace(ab.blockFace), false);
        } else
            data.setFace(doBlockFace(ab.blockFace), false);
        return data;
    }

    public void Gates(User user) {
        Gate data = (Gate) user.variables.dataCopy;
        data.setFacing(doBlockFace(data.getFacing()));
        Place(data, user);
        for (AdjacentBlock ab : Functions.getRelatives(user.variables.currentBlock))
            if (ab.block.getBlockData() instanceof Fence) {
                Fence fence = (Fence) doFencePane(ab.block, user);
                Functions.PlaceBlockRelative(user.variables.xDif, user.variables.yDif, -user.variables.zDif, fence, ab.block.getType(), user, doBlockFace(ab.blockFace));
            }
    }

    public void ButtonLevers(User user) {
        Switch data = (Switch) user.variables.dataCopy;
        if (Functions.Up(user)) {
            switch (Functions.LookDirection(user)) {
                case "east":
                    data.setFacing(EAST);
                    Place(data, user);
                    break;
                case "west":
                    data.setFacing(WEST);
                    Place(data, user);
                    break;
                case "south":
                    data.setFacing(NORTH);
                    Place(data, user);
                    break;
                case "north":
                    data.setFacing(SOUTH);
                    Place(data, user);
                    break;
            }
            data.setFace(Switch.Face.FLOOR);
        } else if (Functions.Down(user))
            data.setFace(Switch.Face.CEILING);
        if (Functions.East(user))
            data.setFacing(EAST);
        else if (Functions.West(user))
            data.setFacing(WEST);
        else if (Functions.South(user))
            data.setFacing(NORTH);
        else if (Functions.North(user))
            data.setFacing(SOUTH);
        Place(data, user);
    }

    public void Torches(User user) {
        String mat = user.variables.materialCopy.name();
        if (mat.equalsIgnoreCase("REDSTONE_WALL_TORCH")) {
            RedstoneWallTorch data = (RedstoneWallTorch) user.variables.dataCopy;
            if (Functions.East(user))
                data.setFacing(EAST);
            else
                data.setFacing(WEST);
            if (Functions.South(user))
                data.setFacing(NORTH);
            else
                data.setFacing(SOUTH);
            Place(data, user);
        } else if (mat.equalsIgnoreCase("WALL_TORCH")) {
            Directional data = (Directional) user.variables.dataCopy;
            if (Functions.East(user))
                data.setFacing(EAST);
            else
                data.setFacing(WEST);
            if (Functions.South(user))
                data.setFacing(NORTH);
            else
                data.setFacing(SOUTH);
            Place(data, user);
        }
    }

    public void Halfslabs(User user) {
        Place(user.variables.dataCopy, user);
        Slab data = (Slab) user.variables.dataCopy;
        if (data.getType() == Slab.Type.DOUBLE) {
            for (AdjacentBlock ab : Functions.getRelatives(user.variables.currentBlock))
                if (ab.block.getBlockData() instanceof Fence) {
                    Fence fence = (Fence) doFencePane(ab.block, user);
                    Functions.PlaceBlockRelative(user.variables.xDif, user.variables.yDif, -user.variables.zDif, fence, ab.block.getType(), user, doBlockFace(ab.blockFace));
                }
        }
    }

    public void Doors(User user) {
        Door datab = (Door) user.variables.currentBlock.getType().createBlockData();
        Door datat = (Door) user.variables.currentBlock.getType().createBlockData();
        Door.Hinge hinge = ((Door) user.variables.dataCopy).getHinge();
        switch (Functions.LookDirection(user)) {
            case "south":
                datab.setFacing(NORTH);
                datab.setHinge(hinge == Door.Hinge.LEFT ? Door.Hinge.RIGHT : Door.Hinge.LEFT);
                datab.setHalf(Bisected.Half.BOTTOM);
                Place(datab, user);

                datat.setFacing(NORTH);
                datat.setHinge(hinge == Door.Hinge.LEFT ? Door.Hinge.RIGHT : Door.Hinge.LEFT);
                datat.setHalf(Bisected.Half.TOP);
                Functions.PlaceBlock(user.variables.xDif, user.variables.yDif + 1, -user.variables.zDif, datat, user);
                break;
            case "north":
                datab.setFacing(SOUTH);
                datab.setHinge(hinge == Door.Hinge.LEFT ? Door.Hinge.RIGHT : Door.Hinge.LEFT);
                datab.setHalf(Bisected.Half.BOTTOM);
                Place(datab, user);

                datat.setFacing(SOUTH);
                datat.setHinge(hinge == Door.Hinge.LEFT ? Door.Hinge.RIGHT : Door.Hinge.LEFT);
                datat.setHalf(Bisected.Half.TOP);
                Functions.PlaceBlock(user.variables.xDif, user.variables.yDif + 1, -user.variables.zDif, datat, user);
                break;
            case "east":
                datab.setFacing(EAST);
                datab.setHinge(hinge == Door.Hinge.LEFT ? Door.Hinge.RIGHT : Door.Hinge.LEFT);
                datab.setHalf(Bisected.Half.BOTTOM);
                Place(datab, user);

                datat.setFacing(EAST);
                datat.setHinge(hinge == Door.Hinge.LEFT ? Door.Hinge.RIGHT : Door.Hinge.LEFT);
                datat.setHalf(Bisected.Half.TOP);
                Functions.PlaceBlock(user.variables.xDif, user.variables.yDif + 1, -user.variables.zDif, datat, user);
                break;
            case "west":
                datab.setFacing(WEST);
                datab.setHinge(hinge == Door.Hinge.LEFT ? Door.Hinge.RIGHT : Door.Hinge.LEFT);
                datab.setHalf(Bisected.Half.BOTTOM);
                Place(datab, user);

                datat.setFacing(WEST);
                datat.setHinge(hinge == Door.Hinge.LEFT ? Door.Hinge.RIGHT : Door.Hinge.LEFT);
                datat.setHalf(Bisected.Half.TOP);
                Functions.PlaceBlock(user.variables.xDif, user.variables.yDif + 1, -user.variables.zDif, datat, user);
                break;
        }
    }

    public void Trapdoors(User user) {
        TrapDoor data = (TrapDoor) user.variables.dataCopy;
        data.setFacing(doBlockFace(data.getFacing()));
        Place(data, user);
    }

    public void Pistons(User user) {
        Piston data = (Piston) user.variables.dataCopy;
        data.setFacing(doBlockFace(data.getFacing()));
        Place(data, user);
    }

    public void EndRods(User user) {
        Directional data = (Directional) user.variables.dataCopy;
        data.setFacing(doBlockFace(data.getFacing()));
        Place(data, user);
    }

    public void Chests(User user) {
        if (user.variables.currentBlock.getType() == Material.ENDER_CHEST) {
            EnderChest data = (EnderChest) user.variables.dataCopy;
            data.setFacing(doBlockFace(data.getFacing()));
            Place(data, user);
        } else {
            Chest data = (Chest) user.variables.dataCopy.clone();
            data.setFacing(doBlockFace(data.getFacing()));
            if (data.getType() != Chest.Type.SINGLE) {
                data.setType(data.getType() == Chest.Type.LEFT ? Chest.Type.RIGHT : Chest.Type.LEFT);
                switch (((Chest) user.variables.dataCopy).getFacing()) {
                    case NORTH:
                        if (user.variables.currentBlock.getRelative(BlockFace.EAST).getType() == user.variables.currentBlock.getType() &&
                                ((Chest) user.variables.currentBlock.getRelative(BlockFace.EAST).getBlockData()).getFacing() == NORTH &&
                                ((Chest) user.variables.currentBlock.getRelative(BlockFace.EAST).getBlockData()).getType() == Chest.Type.SINGLE) {
                            Chest chest = (Chest) user.variables.currentBlock.getRelative(BlockFace.EAST).getBlockData().clone();
                            if (chest.getFacing() == NORTH && chest.getType() == Chest.Type.SINGLE) {
                                chest.setType(Chest.Type.LEFT);
                                data.setType(Chest.Type.RIGHT);
                                Functions.PlaceBlockRelative(user.variables.xDif, user.variables.yDif, -user.variables.zDif, chest, chest.getMaterial(), user, BlockFace.WEST);
                                Functions.PlaceBlockRelative(user.variables.xDif, user.variables.yDif, user.variables.zDif, data, chest.getMaterial(), user, BlockFace.EAST);
                            } else
                                data.setType(Chest.Type.SINGLE);
                        } else if (user.variables.currentBlock.getRelative(BlockFace.WEST).getType() == user.variables.currentBlock.getType()) {
                            Chest chest = (Chest) user.variables.currentBlock.getRelative(BlockFace.WEST).getBlockData().clone();
                            if (chest.getFacing() == NORTH && chest.getType() == Chest.Type.SINGLE) {
                                chest.setType(Chest.Type.RIGHT);
                                data.setType(Chest.Type.LEFT);
                                Functions.PlaceBlockRelative(user.variables.xDif, user.variables.yDif, -user.variables.zDif, chest, chest.getMaterial(), user, BlockFace.EAST);
                                Functions.PlaceBlockRelative(user.variables.xDif, user.variables.yDif, user.variables.zDif, data, chest.getMaterial(), user, BlockFace.WEST);
                            } else
                                data.setType(Chest.Type.SINGLE);
                        }
                        break;
                    case EAST:
                        if (user.variables.currentBlock.getRelative(BlockFace.SOUTH).getType() == user.variables.currentBlock.getType() &&
                                ((Chest) user.variables.currentBlock.getRelative(BlockFace.SOUTH).getBlockData()).getFacing() == EAST &&
                                ((Chest) user.variables.currentBlock.getRelative(BlockFace.SOUTH).getBlockData()).getType() == Chest.Type.SINGLE) {
                            Chest chest = (Chest) user.variables.currentBlock.getRelative(BlockFace.SOUTH).getBlockData().clone();
                            if (chest.getFacing() == EAST && chest.getType() == Chest.Type.SINGLE) {
                                chest.setType(Chest.Type.LEFT);
                                data.setType(Chest.Type.RIGHT);
                                chest.setFacing(BlockFace.WEST);
                                Functions.PlaceBlockRelative(user.variables.xDif, user.variables.yDif, -user.variables.zDif, chest, chest.getMaterial(), user, BlockFace.SOUTH);
                                Chest chest2 = (Chest) data.clone();
                                chest2.setFacing(BlockFace.EAST);
                                Functions.PlaceBlockRelative(user.variables.xDif, user.variables.yDif, user.variables.zDif, chest2, chest.getMaterial(), user, BlockFace.SOUTH);
                            } else
                                data.setType(Chest.Type.SINGLE);
                        } else if (user.variables.currentBlock.getRelative(BlockFace.NORTH).getType() == user.variables.currentBlock.getType()) {
                            Chest chest = (Chest) user.variables.currentBlock.getRelative(BlockFace.NORTH).getBlockData().clone();
                            if (chest.getFacing() == EAST && chest.getType() == Chest.Type.SINGLE) {
                                chest.setType(Chest.Type.RIGHT);
                                data.setType(Chest.Type.LEFT);
                                chest.setFacing(BlockFace.WEST);
                                Functions.PlaceBlockRelative(user.variables.xDif, user.variables.yDif, -user.variables.zDif, chest, chest.getMaterial(), user, BlockFace.NORTH);
                                Chest chest2 = (Chest) data.clone();
                                chest2.setFacing(BlockFace.EAST);
                                Functions.PlaceBlockRelative(user.variables.xDif, user.variables.yDif, user.variables.zDif, chest2, chest.getMaterial(), user, BlockFace.NORTH);
                            } else
                                data.setType(Chest.Type.SINGLE);
                        }
                        break;
                    case SOUTH:
                        if (user.variables.currentBlock.getRelative(BlockFace.WEST).getType() == user.variables.currentBlock.getType() &&
                                ((Chest) user.variables.currentBlock.getRelative(BlockFace.WEST).getBlockData()).getFacing() == SOUTH &&
                                ((Chest) user.variables.currentBlock.getRelative(BlockFace.WEST).getBlockData()).getType() == Chest.Type.SINGLE) {
                            Chest chest = (Chest) user.variables.currentBlock.getRelative(BlockFace.WEST).getBlockData().clone();
                            if (chest.getFacing() == SOUTH && chest.getType() == Chest.Type.SINGLE) {
                                chest.setType(Chest.Type.LEFT);
                                data.setType(Chest.Type.RIGHT);
                                Functions.PlaceBlockRelative(user.variables.xDif, user.variables.yDif, -user.variables.zDif, chest, chest.getMaterial(), user, BlockFace.EAST);
                                Functions.PlaceBlockRelative(user.variables.xDif, user.variables.yDif, user.variables.zDif, data, chest.getMaterial(), user, BlockFace.WEST);
                            } else
                                data.setType(Chest.Type.SINGLE);
                        } else if (user.variables.currentBlock.getRelative(BlockFace.EAST).getType() == user.variables.currentBlock.getType()) {
                            Chest chest = (Chest) user.variables.currentBlock.getRelative(BlockFace.EAST).getBlockData().clone();
                            if (chest.getFacing() == SOUTH && chest.getType() == Chest.Type.SINGLE) {
                                chest.setType(Chest.Type.RIGHT);
                                data.setType(Chest.Type.LEFT);
                                Functions.PlaceBlockRelative(user.variables.xDif, user.variables.yDif, -user.variables.zDif, chest, chest.getMaterial(), user, BlockFace.WEST);
                                Functions.PlaceBlockRelative(user.variables.xDif, user.variables.yDif, user.variables.zDif, data, chest.getMaterial(), user, BlockFace.EAST);
                            } else
                                data.setType(Chest.Type.SINGLE);
                        }
                        break;
                    case WEST:
                        if (user.variables.currentBlock.getRelative(BlockFace.NORTH).getType() == user.variables.currentBlock.getType() &&
                                ((Chest) user.variables.currentBlock.getRelative(BlockFace.NORTH).getBlockData()).getFacing() == WEST &&
                                ((Chest) user.variables.currentBlock.getRelative(BlockFace.NORTH).getBlockData()).getType() == Chest.Type.SINGLE) {
                            Chest chest = (Chest) user.variables.currentBlock.getRelative(BlockFace.NORTH).getBlockData().clone();
                            if (chest.getFacing() == WEST && chest.getType() == Chest.Type.SINGLE) {
                                chest.setType(Chest.Type.LEFT);
                                data.setType(Chest.Type.RIGHT);
                                chest.setFacing(BlockFace.EAST);
                                Functions.PlaceBlockRelative(user.variables.xDif, user.variables.yDif, -user.variables.zDif, chest, chest.getMaterial(), user, BlockFace.NORTH);
                                Chest chest2 = (Chest) data.clone();
                                chest2.setFacing(BlockFace.WEST);
                                Functions.PlaceBlockRelative(user.variables.xDif, user.variables.yDif, user.variables.zDif, chest2, chest.getMaterial(), user, BlockFace.NORTH);
                            } else
                                data.setType(Chest.Type.SINGLE);
                        } else if (user.variables.currentBlock.getRelative(BlockFace.SOUTH).getType() == user.variables.currentBlock.getType()) {
                            Chest chest = (Chest) user.variables.currentBlock.getRelative(BlockFace.SOUTH).getBlockData().clone();
                            if (chest.getFacing() == WEST && chest.getType() == Chest.Type.SINGLE) {
                                chest.setType(Chest.Type.RIGHT);
                                data.setType(Chest.Type.LEFT);
                                chest.setFacing(BlockFace.EAST);
                                Functions.PlaceBlockRelative(user.variables.xDif, user.variables.yDif, -user.variables.zDif, chest, chest.getMaterial(), user, BlockFace.SOUTH);
                                Chest chest2 = (Chest) data.clone();
                                chest2.setFacing(BlockFace.WEST);
                                Functions.PlaceBlockRelative(user.variables.xDif, user.variables.yDif, user.variables.zDif, chest2, chest.getMaterial(), user, BlockFace.SOUTH);
                            } else
                                data.setType(Chest.Type.SINGLE);
                        }
                        break;
                }
            }
            Place(data, user);
        }
    }

    public void RotateXZ(User user) {
        Directional data = (Directional) user.variables.dataCopy;
        data.setFacing(doBlockFace(data.getFacing()));
        Place(data, user);
    }

    public void Vines(User user) {
        MultipleFacing data = (MultipleFacing) user.variables.dataCopy;
        MultipleFacing vine = (MultipleFacing) user.variables.dataCopy.clone();
        for (BlockFace face : data.getFaces()) {
            vine.setFace(face, false);
            vine.setFace(doBlockFace(face), true);
        }
        Place(vine, user);
    }

    public void TripWire(User user) {
        TripwireHook data = (TripwireHook) user.variables.dataCopy.clone();
        data.setFacing(doBlockFace(data.getFacing()));
        Place(data, user);
    }

    public void Hopper(User user) {
        Hopper data = (Hopper) user.variables.dataCopy.clone();
        data.setFacing(doBlockFace(data.getFacing()));
        Place(data, user);
    }

    public void TallFlower(User user) {
        Bisected data = (Bisected) user.variables.dataCopy.clone();
        Bisected data2 = (Bisected) user.variables.dataCopy.clone();
        data2.setHalf(data.getHalf() == Bisected.Half.BOTTOM ? Bisected.Half.TOP : Bisected.Half.BOTTOM);
        Place(data, user);
        int y = user.variables.yDif;
        Functions.PlaceBlock(user.variables.xDif, data.getHalf() == Bisected.Half.BOTTOM ? y + 1 : y - 1, -user.variables.zDif, data2, user);
    }

    public void Terracotta(User user) {
        Directional data = (Directional) user.variables.dataCopy.clone();
        switch (data.getFacing()) {
            case NORTH:
                data.setFacing(BlockFace.WEST);
                break;
            case EAST:
                data.setFacing(BlockFace.SOUTH);
                break;
            case SOUTH:
                data.setFacing(BlockFace.EAST);
                break;
            case WEST:
                data.setFacing(BlockFace.NORTH);
                break;
        }
        Place(data, user);
    }

    private void Place(BlockData data, User user) {
        Functions.PlaceBlock(user.variables.xDif, user.variables.yDif, -user.variables.zDif, data, user);
    }

    public void Default(User user) {
        Functions.PlaceBlock(user.variables.xDif, user.variables.yDif, -user.variables.zDif, user);
        if (user.variables.currentBlock.getType().isSolid())
            for (AdjacentBlock ab : Functions.getRelatives(user.variables.currentBlock))
                if (ab.block.getBlockData() instanceof Fence) {
                    Fence fence = (Fence) doFencePane(ab.block, user);
                    Functions.PlaceBlockRelative(user.variables.xDif, user.variables.yDif, -user.variables.zDif, fence, ab.block.getType(), user, doBlockFace(ab.blockFace));
                } else if (ab.block.getType().name().toLowerCase().contains("glass_pane")) {
                    GlassPane pane = (GlassPane) doFencePane(ab.block, user);
                    Functions.PlaceBlockRelative(user.variables.xDif, user.variables.yDif, -user.variables.zDif, pane, ab.block.getType(), user, doBlockFace(ab.blockFace));
                }
    }

    @SuppressWarnings("Duplicates")
    public void Remove(User user) {
        Functions.RemoveBlock(user.variables.xDif, user.variables.yDif, -user.variables.zDif, user);
        if (user.variables.currentBlock.getBlockData() instanceof Bisected) {
            Bisected data = (Bisected) user.variables.currentBlock.getBlockData().clone();
            int y = user.variables.yDif;
            Functions.RemoveBlock(user.variables.xDif, data.getHalf() == Bisected.Half.BOTTOM ? y + 1 : y - 1, -user.variables.zDif, user);
        }
        for (AdjacentBlock ab : Functions.getRelatives(user.variables.currentBlock))
            if (ab.block.getBlockData() instanceof Fence) {
                Fence fence = (Fence) doFencePane(ab.block, user);
                fence.setFace(doBlockFace(ab.blockFace).getOppositeFace(), false);
                Functions.PlaceBlockRelative(user.variables.xDif, user.variables.yDif, -user.variables.zDif, fence, ab.block.getType(), user, doBlockFace(ab.blockFace));
            } else if (ab.block.getType().name().toLowerCase().contains("glass_pane")) {
                GlassPane pane = (GlassPane) doFencePane(ab.block, user);
                pane.setFace(doBlockFace(ab.blockFace).getOppositeFace(), false);
                Functions.PlaceBlockRelative(user.variables.xDif, user.variables.yDif, -user.variables.zDif, pane, ab.block.getType(), user, doBlockFace(ab.blockFace));
            } else if (ab.block.getBlockData() instanceof Gate && user.variables.currentBlock.getType().name().toLowerCase().contains("cobblestone_wall")) {
                Gate gate = (Gate) ab.block.getBlockData().clone();
                if (!((gate.getFacing() == ab.blockFace) || gate.getFacing().getOppositeFace() == ab.blockFace))
                    if (!ab.block.getRelative(ab.blockFace).getType().name().toLowerCase().contains("cobblestone_wall")) {
                        gate.setInWall(false);
                        Functions.PlaceBlockRelative(user.variables.xDif, user.variables.yDif, -user.variables.zDif, gate, ab.block.getType(), user, doBlockFace(ab.blockFace));
                    }
            } else if (ab.block.getBlockData() instanceof Chest && user.variables.currentBlock.getBlockData() instanceof Chest && user.variables.currentBlock.getType() == ab.block.getType()) {
                Chest data = (Chest) user.variables.currentBlock.getBlockData().clone();
                if (((Chest) ab.block.getBlockData()).getFacing() == data.getFacing())
                    switch (data.getFacing()) {
                        case NORTH:
                            if ((data.getType().equals(Chest.Type.RIGHT) && ab.blockFace.equals(BlockFace.WEST)) || (data.getType().equals(Chest.Type.LEFT) && ab.blockFace.equals(BlockFace.EAST))) {
                                Chest chest = (Chest) ab.block.getBlockData().clone();
                                chest.setType(Chest.Type.SINGLE);
                                chest.setFacing(doBlockFace(chest.getFacing()));
                                Functions.PlaceBlockRelative(user.variables.xDif, user.variables.yDif, -user.variables.zDif, chest, ab.block.getType(), user, doBlockFace(ab.blockFace));
                                Chest chest2 = (Chest) user.variables.currentBlock.getRelative(ab.blockFace).getBlockData().clone();
                                chest2.setType(Chest.Type.SINGLE);
                                Functions.PlaceBlockRelative(user.variables.xDif, user.variables.yDif, user.variables.zDif, chest2, ab.block.getType(), user, ab.blockFace);
                            }
                            break;
                        case EAST:
                            if ((data.getType().equals(Chest.Type.LEFT) && ab.blockFace.equals(SOUTH)) || (data.getType().equals(Chest.Type.RIGHT) && ab.blockFace.equals(NORTH))) {
                                Chest chest = (Chest) ab.block.getBlockData().clone();
                                chest.setType(Chest.Type.SINGLE);
                                chest.setFacing(doBlockFace(chest.getFacing()));
                                Functions.PlaceBlockRelative(user.variables.xDif, user.variables.yDif, -user.variables.zDif, chest, ab.block.getType(), user, doBlockFace(ab.blockFace));
                                Chest chest2 = (Chest) user.variables.currentBlock.getRelative(ab.blockFace).getBlockData().clone();
                                chest2.setType(Chest.Type.SINGLE);
                                Functions.PlaceBlockRelative(user.variables.xDif, user.variables.yDif, user.variables.zDif, chest2, ab.block.getType(), user, ab.blockFace);
                            }
                            break;
                        case SOUTH:
                            if ((data.getType().equals(Chest.Type.RIGHT) && ab.blockFace.equals(BlockFace.EAST)) || (data.getType().equals(Chest.Type.LEFT) && ab.blockFace.equals(BlockFace.WEST))) {
                                Chest chest = (Chest) ab.block.getBlockData().clone();
                                chest.setType(Chest.Type.SINGLE);
                                chest.setFacing(doBlockFace(chest.getFacing()));
                                Functions.PlaceBlockRelative(user.variables.xDif, user.variables.yDif, -user.variables.zDif, chest, ab.block.getType(), user, doBlockFace(ab.blockFace));
                                Chest chest2 = (Chest) user.variables.currentBlock.getRelative(ab.blockFace).getBlockData().clone();
                                chest2.setType(Chest.Type.SINGLE);
                                Functions.PlaceBlockRelative(user.variables.xDif, user.variables.yDif, user.variables.zDif, chest2, ab.block.getType(), user, ab.blockFace);
                            }
                            break;
                        case WEST:
                            if ((data.getType().equals(Chest.Type.LEFT) && ab.blockFace.equals(BlockFace.NORTH)) || (data.getType().equals(Chest.Type.RIGHT) && ab.blockFace.equals(BlockFace.SOUTH))) {
                                Chest chest = (Chest) ab.block.getBlockData().clone();
                                chest.setType(Chest.Type.SINGLE);
                                chest.setFacing(doBlockFace(chest.getFacing()));
                                Functions.PlaceBlockRelative(user.variables.xDif, user.variables.yDif, -user.variables.zDif, chest, ab.block.getType(), user, doBlockFace(ab.blockFace));
                                Chest chest2 = (Chest) user.variables.currentBlock.getRelative(ab.blockFace).getBlockData().clone();
                                chest2.setType(Chest.Type.SINGLE);
                                Functions.PlaceBlockRelative(user.variables.xDif, user.variables.yDif, user.variables.zDif, chest2, ab.block.getType(), user, ab.blockFace);
                            }
                            break;
                    }
            }
    }
}