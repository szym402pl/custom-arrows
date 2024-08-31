package me.xiaojibazhanshi.customarrows.util.arrows;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.util.Vector;

public class Magnet {

    private Magnet() {
    }

    /**
     * @return true if there was at least one item in the vicinity
     */
    public static boolean teleportNearbyItemsTo(Entity target, int radius) {
        boolean itemsNearby = false;

        for (Entity item : target.getNearbyEntities(radius, radius, radius)) {
            if (!(item instanceof Item)) continue;

            item.teleport(target.getLocation().add(new Vector(0, 1.25, 0)));
            itemsNearby = true;
        }

        return itemsNearby;
    }

}
