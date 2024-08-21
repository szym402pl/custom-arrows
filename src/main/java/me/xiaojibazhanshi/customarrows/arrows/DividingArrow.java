package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import me.xiaojibazhanshi.customarrows.util.Util;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.*;

import static me.xiaojibazhanshi.customarrows.util.ArrowSpecificUtil.divideArrow;

public class DividingArrow extends CustomArrow {

    private Map<UUID, List<Arrow>> activeArrows;

    public DividingArrow() {
        super(ArrowFactory.changeTippedColor
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&fDividing Arrow", "dividing_arrow",
                                List.of("", "This arrow can divide mid-air.", "Quickly shoot before it lands!")),
                        Color.WHITE));

        this.activeArrows = new HashMap<>();
    }

    @Override
    public void onShoot(EntityShootBowEvent event, Player shooter) {
        Arrow arrow = ((Arrow) event.getProjectile());
        arrow.setPickupStatus(AbstractArrow.PickupStatus.DISALLOWED);
        UUID uuid = shooter.getUniqueId();

        if (!activeArrows.containsKey(uuid)) {
            activeArrows.put(uuid, List.of((Arrow) event.getProjectile()));
            return;
        }

        event.setCancelled(true);
        List<Arrow> arrows = activeArrows.get(uuid);
        activeArrows.remove(uuid);

        List<Arrow> newArrows = new ArrayList<>();

        for (Arrow arrowA : arrows) {
            newArrows.addAll(divideArrow(arrowA));
        }

        activeArrows.put(uuid, newArrows);
        Util.removeArrowAfter(arrow, 200);
    }

    @Override
    public void onHitBlock(ProjectileHitEvent event, Player shooter) {
        event.getEntity().remove();
        activeArrows.remove(shooter.getUniqueId());
    }

    @Override
    public void onHitEntity(EntityDamageByEntityEvent event, Player shooter) {
        activeArrows.remove(shooter.getUniqueId());
    }


}
