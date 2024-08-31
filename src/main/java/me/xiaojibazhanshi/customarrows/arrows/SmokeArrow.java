package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.CustomArrows;
import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

import static me.xiaojibazhanshi.customarrows.util.arrows.Smoke.createProgressiveSmokeCloud;

public class SmokeArrow extends CustomArrow {

    public SmokeArrow() {
        super(ArrowFactory.changeTippedColor // Or you can use #changeTippedEffect if you need the effect
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&8Smoke Arrow", "smoke_arrow",
                                List.of("", "This arrow will hide the", "target in a dome of smoke")),
                        Color.GRAY));
    }

    @Override
    public void onHitBlock(ProjectileHitEvent event, Player shooter) {
        Entity arrow = event.getEntity();
        Location arrowLocation = arrow.getLocation();
        arrow.remove();

        Bukkit.getScheduler().runTaskAsynchronously(CustomArrows.getInstance(), () -> {
            createProgressiveSmokeCloud(arrowLocation.clone(), Color.GRAY);
        });
    }

    @Override
    public void onHitEntity(EntityDamageByEntityEvent event, Player shooter) {
        Location arrowLocation = event.getDamager().getLocation();

        Bukkit.getScheduler().runTaskAsynchronously(CustomArrows.getInstance(), () -> {
            createProgressiveSmokeCloud(arrowLocation.clone(), Color.GRAY);
        });

        if (!(event.getEntity() instanceof LivingEntity target)) return;

        PotionEffect invisibility = new PotionEffect(PotionEffectType.INVISIBILITY, 10 * 20, 0, true);
        target.addPotionEffect(invisibility);
    }
}
