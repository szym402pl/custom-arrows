package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import me.xiaojibazhanshi.customarrows.util.ArrowSpecificUtil;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class GhostArrow extends CustomArrow {

    public GhostArrow() {
        super(ArrowFactory.changeTippedColor // Or you can use #changeTippedEffect if you need the effect
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&7Ghost Arrow", "ghost_arrow",
                                List.of("", "This arrow is able to phase", "through a single block layer")),
                        Color.GRAY));
    }

    @Override
    public void onHitBlock(ProjectileHitEvent event, Player shooter) {
        event.setCancelled(true);

        Entity arrow = event.getEntity();
        Location currentLocation = arrow.getLocation();

        Block hitBlock = event.getHitBlock();
        Block nextBlock = currentLocation.add(arrow.getVelocity().normalize().multiply(0.5)).getBlock();

        if (nextBlock.getType() != Material.AIR || hitBlock == null) return;

        ArrowSpecificUtil.temporarilyConvertToDisplayItem(hitBlock);
    }

    @Override
    public void onShoot(EntityShootBowEvent event, Player shooter) {

    }
}
