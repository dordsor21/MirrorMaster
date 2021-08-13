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

import java.util.Arrays;

import static org.bukkit.block.BlockFace.*;

@SuppressWarnings("Duplicates")
public class Rotating90 implements Mirroring {

    private final User user;

    public Rotating90(User user) {
        this.user = user;
    }

    public void Stairs() {
        Stairs data = (Stairs) user.variables.dataCopy.clone();
        data.setHalf(Bisected.Half.BOTTOM);
        if (Functions.Down(user))
            data.setHalf(Bisected.Half.TOP);
        String shape = data.getShape().name();
        Stairs data90 = (Stairs) data.clone();
        Stairs data180 = (Stairs) data.clone();
        Stairs data270 = (Stairs) data.clone();
        data.setShape(Stairs.Shape.valueOf(shape.contains("LEFT") ? shape.replace("LEFT", "RIGHT") : shape.replace("RIGHT", "LEFT")));
        switch (Functions.LookDirection(user)) {
            case "south" -> {
                data90.setFacing(WEST);
                data180.setFacing(NORTH);
                data270.setFacing(EAST);
            }
            case "north" -> {
                data90.setFacing(EAST);
                data180.setFacing(SOUTH);
                data270.setFacing(WEST);
            }
            case "east" -> {
                data90.setFacing(SOUTH);
                data180.setFacing(WEST);
                data270.setFacing(NORTH);
            }
            case "west" -> {
                data90.setFacing(NORTH);
                data180.setFacing(EAST);
                data270.setFacing(SOUTH);
            }
        }
        Place(data90, data180, data270);

        for (BlockFace face : data.getFaces())
            if (user.variables.currentBlock.getRelative(face).getBlockData() instanceof Fence) {
                Block b = user.variables.currentBlock.getRelative(face);
                Fence[] fence = Arrays.copyOf(doFencePane(b), 3, Fence[].class);
                fence[0].setFace(doBlockFace(face, 1).getOppositeFace(), true);
                fence[1].setFace(doBlockFace(face, 2).getOppositeFace(), true);
                fence[2].setFace(doBlockFace(face, 3).getOppositeFace(), true);
                PlaceRelatives(fence[0], fence[1], fence[2], face, b.getType());
            }
    }

    public void Fences() {
        Fence data90 = (Fence) user.variables.dataCopy.clone();
        Fence data180 = (Fence) user.variables.dataCopy.clone();
        Fence data270 = (Fence) user.variables.dataCopy.clone();
        for (AdjacentBlock ab : Functions.getRelatives(user.variables.currentBlock)) {
            if (user.variables.currentBlock.getType() == Material.NETHER_BRICK_FENCE) {
                if (ab.block.getType().name().toLowerCase().contains("gate")) {
                    Gate gate = (Gate) ab.block.getBlockData().clone();
                    if (!((gate.getFacing() == ab.blockFace) || gate.getFacing().getOppositeFace() == ab.blockFace)) {
                        data90.setFace(doBlockFace(ab.blockFace, 1), true);
                        data180.setFace(doBlockFace(ab.blockFace, 2), true);
                        data270.setFace(doBlockFace(ab.blockFace, 3), true);
                    } else {
                        data90.setFace(doBlockFace(ab.blockFace, 1), false);
                        data180.setFace(doBlockFace(ab.blockFace, 2), false);
                        data270.setFace(doBlockFace(ab.blockFace, 3), false);
                    }
                } else if (ab.block.getType() == Material.NETHER_BRICK_FENCE) {
                    data90.setFace(doBlockFace(ab.blockFace, 1), true);
                    data180.setFace(doBlockFace(ab.blockFace, 2), true);
                    data270.setFace(doBlockFace(ab.blockFace, 3), true);
                    Fence[] fence = Arrays.copyOf(doFencePane(ab.block), 3, Fence[].class);

                    PlaceRelatives(fence[0], fence[1], fence[2], ab.blockFace, ab.block.getType());
                } else {
                    genericFencePane(data90, ab, 1);
                    genericFencePane(data180, ab, 2);
                    genericFencePane(data270, ab, 3);
                }
            } else if (user.variables.currentBlock.getType().name().toLowerCase().contains("cobblestone_wall")) {
                if (ab.block.getType().name().toLowerCase().contains("gate")) {
                    Gate gate = (Gate) ab.block.getBlockData().clone();
                    if ((gate.getFacing() == ab.blockFace || gate.getFacing().getOppositeFace() == ab.blockFace)) {
                        data90.setFace(doBlockFace(ab.blockFace, 1), false);
                        data180.setFace(doBlockFace(ab.blockFace, 2), false);
                        data270.setFace(doBlockFace(ab.blockFace, 3), false);
                    } else {
                        data90.setFace(doBlockFace(ab.blockFace, 1), true);
                        data180.setFace(doBlockFace(ab.blockFace, 2), true);
                        data270.setFace(doBlockFace(ab.blockFace, 3), true);

                        gate.setInWall(true);
                        Gate gate90 = ((Gate) gate.clone());
                        Gate gate270 = ((Gate) gate.clone());
                        gate90.setFacing(doBlockFace(gate.getFacing(), 1));
                        gate270.setFacing(doBlockFace(gate.getFacing(), 1));

                        PlaceRelatives(gate90, gate, gate270, ab.blockFace, ab.block.getType());
                    }
                } else if (ab.block.getType().name().toLowerCase().contains("cobblestone_wall")) {
                    data90.setFace(doBlockFace(ab.blockFace, 1), true);
                    data180.setFace(doBlockFace(ab.blockFace, 2), true);
                    data270.setFace(doBlockFace(ab.blockFace, 3), true);

                    Fence[] wall = Arrays.copyOf(doFencePane(ab.block), 3, Fence[].class);

                    PlaceRelatives(wall[0], wall[1], wall[2], ab.blockFace, ab.block.getType());
                } else {
                    genericFencePane(data90, ab, 1);
                    genericFencePane(data180, ab, 2);
                    genericFencePane(data270, ab, 3);
                }
            } else if (user.variables.currentBlock.getType().name().equalsIgnoreCase("iron_bars")) {
                if (ab.block.getType().name().toLowerCase().contains("glass_pane")) {
                    data90.setFace(doBlockFace(ab.blockFace, 1), true);
                    data180.setFace(doBlockFace(ab.blockFace, 2), true);
                    data270.setFace(doBlockFace(ab.blockFace, 3), true);

                    GlassPane[] pane = Arrays.copyOf(doFencePane(ab.block), 3, GlassPane[].class);
                    PlaceRelatives(pane[0], pane[1], pane[2], ab.blockFace, ab.block.getType());
                } else if (ab.block.getType().equals(user.variables.currentBlock.getType())) {
                    data90.setFace(doBlockFace(ab.blockFace, 1), true);
                    data180.setFace(doBlockFace(ab.blockFace, 2), true);
                    data270.setFace(doBlockFace(ab.blockFace, 3), true);

                    Fence[] bars = Arrays.copyOf(doFencePane(ab.block), 3, Fence[].class);
                    PlaceRelatives(bars[0], bars[1], bars[2], ab.blockFace, ab.block.getType());
                } else {
                    genericFencePane(data90, ab, 1);
                    genericFencePane(data180, ab, 2);
                    genericFencePane(data270, ab, 3);
                }
            } else {
                if (ab.block.getType().name().toLowerCase().contains("gate")) {
                    Gate gate = (Gate) ab.block.getBlockData().clone();
                    if (!((gate.getFacing() == ab.blockFace) || gate.getFacing().getOppositeFace() == ab.blockFace)) {
                        data90.setFace(doBlockFace(ab.blockFace, 1), true);
                        data180.setFace(doBlockFace(ab.blockFace, 2), true);
                        data270.setFace(doBlockFace(ab.blockFace, 3), true);
                    } else {
                        data90.setFace(doBlockFace(ab.blockFace, 1), false);
                        data180.setFace(doBlockFace(ab.blockFace, 2), false);
                        data270.setFace(doBlockFace(ab.blockFace, 3), false);
                    }
                } else if (ab.block.getType().name().toLowerCase().contains("fence")) {
                    data90.setFace(doBlockFace(ab.blockFace, 1), true);
                    data180.setFace(doBlockFace(ab.blockFace, 2), true);
                    data270.setFace(doBlockFace(ab.blockFace, 3), true);

                    Fence[] fence = Arrays.copyOf(doFencePane(ab.block), 3, Fence[].class);
                    PlaceRelatives(fence[0], fence[1], fence[2], ab.blockFace, ab.block.getType());
                } else {
                    genericFencePane(data90, ab, 1);
                    genericFencePane(data180, ab, 2);
                    genericFencePane(data270, ab, 3);
                }
            }
        }
        if (user.variables.currentBlock.getType().name().toLowerCase().contains("cobblestone_wall")) {
            Fence data = (Fence) user.variables.dataCopy;
            if ((data.getFaces().contains(EAST) && data.getFaces().contains(WEST)) && !(data.getFaces().contains(NORTH) || data.getFaces().contains(SOUTH))
                    || !(data.getFaces().contains(EAST) || data.getFaces().contains(WEST)) && (data.getFaces().contains(NORTH) && data.getFaces().contains(SOUTH))) {
                data90.setFace(UP, false);
                data180.setFace(UP, false);
                data270.setFace(UP, false);
            } else {
                data90.setFace(UP, true);
                data180.setFace(UP, true);
                data270.setFace(UP, true);
            }
        }
        Place(data90, data180, data270);
    }

