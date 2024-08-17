package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import me.xiaojibazhanshi.customarrows.util.ArrowSpecificUtil;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.List;

public class MolotovArrow extends CustomArrow {

    public MolotovArrow() {
        super(ArrowFactory.changeTippedColor // Or you can use #changeTippedEffect if you need the effect
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&6Molotov Arrow", "molotov_arrow",
                                List.of("", "This arrow will act like", "a molotov wherever it lands",
                                        "", "Warning: Only works in open spaces!")),
                Color.ORANGE));
    }

    @Override
    public void onHitBlock(ProjectileHitEvent event, Player shooter) {
        Block arrowLocationBlock = event.getEntity().getLocation().getBlock();

        ArrowSpecificUtil.setFiresAround(arrowLocationBlock, 3);
        event.getEntity().remove();
    }

    @Override
    public void onHitEntity(EntityDamageByEntityEvent event, Player shooter) {
        Block arrowLocationBlock = event.getEntity().getLocation().getBlock();

        ArrowSpecificUtil.setFiresAround(arrowLocationBlock, 3);
        event.getDamager().remove();
    }
}
