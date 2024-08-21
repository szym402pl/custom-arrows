package me.xiaojibazhanshi.customarrows.util.arrows;

import me.xiaojibazhanshi.customarrows.CustomArrows;
import me.xiaojibazhanshi.customarrows.util.ArrowSpecificUtil;
import me.xiaojibazhanshi.customarrows.util.Util;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FlashBang {

    public static void detonateFlashBang(Entity itemDisplay, long delay) {
        Bukkit.getScheduler().runTaskLater(CustomArrows.getInstance(), () -> {
            itemDisplay.remove();
            ArrowSpecificUtil.detonateFirework(itemDisplay.getLocation(), FireworkEffect.Type.BALL, Color.WHITE);

            for (Entity onLooker : ArrowSpecificUtil.getEntitiesLookingAt(itemDisplay, 8)) {
                ArrowSpecificUtil.applyFlashBangEffect(onLooker);
            }
        }, delay);
    }


    public static void applyFlashBangEffect(Entity entity) {
        if (!(entity instanceof LivingEntity livingEntity)) return;

        Random randomInstance = new Random();
        int flashBangDuration = (randomInstance.nextInt(4, 9) + 1) * 20;

        ArrayList<PotionEffect> flashBangEffects = new ArrayList<>(List.of
                (new PotionEffect(PotionEffectType.NAUSEA, flashBangDuration, 2, true),
                        new PotionEffect(PotionEffectType.SLOWNESS, flashBangDuration, 2, true),
                        new PotionEffect(PotionEffectType.BLINDNESS, flashBangDuration, 2, true)));

        for (PotionEffect effect : flashBangEffects) {
            livingEntity.addPotionEffect(effect);
        }
    }

    public static List<Entity> getEntitiesLookingAt(Entity targetEntity, int maxRadius) {
        return targetEntity.getNearbyEntities(maxRadius, maxRadius, maxRadius)
                .stream()
                .filter(entity -> entity instanceof LivingEntity)
                .filter(livingEntity -> ((LivingEntity) livingEntity).hasLineOfSight(targetEntity))
                .filter(livingEntity -> isLookingAt((LivingEntity) livingEntity, targetEntity.getLocation()))
                .toList();
    }

    public static Entity spawnDisplayItem(Material material, Location location, @Nullable String displayName) {
        if (material == null || location == null) return null;

        World world = location.getWorld();
        if (world == null) return null;

        ItemStack item = new ItemStack(material);
        ItemDisplay itemDisplay = location.getWorld().spawn(location, ItemDisplay.class);

        itemDisplay.setItemStack(item);
        itemDisplay.setInvulnerable(true);

        if (displayName != null) {
            itemDisplay.setCustomName(Util.color(displayName));
            itemDisplay.setCustomNameVisible(true);
        }

        return itemDisplay;
    }

    public static boolean isLookingAt(LivingEntity checkedEntity, Location targetLocation) {
        if (checkedEntity == null || targetLocation == null) return false;

        Location eyeLocation = checkedEntity.getEyeLocation();

        Vector directionToLocation = targetLocation.toVector().subtract(eyeLocation.toVector()).normalize();
        Vector entityDirection = eyeLocation.getDirection().normalize();

        double angle = entityDirection.angle(directionToLocation);
        double angleDegrees = Math.toDegrees(angle);
        double maxAngle = 85.0;

        return angleDegrees <= maxAngle;
    }

}
