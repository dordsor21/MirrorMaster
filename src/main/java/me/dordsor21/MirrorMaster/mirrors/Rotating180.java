package me.dordsor21.MirrorMaster.mirrors;

import me.dordsor21.MirrorMaster.objects.AdjacentBlock;
import me.dordsor21.MirrorMaster.objects.User;
import me.dordsor21.MirrorMaster.util.Functions;
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
public class Rotating180 implements Mirroring {

    private final User user;

    public Rotating180(User user) {
        this.user = user;
    }

    public void Stairs() {
        Stairs data = (Stairs) user.variables.dataCopy.clone();
        data.setHalf(Bisected.Half.BOTTOM);
        if (Functions.Down(user))
            data.setHalf(Bisected.Half.TOP);
        String shape = data.getShape().name();
        Stairs data180 = (Stairs) data.clone();
        data.setShape(Stairs.Shape.valueOf(shape.contains("LEFT") ? shape.replace("LEFT", "RIGHT") : shape.replace("RIGHT", "LEFT")));
        switch (Functions.LookDirection(user)) {
            case "south" -> data180.setFacing(NORTH);
            case "north" -> data180.setFacing(SOUTH);
            case "east" -> data180.setFacing(WEST);
            case "west" -> data180.setFacing(EAST);
        }
        Place(data180);

        for (BlockFace face : data.getFaces())
            if (user.variables.currentBlock.getRelative(face).getBlockData() instanceof Fence) {
                Block b = user.variables.currentBlock.getRelative(face);
                Fence fence = (Fence) doFencePane(b);
                fence.setFace(doBlockFace(face).getOppositeFace(), true);
                PlaceRelatives(fence, face, b.getType());
            }
    }

    public void Fences() {
        Fence data180 = (Fence) user.variables.dataCopy.clone();
        for (AdjacentBlock ab : Functions.getRelatives(user.variables.currentBlock)) {
            if (user.variables.currentBlock.getType() == Material.NETHER_BRICK_FENCE) {
                if (ab.block.getType().name().toLowerCase().contains("gate")) {
                    Gate gate = (Gate) ab.block.getBlockData().clone();
                    data180.setFace(doBlockFace(ab.blockFace), !((gate.getFacing() == ab.blockFace) || gate.getFacing().getOppositeFace() == ab.blockFace));
                } else if (ab.block.getType() == Material.NETHER_BRICK_FENCE) {
                    data180.setFace(doBlockFace(ab.blockFace), true);
                    Fence fence = (Fence) doFencePane(ab.block);

                    PlaceRelatives(fence, ab.blockFace, ab.block.getType());
                } else {
                    genericFencePane(data180, ab);
                }
            } else if (user.variables.currentBlock.getType().name().toLowerCase().contains("cobblestone_wall")) {
                if (ab.block.getType().name().toLowerCase().contains("gate")) {
                    Gate gate = (Gate) ab.block.getBlockData().clone();
                    if ((gate.getFacing() == ab.blockFace || gate.getFacing().getOppositeFace() == ab.blockFace)) {
                        data180.setFace(doBlockFace(ab.blockFace), false);
                    } else {
                        data180.setFace(doBlockFace(ab.blockFace), true);

                        gate.setInWall(true);

                        PlaceRelatives(gate, ab.blockFace, ab.block.getType());
                    }
                } else if (ab.block.getType().name().toLowerCase().contains("cobblestone_wall")) {
                    data180.setFace(doBlockFace(ab.blockFace), true);

                    Fence wall = (Fence) doFencePane(ab.block);

                    PlaceRelatives(wall, ab.blockFace, ab.block.getType());
                } else {
                    genericFencePane(data180, ab);
                }
            } else if (user.variables.currentBlock.getType().name().equalsIgnoreCase("iron_bars")) {
                if (ab.block.getType().name().toLowerCase().contains("glass_pane")) {
                    data180.setFace(doBlockFace(ab.blockFace), true);

                    GlassPane pane = (GlassPane) doFencePane(ab.block);
                    PlaceRelatives(pane, ab.blockFace, ab.block.getType());
                } else if (ab.block.getType().equals(user.variables.currentBlock.getType())) {
                    data180.setFace(doBlockFace(ab.blockFace), true);

                    Fence bars = (Fence) doFencePane(ab.block);
                    PlaceRelatives(bars, ab.blockFace, ab.block.getType());
                } else {
                    genericFencePane(data180, ab);
                }
            } else {
                if (ab.block.getType().name().toLowerCase().contains("gate")) {
                    Gate gate = (Gate) ab.block.getBlockData().clone();
                    data180.setFace(doBlockFace(ab.blockFace), !((gate.getFacing() == ab.blockFace) || gate.getFacing().getOppositeFace() == ab.blockFace));
                } else if (ab.block.getType().name().toLowerCase().contains("fence")) {
                    data180.setFace(doBlockFace(ab.blockFace), true);

                    Fence fence = (Fence) doFencePane(ab.block);
                    PlaceRelatives(fence, ab.blockFace, ab.block.getType());
                } else {
                    genericFencePane(data180, ab);
                }
            }
        }
        if (user.variables.currentBlock.getType().name().toLowerCase().contains("cobblestone_wall")) {
            Fence data = (Fence) user.variables.dataCopy;
            data180.setFace(UP, ((!data.getFaces().contains(EAST) || !data.getFaces().contains(WEST)) || (data.getFaces().contains(NORTH) || data.getFaces().contains(SOUTH)))
                    && ((data.getFaces().contains(EAST) || data.getFaces().contains(WEST)) || (!data.getFaces().contains(NORTH) || !data.getFaces().contains(SOUTH))));
        }
        Place(data180);
    }