    public void GlassPanes() {
        GlassPane data90 = (GlassPane) user.variables.dataCopy.clone();
        GlassPane data180 = (GlassPane) user.variables.dataCopy.clone();
        GlassPane data270 = (GlassPane) user.variables.dataCopy.clone();
        for (AdjacentBlock ab : Functions.getRelatives(user.variables.currentBlock))
            if (ab.block.getType().name().toLowerCase().contains("glass_pane")) {
                data90.setFace(doBlockFace(ab.blockFace, 1), true);
                data180.setFace(doBlockFace(ab.blockFace, 2), true);
                data270.setFace(doBlockFace(ab.blockFace, 3), true);
                GlassPane[] pane = Arrays.copyOf(doFencePane(ab.block), 3, GlassPane[].class);
                PlaceRelatives(pane[0], pane[1], pane[2], ab.blockFace, ab.block.getType());
            } else if (ab.block.getType().name().equalsIgnoreCase("iron_bars")) {
                data90.setFace(doBlockFace(ab.blockFace, 1), true);
                data180.setFace(doBlockFace(ab.blockFace, 2), true);
                data270.setFace(doBlockFace(ab.blockFace, 3), true);
                Fence[] bars = Arrays.copyOf(doFencePane(ab.block), 3, Fence[].class);
                PlaceRelatives(bars[0], bars[1], bars[2], ab.blockFace, ab.block.getType());
            } else {
                genericFencePane(data90, ab, 1);
                genericFencePane(data180, ab, 2);
                genericFencePane(data270, ab, 3);
            }
        Place(data90, data180, data270);
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

    private MultipleFacing[] doFencePane(Block b) {
        MultipleFacing[] data = new MultipleFacing[]{(MultipleFacing) b.getBlockData().clone(), (MultipleFacing) b.getBlockData().clone(), (MultipleFacing) b.getBlockData().clone()};
        MultipleFacing datat = (MultipleFacing) b.getBlockData().clone();
        for (int i = 1; i <= 3; i++) {
            for (AdjacentBlock ab : Functions.getRelatives(b)) {
                if ((ab.block.getType() == Material.AIR || ab.block == user.variables.currentBlock) && !user.variables.placing) {
                    data[i - 1].setFace(doBlockFace(ab.blockFace, i), false);
                    continue;
                }
                if (b.getType() == Material.NETHER_BRICK_FENCE) {
                    if (ab.block.getType().name().toLowerCase().contains("gate")) {
                        Gate gate = (Gate) ab.block.getBlockData().clone();
                        data[i - 1].setFace(doBlockFace(ab.blockFace, i), !((gate.getFacing() == ab.blockFace) || gate.getFacing().getOppositeFace() == ab.blockFace));
                    } else if (ab.block.getType() == Material.NETHER_BRICK_FENCE)
                        data[i - 1].setFace(doBlockFace(ab.blockFace, i), true);
                    else
                        data[i - 1] = genericFencePane(data[i - 1], ab, i);
                } else if (b.getType().name().toLowerCase().contains("cobblestone_wall")) {
                    if (ab.block.getType().name().toLowerCase().contains("cobblestone_wall"))
                        data[i - 1].setFace(doBlockFace(ab.blockFace, 1), true);
                    else if (ab.block.getType().name().toLowerCase().contains("gate")) {
                        Gate gate = (Gate) ab.block.getBlockData().clone();
                        data[i - 1].setFace(doBlockFace(ab.blockFace, i), !((gate.getFacing() == ab.blockFace) || gate.getFacing().getOppositeFace() == ab.blockFace));
                    } else
                        data[i - 1] = genericFencePane(data[i - 1], ab, i);
                    data[i - 1].setFace(UP, ((!datat.getFaces().contains(EAST) || !datat.getFaces().contains(WEST)) || (datat.getFaces().contains(NORTH) || datat.getFaces().contains(SOUTH)))
                            && ((datat.getFaces().contains(EAST) || datat.getFaces().contains(WEST)) || (!datat.getFaces().contains(NORTH) || !datat.getFaces().contains(SOUTH))));
                } else if (b.getType().name().equalsIgnoreCase("iron_bars") || b.getType().name().toLowerCase().contains("glass_pane")) {
                    if (ab.block.getType().name().equalsIgnoreCase("iron_bars") || ab.block.getType().name().toLowerCase().contains("glass_pane"))
                        data[i - 1].setFace(doBlockFace(ab.blockFace, i), true);
                    else
                        data[i - 1] = genericFencePane(data[i - 1], ab, i);
                } else {
                    if (ab.block.getType().name().toLowerCase().contains("gate")) {
                        Gate gate = (Gate) ab.block.getBlockData().clone();
                        data[i - 1].setFace(doBlockFace(ab.blockFace, i), !((gate.getFacing() == ab.blockFace) || gate.getFacing().getOppositeFace() == ab.blockFace));
                    } else if (ab.block.getType().name().toLowerCase().contains("fence"))
                        data[i - 1].setFace(doBlockFace(ab.blockFace, i), true);
                    else
                        data[i - 1] = genericFencePane(data[i - 1], ab, i);
                }
            }
        }
        return data;
    }

    private BlockFace doBlockFace(BlockFace bf, int i) {
        switch (i) {
            case 1:
                switch (bf) {
                    case NORTH:
                        return EAST;
                    case EAST:
                        return SOUTH;
                    case SOUTH:
                        return WEST;
                    case WEST:
                        return NORTH;
                }
            case 2:
                switch (bf) {
                    case NORTH:
                        return SOUTH;
                    case EAST:
                        return WEST;
                    case SOUTH:
                        return NORTH;
                    case WEST:
                        return EAST;
                }
            case 3:
                switch (bf) {
                    case NORTH:
                        return WEST;
                    case EAST:
                        return NORTH;
                    case SOUTH:
                        return EAST;
                    case WEST:
                        return SOUTH;
                }
        }
        return bf;
    }

    private MultipleFacing genericFencePane(MultipleFacing data, AdjacentBlock ab, int i) {
        if (ab.block.getType().isSolid() && ab.block.getType().isOccluding())
            data.setFace(doBlockFace(ab.blockFace, i), true);
        else if (ab.block.getBlockData() instanceof Slab && ((Slab) ab.block.getBlockData()).getType() == Slab.Type.DOUBLE)
            data.setFace(doBlockFace(ab.blockFace, i), true);
        else if (ab.block.getBlockData() instanceof Stairs) {
            data.setFace(doBlockFace(ab.blockFace, i), ((Stairs) ab.block.getBlockData()).getFacing().getOppositeFace() == ab.blockFace);
        } else
            data.setFace(doBlockFace(ab.blockFace, i), false);
        return data;
    }

    public void Gates() {
        Gate data90 = (Gate) user.variables.dataCopy.clone();
        Gate data180 = (Gate) user.variables.dataCopy.clone();
        Gate data270 = (Gate) user.variables.dataCopy.clone();
        data90.setFacing(doBlockFace(data90.getFacing(), 1));
        data180.setFacing(doBlockFace(data180.getFacing(), 2));
        data270.setFacing(doBlockFace(data270.getFacing(), 3));
        Place(data90, data180, data270);
        for (AdjacentBlock ab : Functions.getRelatives(user.variables.currentBlock))
            if (ab.block.getBlockData() instanceof Fence) {
                Fence[] fence = Arrays.copyOf(doFencePane(ab.block), 3, Fence[].class);
                PlaceRelatives(fence[0], fence[1], fence[2], ab.blockFace, ab.block.getType());
            }
    }

    public void ButtonLevers() {
        Switch data90 = (Switch) user.variables.dataCopy.clone();
        Switch data180 = (Switch) user.variables.dataCopy.clone();
        Switch data270 = (Switch) user.variables.dataCopy.clone();
        if (Functions.Up(user)) {
            switch (Functions.LookDirection(user)) {
                case "south" -> {
                    data90.setFacing(WEST);
                    data180.setFacing(NORTH);
                    data270.setFacing(EAST);
                }
                case "north" -> {
                    data90.setFacing(EAST);
                    data180.setFacing(SOUTH);
                    data270.setFacing(WEST);
                }
                case "east" -> {
                    data90.setFacing(SOUTH);
                    data180.setFacing(WEST);
                    data270.setFacing(NORTH);
                }
                case "west" -> {
                    data90.setFacing(NORTH);
                    data180.setFacing(EAST);
                    data270.setFacing(SOUTH);
                }
            }
            data90.setFace(Switch.Face.FLOOR);
            data180.setFace(Switch.Face.FLOOR);
            data270.setFace(Switch.Face.FLOOR);
        } else if (Functions.Down(user)) {
            data90.setFace(Switch.Face.CEILING);
            data180.setFace(Switch.Face.CEILING);
            data270.setFace(Switch.Face.CEILING);
        }
        if (Functions.East(user)) {
            data90.setFacing(SOUTH);
            data180.setFacing(WEST);
            data270.setFacing(NORTH);
        } else if (Functions.West(user)) {
            data90.setFacing(NORTH);
            data180.setFacing(EAST);
            data270.setFacing(SOUTH);
        } else if (Functions.South(user)) {
            data90.setFacing(WEST);
            data180.setFacing(NORTH);
            data270.setFacing(EAST);
        } else {
            data90.setFacing(EAST);
            data180.setFacing(SOUTH);
            data270.setFacing(WEST);
        }
        Place(data90, data180, data270);
    }

    public void Torches() {
        String mat = user.variables.materialCopy.name();
        if (mat.equalsIgnoreCase("REDSTONE_WALL_TORCH")) {
            RedstoneWallTorch data90 = (RedstoneWallTorch) user.variables.dataCopy;
            RedstoneWallTorch data180 = (RedstoneWallTorch) user.variables.dataCopy;
            RedstoneWallTorch data270 = (RedstoneWallTorch) user.variables.dataCopy;
            if (Functions.East(user)) {
                data90.setFacing(SOUTH);
                data180.setFacing(WEST);
                data270.setFacing(NORTH);
            } else if (Functions.West(user)) {
                data90.setFacing(NORTH);
                data180.setFacing(EAST);
                data270.setFacing(SOUTH);
            } else if (Functions.South(user)) {
                data90.setFacing(WEST);
                data180.setFacing(NORTH);
                data270.setFacing(EAST);
            } else {
                data90.setFacing(EAST);
                data180.setFacing(SOUTH);
                data270.setFacing(WEST);
            }
            Place(data90, data180, data270);
        } else if (mat.equalsIgnoreCase("WALL_TORCH")) {
            Directional data90 = (Directional) user.variables.dataCopy;
            Directional data180 = (Directional) user.variables.dataCopy;
            Directional data270 = (Directional) user.variables.dataCopy;
            if (Functions.East(user)) {
                data90.setFacing(SOUTH);
                data180.setFacing(WEST);
                data270.setFacing(NORTH);
            } else if (Functions.West(user)) {
                data90.setFacing(NORTH);
                data180.setFacing(EAST);
                data270.setFacing(SOUTH);
            } else if (Functions.South(user)) {
                data90.setFacing(WEST);
                data180.setFacing(NORTH);
                data270.setFacing(EAST);
            } else {
                data90.setFacing(EAST);
                data180.setFacing(SOUTH);
                data270.setFacing(WEST);
            }
            Place(data90, data180, data270);
        }
    }

    public void Halfslabs() {
        Place(user.variables.dataCopy, user.variables.dataCopy, user.variables.dataCopy);
        Slab data = (Slab) user.variables.dataCopy;
        if (data.getType() == Slab.Type.DOUBLE) {
            for (AdjacentBlock ab : Functions.getRelatives(user.variables.currentBlock))
                if (ab.block.getBlockData() instanceof Fence) {
                    Fence[] fence = Arrays.copyOf(doFencePane(ab.block), 3, Fence[].class);
                    PlaceRelatives(fence[0], fence[1], fence[2], ab.blockFace, ab.block.getType());
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
                Place(datab90, datab180, datab270);
                datab90.setFacing(WEST);
                datab180.setFacing(NORTH);
                datab270.setFacing(EAST);
                Place(datab90, datab180, datab270, 1);
            }
            case "north" -> {
                datab90.setFacing(EAST);
                datab180.setFacing(SOUTH);
                datab270.setFacing(WEST);
                Place(datab90, datab180, datab270);
                datat90.setFacing(EAST);
                datat180.setFacing(SOUTH);
                datat270.setFacing(WEST);
                Place(datab90, datab180, datab270, 1);
            }
            case "east" -> {
                datab90.setFacing(SOUTH);
                datab180.setFacing(WEST);
                datab270.setFacing(NORTH);
                Place(datab90, datab180, datab270);
                datat90.setFacing(SOUTH);
                datat180.setFacing(WEST);
                datat270.setFacing(NORTH);
                Place(datab90, datab180, datab270, 1);
            }
            case "west" -> {
                datab90.setFacing(NORTH);
                datab180.setFacing(EAST);
                datab270.setFacing(SOUTH);
                Place(datab90, datab180, datab270);
                datat90.setFacing(SOUTH);
                datat180.setFacing(WEST);
                datat270.setFacing(NORTH);
                Place(datab90, datab180, datab270, 1);
            }
        }
    }

    public void Trapdoors() {
        TrapDoor data = (TrapDoor) user.variables.dataCopy.clone();
        TrapDoor data90 = (TrapDoor) user.variables.dataCopy.clone();
        TrapDoor data180 = (TrapDoor) user.variables.dataCopy.clone();
        TrapDoor data270 = (TrapDoor) user.variables.dataCopy.clone();
        data90.setFacing(doBlockFace(data.getFacing(), 1));
        data180.setFacing(doBlockFace(data.getFacing(), 2));
        data270.setFacing(doBlockFace(data.getFacing(), 3));
        Place(data90, data180, data270);
    }

    public void Pistons() {
        Piston data = (Piston) user.variables.dataCopy.clone();
        Piston data90 = (Piston) user.variables.dataCopy.clone();
        Piston data180 = (Piston) user.variables.dataCopy.clone();
        Piston data270 = (Piston) user.variables.dataCopy.clone();
        data90.setFacing(doBlockFace(data.getFacing(), 1));
        data180.setFacing(doBlockFace(data.getFacing(), 2));
        data270.setFacing(doBlockFace(data.getFacing(), 3));
        Place(data90, data180, data270);
    }

    public void EndRods() {
        Directional data = (Directional) user.variables.dataCopy.clone();
        Directional data90 = (Directional) user.variables.dataCopy.clone();
        Directional data180 = (Directional) user.variables.dataCopy.clone();
        Directional data270 = (Directional) user.variables.dataCopy.clone();
        data90.setFacing(doBlockFace(data.getFacing(), 1));
        data180.setFacing(doBlockFace(data.getFacing(), 2));
        data270.setFacing(doBlockFace(data.getFacing(), 3));
        Place(data90, data180, data270);
    }

    public void Chests() {
        if (user.variables.currentBlock.getType() == Material.ENDER_CHEST) {
            EnderChest data90 = (EnderChest) user.variables.dataCopy.clone();
            EnderChest data180 = (EnderChest) user.variables.dataCopy.clone();
            EnderChest data270 = (EnderChest) user.variables.dataCopy.clone();
            data90.setFacing(doBlockFace(data90.getFacing(), 1));
            data180.setFacing(doBlockFace(data180.getFacing(), 2));
            data270.setFacing(doBlockFace(data270.getFacing(), 3));
            Place(data90, data180, data270);
        } else {
            Chest datat = (Chest) user.variables.dataCopy;
            Chest data90 = (Chest) user.variables.dataCopy.clone();
            Chest data180 = (Chest) user.variables.dataCopy.clone();
            Chest data270 = (Chest) user.variables.dataCopy.clone();
            data90.setFacing(doBlockFace(data90.getFacing(), 1));
            data180.setFacing(doBlockFace(data180.getFacing(), 2));
            data270.setFacing(doBlockFace(data270.getFacing(), 3));
            if (datat.getType() != Chest.Type.SINGLE) {
                switch (((Chest) user.variables.dataCopy).getFacing()) {
                    case NORTH:
                        if (user.variables.currentBlock.getRelative(BlockFace.EAST).getType() == user.variables.currentBlock.getType() &&
                                ((Chest) user.variables.currentBlock.getRelative(BlockFace.EAST).getBlockData()).getFacing() == NORTH &&
                                ((Chest) user.variables.currentBlock.getRelative(BlockFace.EAST).getBlockData()).getType() == Chest.Type.SINGLE) {
                            Chest chest = (Chest) user.variables.currentBlock.getRelative(BlockFace.EAST).getBlockData().clone();
                            Chest chest90 = (Chest) user.variables.currentBlock.getRelative(BlockFace.EAST).getBlockData().clone();
                            Chest chest180 = (Chest) user.variables.currentBlock.getRelative(BlockFace.EAST).getBlockData().clone();
                            Chest chest270 = (Chest) user.variables.currentBlock.getRelative(BlockFace.EAST).getBlockData().clone();
                            if (chest.getFacing() == NORTH && chest.getType() == Chest.Type.SINGLE) {
                                chest.setType(Chest.Type.RIGHT);
                                chest90.setType(Chest.Type.RIGHT);
                                chest180.setType(Chest.Type.RIGHT);
                                chest270.setType(Chest.Type.RIGHT);

                                chest.setFacing(BlockFace.NORTH);
                                chest90.setFacing(doBlockFace(BlockFace.NORTH, 1));
                                chest180.setFacing(doBlockFace(BlockFace.NORTH, 2));
                                chest270.setFacing(doBlockFace(BlockFace.NORTH, 3));

                                data90.setType(Chest.Type.LEFT);
                                data180.setType(Chest.Type.LEFT);
                                data270.setType(Chest.Type.LEFT);

                                Functions.PlaceBlockRelative(user.variables.xDif, user.variables.yDif, user.variables.zDif, chest, chest.getMaterial(), user, BlockFace.EAST);
                                PlaceRelatives(chest90, chest180, chest270, BlockFace.EAST, chest.getMaterial());
                            } else {
                                data90.setType(Chest.Type.SINGLE);
                                data180.setType(Chest.Type.SINGLE);
                                data270.setType(Chest.Type.SINGLE);
                            }
                        } else if (user.variables.currentBlock.getRelative(BlockFace.WEST).getType() == user.variables.currentBlock.getType()) {
                            Chest chest = (Chest) user.variables.currentBlock.getRelative(BlockFace.WEST).getBlockData().clone();
                            Chest chest90 = (Chest) user.variables.currentBlock.getRelative(BlockFace.WEST).getBlockData().clone();
                            Chest chest180 = (Chest) user.variables.currentBlock.getRelative(BlockFace.WEST).getBlockData().clone();
                            Chest chest270 = (Chest) user.variables.currentBlock.getRelative(BlockFace.WEST).getBlockData().clone();
                            if (chest.getFacing() == NORTH && chest.getType() == Chest.Type.SINGLE) {
                                chest.setType(Chest.Type.LEFT);
                                chest90.setType(Chest.Type.LEFT);
                                chest180.setType(Chest.Type.LEFT);
                                chest270.setType(Chest.Type.LEFT);

                                chest.setFacing(BlockFace.NORTH);
                                chest90.setFacing(doBlockFace(BlockFace.NORTH, 1));
                                chest180.setFacing(doBlockFace(BlockFace.NORTH, 2));
                                chest270.setFacing(doBlockFace(BlockFace.NORTH, 3));

                                data90.setType(Chest.Type.RIGHT);
                                data180.setType(Chest.Type.RIGHT);
                                data270.setType(Chest.Type.RIGHT);

                                Functions.PlaceBlockRelative(user.variables.xDif, user.variables.yDif, user.variables.zDif, chest, chest.getMaterial(), user, BlockFace.WEST);
                                PlaceRelatives(chest90, chest180, chest270, BlockFace.WEST, chest.getMaterial());
                            } else {
                                data90.setType(Chest.Type.SINGLE);
                                data180.setType(Chest.Type.SINGLE);
                                data270.setType(Chest.Type.SINGLE);
                            }
                        }
                        break;
                    case EAST:
                        if (user.variables.currentBlock.getRelative(BlockFace.SOUTH).getType() == user.variables.currentBlock.getType() &&
                                ((Chest) user.variables.currentBlock.getRelative(BlockFace.SOUTH).getBlockData()).getFacing() == EAST &&
                                ((Chest) user.variables.currentBlock.getRelative(BlockFace.SOUTH).getBlockData()).getType() == Chest.Type.SINGLE) {
                            Chest chest = (Chest) user.variables.currentBlock.getRelative(BlockFace.SOUTH).getBlockData().clone();
                            Chest chest90 = (Chest) user.variables.currentBlock.getRelative(BlockFace.SOUTH).getBlockData().clone();
                            Chest chest180 = (Chest) user.variables.currentBlock.getRelative(BlockFace.SOUTH).getBlockData().clone();
                            Chest chest270 = (Chest) user.variables.currentBlock.getRelative(BlockFace.SOUTH).getBlockData().clone();
                            if (chest.getFacing() == EAST && chest.getType() == Chest.Type.SINGLE) {
                                chest.setType(Chest.Type.RIGHT);
                                chest90.setType(Chest.Type.RIGHT);
                                chest180.setType(Chest.Type.RIGHT);
                                chest270.setType(Chest.Type.RIGHT);

                                chest.setFacing(BlockFace.EAST);
                                chest90.setFacing(doBlockFace(BlockFace.EAST, 1));
                                chest180.setFacing(doBlockFace(BlockFace.EAST, 2));
                                chest270.setFacing(doBlockFace(BlockFace.EAST, 3));

                                data90.setType(Chest.Type.LEFT);
                                data180.setType(Chest.Type.LEFT);
                                data270.setType(Chest.Type.LEFT);


                                Functions.PlaceBlockRelative(user.variables.xDif, user.variables.yDif, user.variables.zDif, chest, chest.getMaterial(), user, BlockFace.SOUTH);
                                PlaceRelatives(chest90, chest180, chest270, BlockFace.SOUTH, chest.getMaterial());
                            } else {
                                data90.setType(Chest.Type.SINGLE);
                                data180.setType(Chest.Type.SINGLE);
                                data270.setType(Chest.Type.SINGLE);
                            }
                        } else if (user.variables.currentBlock.getRelative(BlockFace.NORTH).getType() == user.variables.currentBlock.getType()) {
                            Chest chest = (Chest) user.variables.currentBlock.getRelative(BlockFace.NORTH).getBlockData().clone();
                            Chest chest90 = (Chest) user.variables.currentBlock.getRelative(BlockFace.NORTH).getBlockData().clone();
                            Chest chest180 = (Chest) user.variables.currentBlock.getRelative(BlockFace.NORTH).getBlockData().clone();
                            Chest chest270 = (Chest) user.variables.currentBlock.getRelative(BlockFace.NORTH).getBlockData().clone();
                            if (chest.getFacing() == EAST && chest.getType() == Chest.Type.SINGLE) {
                                chest.setType(Chest.Type.LEFT);
                                chest90.setType(Chest.Type.LEFT);
                                chest180.setType(Chest.Type.LEFT);
                                chest270.setType(Chest.Type.LEFT);

                                chest.setFacing(BlockFace.EAST);
                                chest90.setFacing(doBlockFace(BlockFace.EAST, 1));
                                chest180.setFacing(doBlockFace(BlockFace.EAST, 2));
                                chest270.setFacing(doBlockFace(BlockFace.EAST, 3));

                                data90.setType(Chest.Type.RIGHT);
                                data180.setType(Chest.Type.RIGHT);
                                data270.setType(Chest.Type.RIGHT);


                                Functions.PlaceBlockRelative(user.variables.xDif, user.variables.yDif, user.variables.zDif, chest, chest.getMaterial(), user, BlockFace.NORTH);
                                PlaceRelatives(chest90, chest180, chest270, BlockFace.NORTH, chest.getMaterial());
                            } else {
                                data90.setType(Chest.Type.SINGLE);
                                data180.setType(Chest.Type.SINGLE);
                                data270.setType(Chest.Type.SINGLE);
                            }
                        }
                        break;
                    case SOUTH:
                        if (user.variables.currentBlock.getRelative(BlockFace.WEST).getType() == user.variables.currentBlock.getType() &&
                                ((Chest) user.variables.currentBlock.getRelative(BlockFace.WEST).getBlockData()).getFacing() == SOUTH &&
                                ((Chest) user.variables.currentBlock.getRelative(BlockFace.WEST).getBlockData()).getType() == Chest.Type.SINGLE) {
                            Chest chest = (Chest) user.variables.currentBlock.getRelative(BlockFace.WEST).getBlockData().clone();
                            Chest chest90 = (Chest) user.variables.currentBlock.getRelative(BlockFace.WEST).getBlockData().clone();
                            Chest chest180 = (Chest) user.variables.currentBlock.getRelative(BlockFace.WEST).getBlockData().clone();
                            Chest chest270 = (Chest) user.variables.currentBlock.getRelative(BlockFace.WEST).getBlockData().clone();
                            if (chest.getFacing() == SOUTH && chest.getType() == Chest.Type.SINGLE) {
                                chest.setType(Chest.Type.RIGHT);
                                chest90.setType(Chest.Type.RIGHT);
                                chest180.setType(Chest.Type.RIGHT);
                                chest270.setType(Chest.Type.RIGHT);

                                chest.setFacing(BlockFace.SOUTH);
                                chest90.setFacing(doBlockFace(BlockFace.SOUTH, 1));
                                chest180.setFacing(doBlockFace(BlockFace.SOUTH, 2));
                                chest270.setFacing(doBlockFace(BlockFace.SOUTH, 3));

                                data90.setType(Chest.Type.LEFT);
                                data180.setType(Chest.Type.LEFT);
                                data270.setType(Chest.Type.LEFT);


                                Functions.PlaceBlockRelative(user.variables.xDif, user.variables.yDif, user.variables.zDif, chest, chest.getMaterial(), user, BlockFace.WEST);
                                PlaceRelatives(chest90, chest180, chest270, BlockFace.WEST, chest.getMaterial());
                            } else {
                                data90.setType(Chest.Type.SINGLE);
                                data180.setType(Chest.Type.SINGLE);
                                data270.setType(Chest.Type.SINGLE);
                            }
                        } else if (user.variables.currentBlock.getRelative(BlockFace.EAST).getType() == user.variables.currentBlock.getType()) {
                            Chest chest = (Chest) user.variables.currentBlock.getRelative(BlockFace.EAST).getBlockData().clone();
                            Chest chest90 = (Chest) user.variables.currentBlock.getRelative(BlockFace.EAST).getBlockData().clone();
                            Chest chest180 = (Chest) user.variables.currentBlock.getRelative(BlockFace.EAST).getBlockData().clone();
                            Chest chest270 = (Chest) user.variables.currentBlock.getRelative(BlockFace.EAST).getBlockData().clone();
                            if (chest.getFacing() == SOUTH && chest.getType() == Chest.Type.SINGLE) {
                                chest.setType(Chest.Type.LEFT);
                                chest90.setType(Chest.Type.LEFT);
                                chest180.setType(Chest.Type.LEFT);
                                chest270.setType(Chest.Type.LEFT);

                                chest.setFacing(BlockFace.SOUTH);
                                chest90.setFacing(doBlockFace(BlockFace.SOUTH, 1));
                                chest180.setFacing(doBlockFace(BlockFace.SOUTH, 2));
                                chest270.setFacing(doBlockFace(BlockFace.SOUTH, 3));

                                data90.setType(Chest.Type.RIGHT);
                                data180.setType(Chest.Type.RIGHT);
                                data270.setType(Chest.Type.RIGHT);


                                Functions.PlaceBlockRelative(user.variables.xDif, user.variables.yDif, user.variables.zDif, chest, chest.getMaterial(), user, BlockFace.EAST);
                                PlaceRelatives(chest90, chest180, chest270, BlockFace.EAST, chest.getMaterial());
                            } else {
                                data90.setType(Chest.Type.SINGLE);
                                data180.setType(Chest.Type.SINGLE);
                                data270.setType(Chest.Type.SINGLE);
                            }
                        }
                        break;
                    case WEST:
                        if (user.variables.currentBlock.getRelative(BlockFace.NORTH).getType() == user.variables.currentBlock.getType() &&
                                ((Chest) user.variables.currentBlock.getRelative(BlockFace.NORTH).getBlockData()).getFacing() == WEST &&
                                ((Chest) user.variables.currentBlock.getRelative(BlockFace.NORTH).getBlockData()).getType() == Chest.Type.SINGLE) {
                            Chest chest = (Chest) user.variables.currentBlock.getRelative(BlockFace.NORTH).getBlockData().clone();
                            Chest chest90 = (Chest) user.variables.currentBlock.getRelative(BlockFace.NORTH).getBlockData().clone();
                            Chest chest180 = (Chest) user.variables.currentBlock.getRelative(BlockFace.NORTH).getBlockData().clone();
                            Chest chest270 = (Chest) user.variables.currentBlock.getRelative(BlockFace.NORTH).getBlockData().clone();
                            if (chest.getFacing() == WEST && chest.getType() == Chest.Type.SINGLE) {
                                chest.setType(Chest.Type.RIGHT);
                                chest90.setType(Chest.Type.RIGHT);
                                chest180.setType(Chest.Type.RIGHT);
                                chest270.setType(Chest.Type.RIGHT);

                                chest.setFacing(BlockFace.WEST);
                                chest90.setFacing(doBlockFace(BlockFace.WEST, 1));
                                chest180.setFacing(doBlockFace(BlockFace.WEST, 2));
                                chest270.setFacing(doBlockFace(BlockFace.WEST, 3));

                                data90.setType(Chest.Type.LEFT);
                                data180.setType(Chest.Type.LEFT);
                                data270.setType(Chest.Type.LEFT);


                                Functions.PlaceBlockRelative(user.variables.xDif, user.variables.yDif, user.variables.zDif, chest, chest.getMaterial(), user, BlockFace.NORTH);
                                PlaceRelatives(chest90, chest180, chest270, BlockFace.NORTH, chest.getMaterial());
                            } else {
                                data90.setType(Chest.Type.SINGLE);
                                data180.setType(Chest.Type.SINGLE);
                                data270.setType(Chest.Type.SINGLE);
                            }
                        } else if (user.variables.currentBlock.getRelative(BlockFace.SOUTH).getType() == user.variables.currentBlock.getType()) {
                            Chest chest = (Chest) user.variables.currentBlock.getRelative(BlockFace.SOUTH).getBlockData().clone();
                            Chest chest90 = (Chest) user.variables.currentBlock.getRelative(BlockFace.SOUTH).getBlockData().clone();
                            Chest chest180 = (Chest) user.variables.currentBlock.getRelative(BlockFace.SOUTH).getBlockData().clone();
                            Chest chest270 = (Chest) user.variables.currentBlock.getRelative(BlockFace.SOUTH).getBlockData().clone();
                            if (chest.getFacing() == WEST && chest.getType() == Chest.Type.SINGLE) {
                                chest.setType(Chest.Type.LEFT);
                                chest90.setType(Chest.Type.LEFT);
                                chest180.setType(Chest.Type.LEFT);
                                chest270.setType(Chest.Type.LEFT);

                                chest.setFacing(BlockFace.WEST);
                                chest90.setFacing(doBlockFace(BlockFace.WEST, 1));
                                chest180.setFacing(doBlockFace(BlockFace.WEST, 2));
                                chest270.setFacing(doBlockFace(BlockFace.WEST, 3));

                                data90.setType(Chest.Type.RIGHT);
                                data180.setType(Chest.Type.RIGHT);
                                data270.setType(Chest.Type.RIGHT);


                                Functions.PlaceBlockRelative(user.variables.xDif, user.variables.yDif, user.variables.zDif, chest, chest.getMaterial(), user, BlockFace.SOUTH);
                                PlaceRelatives(chest90, chest180, chest270, BlockFace.SOUTH, chest.getMaterial());
                            } else {
                                data90.setType(Chest.Type.SINGLE);
                                data180.setType(Chest.Type.SINGLE);
                                data270.setType(Chest.Type.SINGLE);
                            }
                        }
                        break;
                }
            }
            Place(data90, data180, data270);
        }
    }

    public void RotateXZ() {
        Directional data = (Directional) user.variables.dataCopy.clone();
        Directional data90 = (Directional) user.variables.dataCopy.clone();
        Directional data180 = (Directional) user.variables.dataCopy.clone();
        Directional data270 = (Directional) user.variables.dataCopy.clone();
        data90.setFacing(doBlockFace(data.getFacing(), 1));
        data180.setFacing(doBlockFace(data.getFacing(), 2));
        data270.setFacing(doBlockFace(data.getFacing(), 3));
        Place(data90, data180, data270);
    }

    public void Vines() {
        MultipleFacing data = (MultipleFacing) user.variables.dataCopy;
        MultipleFacing vine90 = (MultipleFacing) user.variables.dataCopy.clone();
        MultipleFacing vine180 = (MultipleFacing) user.variables.dataCopy.clone();
        MultipleFacing vine270 = (MultipleFacing) user.variables.dataCopy.clone();
        for (BlockFace face : data.getFaces()) {
            vine90.setFace(face, false);
            vine180.setFace(face, false);
            vine270.setFace(face, false);
            vine90.setFace(doBlockFace(face, 1), true);
            vine180.setFace(doBlockFace(face, 2), true);
            vine270.setFace(doBlockFace(face, 3), true);
        }
        Place(vine90, vine180, vine270);
    }

    public void TripWire() {
        TripwireHook data = (TripwireHook) user.variables.dataCopy.clone();
        TripwireHook data90 = (TripwireHook) user.variables.dataCopy.clone();
        TripwireHook data180 = (TripwireHook) user.variables.dataCopy.clone();
        TripwireHook data270 = (TripwireHook) user.variables.dataCopy.clone();
        data90.setFacing(doBlockFace(data.getFacing(), 1));
        data180.setFacing(doBlockFace(data.getFacing(), 2));
        data270.setFacing(doBlockFace(data.getFacing(), 3));
        Place(data90, data180, data270);
    }

    public void Hopper() {
        Hopper data = (Hopper) user.variables.dataCopy.clone();
        Hopper data90 = (Hopper) user.variables.dataCopy.clone();
        Hopper data180 = (Hopper) user.variables.dataCopy.clone();
        Hopper data270 = (Hopper) user.variables.dataCopy.clone();
        data90.setFacing(doBlockFace(data.getFacing(), 1));
        data180.setFacing(doBlockFace(data.getFacing(), 2));
        data270.setFacing(doBlockFace(data.getFacing(), 3));
        Place(data90, data180, data270);
    }

    public void TallFlower() {
        Bisected data = (Bisected) user.variables.dataCopy.clone();
        Bisected data2 = (Bisected) user.variables.dataCopy.clone();
        data2.setHalf(data.getHalf() == Bisected.Half.BOTTOM ? Bisected.Half.TOP : Bisected.Half.BOTTOM);
        Place(data, data, data);
        Place(data2, data2, data2, data.getHalf() == Bisected.Half.BOTTOM ? 1 : -1);
    }

    public void Terracotta() {
        Directional data = (Directional) user.variables.dataCopy;
        Directional data90 = (Directional) user.variables.dataCopy.clone();
        Directional data180 = (Directional) user.variables.dataCopy.clone();
        Directional data270 = (Directional) user.variables.dataCopy.clone();
        switch (data.getFacing()) {
            case NORTH -> {
                data90.setFacing(BlockFace.EAST);
                data180.setFacing(BlockFace.EAST);
                data270.setFacing(BlockFace.EAST);
            }
            case EAST -> {
                data90.setFacing(BlockFace.EAST);
                data180.setFacing(BlockFace.EAST);
                data270.setFacing(BlockFace.EAST);
            }
            case SOUTH -> {
                data90.setFacing(BlockFace.EAST);
                data180.setFacing(BlockFace.EAST);
                data270.setFacing(BlockFace.EAST);
            }
            case WEST -> {
                data90.setFacing(BlockFace.EAST);
                data180.setFacing(BlockFace.EAST);
                data270.setFacing(BlockFace.EAST);
            }
        }
        Place(data90, data180, data270);
    }

    private void Place(BlockData data90, BlockData data180, BlockData data270) {
        Functions.PlaceBlock(-user.variables.zDif, user.variables.yDif, user.variables.xDif, data90, user);
        Functions.PlaceBlock(user.variables.zDif, user.variables.yDif, -user.variables.xDif, data270, user);
        Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, -user.variables.zDif, data180, user);
    }

