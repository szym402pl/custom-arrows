package me.xiaojibazhanshi.customarrows.util.arrows;

import org.bukkit.entity.*;

import java.util.List;

public class Harmony {

    public static List<LivingEntity> getPassiveMobsNearby(Entity entity) {
        return entity.getNearbyEntities(6, 6, 6)
                .stream()
                .filter(livingEntity -> livingEntity instanceof LivingEntity)
                .map(livingEntity -> (LivingEntity) livingEntity)
                .filter(livingEntity -> livingEntity instanceof Animals
                        || livingEntity instanceof Ambient
                        || livingEntity instanceof NPC
                        || livingEntity instanceof Slime)
                .toList();
    }

}
