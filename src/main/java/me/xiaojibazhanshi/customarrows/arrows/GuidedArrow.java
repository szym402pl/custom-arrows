package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.CustomArrows;
import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.runnables.FishingArrowTrackTask;
import me.xiaojibazhanshi.customarrows.runnables.GuidedArrowTrackTask;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

import static me.xiaojibazhanshi.customarrows.util.GeneralUtil.color;

public class GuidedArrow extends CustomArrow {

    public GuidedArrow() {
        super(ArrowFactory.changeTippedColor
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&8Guided Arrow", "guided_arrow",
                                List.of("", "This arrow will treat", "your crosshair as a guide")),
                        Color.fromRGB(169, 169, 169)));
    }

    @Override
    public void onShoot(EntityShootBowEvent event, Player shooter) {
        double force = event.getForce();
        if (force < 1.4) {
            shooter.sendTitle("", color("&7I need to draw the bow further..."), 5, 25 ,5);
            return;
        }

        Arrow arrow = (Arrow) event.getProjectile();
        double speedMultiplier = 0.5;

        arrow.setVelocity(arrow.getVelocity().multiply(speedMultiplier));
        arrow.setGlowing(true);
        arrow.setCritical(true);

        startTrackingGuidedArrow(arrow, shooter);
    }

    private void startTrackingGuidedArrow(Arrow arrow, Player shooter) {
        GuidedArrowTrackTask guidedArrowTrackTask = new GuidedArrowTrackTask(arrow, shooter);
        Bukkit.getScheduler().runTaskTimer(CustomArrows.getInstance(), guidedArrowTrackTask, 1, 1);
    }


}
