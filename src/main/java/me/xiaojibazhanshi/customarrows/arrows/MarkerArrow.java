package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.CustomArrows;
import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import me.xiaojibazhanshi.customarrows.util.ArrowSpecificUtil;
import me.xiaojibazhanshi.customarrows.util.GeneralUtil;
import org.bukkit.*;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import java.util.List;

public class MarkerArrow extends CustomArrow {

    public MarkerArrow() {
        super(ArrowFactory.changeTippedColor
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&7Marker Arrow", "marker_arrow",
                                List.of("", "This arrow will create a marker", "using a tower of blocks upon impact",
                                        "", "Note: double hit a block to make a blue one",
                                        "and hit an entity to make a red one")),
                        Color.GRAY));
    }

    @Override
    public void onHitBlock(ProjectileHitEvent event, Player shooter) {
        if (event.getEntity().isDead() || event.getHitBlock() == null) return;
        ((Arrow) event.getEntity()).setPickupStatus(AbstractArrow.PickupStatus.DISALLOWED);

        Bukkit.getScheduler().runTaskLater(CustomArrows.getInstance(), () -> {
            int count = 0;

            for (Entity entity : event.getEntity().getNearbyEntities(0.5, 0.5, 0.5)) {
                if (entity instanceof Arrow arrow) {
                    count++;
                    GeneralUtil.removeArrowAfter(arrow, 1);
                }
            }

            Location location = event.getEntity().getLocation().clone().add(0, 3, 0);

            if (count >= 1) {
                ArrowSpecificUtil.spawnBeam(location, 50,15, Material.BLUE_STAINED_GLASS);
            } else {
                ArrowSpecificUtil.spawnBeam(location, 50,15, Material.WHITE_STAINED_GLASS);
            }
        }, 60);
    }

    @Override
    public void onHitEntity(EntityDamageByEntityEvent event, Player shooter) {
        if (event.getEntity().isDead()) return;
        Location location = event.getEntity().getLocation().clone().add(0, 3, 0);

        ArrowSpecificUtil.spawnBeam(location, 50,15, Material.RED_STAINED_GLASS);
    }


}
