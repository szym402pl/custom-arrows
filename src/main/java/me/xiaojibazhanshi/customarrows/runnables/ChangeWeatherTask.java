package me.xiaojibazhanshi.customarrows.runnables;

import me.xiaojibazhanshi.customarrows.util.ArrowSpecificUtil;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitTask;

import java.util.function.Consumer;

public class ChangeWeatherTask implements Consumer<BukkitTask> {

    private final Entity arrow;

    public ChangeWeatherTask(Entity arrow) {
        this.arrow = arrow;
    }

    @Override
    public void accept(BukkitTask bukkitTask) {
        final int minHeight = 120;

        Location location = arrow.getLocation();
        World world = location.clone().getWorld();

        if (world == null) return;
        if (world.getEnvironment() != World.Environment.NORMAL) return;
        if (location.getY() < minHeight) return;

        boolean isClearWeather = world.isClearWeather();
        Color fireworkColor = isClearWeather ? Color.BLUE : Color.GREEN;

        ArrowSpecificUtil.detonateFirework(location, FireworkEffect.Type.STAR, fireworkColor);
        world.setStorm(isClearWeather);
        arrow.remove();
    }

}
