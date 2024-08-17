package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.CustomArrows;
import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.runnables.CrystalHealTask;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import me.xiaojibazhanshi.customarrows.util.ArrowSpecificUtil;
import me.xiaojibazhanshi.customarrows.util.GeneralUtil;
import org.bukkit.*;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.*;

public class CrystalHealArrow extends CustomArrow {

    private static Map<UUID, EnderCrystal> playersWithACrystal = new HashMap<>();

    public CrystalHealArrow() {
        super(ArrowFactory.changeTippedColor
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&dCrystal Heal Arrow", "crystal_heal_arrow",
                                List.of("", "This arrow will spawn a crystal", "that will heal you for 30 seconds")),
                Color.PURPLE));
    }

    @Override
    public void onHitBlock(ProjectileHitEvent event, Player shooter) {
        event.getEntity().remove();
        int period = 4;

        ArrowSpecificUtil.updateHealCrystalMap(playersWithACrystal, shooter, event.getEntity());

        CrystalHealTask task = new CrystalHealTask(playersWithACrystal, shooter, 30, period);
        Bukkit.getScheduler().runTaskTimer(CustomArrows.getInstance(), task, 1, period);
    }

    @Override
    public void onHitEntity(EntityDamageByEntityEvent event, Player shooter) {
        event.getDamager().remove();
    }
}
