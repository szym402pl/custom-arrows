package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import me.xiaojibazhanshi.customarrows.util.Util;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Bee;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

import java.util.List;

public class HiveArrow extends CustomArrow {

    public HiveArrow() {
        super(ArrowFactory.changeTippedColor
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&eHive Arrow", "hive_arrow",
                                List.of("", "This arrow will spawn a", "bee army on your target",
                                        "", "Note: Has to be shot at an entity!")),
                        Color.YELLOW));
    }

    @Override
    public void onHitEntity(EntityDamageByEntityEvent event, Player shooter) {
        if (!(event.getEntity() instanceof LivingEntity target)) return;

        Vector heightAdjustment = new Vector(0, 1, 0);
        Location spawnLocation = event.getEntity().getLocation().clone().add(heightAdjustment);
        World world = spawnLocation.getWorld();
        assert world != null;

        int beeAmount = 4;

        for (int i = 0; i < beeAmount; i++) {
            Bee bee = world.spawn(spawnLocation, Bee.class);
            bee.setAnger(999);
            bee.setTarget(target);

            Util.removeEntityAfter(bee, 400);
        }
    }


}
