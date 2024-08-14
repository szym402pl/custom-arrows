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

    @Override
    public void run() {
        if ((homingEntity == null || homingEntity.isDead()) || (target == null || target.isDead())) {
            cancel();
            return;
        }

        int MAX_DEGREES = 60;
        if (!ArrowSpecificUtil.isTargetWithinDegrees(homingEntity, target, MAX_DEGREES)) return;

        int MAX_DISTANCE = 100;
        if (ArrowSpecificUtil.isDistanceGreaterThan(homingEntity, target, MAX_DISTANCE)) return;

        Vector directionToTarget = ArrowSpecificUtil.getDirectionFromEntityToTarget(homingEntity, target);
        Vector finalVelocity =  directionToTarget.multiply(initialSpeed.length());

        homingEntity.setVelocity(finalVelocity);
    }

    public void start(Entity homingEntity, LivingEntity target, Vector initialSpeed) {
        this.initialSpeed = initialSpeed;
        this.homingEntity = homingEntity;
        this.target = target;

        runTaskTimer(CustomArrows.getInstance(), 4, 6);
    }

    public void stop() {
        cancel();
    }
}
