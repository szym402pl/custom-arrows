package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.ArrayList;
import java.util.List;

import static me.xiaojibazhanshi.customarrows.util.GeneralUtil.color;
import static me.xiaojibazhanshi.customarrows.util.arrows.SnowTrap.isEligibleForTrap;
import static me.xiaojibazhanshi.customarrows.util.arrows.SnowTrap.setUpTrap;

public class SnowTrapArrow extends CustomArrow {

    private final List<BlockFace> blockFacesToCheck = new ArrayList<>();

    public SnowTrapArrow() {
        super(ArrowFactory.changeTippedColor
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&fSnow Trap Arrow", "snow_trap_arrow",
                                List.of("", "This arrow will make a snow", "powder trap wherever it lands")),
                        Color.WHITE));

        blockFacesToCheck.add(BlockFace.EAST);
        blockFacesToCheck.add(BlockFace.NORTH);
        blockFacesToCheck.add(BlockFace.WEST);
        blockFacesToCheck.add(BlockFace.SOUTH);
    }

    @Override
    public void onHitBlock(ProjectileHitEvent event, Player shooter) {
        Block hitBlock = event.getHitBlock();
        if (hitBlock == null) return;

        if (!isEligibleForTrap(hitBlock, blockFacesToCheck)) {
            shooter.sendTitle("", color("&7I can't set up a trap here..."), 5, 25, 5);
            return;
        }

        Arrow arrow = (Arrow) event.getEntity();
        arrow.remove();

        setUpTrap(hitBlock);
    }


}
