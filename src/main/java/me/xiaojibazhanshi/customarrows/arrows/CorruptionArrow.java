package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.CustomArrows;
import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.runnables.CorruptEntitiesTask;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.util.Vector;

import java.util.List;

import static me.xiaojibazhanshi.customarrows.util.arrows.Corruption.temporarilyCorruptBlocksInRadius;

public class CorruptionArrow extends CustomArrow {

    private static boolean shouldTrigger;

    public CorruptionArrow() {
        super(ArrowFactory.changeTippedColor
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&dCorruption Arrow", "corruption_arrow",
                                List.of("", "This arrow will corrupt", "blocks within arrow's range")),
                        Color.PURPLE));

        shouldTrigger = true;
    }

    @Override
    public void onHitBlock(ProjectileHitEvent event, Player shooter) {
        Block hitBlock = event.getHitBlock();
        if (hitBlock == null) return;

        if (!(shouldTrigger))  {
            shouldTrigger = true;
            return;
        }

        shouldTrigger = false;

        Arrow arrow = (Arrow) event.getEntity();
        arrow.setInvulnerable(true);
        arrow.setPickupStatus(AbstractArrow.PickupStatus.DISALLOWED);
        arrow.setVisibleByDefault(false);

        temporarilyCorruptBlocksInRadius(hitBlock, 3, 10);

        CorruptEntitiesTask task = new CorruptEntitiesTask(arrow, 10, 4);
        Bukkit.getScheduler().runTaskTimer(CustomArrows.getInstance(), task, 1, 4);
    }
}
