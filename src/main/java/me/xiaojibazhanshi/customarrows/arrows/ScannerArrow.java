package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.List;

import static me.xiaojibazhanshi.customarrows.util.arrows.Repulsion.detonateFirework;
import static me.xiaojibazhanshi.customarrows.util.arrows.Scanner.areEnemiesNearby;

public class ScannerArrow extends CustomArrow {

    public ScannerArrow() {
        super(ArrowFactory.changeTippedColor
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&eScanner Arrow", "scanner_arrow",
                                List.of("", "This arrow scans for", "entities hiding nearby")),
                        Color.YELLOW));
    }

    @Override
    public void onHitEntity(EntityDamageByEntityEvent event, Player shooter) {
        Arrow arrow = (Arrow) event.getDamager();

        detonateFirework(arrow.getLocation(),
                FireworkEffect.Type.BALL_LARGE,
                areEnemiesNearby(arrow, shooter) ? Color.RED : Color.AQUA);

        arrow.remove();
    }

    @Override
    public void onHitBlock(ProjectileHitEvent event, Player shooter) {
        Arrow arrow = (Arrow) event.getEntity();

        detonateFirework(arrow.getLocation(),
                FireworkEffect.Type.BALL_LARGE,
                areEnemiesNearby(arrow, shooter) ? Color.RED : Color.AQUA);

        arrow.remove();
    }


}
