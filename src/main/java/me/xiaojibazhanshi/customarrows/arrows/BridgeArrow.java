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

import java.util.ArrayList;
import java.util.List;

import static me.xiaojibazhanshi.customarrows.util.GeneralUtil.color;
import static me.xiaojibazhanshi.customarrows.util.arrows.Bridge.getALineOfAirBlocks;

public class BridgeArrow extends CustomArrow {

    private final List<BlockFace> acceptedBlockFaces = new ArrayList<>();

    public BridgeArrow() {
        super(ArrowFactory.changeTippedColor
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&7Bridge Arrow", "bridge_arrow",
                                List.of("", "This arrow will make a bridge", "extruding from the block you hit")),
                        Color.GRAY));

        acceptedBlockFaces.add(BlockFace.EAST);
        acceptedBlockFaces.add(BlockFace.NORTH);
        acceptedBlockFaces.add(BlockFace.WEST);
        acceptedBlockFaces.add(BlockFace.SOUTH);
    }

    @Override
    public void onHitBlock(ProjectileHitEvent event, Player shooter) {
        Block hitBlock = event.getHitBlock();

        if (hitBlock == null || !hitBlock.getType().isSolid()) {
            shooter.sendTitle("", color("&7I can't make a bridge here..."), 5, 25, 5);
            return;
        }

        BlockFace hitBlockFace = event.getHitBlockFace();

        if (!acceptedBlockFaces.contains(hitBlockFace)) {
            shooter.sendTitle("", color("&7I can't make a bridge here..."), 5, 25, 5);
            return;
        }

        Arrow arrow = (Arrow) event.getEntity();
        arrow.remove();

        int maxBridgeLength = 10;

        List<Block> blocksToPlace = getALineOfAirBlocks(hitBlock, hitBlockFace, maxBridgeLength);

        BridgeTask task = new BridgeTask(blocksToPlace);
        task.start();
    }

    @Override
    public void onHitEntity(EntityDamageByEntityEvent event, Player shooter) {
        shooter.sendTitle("", color("&7That's not a block..."), 5, 25, 5);
    }
}
