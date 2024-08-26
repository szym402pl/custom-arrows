package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.ArrayList;
import java.util.List;

import static me.xiaojibazhanshi.customarrows.util.arrows.TurboMine.getAdjacentBlocks;

public class TurboMineArrow extends CustomArrow {

    private final List<BlockFace> allowedBlockFaces = List.of(
            BlockFace.SELF,
            BlockFace.NORTH,
            BlockFace.SOUTH,
            BlockFace.EAST,
            BlockFace.WEST,
            BlockFace.NORTH_EAST,
            BlockFace.NORTH_WEST,
            BlockFace.SOUTH_EAST,
            BlockFace.SOUTH_WEST,
            BlockFace.NORTH_NORTH_EAST,
            BlockFace.NORTH_NORTH_WEST,
            BlockFace.SOUTH_SOUTH_EAST,
            BlockFace.SOUTH_SOUTH_WEST,
            BlockFace.EAST_NORTH_EAST,
            BlockFace.EAST_SOUTH_EAST,
            BlockFace.WEST_NORTH_WEST,
            BlockFace.WEST_SOUTH_WEST
    );

    public TurboMineArrow() {
        super(ArrowFactory.changeTippedColor
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&7Turbo Mine Arrow", "turbo_mine_arrow",
                                List.of("", "This arrow mines blocks in", "a 3x3 radius of hit location")),
                        Color.GRAY));
    }

    @Override
    public void onHitBlock(ProjectileHitEvent event, Player shooter) {
        Block hitBlock = event.getHitBlock();
        if (hitBlock == null) return;

        Block blockAbove = hitBlock.getRelative(BlockFace.UP);
        Block blockBelow = hitBlock.getRelative(BlockFace.DOWN);
        List<Block> adjacentBlocks = new ArrayList<>();

        adjacentBlocks.addAll(getAdjacentBlocks(blockAbove, allowedBlockFaces)); // top layer
        adjacentBlocks.addAll(getAdjacentBlocks(hitBlock, allowedBlockFaces)); // middle layer
        adjacentBlocks.addAll(getAdjacentBlocks(blockBelow, allowedBlockFaces)); // bottom layer

        for (Block block : adjacentBlocks) {
            block.breakNaturally();
        }

        event.getEntity().remove();
    }


}
