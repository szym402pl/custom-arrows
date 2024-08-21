package me.xiaojibazhanshi.customarrows.listeners;

import me.xiaojibazhanshi.customarrows.managers.ArrowManager;
import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowSpecificUtil;
import me.xiaojibazhanshi.customarrows.util.Util;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

public class ArrowHitBlockListener implements Listener {

    private final ArrowManager arrowManager;

    public ArrowHitBlockListener(ArrowManager arrowManager) {
        this.arrowManager = arrowManager;
    }

    @EventHandler
    public void onArrowHitBlock(ProjectileHitEvent event) {
        if (ArrowSpecificUtil.isFakeArrow(event.getEntity())) {
            CustomArrow customArrow = arrowManager.getCustomArrows().get(Util.createStringNSKey("ghost_arrow"));
            customArrow.onHitBlock(event, (Player) event.getEntity().getShooter());
        }

        if (!(event.getEntity() instanceof Arrow arrow)) return;
        if (!(arrow.getShooter() instanceof Player player)) return;
        if (event.getHitEntity() != null) return;

        CustomArrow customArrow = arrowManager.getCustomArrow(arrow);
        if (customArrow == null) return;

        customArrow.onHitBlock(event, player);
    }

}