    public void GlassPanes() {
        GlassPane data180 = (GlassPane) user.variables.dataCopy.clone();
        for (AdjacentBlock ab : Functions.getRelatives(user.variables.currentBlock))
            if (ab.block.getType().name().toLowerCase().contains("glass_pane")) {
                data180.setFace(doBlockFace(ab.blockFace), true);
                GlassPane pane = (GlassPane) doFencePane(ab.block);
                PlaceRelatives(pane, ab.blockFace, ab.block.getType());
            } else if (ab.block.getType().name().equalsIgnoreCase("iron_bars")) {
                data180.setFace(doBlockFace(ab.blockFace), true);
                Fence bars = (Fence) doFencePane(ab.block);
                PlaceRelatives(bars, ab.blockFace, ab.block.getType());
            } else {
                genericFencePane(data180, ab);
            }
        Place(data180);
        if (user.variables.touchingBlock.getType().name().toLowerCase().contains("glass_pane")) {
            GlassPane pane = (GlassPane) user.variables.touchingBlock.getBlockData().clone();
            for (AdjacentBlock ab : Functions.getRelatives(user.variables.touchingBlock))
                if (ab.block.getType().name().toLowerCase().contains("glass_pane")) {
                    pane.setFace(ab.blockFace, true);
                } else if (user.variables.currentBlock.getType().name().equalsIgnoreCase("iron_bars"))
                    pane.setFace(ab.blockFace, true);
                else if (ab.block.getType().isSolid() && ab.block.getType().isOccluding())
                    pane.setFace(ab.blockFace, true);
                else if (ab.block.getBlockData() instanceof Slab && ((Slab) ab.block.getBlockData()).getType() == Slab.Type.DOUBLE)
                    pane.setFace(ab.blockFace, true);
                else if (ab.block.getBlockData() instanceof Stairs) {
                    if (((Stairs) ab.block.getBlockData()).getFacing().getOppositeFace() == ab.blockFace)
                        pane.setFace(ab.blockFace, true);
                } else
                    pane.setFace(ab.blockFace, false);
            Functions.PlaceBlock(user.variables.touchingBlock, pane, pane.getMaterial(), user);
        }
    }

