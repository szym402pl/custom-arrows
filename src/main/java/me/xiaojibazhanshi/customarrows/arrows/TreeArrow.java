package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.type.Sapling;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import static me.xiaojibazhanshi.customarrows.util.GeneralUtil.color;
import static me.xiaojibazhanshi.customarrows.util.arrows.AimAssist.provideAimAssist;
import static me.xiaojibazhanshi.customarrows.util.arrows.Homing.findEntityInSight;
import static me.xiaojibazhanshi.customarrows.util.arrows.Tree.*;

public class TreeArrow extends CustomArrow {

    private final List<TreeType> treeTypes = List.of(
                TreeType.MANGROVE,
                TreeType.ACACIA,
                TreeType.TREE,
                TreeType.BIRCH,
                TreeType.DARK_OAK,
                TreeType.AZALEA,
                TreeType.CHERRY,
                TreeType.SMALL_JUNGLE
    );

    public TreeArrow() {
        super(ArrowFactory.changeTippedColor
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&2Tree Arrow", "tree_arrow",
                                List.of("", "This arrow literally just plants a tree")),
                        Color.GREEN));
    }

    @Override
    public void onHitBlock(ProjectileHitEvent event, Player shooter) {
        Block block = event.getHitBlock();

        if (!isSaplingEligible(block)) {
            shooter.sendTitle("", color("&7I can't grow a tree there..."), 5, 25, 5);
            return;
        }

        event.getEntity().remove();

        Block blockAbove = event.getHitBlock().getRelative(BlockFace.UP);
        int whichTree = ThreadLocalRandom.current().nextInt(0, treeTypes.size() -1);

        generateTree(blockAbove, treeTypes.get(whichTree));
    }
}
