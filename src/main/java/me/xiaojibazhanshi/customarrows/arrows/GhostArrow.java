package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.CustomArrows;
import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.runnables.GhostArrowTrackingTask;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import me.xiaojibazhanshi.customarrows.util.ArrowSpecificUtil;
import me.xiaojibazhanshi.customarrows.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class GhostArrow extends CustomArrow {

    public GhostArrow() {
        super(ArrowFactory.changeTippedColor // Or you can use #changeTippedEffect if you need the effect
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&7Ghost Arrow", "ghost_arrow",
                                List.of("", "This arrow is able to phase", "through a single block layer",
                                "", "Note: This arrow can only", "be shot using a crossbow!")),
                        Color.GRAY));
    }

    @Override
    public void onShoot(EntityShootBowEvent event, Player shooter) {
        GeneralUtil.restrictUseIfWeaponIsNot(event, shooter, Material.CROSSBOW);
        event.getProjectile().setPersistent(true);
        event.getProjectile().setGravity(false);
        final long delay = 2;

        GhostArrowTrackingTask task = new GhostArrowTrackingTask(event.getProjectile(), 6, delay);
        Bukkit.getScheduler().runTaskTimer(CustomArrows.getInstance(), task, 6, delay);
    }
}
