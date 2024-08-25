package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.CustomArrows;
import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.List;

import static me.xiaojibazhanshi.customarrows.util.GeneralUtil.color;
import static me.xiaojibazhanshi.customarrows.util.arrows.Growth.temporarilyConvertToBiggerBlock;

public class GrowthArrow extends CustomArrow {

    private final int delay = 20 * 10;
    private final double sizeMultiplier = 3.0;

    public GrowthArrow() {
        super(ArrowFactory.changeTippedColor
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&dGrowth Arrow", "growth_arrow",
                                List.of("", "This arrow will make anything", "you hit big (including blocks)")),
                        Color.PURPLE));
    }

    @Override
    public void onHitBlock(ProjectileHitEvent event, Player shooter) {
        Block block = event.getHitBlock();
        if (block == null) return;

        event.getEntity().remove();
        temporarilyConvertToBiggerBlock(block, delay, sizeMultiplier);
    }

    @Override
    public void onHitEntity(EntityDamageByEntityEvent event, Player shooter) {
        if (!(event.getEntity() instanceof LivingEntity livingEntity)) return;
        event.getDamager().remove();

        AttributeInstance attribute = livingEntity.getAttribute(Attribute.GENERIC_SCALE);
        double originalSize = attribute.getBaseValue();

        attribute.setBaseValue(originalSize * sizeMultiplier);

        Bukkit.getScheduler().runTaskLater(CustomArrows.getInstance(), () -> {
            if (livingEntity.isDead()) return;

            attribute.setBaseValue(originalSize);
        }, delay);
    }
}
