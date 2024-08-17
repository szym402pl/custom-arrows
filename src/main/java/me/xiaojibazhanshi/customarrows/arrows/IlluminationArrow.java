package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.CustomArrows;
import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.List;

public class IlluminationArrow extends CustomArrow {

    public IlluminationArrow() {
        super(ArrowFactory.changeTippedColor // Or you can use #changeTippedEffect if you need the effect
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&eIllumination Arrow", "illumination_arrow",
                                List.of("", "This arrow will light up the area",
                                        "surrounding it's landing location",
                                        "", "Note: Light is active for one minute, and",
                                        "the arrow can only be retrieved in this time frame")),
                Color.YELLOW));
    }

    @Override
    public void onHitBlock(ProjectileHitEvent event, Player shooter) {
        Arrow arrow = (Arrow) event.getEntity();

        Block hitBlock = event.getHitBlock();
        if (hitBlock == null) return;

        Material startingMaterial = hitBlock.getType();
        hitBlock.setType(Material.GLOWSTONE);

        Bukkit.getScheduler().runTaskLater(CustomArrows.getInstance(), () -> {
            try {
                arrow.remove();
            } catch (Exception ignored) {
            } // Just a failsafe for when the arrow got picked up

            hitBlock.setType(startingMaterial);
        }, 60 * 20);
    }
}
