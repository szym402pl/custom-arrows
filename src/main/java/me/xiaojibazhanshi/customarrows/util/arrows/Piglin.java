package me.xiaojibazhanshi.customarrows.util.arrows;

import org.bukkit.entity.Entity;

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
}
