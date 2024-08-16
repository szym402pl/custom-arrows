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
import org.bukkit.block.data.Levelled;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.List;
import java.util.Map;

public class HoneyTrapArrow extends CustomArrow {

    private final int DELETE_AFTER_SECONDS = 5;
    private final Material REPLACEMENT = Material.HONEY_BLOCK;

    public HoneyTrapArrow() {
        super(ArrowFactory.changeTippedColor
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&6Honey Trap Arrow", "honey_trap_arrow",
                                List.of("", "This arrow will trap the", "target in honey blocks")),
                        Color.ORANGE));
    }

    @Override
    public void onHitEntity(EntityDamageByEntityEvent event, Player shooter) {
        event.getDamager().remove();
        int radius = 4;

        Location targetLocation = event.getEntity().getLocation();
        Map<Location, Material> targetBlockLocations = ArrowSpecificUtil.getAHollowSphereAround(targetLocation, radius);

        ArrowSpecificUtil.placeTemporaryBlocks(targetBlockLocations, DELETE_AFTER_SECONDS, REPLACEMENT);
    }

    @Override
    public void onHitGround(ProjectileHitEvent event, Player shooter) {
        event.getEntity().remove();

        Block hitBlock = event.getHitBlock();
        assert hitBlock != null;

        Map<Location, Material> targetBlockLocations = Map.of(hitBlock.getLocation(), hitBlock.getType());

        ArrowSpecificUtil.placeTemporaryBlocks(targetBlockLocations, DELETE_AFTER_SECONDS, REPLACEMENT);
    }
}