    private MultipleFacing doFencePane(Block b) {
        MultipleFacing data = (MultipleFacing) b.getBlockData().clone();
        for (AdjacentBlock ab : Functions.getRelatives(b)) {
            if ((ab.block.getType() == Material.AIR || ab.block == user.variables.currentBlock) && !user.variables.placing) {
                data.setFace(doBlockFace(ab.blockFace), false);
                continue;
            }
            if (b.getType() == Material.NETHER_BRICK_FENCE) {
                if (ab.block.getType().name().toLowerCase().contains("gate")) {
                    Gate gate = (Gate) ab.block.getBlockData().clone();
                    data.setFace(doBlockFace(ab.blockFace), !((gate.getFacing() == ab.blockFace) || gate.getFacing().getOppositeFace() == ab.blockFace));
                } else if (ab.block.getType() == Material.NETHER_BRICK_FENCE)
                    data.setFace(doBlockFace(ab.blockFace), true);
                else {
                    genericFencePane(data, ab);
                }
            } else if (b.getType().name().toLowerCase().contains("cobblestone_wall")) {
                if (ab.block.getType().name().toLowerCase().contains("cobblestone_wall"))
                    data.setFace(doBlockFace(ab.blockFace), true);
                else if (ab.block.getType().name().toLowerCase().contains("gate")) {
                    Gate gate = (Gate) ab.block.getBlockData().clone();
                    data.setFace(doBlockFace(ab.blockFace), !((gate.getFacing() == ab.blockFace) || gate.getFacing().getOppositeFace() == ab.blockFace));
                } else {
                    genericFencePane(data, ab);
                }
                data.setFace(UP, ((!data.getFaces().contains(EAST) || !data.getFaces().contains(WEST)) || (data.getFaces().contains(NORTH) || data.getFaces().contains(SOUTH)))
                        && ((data.getFaces().contains(EAST) || data.getFaces().contains(WEST)) || (!data.getFaces().contains(NORTH) || !data.getFaces().contains(SOUTH))));
            } else if (b.getType().name().equalsIgnoreCase("iron_bars") || b.getType().name().toLowerCase().contains("glass_pane")) {
                if (ab.block.getType().name().equalsIgnoreCase("iron_bars") || ab.block.getType().name().toLowerCase().contains("glass_pane"))
                    data.setFace(doBlockFace(ab.blockFace), true);
                else {
                    genericFencePane(data, ab);
                }
            } else {
                if (ab.block.getType().name().toLowerCase().contains("gate")) {
                    Gate gate = (Gate) ab.block.getBlockData().clone();
                    data.setFace(doBlockFace(ab.blockFace), !((gate.getFacing() == ab.blockFace) || gate.getFacing().getOppositeFace() == ab.blockFace));
                } else if (ab.block.getType().name().toLowerCase().contains("fence"))
                    data.setFace(doBlockFace(ab.blockFace), true);
                else {
                    genericFencePane(data, ab);
                }
            }
        }
        return data;
    }

    private BlockFace doBlockFace(BlockFace bf) {
        return switch (bf) {
            case NORTH -> SOUTH;
            case EAST -> WEST;
            case SOUTH -> NORTH;
            case WEST -> EAST;
            default -> bf;
        };
    }

    private void genericFencePane(MultipleFacing data, AdjacentBlock ab) {
        if (ab.block.getType().isSolid() && ab.block.getType().isOccluding())
            data.setFace(doBlockFace(ab.blockFace), true);
        else if (ab.block.getBlockData() instanceof Slab && ((Slab) ab.block.getBlockData()).getType() == Slab.Type.DOUBLE)
            data.setFace(doBlockFace(ab.blockFace), true);
        else if (ab.block.getBlockData() instanceof Stairs) {
            data.setFace(doBlockFace(ab.blockFace), ((Stairs) ab.block.getBlockData()).getFacing().getOppositeFace() == ab.blockFace);
        } else
            data.setFace(doBlockFace(ab.blockFace), false);
    }

    public void Gates() {
        Gate data180 = (Gate) user.variables.dataCopy.clone();
        data180.setFacing(doBlockFace(data180.getFacing()));
        Place(data180);
        for (AdjacentBlock ab : Functions.getRelatives(user.variables.currentBlock))
            if (ab.block.getBlockData() instanceof Fence) {
                Fence fence = (Fence) doFencePane(ab.block);
                PlaceRelatives(fence, ab.blockFace, ab.block.getType());
            }
    }

    public void ButtonLevers() {
        Switch data180 = (Switch) user.variables.dataCopy.clone();
        if (Functions.Up(user)) {
            switch (Functions.LookDirection(user)) {
                case "south" -> data180.setFacing(NORTH);
                case "north" -> data180.setFacing(SOUTH);
                case "east" -> data180.setFacing(WEST);
                case "west" -> data180.setFacing(EAST);
            }
            data180.setFace(Switch.Face.FLOOR);
        } else if (Functions.Down(user)) {
            data180.setFace(Switch.Face.CEILING);
        }
        if (Functions.East(user)) {
            data180.setFacing(WEST);
        } else if (Functions.West(user)) {
            data180.setFacing(EAST);
        } else if (Functions.South(user)) {
            data180.setFacing(NORTH);
        } else {
            data180.setFacing(SOUTH);
        }
        Place(data180);
    }

