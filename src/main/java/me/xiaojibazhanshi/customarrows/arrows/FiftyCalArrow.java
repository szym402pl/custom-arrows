package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.CustomArrows;
import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import me.xiaojibazhanshi.customarrows.util.ArrowSpecificUtil;
import me.xiaojibazhanshi.customarrows.util.GeneralUtil;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.Random;

public class FiftyCalArrow extends CustomArrow {

    public FiftyCalArrow() {
        super(ArrowFactory.changeTippedColor // Or you can use #changeTippedEffect if you need the effect
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&1.50 cal Arrow", "fifty_cal_arrow",
                                List.of("", "This arrow is a literal 50 cal bullet",
                                        "and can only be shot using crossbows!")),
                        Color.NAVY));
    }

    @Override
    public void onHitGround(ProjectileHitEvent event, Player shooter) {
        Entity arrow = event.getEntity();

        arrow.getWorld().createExplosion(event.getEntity().getLocation(), 0.75F, false, true);
        arrow.remove();
    }

    @Override
    public void onHitEntity(EntityDamageByEntityEvent event, Player shooter) {
        Entity arrow = event.getDamager();

        arrow.getWorld().createExplosion(event.getEntity().getLocation(), 0.25F);
        arrow.remove();

        event.setDamage(event.getDamage() * 3);
    }

    @Override
    public void onShoot(EntityShootBowEvent event, Player shooter) {
        if (event.getBow() == null) return;
        Entity arrow = event.getProjectile();

        if (event.getBow().getType() != Material.CROSSBOW) {
            shooter.playSound(shooter, Sound.ENTITY_VILLAGER_NO, 1.0F ,1.0F);
            event.setCancelled(true);
        }

        boolean catchOnFire = new Random().nextInt(5) == 2;
        Location explosionLocation = arrow.getLocation().add(arrow.getVelocity().multiply(0.1));

        arrow.getWorld().createExplosion(explosionLocation, 0.3F, catchOnFire);
        GeneralUtil.shootLikeABullet(arrow, 0.4);
    }
}
