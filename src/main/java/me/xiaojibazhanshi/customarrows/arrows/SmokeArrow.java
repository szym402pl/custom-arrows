package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import me.xiaojibazhanshi.customarrows.util.ArrowSpecificUtil;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

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
        Location arrowLocation = event.getEntity().getLocation();

        ArrowSpecificUtil.createSmokeCloud(arrowLocation);
    }

    @Override
    public void onHitEntity(EntityDamageByEntityEvent event, Player shooter) {
        Location arrowLocation = event.getDamager().getLocation();
        ArrowSpecificUtil.createSmokeCloud(arrowLocation);

        if (!(event.getEntity() instanceof LivingEntity target)) return;

        int potDuration = 5 * 20;
        int amplifier = 0;

        PotionEffect invisibility = new PotionEffect(PotionEffectType.INVISIBILITY, potDuration, amplifier, true);
        target.addPotionEffect(invisibility);
    }
}
