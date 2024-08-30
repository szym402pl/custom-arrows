package me.xiaojibazhanshi.customarrows.runnables;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Arrow;
import org.bukkit.scheduler.BukkitTask;

import java.util.function.Consumer;

import static me.xiaojibazhanshi.customarrows.util.arrows.Signal.detonateSilentFirework;
import static me.xiaojibazhanshi.customarrows.util.arrows.Thunder.randomizeLocation;

public class SignalRunnable implements Consumer<BukkitTask> {

    private final Arrow arrow;

    public SignalRunnable(Arrow arrow) {
        this.arrow = arrow;
    }

    @Override
    public void accept(BukkitTask bukkitTask) {
        if (arrow == null || arrow.isDead() || arrow.isOnGround() || arrow.isInWater()) {
            bukkitTask.cancel();
            return;
        }

        Location location = arrow.getLocation();
        FireworkEffect.Type type = FireworkEffect.Type.BURST;
        Color color = Color.RED;

        detonateSilentFirework(location, type, color);
    }

}
