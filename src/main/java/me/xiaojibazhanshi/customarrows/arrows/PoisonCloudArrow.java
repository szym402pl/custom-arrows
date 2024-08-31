package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.CustomArrows;
import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

import static me.xiaojibazhanshi.customarrows.util.arrows.PoisonCloud.applyPoisonArrowProperties;
import static me.xiaojibazhanshi.customarrows.util.arrows.PoisonCloud.applyPoisonEffectIfCloseTo;
import static me.xiaojibazhanshi.customarrows.util.arrows.Smoke.createProgressiveSmokeCloud;

public class PoisonCloudArrow extends CustomArrow {

    private final Color COLOR = Color.OLIVE;
    private final PotionEffect POISON = new PotionEffect(PotionEffectType.POISON, 10 * 20, 1, true);

    public PoisonCloudArrow() {
        super(ArrowFactory.changeTippedColor
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&2Poison Cloud Arrow", "poison_cloud_arrow",
                                List.of("", "This arrow will create", "a poison smoke cloud")),
                        Color.GREEN));
    }

    @Override
    public void onHitBlock(ProjectileHitEvent event, Player shooter) {
        Arrow arrow = (Arrow) event.getEntity();
        Location arrowLocation = arrow.getLocation();

        applyPoisonArrowProperties(arrow);

        createProgressiveSmokeCloud(arrowLocation.clone(), COLOR);

        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                if (arrow.isDead()) {
                    cancel();
                    return;
                }

                applyPoisonEffectIfCloseTo(arrow, POISON);
            }
        };

        // 1 sec + 2 ticks period so the entity experiences the damage before applying the effect again
        runnable.runTaskTimer(CustomArrows.getInstance(), 1, 20 + 2);

    }

    @Override
    public void onHitEntity(EntityDamageByEntityEvent event, Player shooter) {
        Entity entity = event.getEntity();
        if (!(entity instanceof LivingEntity target)) return;

        target.addPotionEffect(POISON);
    }


}
