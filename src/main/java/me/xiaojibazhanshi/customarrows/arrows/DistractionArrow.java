package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.CustomArrows;
import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.runnables.DistractionTask;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

import static me.xiaojibazhanshi.customarrows.util.arrows.AimAssist.provideAimAssist;
import static me.xiaojibazhanshi.customarrows.util.arrows.Distraction.distract;
import static me.xiaojibazhanshi.customarrows.util.arrows.Homing.findEntityInSight;

public class DistractionArrow extends CustomArrow {

    private final int FIREWORKS = 10;

    public DistractionArrow() {
        super(ArrowFactory.changeTippedColor
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&0Distraction Arrow", "distraction_arrow",
                                List.of("", "This arrow will just", "distract your enemies")),
                        Color.BLACK));
    }

    @Override
    public void onHitEntity(EntityDamageByEntityEvent event, Player shooter) {
        Location location = shooter.getEyeLocation();

        distract(FIREWORKS, location);
    }

    @Override
    public void onHitBlock(ProjectileHitEvent event, Player shooter) {
        Block hitBlock = event.getHitBlock();
        if (hitBlock == null) return;

        Location location = hitBlock.getLocation();

        distract(FIREWORKS, location);
    }
}
