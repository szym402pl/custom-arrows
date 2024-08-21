package me.xiaojibazhanshi.customarrows.runnables;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitTask;

import java.util.function.Consumer;

import static me.xiaojibazhanshi.customarrows.util.arrows.Repulsion.detonateFirework;

public class ChangeDayCycleTask implements Consumer<BukkitTask> {

    private final Entity arrow;

    public ChangeDayCycleTask(Entity arrow) {
        this.arrow = arrow;
    }

    @Override
    public void accept(BukkitTask bukkitTask) {
        final long nightPeak = 18000;
        final long dayPeak = 6000;
        final long nightThreshold = 12000;
        final long dayThreshold = 0;

        final int minHeight = 120;

        Location location = arrow.getLocation();
        World world = location.clone().getWorld();

        if (world == null) return;
        if (world.getEnvironment() != World.Environment.NORMAL) return;
        if (location.getY() < minHeight) return;

        boolean isDay = world.getTime() > dayThreshold && world.getTime() < nightThreshold;

        long time = isDay ? nightPeak : dayPeak;
        Color fireworkColor = isDay ? Color.BLACK : Color.AQUA;

        detonateFirework(location, FireworkEffect.Type.STAR, fireworkColor);

        world.setTime(time);
        arrow.remove();
    }

}
