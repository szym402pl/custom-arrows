package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.CustomArrows;
import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import me.xiaojibazhanshi.customarrows.util.ArrowSpecificUtil;
import me.xiaojibazhanshi.customarrows.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

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

        Entity arrow = event.getProjectile();
        arrow.setPersistent(true);
        arrow.setGravity(false);
        arrow.setGlowing(true);

        ArrowSpecificUtil.shootFakeArrow(event, shooter);

        GeneralUtil.removeArrowAfter((Arrow) event.getProjectile(), 200);
    }

    @Override
    public void onHitBlock(ProjectileHitEvent event, Player shooter) {
        if (event.getHitBlock() != null && event.getHitBlock().getType().isSolid() &&
        GeneralUtil.isNotPlant(event.getHitBlock()) && !event.getEntity().isVisibleByDefault()) {

            ArrowSpecificUtil.temporarilyConvertToDisplayItem(event.getHitBlock());
            event.getEntity().remove();

        }
    }
}
