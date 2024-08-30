package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.List;

import static me.xiaojibazhanshi.customarrows.util.GeneralUtil.color;
import static me.xiaojibazhanshi.customarrows.util.arrows.AimAssist.provideAimAssist;
import static me.xiaojibazhanshi.customarrows.util.arrows.BoneMeal.applyBonemealEffect;
import static me.xiaojibazhanshi.customarrows.util.arrows.Homing.findEntityInSight;

public class BoneMealArrow extends CustomArrow {

    public BoneMealArrow() {
        super(ArrowFactory.changeTippedColor
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&fBone Meal Arrow", "bone_meal_arrow",
                                List.of("", "This arrow grows stuff. That's it.")),
                        Color.WHITE));
    }

    @Override
    public void onHitBlock(ProjectileHitEvent event, Player shooter) {
        Block hitBlock = event.getHitBlock();

        if (hitBlock == null) {
            return;
        }

        Block above = hitBlock.getRelative(BlockFace.UP);
        String name = above.getType().toString().toLowerCase();

        boolean effectApplied;

        if (name.contains("sapling")) {
            effectApplied = applyBonemealEffect(above);
        } else {
            effectApplied = applyBonemealEffect(hitBlock);
        }

        if (!effectApplied) {
            shooter.sendTitle("", color("&7I can't use bone meal on this block..."), 5, 25 ,5);
            return;
        }

        Arrow arrow = (Arrow) event.getEntity();
        arrow.remove();
    }
}
