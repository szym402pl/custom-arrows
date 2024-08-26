package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

import static me.xiaojibazhanshi.customarrows.util.GeneralUtil.removeEntityAfter;
import static me.xiaojibazhanshi.customarrows.util.arrows.AimAssist.provideAimAssist;
import static me.xiaojibazhanshi.customarrows.util.arrows.Homing.findEntityInSight;

public class CreeperArrow extends CustomArrow {

    public CreeperArrow() {
        super(ArrowFactory.changeTippedColor
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&aCreeper Arrow", "creeper_arrow",
                                List.of("", "This arrow launches a creeper", "in the looked at direction")),
                        Color.LIME));
    }

    @Override
    public void onShoot(EntityShootBowEvent event, Player shooter) {
        Entity arrow = event.getProjectile();
        Location arrowLocation = arrow.getLocation();
        arrow.remove();

        World world = shooter.getWorld();

        Creeper creeper = world.spawn(arrowLocation, Creeper.class);

        creeper.setPowered(true);
        creeper.setVelocity(arrow.getVelocity());

        removeEntityAfter(creeper, 20 * 20); // you don't really want to clutter the map
    }
}
