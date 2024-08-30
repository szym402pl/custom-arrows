package me.xiaojibazhanshi.customarrows.util.arrows;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

import static me.xiaojibazhanshi.customarrows.util.GeneralUtil.color;
import static me.xiaojibazhanshi.customarrows.util.GeneralUtil.removeEntityAfter;

public class ExplosiveDecoy {

    private ExplosiveDecoy() {}

    public static void spawnAngryDecoy(LivingEntity target, @Nullable Location customLocation) {
        long lifeTime = 10 * 20L;

        Zombie decoy = target.getWorld().spawn
                (customLocation != null ? customLocation : target.getLocation(),
                        Zombie.class);

        decoy.setHealth(5.0);
        decoy.setCustomName(color("&cHit me!"));
        decoy.setCustomNameVisible(true);
        decoy.setCollidable(false);

        try {
            long currentTime = decoy.getWorld().getTime();

            if (isDay(currentTime))
                decoy.getEquipment().setHelmet(new ItemStack(Material.LEATHER_HELMET));

        } catch (NullPointerException ignored) {
        }

        decoy.setTarget(target);
        removeEntityAfter(decoy, lifeTime);
    }

    public static boolean isDay(long currentTime) {
        return currentTime >= 0 && currentTime <= 12000;
    }

}
