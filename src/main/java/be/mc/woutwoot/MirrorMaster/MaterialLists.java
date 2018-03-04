package be.mc.woutwoot.MirrorMaster;

import org.bukkit.Material;

import java.util.ArrayList;


public class MaterialLists {

    static ArrayList<Material> Stairs = new ArrayList<>();
    static ArrayList<Material> Levers = new ArrayList<>();
    static ArrayList<Material> Torches = new ArrayList<>();
    static ArrayList<Material> Buttons = new ArrayList<>();
    static ArrayList<Material> Halfslabs = new ArrayList<>();
    static ArrayList<Material> Doors = new ArrayList<>();

    static void Init() {
        Stairs.add(Material.WOOD_STAIRS);
        Stairs.add(Material.BIRCH_WOOD_STAIRS);
        Stairs.add(Material.JUNGLE_WOOD_STAIRS);
        Stairs.add(Material.SPRUCE_WOOD_STAIRS);
        Stairs.add(Material.SMOOTH_STAIRS);
        Stairs.add(Material.COBBLESTONE_STAIRS);
        Stairs.add(Material.BRICK_STAIRS);
        Stairs.add(Material.NETHER_BRICK_STAIRS);
        Stairs.add(Material.SANDSTONE_STAIRS);
        Stairs.add(Material.QUARTZ_STAIRS);

        Levers.add(Material.LEVER);

        Torches.add(Material.TORCH);
        Torches.add(Material.REDSTONE_TORCH_ON);
        Torches.add(Material.REDSTONE_TORCH_OFF);

        Buttons.add(Material.STONE_BUTTON);
        Buttons.add(Material.WOOD_BUTTON);

        Halfslabs.add(Material.STEP);
        Halfslabs.add(Material.WOOD_STEP);

        Doors.add(Material.IRON_DOOR);
        Doors.add(Material.IRON_DOOR_BLOCK);
        Doors.add(Material.WOODEN_DOOR);
        Doors.add(Material.WOOD_DOOR);
    }
}