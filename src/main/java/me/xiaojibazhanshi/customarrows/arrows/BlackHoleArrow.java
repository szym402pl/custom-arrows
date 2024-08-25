package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.CustomArrows;
import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.runnables.blackhole.BlackHoleTask;
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
        int duration = 10;

        event.getDamager().remove();
        Location holeLocation = event.getDamager().getLocation().clone().add(1.1, 0, 1.1);

        executeBlackHoleTask(holeLocation, duration);
    }

    @Override
    public void onHitBlock(ProjectileHitEvent event, Player shooter) {
        if (event.getHitBlock() == null || event.getHitBlock().getType() == Material.AIR) return;

        int duration = 5;

        event.getEntity().remove();
        Location holeLocation = event.getEntity().getLocation().clone().add(1.1, 1, 1.1);

        executeBlackHoleTask(holeLocation, duration);
    }

    private void executeBlackHoleTask(Location location, int durationInSeconds) {
        BlackHoleTask task = new BlackHoleTask(location, durationInSeconds, 2);
        Bukkit.getScheduler().runTaskTimer(CustomArrows.getInstance(), task, 1, 2);
    }
}
