package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityShootBowEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static me.xiaojibazhanshi.customarrows.util.GeneralUtil.color;
import static me.xiaojibazhanshi.customarrows.util.GeneralUtil.restrictUseIfWeaponIsNot;
import static me.xiaojibazhanshi.customarrows.util.arrows.Homing.findEntityInSight;
import static me.xiaojibazhanshi.customarrows.util.arrows.Laser.createParticleLaser;

public class LaserArrow extends CustomArrow {

    public LaserArrow() {
        super(ArrowFactory.changeTippedColor
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&aLaser Arrow", "laser_arrow",
                                List.of("", "This arrow is a literal laser,", "the laser also deals damage")),
                        Color.GREEN));
    }

    @Override
    public void onShoot(EntityShootBowEvent event, Player shooter) {
        restrictUseIfWeaponIsNot(event, shooter, Material.CROSSBOW);
        event.getProjectile().remove();

        ArrayList<Color> colors = new ArrayList<>(List.of(Color.GREEN, Color.BLUE, Color.LIME, Color.RED, Color.PURPLE));
        Collections.shuffle(colors);

        int laserLength = 50;
        createParticleLaser(shooter.getEyeLocation(), laserLength, colors.getFirst());

        int maxDistance = 50;
        LivingEntity entity = findEntityInSight(shooter, maxDistance, 1.0);

        if (entity == null) return;

        shooter.sendTitle("", color("&7That's a hit!"), 5, 15, 5);
        createParticleLaser(shooter.getEyeLocation(), entity.getEyeLocation(), colors.getFirst());
        entity.damage(5.0);
    }
}
