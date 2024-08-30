package me.xiaojibazhanshi.customarrows.util.arrows;

import me.xiaojibazhanshi.customarrows.CustomArrows;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Display;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TextDisplay;
import org.bukkit.inventory.Inventory;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static me.xiaojibazhanshi.customarrows.util.GeneralUtil.color;

public class PersonalChest {

    private PersonalChest() {

    }

    public static void placeTemporaryPersonalChest(UUID uuid, Block targetBlock, int deleteAfter,
                                                   Map<UUID, Inventory> privateChests, List<UUID> activeChests) {
        if (targetBlock == null) return;
        BlockData ogBlockData = targetBlock.getBlockData();

        targetBlock.setType(Material.CHEST);
        BlockState state = targetBlock.getState();

        if (!(state instanceof Chest chest)) {
            targetBlock.setBlockData(ogBlockData);
            return;
        }

        Inventory chestInventory = privateChests.get(uuid);
        chest.getBlockInventory().setContents(chestInventory.getContents());

        Location displayLocation = targetBlock.getLocation().add(0.5, 1.25, 0.5);
        String displayName = Bukkit.getOfflinePlayer(uuid).getName();

        TextDisplay textDisplay = (TextDisplay) targetBlock.getWorld().spawnEntity(displayLocation, EntityType.TEXT_DISPLAY);
        textDisplay.setText(color("&a" + displayName + "'s Personal Chest"));
        textDisplay.setBillboard(Display.Billboard.CENTER);
        textDisplay.setSeeThrough(true);

        Bukkit.getScheduler().runTaskLater(CustomArrows.getInstance(), () -> {
            privateChests.put(uuid, chest.getBlockInventory());
            activeChests.remove(uuid);

            textDisplay.remove();
            targetBlock.setBlockData(ogBlockData);
        }, deleteAfter * 20L);
    }

}
