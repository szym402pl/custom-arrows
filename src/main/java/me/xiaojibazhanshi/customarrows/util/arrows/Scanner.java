package me.xiaojibazhanshi.customarrows.util.arrows;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;

public class Scanner {

    private Scanner() {

    }

    public static boolean areEnemiesNearby(Entity entity, LivingEntity excluded) {
        return !(entity.getNearbyEntities(8, 6, 8)
                .stream()
                .filter(livingEntity -> livingEntity instanceof LivingEntity)
                .filter(livingEntity -> livingEntity instanceof Player || livingEntity instanceof Monster)
                .map(livingEntity -> (LivingEntity) livingEntity)
                .filter(livingEntity -> !(livingEntity.equals(excluded)))
                .toList()
                .isEmpty());
    }

}
