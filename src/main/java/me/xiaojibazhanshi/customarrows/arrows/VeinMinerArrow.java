package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.CustomArrows;
import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.runnables.VeinMinerTask;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.List;

import static me.xiaojibazhanshi.customarrows.util.GeneralUtil.color;
import static me.xiaojibazhanshi.customarrows.util.arrows.VeinMiner.*;

public class VeinMinerArrow extends CustomArrow {

    public VeinMinerArrow() {
        super(ArrowFactory.changeTippedColor
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&6Vein Miner Arrow", "vein_miner_arrow",
                                List.of("", "This arrow will mine an", "entire ore vein for you")),
                        Color.ORANGE));
    }

    @Override
    public void onHitBlock(ProjectileHitEvent event, Player shooter) {
        Block block = event.getHitBlock();

        if (!isOre(block)) {
            shooter.sendTitle("", color("&7I need to find an ore vein..."), 5, 20, 5);
            return;
        }

        event.getEntity().remove();

        List<Block> vein = getFullOreVein(List.of(block));
        List<Block> sortedVein = sortByDistanceTo(vein, block);

        VeinMinerTask task = new VeinMinerTask(sortedVein);
        Bukkit.getScheduler().runTaskTimer(CustomArrows.getInstance(), task, 2, 4); // breaks blocks 1 by 1
    }


}
