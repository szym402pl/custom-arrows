package me.xiaojibazhanshi.customarrows.runnables;

import me.xiaojibazhanshi.customarrows.CustomArrows;
import me.xiaojibazhanshi.customarrows.util.ArrowSpecificUtil;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

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

        LivingEntity target = ArrowSpecificUtil.findFirstEntityBelow(projectile, 4, 50);
        if (target == null || !target.hasLineOfSight(projectile) || target.equals(shooter)) return;
        if (!(ArrowSpecificUtil.isDistanceGreaterThan(projectile, target, 20.0))) return;

        Vector directionToTarget = ArrowSpecificUtil.getDirectionFromEntityToTarget(projectile, target);
        Vector finalVelocity =  directionToTarget.multiply(initialSpeed.length());

        projectile.setVelocity(finalVelocity.multiply(0.55));

        projectile.setGlowing(true);
        target.setGlowing(true);

        cancel();
    }

    public void start(Player shooter, Entity projectile, Vector initialSpeed){
        this.shooter = shooter;
        this.initialSpeed = initialSpeed;
        this.projectile = projectile;
        runTaskTimer(CustomArrows.getInstance(), 5, 5);
    }
}
