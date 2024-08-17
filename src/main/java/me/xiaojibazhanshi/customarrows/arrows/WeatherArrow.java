package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.CustomArrows;
import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.runnables.ChangeWeatherTask;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import me.xiaojibazhanshi.customarrows.util.GeneralUtil;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.List;

public class WeatherArrow extends CustomArrow {

    public WeatherArrow() {
        super(ArrowFactory.changeTippedColor
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&bWeather Arrow", "weather_arrow",
                                List.of("", "This arrow will change", "the weather in your world")),
                        Color.AQUA));
    }

    @Override
    public void onShoot(EntityShootBowEvent event, Player shooter) {
        int heightCheckDelay = 50;

        ChangeWeatherTask task = new ChangeWeatherTask(event.getProjectile());
        Bukkit.getScheduler().runTaskLater(CustomArrows.getInstance(), task, heightCheckDelay);
    }

    @Override
    public void onHitEntity(EntityDamageByEntityEvent event, Player shooter) {
        if (shooter.getWorld().getEnvironment() == World.Environment.NORMAL) {
            shooter.sendTitle("", GeneralUtil.color("&7I need to aim higher..."), 5, 20, 5);
        } else {
            shooter.sendTitle(GeneralUtil.color("&7Oh wait..."),
                    GeneralUtil.color("&7there's no weather here..."), 5, 30, 5);
        }
    }

    @Override
    public void onHitBlock(ProjectileHitEvent event, Player shooter) {
        if (shooter.getWorld().getEnvironment() == World.Environment.NORMAL) {
            shooter.sendTitle("", GeneralUtil.color("&7I need to aim higher..."), 5, 20, 5);
        } else {
            shooter.sendTitle(GeneralUtil.color("&7Oh wait..."),
                    GeneralUtil.color("&7there's no weather here..."), 5, 30, 5);
        }

        event.getEntity().remove();
    }
}
