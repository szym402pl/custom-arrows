package me.xiaojibazhanshi.customarrows.util.arrows;

import org.bukkit.Material;
import org.bukkit.Note;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.*;
import org.bukkit.entity.TNTPrimed;

import java.util.Arrays;
import java.util.List;

public class Redstone {

    private Redstone() {
    }

    public static List<Material> getRedstonePoweredDevices() {
        return Arrays.asList(
                Material.PISTON,
                Material.STICKY_PISTON,
                Material.REDSTONE_LAMP,
                Material.TNT,
                Material.IRON_DOOR,
                Material.DISPENSER,
                Material.DROPPER,
                Material.NOTE_BLOCK,
                Material.COMPARATOR,
                Material.REPEATER,
                Material.COMMAND_BLOCK,
                Material.JUKEBOX,
                Material.HOPPER,
                Material.REDSTONE_BLOCK
        );
    }

    public static void handleRedstoneDevice(List<Material> deviceList, Block block) {
        if (deviceList.contains(block.getType())) {
            BlockData data = block.getBlockData();

            switch (block.getType()) {
                case PISTON:
                case STICKY_PISTON:
                    Piston piston = (Piston) data;
                    piston.setExtended(!piston.isExtended());
                    block.setBlockData(piston);
                    Block above = block.getRelative(BlockFace.UP);
                    above.setType(Material.PISTON_HEAD);
                    break;
                case TNT:
                    block.setType(Material.AIR);
                    block.getWorld().spawn(block.getLocation(), TNTPrimed.class);
                    break;

                case IRON_DOOR:
                    Door door = (Door) data;
                    door.setOpen(!door.isOpen());
                    block.setBlockData(door);
                    break;

                case NOTE_BLOCK:
                    NoteBlock noteBlock = (NoteBlock) data;
                    noteBlock.setNote(Note.flat(2, Note.Tone.E));
                    block.setBlockData(noteBlock);
                    break;

                case COMPARATOR:
                    Comparator comparator = (Comparator) data;
                    comparator.setMode(Comparator.Mode.COMPARE);
                    block.setBlockData(comparator);
                    break;

                case REPEATER:
                    Repeater repeater = (Repeater) data;
                    repeater.setDelay(repeater.getDelay() + 1);
                    block.setBlockData(repeater);
                    break;
                default:
            }
        }
    }
}
