package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.CustomArrows;
import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.runnables.FrostWalkerRunnable;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityShootBowEvent;

import java.util.List;

public class FrostWalkerArrow extends CustomArrow {

    public FrostWalkerArrow() {
        super(ArrowFactory.changeTippedColor
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&bFrost Walker Arrow", "frost_walker_arrow",
                                List.of("", "This arrow will provide a", "frost walker effect on the hit area",
                                        "", "Note: Has to be shot at water!")),
                        Color.AQUA));
    }

    @Override
    public void onShoot(EntityShootBowEvent event, Player shooter) {
        Arrow arrow = (Arrow) event.getProjectile();

        FrostWalkerRunnable runnable = new FrostWalkerRunnable(arrow);
        Bukkit.getScheduler().runTaskTimer(CustomArrows.getInstance(), runnable, 4, 1);
    }
}
