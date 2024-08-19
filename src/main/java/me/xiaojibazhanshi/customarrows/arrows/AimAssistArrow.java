package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import me.xiaojibazhanshi.customarrows.util.ArrowSpecificUtil;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityShootBowEvent;

import java.util.List;

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
        LivingEntity target = ArrowSpecificUtil.findEntityInSight(shooter, 60, 5.0);

        if (target == null || target.isDead()) return;
        if (!shooter.hasLineOfSight(target)) return;

        Entity projectile = event.getProjectile();

        ArrowSpecificUtil.provideAimAssist(projectile, target);
    }
}
