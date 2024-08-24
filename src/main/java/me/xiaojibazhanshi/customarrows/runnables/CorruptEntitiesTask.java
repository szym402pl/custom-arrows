package me.xiaojibazhanshi.customarrows.runnables;

import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

import static me.xiaojibazhanshi.customarrows.util.GeneralUtil.color;
import static me.xiaojibazhanshi.customarrows.util.arrows.Homing.getDirectionFromEntityToTarget;

public class CorruptEntitiesTask implements Consumer<BukkitTask> {

    private final int durationInSeconds;
    private final int chosenPeriod;
    private final Arrow arrow;

    private int counter = 1;

    public CorruptEntitiesTask(Arrow arrow, int durationInSeconds, int chosenPeriod) {
        this.durationInSeconds = durationInSeconds;
        this.chosenPeriod = chosenPeriod;
        this.arrow = arrow;
    }

    @Override
    public void accept(BukkitTask bukkitTask) {
        if (counter * chosenPeriod >= durationInSeconds * 20) {
            arrow.remove();
            bukkitTask.cancel();
            return;
        }

        PotionEffect wither = new PotionEffect(PotionEffectType.WITHER, 20, 0, true);

        arrow.getNearbyEntities(3, 3, 3)
                .stream()
                .filter(entity -> entity instanceof LivingEntity)
                .forEach(livingEntity -> {
                    ((LivingEntity) livingEntity).addPotionEffect(wither);

                    if (livingEntity instanceof Player player) {
                        player.sendTitle("", color("&7You're being corrupted..."), 0, 20, 0);
                        player.damage(1.0);
                    }
                });

        counter++;
    }

}
