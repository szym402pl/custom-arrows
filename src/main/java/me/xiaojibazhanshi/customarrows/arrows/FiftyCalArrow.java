package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import me.xiaojibazhanshi.customarrows.util.ArrowSpecificUtil;
import me.xiaojibazhanshi.customarrows.util.GeneralUtil;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.List;

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
    public void onHitBlock(ProjectileHitEvent event, Player shooter) {
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
        if (GeneralUtil.restrictUseIfWeaponIsNot(event, shooter, Material.CROSSBOW)) return;

        Entity arrow = event.getProjectile();
        Location explosionLocation = arrow.getLocation().add(arrow.getVelocity().multiply(0.1));

        GeneralUtil.damageWeapon(event.getBow(), 5);

        arrow.getWorld().createExplosion(explosionLocation, 0.3F);
        GeneralUtil.shootLikeABullet(arrow, 0.4);

        ArrowSpecificUtil.applyEffectsIfShotRapidly(shooter);
    }
}
