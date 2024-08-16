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
import org.bukkit.util.Vector;

import java.util.List;

public class FiftyCalArrow extends CustomArrow {

    public FiftyCalArrow() {
        super(ArrowFactory.changeTippedColor // Or you can use #changeTippedEffect if you need the effect
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&1.50 cal Arrow", "fifty_cal_arrow",
                                List.of("", "This arrow is a literal", "50 caliber bullet")),
                        Color.NAVY));
    }

    @Override
    public void onHitGround(ProjectileHitEvent event, Player shooter) {
        event.getEntity().getWorld().createExplosion(event.getEntity().getLocation(), 0.5F);
        event.getEntity().remove();
    }

    @Override
    public void onHitEntity(EntityDamageByEntityEvent event, Player shooter) {
        event.getDamager().getWorld().createExplosion(event.getDamager().getLocation(), 0.5F);
        event.getDamager().remove();
    }

    @Override
    public void onShoot(EntityShootBowEvent event, Player shooter) {
        event.getProjectile().getWorld().createExplosion(event.getProjectile().getLocation(), 0.3F);
        GeneralUtil.shootLikeABullet(event.getProjectile(), 0.3);
    }
}
