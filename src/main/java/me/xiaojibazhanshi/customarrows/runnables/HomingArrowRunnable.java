package me.xiaojibazhanshi.customarrows.runnables;

import me.xiaojibazhanshi.customarrows.CustomArrows;
import me.xiaojibazhanshi.customarrows.util.ArrowSpecificUtil;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class HomingArrowRunnable extends BukkitRunnable {

    private Vector initialSpeed;
    private LivingEntity target;
    private Entity homingEntity;
    private int maxDistance;

    @Override
    public void run() {
        if ((homingEntity == null || homingEntity.isDead()) || (target == null || target.isDead())) {
            cancel();
            return;
        }

        int MAX_TURN_DEGREES = 120;

        if (!ArrowSpecificUtil.isTargetWithinDegrees(homingEntity, target, MAX_TURN_DEGREES)) return;
        if (ArrowSpecificUtil.isDistanceGreaterThan(homingEntity, target, maxDistance)) return;

        Vector directionToTarget = ArrowSpecificUtil.getDirectionFromEntityToTarget(homingEntity, target);
        Vector finalVelocity = directionToTarget.multiply(initialSpeed.length());

        homingEntity.setVelocity(finalVelocity);
    }

    public void start(Entity homingEntity, LivingEntity target, Vector initialSpeed, int maxDistance) {
        this.initialSpeed = initialSpeed;
        this.homingEntity = homingEntity;
        this.target = target;
        this.maxDistance = maxDistance;

        runTaskTimer(CustomArrows.getInstance(), 4, 6);
    }

    public void stop() {
        cancel();
    }
}
