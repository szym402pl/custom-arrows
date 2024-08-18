package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.CustomArrows;
import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.runnables.BlackHoleTask;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.List;

public class BlackHoleArrow extends CustomArrow {

    public BlackHoleArrow() {
        super(ArrowFactory.changeTippedColor
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&0Black Hole Arrow", "black_hole_arrow",
                                List.of("", "This arrow will turn the arrow", "into a black hole upon impact")),
                Color.BLACK));
    }

    @Override
    public void onHitEntity(EntityDamageByEntityEvent event, Player shooter) {
        event.getDamager().remove();

        BlackHoleTask task = new BlackHoleTask(event.getDamager().getLocation(), 5, 2);
        Bukkit.getScheduler().runTaskTimer(CustomArrows.getInstance(), task, 1, 2);
    }

    @Override
    public void onHitBlock(ProjectileHitEvent event, Player shooter) {
        event.getEntity().remove();

        BlackHoleTask task = new BlackHoleTask(event.getEntity().getLocation(), 5, 2);
        Bukkit.getScheduler().runTaskTimer(CustomArrows.getInstance(), task, 1, 2);
    }
}
