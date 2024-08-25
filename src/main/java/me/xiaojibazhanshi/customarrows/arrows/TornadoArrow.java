package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.CustomArrows;
import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.runnables.tornado.TornadoParticleTask;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.List;

public class TornadoArrow extends CustomArrow {

    private final int durationInSeconds = 15;
    private final int period = 6;

    public TornadoArrow() {
        super(ArrowFactory.changeTippedColor
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&0Tornado Arrow", "tornado_arrow",
                                List.of("", "This arrow will spawn a tornado. Yeah, I mean it.")),
                        Color.BLACK));
    }

    @Override
    public void onHitBlock(ProjectileHitEvent event, Player shooter) {
        Block hitBlock = event.getHitBlock();
        if (hitBlock == null) return;

        Entity arrow = event.getEntity();


        initializeTornado(arrow, durationInSeconds, period);
    }

    @Override
    public void onHitEntity(EntityDamageByEntityEvent event, Player shooter) {
        Entity entity = event.getEntity();
        if (entity.isDead()) return;

        initializeTornado(entity, durationInSeconds, period);
    }

    private void initializeTornado(Entity centerEntity, int durationInSeconds, int period) {
        Location center = centerEntity.getLocation();

        TornadoParticleTask animation = new TornadoParticleTask(center, durationInSeconds, period);
        Bukkit.getScheduler().runTaskTimer(CustomArrows.getInstance(), animation, 1, period);
    }
}
