package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import me.xiaojibazhanshi.customarrows.util.ArrowSpecificUtil;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.Map;

public class HoneypotArrow extends CustomArrow {

    private final int DELETE_AFTER_SECONDS = 5;
    private final Material REPLACEMENT = Material.ORANGE_STAINED_GLASS;

    public HoneypotArrow() {
        super(ArrowFactory.changeTippedColor
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&6Honeypot Arrow", "honeypot_arrow",
                                List.of("", "This arrow will trap the", "target in a temporary dome")),
                        Color.ORANGE));
    }

    @Override
    public void onHitEntity(EntityDamageByEntityEvent event, Player shooter) {
        event.getDamager().remove();
        int radius = 4;

        Location targetLocation = event.getEntity().getLocation().add(new Vector(0.5, 0, 0.5));
        Map<Location, Material> targetBlockLocations = ArrowSpecificUtil.getAHollowSphereAround(targetLocation, radius);

        ArrowSpecificUtil.placeTemporaryBlocks(targetBlockLocations, DELETE_AFTER_SECONDS, REPLACEMENT);
    }

    @Override
    public void onHitBlock(ProjectileHitEvent event, Player shooter) {
        event.getEntity().remove();

        Block hitBlock = event.getHitBlock();
        assert hitBlock != null;

        Map<Location, Material> targetBlockLocations = Map.of(hitBlock.getLocation(), hitBlock.getType());

        ArrowSpecificUtil.placeTemporaryBlocks(targetBlockLocations, DELETE_AFTER_SECONDS, REPLACEMENT);
    }
}
