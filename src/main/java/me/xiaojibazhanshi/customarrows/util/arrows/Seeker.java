package me.xiaojibazhanshi.customarrows.util.arrows;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class Seeker {

    private Seeker() {

    }

    public static LivingEntity findFirstEntityBelow(Entity entity, int radius, int maxHeight) {
        // this way is just faster, idc if it's less performant
        return (LivingEntity) entity.getNearbyEntities(radius, maxHeight, radius)
                .stream()
                .filter(entityNearby -> entityNearby instanceof LivingEntity)
                .findFirst()
                .orElse(null);
    }

}
