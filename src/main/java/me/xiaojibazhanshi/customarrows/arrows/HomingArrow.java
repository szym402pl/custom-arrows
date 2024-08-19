package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.runnables.HomingArrowRunnable;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import me.xiaojibazhanshi.customarrows.util.ArrowSpecificUtil;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.List;

public class HomingArrow extends CustomArrow {

    private HomingArrowRunnable homingArrowRunnable;

    public HomingArrow() {
        super(ArrowFactory.changeTippedColor // Or you can use #changeTippedEffect if you need the effect
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&4Homing Arrow", "homing_arrow",
                                List.of("", "This arrow chases your enemy down", "as long as they aren't too far")),
                        Color.RED));
    }

    @Override
    public void onShoot(EntityShootBowEvent event, Player shooter) {
        final int MAX_DISTANCE = 75;

        LivingEntity target = ArrowSpecificUtil.findEntityInSight(shooter, MAX_DISTANCE, 2.5);
        if (target == null || target.isDead()) return;

        Entity projectile = event.getProjectile();
        projectile.setGlowing(true);

        homingArrowRunnable = new HomingArrowRunnable();
        homingArrowRunnable.start(projectile, target, projectile.getVelocity(), MAX_DISTANCE);
    }

    @Override
    public void onHitBlock(ProjectileHitEvent event, Player shooter) {
        if (homingArrowRunnable != null)
            homingArrowRunnable.stop();

        event.getEntity().setGlowing(false);
    }

    @Override
    public void onHitEntity(EntityDamageByEntityEvent event, Player shooter) {
        if (homingArrowRunnable != null)
            homingArrowRunnable.stop();
    }
}
