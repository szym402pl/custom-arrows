package me.xiaojibazhanshi.customarrows.listeners;

import me.xiaojibazhanshi.customarrows.arrows.ImmunityBubbleArrow;
import me.xiaojibazhanshi.customarrows.managers.ArrowManager;
import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.GeneralUtil;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import static me.xiaojibazhanshi.customarrows.util.GeneralUtil.color;

public class ArrowHitEntityListener implements Listener {

    private final ArrowManager arrowManager;

    public ArrowHitEntityListener(ArrowManager arrowManager) {
        this.arrowManager = arrowManager;
    }

    @EventHandler
    public void onEntityHitByEntity(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();

        if (ImmunityBubbleArrow.invincibleEntities.contains(entity.getUniqueId())) {
            event.setCancelled(true);
            event.getDamager().remove();
            return;
        }

        if (entity.isCustomNameVisible()
                && entity.getCustomName() != null
                && entity.getCustomName().equals(color("&cHit me!"))) {

            entity.setCustomName("");
            entity.setCustomNameVisible(false);
            entity.getWorld().createExplosion(entity.getLocation(), 3.0F);
            return;
        }

        if (!event.getDamager().isVisibleByDefault()) event.setCancelled(true);

        if (GeneralUtil.isHealingCrystal(entity)) {
            event.setCancelled(true);
        }

        if (!(event.getDamager() instanceof Arrow arrow)) return;
        if (!(arrow.getShooter() instanceof Player player)) return;

        CustomArrow customArrow = arrowManager.getCustomArrow(arrow);
        if (customArrow == null) return;

        customArrow.onHitEntity(event, player);
    }

}
