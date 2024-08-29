package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.CustomArrows;
import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.runnables.FrostWalkerRunnable;
import me.xiaojibazhanshi.customarrows.runnables.LavaWalkerRunnable;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityShootBowEvent;

import java.util.List;

public class LavaWalkerArrow extends CustomArrow {

    public LavaWalkerArrow() {
        super(ArrowFactory.changeTippedColor
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&4Lava Walker Arrow", "lava_walker_arrow",
                                List.of("", "This arrow will provide a", "steady obsidian platform",
                                        "", "NOTE: Has to be shot at lava!")),
                        Color.RED));
    }

    @Override
    public void onShoot(EntityShootBowEvent event, Player shooter) {
        Arrow arrow = (Arrow) event.getProjectile();
        arrow.setInvulnerable(true);

        executeLavaWalkerRunnable(arrow);
    }

    private void executeLavaWalkerRunnable(Arrow arrow) {
        LavaWalkerRunnable runnable = new LavaWalkerRunnable(arrow);
        Bukkit.getScheduler().runTaskTimer(CustomArrows.getInstance(), runnable, 4, 1);
    }
}
