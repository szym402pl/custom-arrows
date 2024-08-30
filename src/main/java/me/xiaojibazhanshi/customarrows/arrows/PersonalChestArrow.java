package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.Inventory;

import java.util.*;

import static me.xiaojibazhanshi.customarrows.util.GeneralUtil.color;
import static me.xiaojibazhanshi.customarrows.util.arrows.PersonalChest.placeTemporaryPersonalChest;

public class PersonalChestArrow extends CustomArrow {

    private final Map<UUID, Inventory> privateChests = new HashMap<>();
    private final List<UUID> activeChests = new ArrayList<>();

    public PersonalChestArrow() {
        super(ArrowFactory.changeTippedColor
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&6Personal Chest Arrow", "personal_chest_arrow",
                                List.of("", "This arrow will spawn a", "personal chest for you!")),
                        Color.ORANGE));
    }

    @Override
    public void onHitBlock(ProjectileHitEvent event, Player shooter) {
        Block hitBlock = event.getHitBlock();
        if (hitBlock == null) return;

        Location playerLocation = shooter.getLocation();
        Location hitBlockLocation = hitBlock.getLocation();

        double distance = playerLocation.distance(hitBlockLocation);

        if (distance > 10) {
            shooter.sendTitle("", color("&7I shot too far..."), 5, 25, 5);
            return;
        }

        Block blockAbove = hitBlock.getRelative(BlockFace.UP);
        if (blockAbove.getType() == Material.WATER) return;

        UUID uuid = shooter.getUniqueId();

        if (activeChests.contains(uuid)) {
            shooter.sendTitle("", color("&7I already have my chest with me..."), 5, 25, 5);
            return;
        }

        privateChests.putIfAbsent(uuid, Bukkit.createInventory(shooter, 27));
        activeChests.add(uuid);

        int deleteAfter = 15;
        placeTemporaryPersonalChest(uuid, blockAbove, deleteAfter, privateChests, activeChests);
    }


}
