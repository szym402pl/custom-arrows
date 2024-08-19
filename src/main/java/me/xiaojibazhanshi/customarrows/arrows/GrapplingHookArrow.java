package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import me.xiaojibazhanshi.customarrows.util.ArrowSpecificUtil;
import me.xiaojibazhanshi.customarrows.util.GeneralUtil;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class GrapplingHookArrow extends CustomArrow {

    public GrapplingHookArrow() {
        super(ArrowFactory.changeTippedColor
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&9Grappling Hook Arrow", "grappling_hook_arrow",
                                List.of("", "This arrow will grapple you", "up to wherever it lands")),
                        Color.BLUE));
    }

    @Override
    public void onHitBlock(ProjectileHitEvent event, Player shooter) {
        Arrow arrow = (Arrow) event.getEntity();

        PotionEffect fall = new PotionEffect(PotionEffectType.SLOW_FALLING, 60, 0);
        shooter.addPotionEffect(fall);

        double speed = event.getEntity().isOnGround() ? 1.9 : 1.7;
        ArrowSpecificUtil.applyGrapplingHookVelocity(shooter, arrow.getLocation(), speed);
    }

    @Override
    public void onHitEntity(EntityDamageByEntityEvent event, Player shooter) {
        if (!(event.getEntity() instanceof LivingEntity target)) return;
        if (event.getEntity().equals(shooter)) return;

        double speed = event.getEntity().isOnGround() ? 6.0 : 4.0;
        ArrowSpecificUtil.applyGrapplingHookVelocity(target, shooter.getLocation(), speed);
    }

    @Override
    public void onShoot(EntityShootBowEvent event, Player shooter) {
        float force = event.getForce();

        if (force < 2.0) {
            event.setCancelled(true);
            shooter.sendTitle("", GeneralUtil.color("&7I need to draw the bow further..."));
            return;
        }

        Arrow arrow = (Arrow) event.getProjectile();
        arrow.setVelocity(arrow.getVelocity().multiply(0.65));
    }
}
