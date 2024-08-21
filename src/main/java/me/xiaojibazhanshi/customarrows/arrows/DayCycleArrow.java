package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.CustomArrows;
import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.runnables.ChangeDayCycleTask;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import me.xiaojibazhanshi.customarrows.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.List;

public class DayCycleArrow extends CustomArrow {

    public DayCycleArrow() {
        super(ArrowFactory.changeTippedColor
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&aDay Cycle Arrow", "day_cycle_arrow",
                                List.of("", "This arrow will change", "the time in your world")),
                        Color.LIME));
    }

    @Override
    public void onShoot(EntityShootBowEvent event, Player shooter) {
        int heightCheckDelay = 50;

        ChangeDayCycleTask task = new ChangeDayCycleTask(event.getProjectile());
        Bukkit.getScheduler().runTaskLater(CustomArrows.getInstance(), task, heightCheckDelay);
    }

    @Override
    public void onHitEntity(EntityDamageByEntityEvent event, Player shooter) {
        sendTitle(shooter);
    }

    @Override
    public void onHitBlock(ProjectileHitEvent event, Player shooter) {
        sendTitle(shooter);

        event.getEntity().remove();
    }

    private void sendTitle(Player shooter) {
        if (shooter.getWorld().getEnvironment() == World.Environment.NORMAL) {
            shooter.sendTitle("", Util.color("&7I need to aim higher..."), 5, 20, 5);
        } else {
            shooter.sendTitle(Util.color("&7Oh wait..."),
                    Util.color("&7time doesn't change here..."), 5, 30, 5);
        }
    }
}
