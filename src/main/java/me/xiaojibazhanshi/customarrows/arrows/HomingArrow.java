package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.CustomArrows;
import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.runnables.HomingArrowRunnable;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import me.xiaojibazhanshi.customarrows.util.ArrowSpecificUtil;
import me.xiaojibazhanshi.customarrows.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class HomingArrow extends CustomArrow {

    private HomingArrowRunnable homingArrowRunnable;

    public HomingArrow() {
        super(ArrowFactory.changeTippedColor // Or you can use #changeTippedEffect if you need the effect
                (ArrowFactory.createArrowItemStack(
                               Material.TIPPED_ARROW, "&4Homing Arrow", "homing_arrow",
                               List.of("", "This arrow chases your enemy down", "as long as they aren't too far",
                                       "or the angle isn't too steep (>60°)", "", "Make sure to fully draw your bow!")),
                        Color.RED));
    }

    @Override
    public void onShoot(EntityShootBowEvent event, Player shooter) {
        if (!(event.getForce() >= 1.0)) return;

        LivingEntity target = ArrowSpecificUtil.findEntityInSight(shooter, 100);
        if (target == null) return;

        Entity projectile = event.getProjectile();
        projectile.setGlowing(true);

        homingArrowRunnable = new HomingArrowRunnable();
        homingArrowRunnable.start(projectile, target, projectile.getVelocity());
    }

    @Override
    public void onHitGround(ProjectileHitEvent event, Player shooter) {
        if (homingArrowRunnable != null)
            homingArrowRunnable.stop();
    }

    @Override
    public void onHitEntity(EntityDamageByEntityEvent event, Player shooter) {
        if (homingArrowRunnable != null)
            homingArrowRunnable.stop();
    }
}
