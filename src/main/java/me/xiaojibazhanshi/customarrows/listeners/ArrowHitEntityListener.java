package me.xiaojibazhanshi.customarrows.listeners;

import me.xiaojibazhanshi.customarrows.managers.ArrowManager;
import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.GeneralUtil;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class ArrowHitEntityListener implements Listener {

    private final ArrowManager arrowManager;

    public ArrowHitEntityListener(ArrowManager arrowManager) {
        this.arrowManager = arrowManager;
    }

    @EventHandler
    public void onEntityHitByEntity(EntityDamageByEntityEvent event) {
        if (GeneralUtil.isHealingCrystal(event.getEntity())) {
            event.setCancelled(true);
        }

        if (!(event.getDamager() instanceof Arrow arrow)) return;
        if (!(arrow.getShooter() instanceof Player player)) return;

        CustomArrow customArrow = arrowManager.getCustomArrow(arrow);
        if (customArrow == null) return;

        customArrow.onHitEntity(event, player);
    }

}
