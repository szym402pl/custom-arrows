package me.xiaojibazhanshi.customarrows.runnables;

import me.xiaojibazhanshi.customarrows.CustomArrows;
import me.xiaojibazhanshi.customarrows.util.ArrowSpecificUtil;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class HomingArrowRunnable extends BukkitRunnable {

    private final int MAX_DEGREES = 60;

    private LivingEntity target;
    private Entity homingEntity;

    @Override
    public void run() {
        if (target.isDead()) return;
        if (target == null) return;
        if (!ArrowSpecificUtil.isTargetWithinDegrees(homingEntity, target, MAX_DEGREES)) return;


    }

    public void start(Entity homingEntity, LivingEntity target, Vector initialSpeed) {
        this.homingEntity = homingEntity;
        this.target = target;

        runTaskTimer(CustomArrows.getInstance(), 10, 5);
    }

    public void stop() {
        cancel();
    }
}
