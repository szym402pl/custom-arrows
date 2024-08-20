package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import me.xiaojibazhanshi.customarrows.util.ArrowSpecificUtil;
import me.xiaojibazhanshi.customarrows.util.GeneralUtil;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

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

        Entity arrow = event.getProjectile();
        arrow.setPersistent(true);
        arrow.setGravity(false);
        arrow.setGlowing(true);

        ArrowSpecificUtil.shootFakeArrow(event, shooter);
        GeneralUtil.removeArrowAfter((Arrow) arrow, 200);
    }

    @Override
    public void onHitBlock(ProjectileHitEvent event, Player shooter) {
        Block hitBlock = event.getHitBlock();

        if (hitBlock != null
                && hitBlock.getType().isSolid()
                && GeneralUtil.isNotPlant(hitBlock)
                && ArrowSpecificUtil.isFakeArrow(event.getEntity())
                && hitBlock.getType().isBlock()
                && !(hitBlock instanceof Container)) {

            ArrowSpecificUtil.temporarilyConvertToDisplayItem(hitBlock, 4, null);
            event.getEntity().remove();
        }
    }
}
