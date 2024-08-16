package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.CustomArrows;
import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import me.xiaojibazhanshi.customarrows.util.ArrowSpecificUtil;
import me.xiaojibazhanshi.customarrows.util.GeneralUtil;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.util.Vector;

import java.util.List;

public class FlashbangArrow extends CustomArrow {

    public FlashbangArrow() {
        super(ArrowFactory.changeTippedColor // Or you can use #changeTippedEffect if you need the effect
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&fFlashbang Arrow", "flashbang_arrow",
                                List.of("", "This arrow will drop a flashbang,", "blinding entities in vicinity")),
                        Color.WHITE));
    }

    @Override
    public void onHitBlock(ProjectileHitEvent event, Player shooter) {
        Location arrowLocation = event.getEntity().getLocation();
        event.getEntity().remove();

        Entity itemDisplay = ArrowSpecificUtil.spawnDisplayItem
                (Material.SNOWBALL, arrowLocation.add(new Vector(0, 0.2, 0)), "&eLook here!");

        Location itemDisplayLocation = itemDisplay.getLocation();
        assert itemDisplayLocation.getWorld() != null;

        itemDisplayLocation.getWorld().playSound(itemDisplayLocation, Sound.ENTITY_VILLAGER_CELEBRATE, 1.0F, 1.0F);

        ArrowSpecificUtil.detonateFlashbang(itemDisplay, 50L);
    }

    @Override
    public void onHitEntity(EntityDamageByEntityEvent event, Player shooter) {
        ArrowSpecificUtil.applyFlashbangEffect(event.getEntity());
        event.getDamager().remove();
    }

}
