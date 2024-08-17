package me.xiaojibazhanshi.customarrows.runnables;

import me.xiaojibazhanshi.customarrows.util.ArrowSpecificUtil;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitTask;

import java.util.function.Consumer;

public class ChangeDayCycleTask implements Consumer<BukkitTask> {

    private final Entity arrow;

    public ChangeDayCycleTask(Entity arrow) {
        this.arrow = arrow;
    }

    @Override
    public void accept(BukkitTask bukkitTask) {
        Location location = arrow.getLocation();
        World world = location.clone().getWorld();

        if (world == null) return;
        if (world.getEnvironment() != World.Environment.NORMAL) return;
        if (location.getY() < 120) return;

        boolean isDay = world.getTime() > 0 && world.getTime() < 12000;
        Color nextCycle = isDay ? Color.BLACK : Color.AQUA;

        ArrowSpecificUtil.detonateFirework(location, FireworkEffect.Type.STAR, nextCycle);
        world.setTime(isDay ? 18000 : 6000);
        arrow.remove();
    }

}
