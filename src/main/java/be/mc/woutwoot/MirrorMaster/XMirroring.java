package be.mc.woutwoot.MirrorMaster;

import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class XMirroring {

    static void Mirror() {
        if (Functions.CheckBlockMaterialLists(MaterialLists.Stairs))
            Stairs();
        else if (Functions.CheckBlockMaterialLists(MaterialLists.Levers))
            Levers();
        else if (Functions.CheckBlockMaterialLists(MaterialLists.Torches))
            Torches();
        else if (Functions.CheckBlockMaterialLists(MaterialLists.Halfslabs))
            Halfslabs();
        else if (Functions.CheckBlockMaterialLists(MaterialLists.Buttons))
            Buttons();
        else if (Functions.CheckBlockMaterialLists(MaterialLists.Doors))
            Doors();
        else
            Default();
    }

    static void RemoveResources(BlockPlaceEvent event) {
        if (!Functions.CheckBlockMaterialLists(MaterialLists.Doors)) {
            if (Variables.zDif != 0) {
                if (event.getItemInHand().getAmount() >= 2)
                    event.getItemInHand().setAmount(event.getItemInHand().getAmount() - 1);
                else
                    event.getPlayer().sendMessage("Failed mirroring, you need more blocks!");
            }
        } else {
            ArrayList<ItemStack> doors = new ArrayList<>();
            ItemStack[] arrayOfItemStack;
            int j = (arrayOfItemStack = event.getPlayer().getInventory().getContents()).length;
            CrossMirroring.collectDoors(event, doors, arrayOfItemStack, j);

            int handItem = event.getPlayer().getInventory().getHeldItemSlot();

            if (Variables.zDif != 0) {
                if (Rotating180.collectDoors(event, doors, handItem)) return;
            }
            event.getPlayer().updateInventory();
        }
    }

    private static void Stairs() {
        if (!Functions.Down()) {
            switch (Functions.LookDirection()) {
                case 0:
                    Functions.PlaceBlock(Variables.xDif, Variables.yDif, -Variables.zDif, (byte) 3);
                    break;
                case 1:
                    Functions.PlaceBlock(Variables.xDif, Variables.yDif, -Variables.zDif, (byte) 1);
                    break;
                case 2:
                    Functions.PlaceBlock(Variables.xDif, Variables.yDif, -Variables.zDif, (byte) 2);
                    break;
                case 3:
                    Functions.PlaceBlock(Variables.xDif, Variables.yDif, -Variables.zDif, (byte) 0);
                    break;
            }
        } else {
            switch (Functions.LookDirection()) {
                case 0:
                    Functions.PlaceBlock(Variables.xDif, Variables.yDif, -Variables.zDif, (byte) 7);
                    break;
                case 1:
                    Functions.PlaceBlock(Variables.xDif, Variables.yDif, -Variables.zDif, (byte) 5);
                    break;
                case 2:
                    Functions.PlaceBlock(Variables.xDif, Variables.yDif, -Variables.zDif, (byte) 6);
                    break;
                case 3:
                    Functions.PlaceBlock(Variables.xDif, Variables.yDif, -Variables.zDif, (byte) 4);
                    break;
            }

        }
    }

    private static void Levers() {
        if (Functions.Up())
            Functions.PlaceBlock(Variables.xDif, Variables.yDif, -Variables.zDif, (byte) 6);
        if (Functions.East())
            Functions.PlaceBlock(Variables.xDif, Variables.yDif, -Variables.zDif, (byte) 3);
        if (Functions.West())
            Functions.PlaceBlock(Variables.xDif, Variables.yDif, -Variables.zDif, (byte) 4);
        if (Functions.South())
            Functions.PlaceBlock(Variables.xDif, Variables.yDif, -Variables.zDif, (byte) 1);
        if (Functions.North())
            Functions.PlaceBlock(Variables.xDif, Variables.yDif, -Variables.zDif, (byte) 2);
    }

    private static void Torches() {
        if (Functions.Up())
            Functions.PlaceBlock(Variables.xDif, Variables.yDif, -Variables.zDif, (byte) 5);
        if (Functions.East())
            Functions.PlaceBlock(Variables.xDif, Variables.yDif, -Variables.zDif, (byte) 3);
        if (Functions.West())
            Functions.PlaceBlock(Variables.xDif, Variables.yDif, -Variables.zDif, (byte) 4);
        if (Functions.South())
            Functions.PlaceBlock(Variables.xDif, Variables.yDif, -Variables.zDif, (byte) 1);
        if (Functions.North())
            Functions.PlaceBlock(Variables.xDif, Variables.yDif, -Variables.zDif, (byte) 2);
    }

    private static void Halfslabs() {
        if (Functions.Down())
            Functions.PlaceBlock(Variables.xDif, Variables.yDif, -Variables.zDif, (byte) (Variables.dataCopy + 8));
        else
            Functions.PlaceBlock(Variables.xDif, Variables.yDif, -Variables.zDif);
    }

    private static void Buttons() {
        if (Functions.East())
            Functions.PlaceBlock(Variables.xDif, Variables.yDif, -Variables.zDif, (byte) 3);
        if (Functions.West())
            Functions.PlaceBlock(Variables.xDif, Variables.yDif, -Variables.zDif, (byte) 4);
        if (Functions.South())
            Functions.PlaceBlock(Variables.xDif, Variables.yDif, -Variables.zDif, (byte) 1);
        if (Functions.North())
            Functions.PlaceBlock(Variables.xDif, Variables.yDif, -Variables.zDif, (byte) 2);
    }

    private static void Doors() {
        switch (Functions.LookDirection()) {
            case 0:
                Functions.PlaceBlock(Variables.xDif, Variables.yDif, -Variables.zDif, (byte) 3);
                Functions.PlaceBlock(Variables.xDif, ++Variables.yDif, -Variables.zDif, (byte) 11);
                break;
            case 1:
                Functions.PlaceBlock(Variables.xDif, Variables.yDif, -Variables.zDif, (byte) 2);
                Functions.PlaceBlock(Variables.xDif, ++Variables.yDif, -Variables.zDif, (byte) 10);
                break;
            case 2:
                Functions.PlaceBlock(Variables.xDif, Variables.yDif, -Variables.zDif, (byte) 1);
                Functions.PlaceBlock(Variables.xDif, ++Variables.yDif, -Variables.zDif, (byte) 9);
                break;
            case 3:
                Functions.PlaceBlock(Variables.xDif, Variables.yDif, -Variables.zDif, (byte) 0);
                Functions.PlaceBlock(Variables.xDif, ++Variables.yDif, -Variables.zDif, (byte) 8);
                break;
        }

    }

    private static void Default() {
        Functions.PlaceBlock(Variables.xDif, Variables.yDif, -Variables.zDif);
    }

    static void Remove() {
        if (UsersManager.user.player.getGameMode() == org.bukkit.GameMode.CREATIVE)
            Functions.RemoveBlock(Variables.xDif, Variables.yDif, -Variables.zDif);
        else
            Functions.RemoveBlock(Variables.xDif, Variables.yDif, -Variables.zDif, true);
    }
}