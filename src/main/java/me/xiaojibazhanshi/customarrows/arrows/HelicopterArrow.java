package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.CustomArrows;
import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.runnables.HelicopterTask;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import me.xiaojibazhanshi.customarrows.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class HelicopterArrow extends CustomArrow {

    public HelicopterArrow() {
        super(ArrowFactory.changeTippedColor
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&dHelicopter Arrow", "helicopter_arrow",
                                List.of("", "This arrow will make the", "target into a helicopter")),
                Color.PURPLE));
    }

    @Override
    public void onHitEntity(EntityDamageByEntityEvent event, Player shooter) {
        if (!(event.getEntity() instanceof LivingEntity target)) return;
        event.setCancelled(true);
        event.getDamager().remove();

        int period = 2;

        int durationInSeconds = 5;
        int effectDelay = (int) (durationInSeconds * 20 * 1.25);

        HelicopterTask task = new HelicopterTask(target, durationInSeconds, period, 45.0F, 0.5);
        Bukkit.getScheduler().runTaskTimer(CustomArrows.getInstance(), task, 1, period);

        Bukkit.getScheduler().runTaskLater(CustomArrows.getInstance(), () -> {
            target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 20, 1));
            target.setGravity(false);

            Bukkit.getScheduler().runTaskLater(CustomArrows.getInstance(), () -> target.setGravity(true), 10);
        }, effectDelay);
    }
}
