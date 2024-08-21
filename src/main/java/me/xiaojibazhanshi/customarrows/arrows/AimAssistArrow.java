package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityShootBowEvent;

import java.util.List;

import static me.xiaojibazhanshi.customarrows.util.arrows.AimAssist.provideAimAssist;
import static me.xiaojibazhanshi.customarrows.util.arrows.Homing.findEntityInSight;

public class AimAssistArrow extends CustomArrow {

    public AimAssistArrow() {
        super(ArrowFactory.changeTippedColor // Or you can use #changeTippedEffect if you need the effect
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&aAim Assist Arrow", "aim_assist_arrow",
                                List.of("", "This arrow makes sure you", "shoot straight at your target")),
                        Color.LIME));
    }

    @Override
    public void onShoot(EntityShootBowEvent event, Player shooter) {
        LivingEntity target = findEntityInSight(shooter, 60, 5.0);

        if (target == null || target.isDead()) return;
        if (!shooter.hasLineOfSight(target)) return;

        Entity projectile = event.getProjectile();

        provideAimAssist(projectile, target);
    }
}
