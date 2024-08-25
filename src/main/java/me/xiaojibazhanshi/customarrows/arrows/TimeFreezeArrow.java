package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.CustomArrows;
import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static me.xiaojibazhanshi.customarrows.util.arrows.AimAssist.provideAimAssist;
import static me.xiaojibazhanshi.customarrows.util.arrows.Homing.findEntityInSight;
import static me.xiaojibazhanshi.customarrows.util.arrows.TimeFreeze.freezeInPlace;
import static me.xiaojibazhanshi.customarrows.util.arrows.TimeFreeze.unFreeze;

public class TimeFreezeArrow extends CustomArrow {

    private final Map<Arrow, Vector> frozenArrows;

    public TimeFreezeArrow() {
        super(ArrowFactory.changeTippedColor
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&bTime Freeze Arrow", "time_freeze_arrow",
                                List.of("", "This arrow will freeze in", "time once it was shot",
                                        "", "NOTE: Shoot with bow to freeze it,", "and shoot with crossbow to activate")),
                        Color.AQUA));

        frozenArrows = new HashMap<>();
    }

    @Override
    public void onShoot(EntityShootBowEvent event, Player shooter) {
        ItemStack bow = event.getBow();

        Arrow arrow = (Arrow) event.getProjectile();
        long arrowFreezeTime = 20 * 20;

        boolean shotOutOfBow = bow != null && bow.getType() == Material.BOW;

        if (shotOutOfBow) {
            freezeInPlace(arrow, frozenArrows);

            Bukkit.getScheduler().runTaskLater(CustomArrows.getInstance(), () ->  {
                        unFreeze(arrow, frozenArrows, true);
                        frozenArrows.remove(arrow);
            }, arrowFreezeTime);

        } else {
            if (frozenArrows.isEmpty()) return;

            for (Arrow arrowItem : frozenArrows.keySet()) {
                unFreeze(arrowItem, frozenArrows, false);
            }

            arrow.remove();
            frozenArrows.clear();
        }
    }


}
