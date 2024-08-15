package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.CustomArrows;
import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import me.xiaojibazhanshi.customarrows.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.util.Vector;

import java.util.List;

public class LightweightArrow extends CustomArrow {

    public LightweightArrow() {
        super(ArrowFactory.changeTippedColor // Or you can use #changeTippedEffect if you need the effect
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&fLightweight Arrow", "lightweight_arrow",
                                List.of("", "This arrow is 100% faster", "but deals 25% less damage")),
                        Color.WHITE));
    }

    @Override
    public void onHitEntity(EntityDamageByEntityEvent event, Player shooter) {
        if (!(event.getEntity() instanceof LivingEntity)) return;

        event.setDamage(event.getDamage() * 0.75);
    }

    @Override
    public void onShoot(EntityShootBowEvent event, Player shooter) {
        Bukkit.getScheduler().runTaskLater(CustomArrows.getInstance(), () -> {
            Entity projectile = event.getProjectile();
            Vector doubledVelocity = projectile.getVelocity().clone().multiply(2.0);

            event.getProjectile().setVelocity(doubledVelocity);
        }, 2); // This ensures that the velocity is set correctly
    }
}
