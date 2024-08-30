package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.CustomArrows;
import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.runnables.FishingArrowTrackTask;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityShootBowEvent;

import java.util.List;

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