    public void Torches() {
        String mat = user.variables.materialCopy.name();
        if (mat.equalsIgnoreCase("REDSTONE_WALL_TORCH")) {
            RedstoneWallTorch data180 = (RedstoneWallTorch) user.variables.dataCopy;
            if (Functions.East(user)) {
                data180.setFacing(WEST);
            } else if (Functions.West(user)) {
                data180.setFacing(EAST);
            } else if (Functions.South(user)) {
                data180.setFacing(NORTH);
            } else {
                data180.setFacing(SOUTH);
            }
            Place(data180);
        } else if (mat.equalsIgnoreCase("WALL_TORCH")) {
            Directional data180 = (Directional) user.variables.dataCopy;
            if (Functions.East(user)) {
                data180.setFacing(WEST);
            } else if (Functions.West(user)) {
                data180.setFacing(EAST);
            } else if (Functions.South(user)) {
                data180.setFacing(NORTH);
            } else {
                data180.setFacing(SOUTH);
            }
            Place(data180);
        }
    }

    public void Halfslabs() {
        Place(user.variables.dataCopy);
        Slab data = (Slab) user.variables.dataCopy;
        if (data.getType() == Slab.Type.DOUBLE) {
            for (AdjacentBlock ab : Functions.getRelatives(user.variables.currentBlock))
                if (ab.block.getBlockData() instanceof Fence) {
                    Fence fence = (Fence) doFencePane(ab.block);
                    PlaceRelatives(fence, ab.blockFace, ab.block.getType());
                }
        }
    }

    public void Doors() {
        Door.Hinge hinge = ((Door) user.variables.dataCopy).getHinge();
        Door datab = (Door) user.variables.currentBlock.getType().createBlockData();
        datab.setHalf(Bisected.Half.BOTTOM);
        datab.setHinge(hinge);
        Door datat = (Door) user.variables.currentBlock.getType().createBlockData();
        datat.setHalf(Bisected.Half.TOP);
        datat.setHinge(hinge);
        Door datab90 = (Door) datab.clone();
        Door datab180 = (Door) datab.clone();
        Door datab270 = (Door) datab.clone();
        Door datat90 = (Door) datat.clone();
        Door datat180 = (Door) datat.clone();
        Door datat270 = (Door) datat.clone();
        switch (Functions.LookDirection(user)) {
            case "south" -> {
                datab90.setFacing(WEST);
                datab180.setFacing(NORTH);
                datab270.setFacing(EAST);
                Place(datab180);
                datab90.setFacing(WEST);
                datab180.setFacing(NORTH);
                datab270.setFacing(EAST);
                Place(datab180, 1);
            }
            case "north" -> {
                datab90.setFacing(EAST);
                datab180.setFacing(SOUTH);
                datab270.setFacing(WEST);
                Place(datab180);
                datat90.setFacing(EAST);
                datat180.setFacing(SOUTH);
                datat270.setFacing(WEST);
                Place(datab180, 1);
            }
            case "east" -> {
                datab90.setFacing(SOUTH);
                datab180.setFacing(WEST);
                datab270.setFacing(NORTH);
                Place(datab180);
                datat90.setFacing(SOUTH);
                datat180.setFacing(WEST);
                datat270.setFacing(NORTH);
                Place(datab180, 1);
            }
            case "west" -> {
                datab90.setFacing(NORTH);
                datab180.setFacing(EAST);
                datab270.setFacing(SOUTH);
                Place(datab180);
                datat90.setFacing(SOUTH);
                datat180.setFacing(WEST);
                datat270.setFacing(NORTH);
                Place(datab180, 1);
            }
        }
    }

    public void Trapdoors() {
        TrapDoor data = (TrapDoor) user.variables.dataCopy.clone();
        TrapDoor data180 = (TrapDoor) user.variables.dataCopy.clone();
        data180.setFacing(doBlockFace(data.getFacing()));
        Place(data180);
    }

    public void Pistons() {
        Piston data = (Piston) user.variables.dataCopy.clone();
        Piston data180 = (Piston) user.variables.dataCopy.clone();
        data180.setFacing(doBlockFace(data.getFacing()));
        Place(data180);
    }

    public void EndRods() {
        Directional data = (Directional) user.variables.dataCopy.clone();
        Directional data180 = (Directional) user.variables.dataCopy.clone();
        data180.setFacing(doBlockFace(data.getFacing()));
        Place(data180);
    }

