package me.xiaojibazhanshi.customarrows.util.arrows;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;

public class MobAggro {

    private MobAggro() {

    }

    public static void aggroMobsNearby(LivingEntity target, int radius) {
        target.getNearbyEntities(radius, radius, radius)
                .stream()
                .filter(entity -> entity instanceof Monster)
                .forEach(entity -> ((Monster) entity).setTarget(target));
    }
}
