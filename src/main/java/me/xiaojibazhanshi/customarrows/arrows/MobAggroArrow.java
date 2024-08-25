package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.List;

import static me.xiaojibazhanshi.customarrows.util.arrows.MobAggro.aggroMobsNearby;

public class MobAggroArrow extends CustomArrow {

    public MobAggroArrow() {
        super(ArrowFactory.changeTippedColor
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&4Mob Aggro Arrow", "mob_aggro_arrow",
                                List.of("", "This arrow makes sure your", "target is surrounded by mobs")),
                        Color.RED));
    }

    @Override
    public void onHitEntity(EntityDamageByEntityEvent event, Player shooter) {
        if (!(event.getEntity() instanceof LivingEntity livingEntity)) return;

        int radius = 6;
        aggroMobsNearby(livingEntity, radius);
    }
}