    public void Chests() {
        if (user.variables.currentBlock.getType() == Material.ENDER_CHEST) {
            EnderChest data = (EnderChest) user.variables.dataCopy;
            data.setFacing(doBlockFace(data.getFacing()));
            Place(data);
        } else {
            Chest data = (Chest) user.variables.dataCopy.clone();
            data.setFacing(doBlockFace(data.getFacing()));
            if (data.getType() != Chest.Type.SINGLE) {
                switch (((Chest) user.variables.dataCopy).getFacing()) {
                    case NORTH:
                        if (user.variables.currentBlock.getRelative(BlockFace.EAST).getType() == user.variables.currentBlock.getType() &&
                                ((Chest) user.variables.currentBlock.getRelative(BlockFace.EAST).getBlockData()).getFacing() == NORTH &&
                                ((Chest) user.variables.currentBlock.getRelative(BlockFace.EAST).getBlockData()).getType() == Chest.Type.SINGLE) {
                            Chest chest180 = (Chest) user.variables.currentBlock.getRelative(BlockFace.EAST).getBlockData().clone();
                            Chest chest = (Chest) user.variables.currentBlock.getRelative(BlockFace.EAST).getBlockData().clone();
                            if (chest.getFacing() == NORTH && chest.getType() == Chest.Type.SINGLE) {
                                chest180.setType(Chest.Type.RIGHT);
                                chest.setType(Chest.Type.RIGHT);
                                chest180.setFacing(BlockFace.SOUTH);
                                PlaceRelatives(chest180, BlockFace.EAST, chest.getMaterial());
                                Functions.PlaceBlockRelative(user.variables.xDif, user.variables.yDif, user.variables.zDif, chest, chest.getMaterial(), user, BlockFace.EAST);
                                data.setType(Chest.Type.LEFT);
                            } else
                                data.setType(Chest.Type.SINGLE);
                        } else if (user.variables.currentBlock.getRelative(BlockFace.WEST).getType() == user.variables.currentBlock.getType()) {
                            Chest chest180 = (Chest) user.variables.currentBlock.getRelative(BlockFace.WEST).getBlockData().clone();
                            Chest chest = (Chest) user.variables.currentBlock.getRelative(BlockFace.WEST).getBlockData().clone();
                            if (chest.getFacing() == NORTH && chest.getType() == Chest.Type.SINGLE) {
                                chest180.setType(Chest.Type.LEFT);
                                chest.setType(Chest.Type.LEFT);
                                chest180.setFacing(BlockFace.SOUTH);
                                PlaceRelatives(chest180, BlockFace.WEST, chest.getMaterial());
                                Functions.PlaceBlockRelative(user.variables.xDif, user.variables.yDif, user.variables.zDif, chest, chest.getMaterial(), user, BlockFace.WEST);
                                data.setType(Chest.Type.RIGHT);
                            } else
                                data.setType(Chest.Type.SINGLE);
                        }
                        break;
                    case EAST:
                        if (user.variables.currentBlock.getRelative(BlockFace.SOUTH).getType() == user.variables.currentBlock.getType() &&
                                ((Chest) user.variables.currentBlock.getRelative(BlockFace.SOUTH).getBlockData()).getFacing() == EAST &&
                                ((Chest) user.variables.currentBlock.getRelative(BlockFace.SOUTH).getBlockData()).getType() == Chest.Type.SINGLE) {
                            Chest chest180 = (Chest) user.variables.currentBlock.getRelative(BlockFace.SOUTH).getBlockData().clone();
                            Chest chest = (Chest) user.variables.currentBlock.getRelative(BlockFace.SOUTH).getBlockData().clone();
                            if (chest.getFacing() == EAST && chest.getType() == Chest.Type.SINGLE) {
                                chest180.setType(Chest.Type.RIGHT);
                                chest.setType(Chest.Type.RIGHT);
                                chest180.setFacing(BlockFace.WEST);
                                PlaceRelatives(chest180, BlockFace.SOUTH, chest.getMaterial());
                                Functions.PlaceBlockRelative(user.variables.xDif, user.variables.yDif, user.variables.zDif, chest, chest.getMaterial(), user, BlockFace.SOUTH);
                                data.setType(Chest.Type.LEFT);
                            } else
                                data.setType(Chest.Type.SINGLE);
                        } else if (user.variables.currentBlock.getRelative(BlockFace.NORTH).getType() == user.variables.currentBlock.getType()) {
                            Chest chest180 = (Chest) user.variables.currentBlock.getRelative(BlockFace.NORTH).getBlockData().clone();
                            Chest chest = (Chest) user.variables.currentBlock.getRelative(BlockFace.NORTH).getBlockData().clone();
                            if (chest.getFacing() == EAST && chest.getType() == Chest.Type.SINGLE) {
                                chest180.setType(Chest.Type.LEFT);
                                chest.setType(Chest.Type.LEFT);
                                chest180.setFacing(BlockFace.WEST);
                                PlaceRelatives(chest180, BlockFace.NORTH, chest.getMaterial());
                                Functions.PlaceBlockRelative(user.variables.xDif, user.variables.yDif, user.variables.zDif, chest, chest.getMaterial(), user, BlockFace.NORTH);
                                data.setType(Chest.Type.RIGHT);
                            } else
                                data.setType(Chest.Type.SINGLE);
                        }
                        break;
                    case SOUTH:
                        if (user.variables.currentBlock.getRelative(BlockFace.WEST).getType() == user.variables.currentBlock.getType() &&
                                ((Chest) user.variables.currentBlock.getRelative(BlockFace.WEST).getBlockData()).getFacing() == SOUTH &&
                                ((Chest) user.variables.currentBlock.getRelative(BlockFace.WEST).getBlockData()).getType() == Chest.Type.SINGLE) {
                            Chest chest180 = (Chest) user.variables.currentBlock.getRelative(BlockFace.WEST).getBlockData().clone();
                            Chest chest = (Chest) user.variables.currentBlock.getRelative(BlockFace.WEST).getBlockData().clone();
                            if (chest.getFacing() == SOUTH && chest.getType() == Chest.Type.SINGLE) {
                                chest180.setType(Chest.Type.RIGHT);
                                chest.setType(Chest.Type.RIGHT);
                                chest180.setFacing(BlockFace.NORTH);
                                PlaceRelatives(chest180, BlockFace.WEST, chest.getMaterial());
                                Functions.PlaceBlockRelative(user.variables.xDif, user.variables.yDif, user.variables.zDif, chest, chest.getMaterial(), user, BlockFace.WEST);
                                data.setType(Chest.Type.LEFT);
                            } else
                                data.setType(Chest.Type.SINGLE);
                        } else if (user.variables.currentBlock.getRelative(BlockFace.EAST).getType() == user.variables.currentBlock.getType()) {
                            Chest chest180 = (Chest) user.variables.currentBlock.getRelative(BlockFace.EAST).getBlockData().clone();
                            Chest chest = (Chest) user.variables.currentBlock.getRelative(BlockFace.EAST).getBlockData().clone();
                            if (chest.getFacing() == SOUTH && chest.getType() == Chest.Type.SINGLE) {
                                chest180.setType(Chest.Type.LEFT);
                                chest.setType(Chest.Type.LEFT);
                                chest180.setFacing(BlockFace.NORTH);
                                PlaceRelatives(chest180, BlockFace.EAST, chest.getMaterial());
                                Functions.PlaceBlockRelative(user.variables.xDif, user.variables.yDif, user.variables.zDif, chest, chest.getMaterial(), user, BlockFace.EAST);
                                data.setType(Chest.Type.RIGHT);
                            } else
                                data.setType(Chest.Type.SINGLE);
                        }
                        break;
                    case WEST:
                        if (user.variables.currentBlock.getRelative(BlockFace.NORTH).getType() == user.variables.currentBlock.getType() &&
                                ((Chest) user.variables.currentBlock.getRelative(BlockFace.NORTH).getBlockData()).getFacing() == WEST &&
                                ((Chest) user.variables.currentBlock.getRelative(BlockFace.NORTH).getBlockData()).getType() == Chest.Type.SINGLE) {
                            Chest chest180 = (Chest) user.variables.currentBlock.getRelative(BlockFace.NORTH).getBlockData().clone();
                            Chest chest = (Chest) user.variables.currentBlock.getRelative(BlockFace.NORTH).getBlockData().clone();
                            if (chest.getFacing() == WEST && chest.getType() == Chest.Type.SINGLE) {
                                chest180.setType(Chest.Type.RIGHT);
                                chest.setType(Chest.Type.RIGHT);
                                chest180.setFacing(BlockFace.EAST);
                                PlaceRelatives(chest180, BlockFace.NORTH, chest.getMaterial());
                                Functions.PlaceBlockRelative(user.variables.xDif, user.variables.yDif, user.variables.zDif, chest, chest.getMaterial(), user, BlockFace.NORTH);
                                data.setType(Chest.Type.LEFT);
                            } else
                                data.setType(Chest.Type.SINGLE);
                        } else if (user.variables.currentBlock.getRelative(BlockFace.SOUTH).getType() == user.variables.currentBlock.getType()) {
                            Chest chest180 = (Chest) user.variables.currentBlock.getRelative(BlockFace.SOUTH).getBlockData().clone();
                            Chest chest = (Chest) user.variables.currentBlock.getRelative(BlockFace.SOUTH).getBlockData().clone();
                            if (chest.getFacing() == WEST && chest.getType() == Chest.Type.SINGLE) {
                                chest180.setType(Chest.Type.LEFT);
                                chest.setType(Chest.Type.LEFT);
                                chest180.setFacing(BlockFace.EAST);
                                PlaceRelatives(chest180, BlockFace.SOUTH, chest.getMaterial());
                                Functions.PlaceBlockRelative(user.variables.xDif, user.variables.yDif, user.variables.zDif, chest, chest.getMaterial(), user, BlockFace.SOUTH);
                                data.setType(Chest.Type.RIGHT);
                            } else
                                data.setType(Chest.Type.SINGLE);
                        }
                        break;
                }
            }
            Place(data);
        }
    }

