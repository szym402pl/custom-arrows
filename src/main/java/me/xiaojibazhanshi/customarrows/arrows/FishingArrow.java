package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.CustomArrows;
import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.runnables.BridgeTask;
import me.xiaojibazhanshi.customarrows.runnables.FishingArrowTrackTask;
import me.xiaojibazhanshi.customarrows.runnables.LavaWalkerRunnable;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;

import static me.xiaojibazhanshi.customarrows.util.GeneralUtil.color;
import static me.xiaojibazhanshi.customarrows.util.arrows.Bridge.getALineOfAirBlocks;
import static me.xiaojibazhanshi.customarrows.util.arrows.Fishing.getFishingLootTable;

public class FishingArrow extends CustomArrow {

    public FishingArrow() {
        super(ArrowFactory.changeTippedColor
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&bFishing Arrow", "fishing_arrow",
                                List.of("", "This arrow will fish for you. That's it.")),
                        Color.AQUA));

    }

    @Override
    public void onShoot(EntityShootBowEvent event, Player shooter) {
        Arrow arrow = (Arrow) event.getProjectile();

        startTrackingFishingArrow(arrow, shooter);
    }

    private void startTrackingFishingArrow(Arrow arrow, Player shooter) {
        FishingArrowTrackTask fishingTask = new FishingArrowTrackTask(arrow, shooter);
        Bukkit.getScheduler().runTaskTimer(CustomArrows.getInstance(), fishingTask, 1, 1);
    }
}
