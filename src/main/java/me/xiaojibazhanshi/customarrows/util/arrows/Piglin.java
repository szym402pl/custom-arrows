package me.xiaojibazhanshi.customarrows.util.arrows;

import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

import static me.xiaojibazhanshi.customarrows.util.GeneralUtil.removeEntityAfter;
import static me.xiaojibazhanshi.customarrows.util.arrows.Repulsion.detonateFirework;

public class Piglin {

    private Piglin() {

    }

    public static boolean arePiglinsNearby(Entity entity) {
        return !(entity.getNearbyEntities(8, 6, 8)
                .stream()
                .filter(oneOfEntities -> oneOfEntities instanceof org.bukkit.entity.Piglin)
                .toList()
                .isEmpty());
    }

    public static void dropFakeGold(Location location, World world) {
        ItemStack nuggetItem = new ItemStack(Material.GOLD_INGOT);
        Item nugget = world.dropItem(location, nuggetItem);

        nugget.setPickupDelay(999);
        nugget.setVisibleByDefault(false);

        removeEntityAfter(nugget, 15 * 20);
        detonateFirework(location, FireworkEffect.Type.BALL, Color.YELLOW);
    }
}
