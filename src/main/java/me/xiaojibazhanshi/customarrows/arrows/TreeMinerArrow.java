package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.runnables.TreeMinerTask;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

import static me.xiaojibazhanshi.customarrows.util.GeneralUtil.color;
import static me.xiaojibazhanshi.customarrows.util.arrows.AimAssist.provideAimAssist;
import static me.xiaojibazhanshi.customarrows.util.arrows.Homing.findEntityInSight;
import static me.xiaojibazhanshi.customarrows.util.arrows.TreeMiner.getAllTreeLogs;

public class TreeMinerArrow extends CustomArrow {

    public TreeMinerArrow() {
        super(ArrowFactory.changeTippedColor
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&2Tree Miner Arrow", "tree_miner_arrow",
                                List.of("", "This arrow mines a tree you", "hit (chops it bottom to top)")),
                        Color.OLIVE));
    }

    @Override
    public void onHitBlock(ProjectileHitEvent event, Player shooter) {
        Block hitBlock = event.getHitBlock();
        if (hitBlock == null) return;

        List<Block> logs = getAllTreeLogs(hitBlock);

        if (logs.size() < 3) {
            shooter.sendTitle("", color("&7This isn't a tree..."), 5, 25 , 5);
            return;
        } else if (logs.size() < 5) {
            shooter.sendTitle("", color("&7I have to shoot lower..."), 5, 25 , 5);
            return;
        }

        Arrow arrow = (Arrow) event.getEntity();
        arrow.remove();

        TreeMinerTask task = new TreeMinerTask(logs);
        task.start();
    }
}
