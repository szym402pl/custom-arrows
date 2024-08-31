package me.xiaojibazhanshi.customarrows.util.arrows;

import me.xiaojibazhanshi.customarrows.CustomArrows;
import org.bukkit.Bukkit;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;

public class PoisonCloud {

    private PoisonCloud() {

    }

    public static void applyPoisonEffectIfCloseTo(Arrow arrow, PotionEffect poison) {
        arrow.getNearbyEntities(5, 5,5)
                .stream()
                .filter(entity -> entity instanceof LivingEntity)
                .map(entity -> (LivingEntity) entity)
                .forEach(entity -> entity.addPotionEffect(poison));
    }

    public static void applyPoisonArrowProperties(Arrow arrow) {
        arrow.setPickupStatus(AbstractArrow.PickupStatus.DISALLOWED);
        arrow.setVisibleByDefault(false);
        arrow.setInvulnerable(true);

        Bukkit.getScheduler().runTaskLater(CustomArrows.getInstance(), arrow::remove, 25 * 20);
    }

}
