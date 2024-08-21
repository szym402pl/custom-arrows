package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.CustomArrows;
import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.List;

import static me.xiaojibazhanshi.customarrows.util.arrows.Magnet.teleportNearbyItemsTo;
import static me.xiaojibazhanshi.customarrows.util.arrows.Repulsion.detonateFirework;

public class MagnetArrow extends CustomArrow {

    private final int RADIUS = 5;
    private final int DELAY = 40;

    public MagnetArrow() {
        super(ArrowFactory.changeTippedColor // Or you can use #changeTippedEffect if you need the effect
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&fMagnet &4Arrow", "magnet_arrow",
                                List.of("", "This arrow will retrieve", "all items in your vicinity")),
                        Color.RED));
    }

    @Override
    public void onHitBlock(ProjectileHitEvent event, Player shooter) {
        Entity arrow = event.getEntity();
        Location arrowLocation = arrow.getLocation();

        detonateFirework(arrowLocation, FireworkEffect.Type.BALL_LARGE, Color.GREEN);

        Bukkit.getScheduler().runTaskLater(CustomArrows.getInstance(), () -> {
            boolean wereThereItems = teleportNearbyItemsTo(arrow, RADIUS);

            Sound sound = wereThereItems ? Sound.ENTITY_VILLAGER_CELEBRATE : Sound.ENTITY_VILLAGER_NO;
            shooter.getWorld().playSound(arrowLocation, sound, 1.0F, 1.0F);
        }, DELAY);

        arrow.remove();
    }

    @Override
    public void onHitEntity(EntityDamageByEntityEvent event, Player shooter) {
        Entity arrow = event.getDamager();
        Location arrowLocation = arrow.getLocation();

        detonateFirework(arrowLocation, FireworkEffect.Type.BALL_LARGE, Color.GREEN);

        Bukkit.getScheduler().runTaskLater(CustomArrows.getInstance(), () -> {
            boolean wereThereItems = teleportNearbyItemsTo(arrow, RADIUS);

            Sound sound = wereThereItems ? Sound.ENTITY_VILLAGER_CELEBRATE : Sound.ENTITY_VILLAGER_NO;
            shooter.getWorld().playSound(arrowLocation, sound, 1.0F, 1.0F);
        }, DELAY);
    }


}
