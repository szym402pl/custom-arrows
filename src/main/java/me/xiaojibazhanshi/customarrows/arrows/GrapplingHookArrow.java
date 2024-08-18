package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.CustomArrows;
import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import me.xiaojibazhanshi.customarrows.util.ArrowSpecificUtil;
import me.xiaojibazhanshi.customarrows.util.GeneralUtil;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

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

        Vector direction = ArrowSpecificUtil.getDirectionFromEntityToTarget(arrow, shooter);
        shooter.setVelocity(direction.add(new Vector(0, 1, 0)));
    }

    @Override
    public void onHitEntity(EntityDamageByEntityEvent event, Player shooter) {
        if (!(event.getEntity() instanceof LivingEntity target)) return;
        if (event.getEntity().equals(shooter)) return;

        Vector direction = ArrowSpecificUtil.getDirectionFromEntityToTarget(target, shooter);
        target.setVelocity(direction);
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
        arrow.setVelocity(arrow.getVelocity().multiply(0.6));
    }
}
