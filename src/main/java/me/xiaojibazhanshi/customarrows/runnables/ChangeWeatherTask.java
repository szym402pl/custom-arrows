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
        Location location = arrow.getLocation();
        World world = location.clone().getWorld();

        if (world == null) return;
        if (world.getEnvironment() != World.Environment.NORMAL) return;
        if (location.getY() < 120) return;

        boolean isClearWeather = world.isClearWeather();
        Color nextWeatherColor = isClearWeather ? Color.BLUE : Color.GREEN;

        ArrowSpecificUtil.detonateFirework(location, FireworkEffect.Type.STAR, nextWeatherColor);
        world.setStorm(isClearWeather);
        arrow.remove();
    }

}
