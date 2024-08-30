package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.List;

import static me.xiaojibazhanshi.customarrows.util.arrows.WaterArrow.spawnAWaterBlockAbove;

public class WaterArrow extends CustomArrow {

    public WaterArrow() {
        super(ArrowFactory.changeTippedColor
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&bWater Arrow", "water_arrow",
                                List.of("", "This arrow will place water. That's it.")),
                        Color.AQUA));
    }

    @Override
    public void onHitBlock(ProjectileHitEvent event, Player shooter) {
        Block hitBlock = event.getHitBlock();
        Arrow arrow = (Arrow) event.getEntity();

        if (hitBlock == null) return;

        Material hitBlockType = hitBlock.getType();

        if (!hitBlockType.isSolid()) return;

        spawnAWaterBlockAbove(hitBlock);
        arrow.remove();
    }
}
