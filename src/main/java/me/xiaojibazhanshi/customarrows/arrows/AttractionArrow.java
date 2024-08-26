package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.CustomArrows;
import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.runnables.AttractionTask;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.util.Vector;

import java.util.List;

import static me.xiaojibazhanshi.customarrows.util.arrows.TimeFreeze.freezeInPlace;

public class AttractionArrow extends CustomArrow {

    public AttractionArrow() {
        super(ArrowFactory.changeTippedColor
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&1Attraction Arrow", "attraction_arrow",
                                List.of("", "This arrow attracts", "mobs into hit location")),
                        Color.BLUE));
    }

    @Override
    public void onHitBlock(ProjectileHitEvent event, Player shooter) {
        Arrow arrow = (Arrow) event.getEntity();
        Location attractionPoint = arrow.getLocation();
        int durationInSeconds = 5;
        int period = 2;

        arrow.setPickupStatus(AbstractArrow.PickupStatus.DISALLOWED);
        arrow.setInvulnerable(true);
        arrow.setGravity(false);
        arrow.setGlowing(true);
        arrow.setVelocity(new Vector());

        AttractionTask task = new AttractionTask(attractionPoint, durationInSeconds, period);
        Bukkit.getScheduler().runTaskTimer(CustomArrows.getInstance(), task, 1, period);

        Bukkit.getScheduler().runTaskLater(CustomArrows.getInstance(), arrow::remove, durationInSeconds * 20);
    }

    @Override
    public void onHitEntity(EntityDamageByEntityEvent event, Player shooter) {
        Arrow arrow = (Arrow) event.getDamager();
        Location attractionPoint = arrow.getLocation();
        int durationInSeconds = 5;
        int period = 2;

        arrow.setPickupStatus(AbstractArrow.PickupStatus.DISALLOWED);
        arrow.setInvulnerable(true);
        arrow.setGravity(false);
        arrow.setGlowing(true);
        arrow.setVelocity(new Vector());

        AttractionTask task = new AttractionTask(attractionPoint, durationInSeconds, period);
        Bukkit.getScheduler().runTaskTimer(CustomArrows.getInstance(), task, 1, period);

        Bukkit.getScheduler().runTaskLater(CustomArrows.getInstance(), arrow::remove, durationInSeconds * 20);
    }
}
