package me.xiaojibazhanshi.customarrows.runnables;

import me.xiaojibazhanshi.customarrows.CustomArrows;
import org.bukkit.entity.*;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import static me.xiaojibazhanshi.customarrows.util.arrows.Homing.getDirectionFromEntityToTarget;
import static me.xiaojibazhanshi.customarrows.util.arrows.Homing.isDistanceGreaterThan;
import static me.xiaojibazhanshi.customarrows.util.arrows.Seeker.findFirstEntityBelow;

public class SeekerArrowRunnable extends BukkitRunnable {

    private Entity projectile;
    private Vector initialSpeed;
    private Player shooter;

    @Override
    public void run() {
        if (projectile == null || projectile.isDead() || projectile.isOnGround()) {
            cancel();
            return;
        }

        LivingEntity target = findFirstEntityBelow(projectile, 4, 100);
        if (target == null || !target.hasLineOfSight(projectile) || target.equals(shooter)) return;
        if (!(isDistanceGreaterThan(projectile, target, 12.5))) return;
        if (target.getType() == EntityType.ENDERMAN || target instanceof Fish) return;

        Vector directionToTarget = getDirectionFromEntityToTarget(projectile, target);
        Vector finalVelocity = directionToTarget.multiply(initialSpeed.length());

        final double velocityClamp = 0.75;
        projectile.setVelocity(finalVelocity.multiply(velocityClamp));
        projectile.setGlowing(true);

        cancel();
    }

    public void start(Player shooter, Entity projectile, Vector initialSpeed) {
        this.shooter = shooter;
        this.initialSpeed = initialSpeed;
        this.projectile = projectile;
        runTaskTimer(CustomArrows.getInstance(), 5, 5);
    }
}
