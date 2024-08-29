package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.runnables.BridgeTask;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;

import static me.xiaojibazhanshi.customarrows.util.GeneralUtil.color;
import static me.xiaojibazhanshi.customarrows.util.arrows.Bridge.getALineOfAirBlocks;
import static me.xiaojibazhanshi.customarrows.util.arrows.Fishing.getFishingLootTable;

public class FishingArrow extends CustomArrow {

    private final Map<ItemStack, Double> lootTable;

    public FishingArrow() {
        super(ArrowFactory.changeTippedColor
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&7Bridge Arrow", "bridge_arrow",
                                List.of("", "This arrow will make a bridge", "extruding from the block you hit")),
                        Color.GRAY));

        lootTable = getFishingLootTable();
    }

    @Override
    public void onHitBlock(ProjectileHitEvent event, Player shooter) {

    }

    @Override
    public void onHitEntity(EntityDamageByEntityEvent event, Player shooter) {

    }
}
