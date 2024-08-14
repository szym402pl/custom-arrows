package me.xiaojibazhanshi.customarrows.listeners;

import me.xiaojibazhanshi.customarrows.managers.ArrowManager;
import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;

public class ArrowFireListener implements Listener {

    private final ArrowManager arrowManager;

    public ArrowFireListener(ArrowManager arrowManager) {
        this.arrowManager = arrowManager;
    }

    @EventHandler
    public void onArrowFire(EntityShootBowEvent event) {
        if (!(event.getProjectile() instanceof Arrow arrow)) return;
        if (!(arrow.getShooter() instanceof Player player)) return;

        CustomArrow customArrow = arrowManager.getCustomArrow(arrow);
        if (customArrow == null) return;

        customArrow.onShoot(event, player);
    }

}
