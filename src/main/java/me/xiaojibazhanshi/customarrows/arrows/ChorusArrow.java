package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Beacon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.List;

import static me.xiaojibazhanshi.customarrows.util.arrows.Chorus.randomizeLocation;

public class ChorusArrow extends CustomArrow {

    public ChorusArrow() {
        super(ArrowFactory.changeTippedColor
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&dChorus Arrow", "chorus_arrow",
                                List.of("", "This arrow will randomly", "teleport the target")),
                        Color.PURPLE));
    }

    @Override
    public void onHitEntity(EntityDamageByEntityEvent event, Player shooter) {
        Entity entity = event.getEntity();
        if (!(entity instanceof LivingEntity livingEntity)) return;

        Location entityLocation = livingEntity.getLocation();
        int maxOffset = 6;

        Location randomizedLocation = randomizeLocation(entityLocation, maxOffset);

        livingEntity.teleport(randomizedLocation);
    }


}
