package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
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

        // * 0.72 cuts it down to default value and * 0.75 brings it to 25% less than normal
        event.setDamage(event.getDamage() * 0.72 * 0.75);
    }

    @Override
    public void onShoot(EntityShootBowEvent event, Player shooter) {
        Entity projectile = event.getProjectile();
        Vector doubledVelocity = projectile.getVelocity().clone().multiply(2.0);

        projectile.setVelocity(doubledVelocity);
    }
}