    private void Place(BlockData data90, BlockData data180, BlockData data270, int yDif) {
        Functions.PlaceBlock(-user.variables.zDif, user.variables.yDif + yDif, user.variables.xDif, data90, user);
        Functions.PlaceBlock(user.variables.zDif, user.variables.yDif + yDif, -user.variables.xDif, data270, user);
        Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif + yDif, -user.variables.zDif, data180, user);
    }

    private void PlaceRelatives(BlockData data90, BlockData data180, BlockData data270, BlockFace bf, Material mat) {
        Functions.PlaceBlockRelative(-user.variables.zDif, user.variables.yDif, user.variables.xDif, data90, mat, user, doBlockFace(bf, 1));
        Functions.PlaceBlockRelative(user.variables.zDif, user.variables.yDif, -user.variables.xDif, data270, mat, user, doBlockFace(bf, 3));
        Functions.PlaceBlockRelative(-user.variables.xDif, user.variables.yDif, -user.variables.zDif, data180, mat, user, doBlockFace(bf, 2));
    }

    public void Default() {
        Functions.PlaceBlock(-user.variables.xDif, user.variables.yDif, -user.variables.zDif, user);
        Functions.PlaceBlock(-user.variables.zDif, user.variables.yDif, user.variables.xDif, user);
        Functions.PlaceBlock(user.variables.zDif, user.variables.yDif, -user.variables.xDif, user);
        if (user.variables.currentBlock.getType().isSolid())
            for (AdjacentBlock ab : Functions.getRelatives(user.variables.currentBlock))
                if (ab.block.getBlockData() instanceof Fence) {
                    Fence[] fence = Arrays.copyOf(doFencePane(ab.block), 3, Fence[].class);
                    PlaceRelatives(fence[0], fence[1], fence[2], ab.blockFace, ab.block.getType());
                } else if (ab.block.getType().name().toLowerCase().contains("glass_pane")) {
                    GlassPane[] pane = Arrays.copyOf(doFencePane(ab.block), 3, GlassPane[].class);
                    PlaceRelatives(pane[0], pane[1], pane[2], ab.blockFace, ab.block.getType());
                }
    }

    @SuppressWarnings("Duplicates")
    public void Remove() {
        Functions.RemoveBlock(-user.variables.xDif, user.variables.yDif, -user.variables.zDif, user);
        Functions.RemoveBlock(user.variables.zDif, user.variables.yDif, -user.variables.xDif, user);
        Functions.RemoveBlock(-user.variables.zDif, user.variables.yDif, user.variables.xDif, user);
        if (user.variables.currentBlock.getBlockData() instanceof Bisected) {
            Bisected data = (Bisected) user.variables.currentBlock.getBlockData().clone();
            int y = user.variables.yDif;
            Functions.RemoveBlock(-user.variables.xDif, data.getHalf() == Bisected.Half.BOTTOM ? y + 1 : y - 1, -user.variables.zDif, user);
            Functions.RemoveBlock(user.variables.xDif, data.getHalf() == Bisected.Half.BOTTOM ? y + 1 : y - 1, -user.variables.zDif, user);
            Functions.RemoveBlock(-user.variables.xDif, data.getHalf() == Bisected.Half.BOTTOM ? y + 1 : y - 1, user.variables.zDif, user);
        }
        for (AdjacentBlock ab : Functions.getRelatives(user.variables.currentBlock))
            if (ab.block.getBlockData() instanceof Fence) {
                Fence[] fence = Arrays.copyOf(doFencePane(ab.block), 3, Fence[].class);
                for (int i = 1; i <= 3; i++)
                    fence[i - 1].setFace(doBlockFace(ab.blockFace, i).getOppositeFace(), false);
                PlaceRelatives(fence[0], fence[1], fence[2], ab.blockFace, ab.block.getType());
            } else if (ab.block.getType().name().toLowerCase().contains("glass_pane")) {
                GlassPane[] pane = Arrays.copyOf(doFencePane(ab.block), 3, GlassPane[].class);
                for (int i = 1; i <= 3; i++)
                    pane[i - 1].setFace(doBlockFace(ab.blockFace, i).getOppositeFace(), false);
                PlaceRelatives(pane[0], pane[1], pane[2], ab.blockFace, ab.block.getType());
            } else if (ab.block.getBlockData() instanceof Gate && user.variables.currentBlock.getType().name().toLowerCase().contains("cobblestone_wall")) {
                Gate gate = (Gate) ab.block.getBlockData().clone();
                if (!((gate.getFacing() == ab.blockFace) || gate.getFacing().getOppositeFace() == ab.blockFace))
                    if (!ab.block.getRelative(ab.blockFace).getType().name().toLowerCase().contains("cobblestone_wall")) {
                        gate.setInWall(false);
                        Gate gate90 = (Gate) gate.clone();
                        gate90.setFacing(doBlockFace(gate.getFacing(), 1));
                        Gate gate270 = (Gate) gate.clone();
                        gate270.setFacing(doBlockFace(gate.getFacing(), 3));
                        PlaceRelatives(gate90, gate, gate270, ab.blockFace, ab.block.getType());
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
        Chest chest90 = (Chest) ab.block.getBlockData().clone();
        Chest chest180 = (Chest) ab.block.getBlockData().clone();
        Chest chest270 = (Chest) ab.block.getBlockData().clone();
        chest.setType(Chest.Type.SINGLE);
        chest90.setType(Chest.Type.SINGLE);
        chest180.setType(Chest.Type.SINGLE);
        chest270.setType(Chest.Type.SINGLE);
        chest90.setFacing(doBlockFace(chest.getFacing(), 1));
        chest180.setFacing(doBlockFace(chest.getFacing(), 2));
        chest270.setFacing(doBlockFace(chest.getFacing(), 3));
        PlaceRelatives(chest90, chest180, chest270, ab.blockFace, chest.getMaterial());
        Functions.PlaceBlockRelative(user.variables.xDif, user.variables.yDif, user.variables.zDif, chest, ab.block.getType(), user, ab.blockFace);
    }

}