    public void RotateXZ() {
        Directional data = (Directional) user.variables.dataCopy.clone();
        Directional data180 = (Directional) user.variables.dataCopy.clone();
        data180.setFacing(doBlockFace(data.getFacing()));
        Place(data180);
    }

    public void Vines() {
        MultipleFacing data = (MultipleFacing) user.variables.dataCopy;
        MultipleFacing vine180 = (MultipleFacing) user.variables.dataCopy.clone();
        for (BlockFace face : data.getFaces()) {
            vine180.setFace(face, false);
            vine180.setFace(doBlockFace(face), true);
        }
        Place(vine180);
    }

    public void TripWire() {
        TripwireHook data = (TripwireHook) user.variables.dataCopy.clone();
        TripwireHook data180 = (TripwireHook) user.variables.dataCopy.clone();
        data180.setFacing(doBlockFace(data.getFacing()));
        Place(data180);
    }

    public void Hopper() {
        Hopper data = (Hopper) user.variables.dataCopy.clone();
        Hopper data180 = (Hopper) user.variables.dataCopy.clone();
        data180.setFacing(doBlockFace(data.getFacing()));
        Place(data180);
    }

    public void TallFlower() {
        Bisected data = (Bisected) user.variables.dataCopy.clone();
        Bisected data2 = (Bisected) user.variables.dataCopy.clone();
        data2.setHalf(data.getHalf() == Bisected.Half.BOTTOM ? Bisected.Half.TOP : Bisected.Half.BOTTOM);
        Place(data);
        Place(data2, data.getHalf() == Bisected.Half.BOTTOM ? 1 : -1);
    }

    public void Terracotta() {
        Directional data = (Directional) user.variables.dataCopy;
        Directional data180 = (Directional) user.variables.dataCopy.clone();
        switch (data.getFacing()) {
            case NORTH -> data180.setFacing(BlockFace.EAST);
            case EAST -> data180.setFacing(BlockFace.EAST);
            case SOUTH -> data180.setFacing(BlockFace.EAST);
            case WEST -> data180.setFacing(BlockFace.EAST);
        }
        Place(data180);
    }

    private void Place(BlockData data180) {

        Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, -user.variables.zDif, data180, user);
    }

    private void Place(BlockData data180, int yDif) {
        Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif + yDif, -user.variables.zDif, data180, user);
    }

    private void PlaceRelatives(BlockData data180, BlockFace bf, Material mat) {
        Functions.PlaceBlockRelative(-user.variables.xDif, user.variables.yDif, -user.variables.zDif, data180, mat, user, doBlockFace(bf));
    }

    public void Default() {
        Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, -user.variables.zDif, user);
        if (user.variables.currentBlock.getType().isSolid())
            for (AdjacentBlock ab : Functions.getRelatives(user.variables.currentBlock))
                if (ab.block.getBlockData() instanceof Fence) {
                    Fence fence = (Fence) doFencePane(ab.block);
                    PlaceRelatives(fence, ab.blockFace, ab.block.getType());
                } else if (ab.block.getType().name().toLowerCase().contains("glass_pane")) {
                    GlassPane pane = (GlassPane) doFencePane(ab.block);
                    PlaceRelatives(pane, ab.blockFace, ab.block.getType());
                }
    }

    @SuppressWarnings("Duplicates")
    public void Remove() {
        Functions.RemoveBlock(-user.variables.xDif, user.variables.yDif, -user.variables.zDif, user);
        if (user.variables.currentBlock.getBlockData() instanceof Bisected) {
            Bisected data = (Bisected) user.variables.currentBlock.getBlockData().clone();
            int y = user.variables.yDif;
            Functions.RemoveBlock(-user.variables.xDif, data.getHalf() == Bisected.Half.BOTTOM ? y + 1 : y - 1, -user.variables.zDif, user);
        }
        for (AdjacentBlock ab : Functions.getRelatives(user.variables.currentBlock))
            if (ab.block.getBlockData() instanceof Fence) {
                Fence fence = (Fence) doFencePane(ab.block);
                fence.setFace(doBlockFace(ab.blockFace).getOppositeFace(), false);
                PlaceRelatives(fence, ab.blockFace, ab.block.getType());
            } else if (ab.block.getType().name().toLowerCase().contains("glass_pane")) {
                GlassPane pane = (GlassPane) doFencePane(ab.block);
                pane.setFace(doBlockFace(ab.blockFace).getOppositeFace(), false);
                PlaceRelatives(pane, ab.blockFace, ab.block.getType());
            } else if (ab.block.getBlockData() instanceof Gate && user.variables.currentBlock.getType().name().toLowerCase().contains("cobblestone_wall")) {
                Gate gate = (Gate) ab.block.getBlockData().clone();
                if (!((gate.getFacing() == ab.blockFace) || gate.getFacing().getOppositeFace() == ab.blockFace))
                    if (!ab.block.getRelative(ab.blockFace).getType().name().toLowerCase().contains("cobblestone_wall")) {
                        gate.setInWall(false);
                        PlaceRelatives(gate, ab.blockFace, ab.block.getType());
                    }
            } else if (ab.block.getBlockData() instanceof Chest && user.variables.currentBlock.getBlockData() instanceof Chest && user.variables.currentBlock.getType() == ab.block.getType()) {
                Chest data = (Chest) user.variables.currentBlock.getBlockData().clone();
                if (((Chest) ab.block.getBlockData()).getFacing() == data.getFacing())
                    switch (data.getFacing()) {
                        case NORTH:
                            if ((data.getType().equals(Chest.Type.RIGHT) && ab.blockFace.equals(BlockFace.WEST)) || (data.getType().equals(Chest.Type.LEFT) && ab.blockFace.equals(BlockFace.EAST))) {
                                doBreakChest(ab);
                            }
                            break;
                        case EAST:
                            if ((data.getType().equals(Chest.Type.LEFT) && ab.blockFace.equals(SOUTH)) || (data.getType().equals(Chest.Type.RIGHT) && ab.blockFace.equals(NORTH))) {
                                doBreakChest(ab);
                            }
                            break;
                        case SOUTH:
                            if ((data.getType().equals(Chest.Type.RIGHT) && ab.blockFace.equals(BlockFace.EAST)) || (data.getType().equals(Chest.Type.LEFT) && ab.blockFace.equals(BlockFace.WEST))) {
                                doBreakChest(ab);
                            }
                            break;
                        case WEST:
                            if ((data.getType().equals(Chest.Type.LEFT) && ab.blockFace.equals(BlockFace.NORTH)) || (data.getType().equals(Chest.Type.RIGHT) && ab.blockFace.equals(BlockFace.SOUTH))) {
                                doBreakChest(ab);
                            }
                            break;
                    }
            }
    }


    private void doBreakChest(AdjacentBlock ab) {
        Chest chest = (Chest) ab.block.getBlockData().clone();
        Chest chest180 = (Chest) ab.block.getBlockData().clone();
        chest.setType(Chest.Type.SINGLE);
        chest180.setType(Chest.Type.SINGLE);
        chest180.setFacing(doBlockFace(chest.getFacing()));
        PlaceRelatives(chest180, ab.blockFace, chest.getMaterial());
        Functions.PlaceBlockRelative(user.variables.xDif, user.variables.yDif, user.variables.zDif, chest, ab.block.getType(), user, ab.blockFace);
    }
}
