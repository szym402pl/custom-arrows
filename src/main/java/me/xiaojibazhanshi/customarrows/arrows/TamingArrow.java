package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

import static me.xiaojibazhanshi.customarrows.util.GeneralUtil.color;
import static me.xiaojibazhanshi.customarrows.util.arrows.AimAssist.provideAimAssist;
import static me.xiaojibazhanshi.customarrows.util.arrows.Homing.findEntityInSight;
import static me.xiaojibazhanshi.customarrows.util.arrows.Taming.tameAnimal;

public class TamingArrow extends CustomArrow {

    public TamingArrow() {
        super(ArrowFactory.changeTippedColor
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&aTaming Arrow", "taming_arrow",
                                List.of("", "This arrow lets you", "tame any animal")),
                        Color.LIME));
    }

    @Override
    public void onHitEntity(EntityDamageByEntityEvent event, Player shooter) {
        Entity entity = event.getEntity();

        if (!(entity instanceof LivingEntity livingEntity)) {
            shooter.sendTitle("", color("&7I can't tame this..."), 5, 25 ,5);
            return;
        }

        event.setCancelled(true);

        Arrow arrow = (Arrow) event.getDamager();
        arrow.remove();

        tameAnimal(livingEntity, shooter);
    }

    @Override
    public void onHitBlock(ProjectileHitEvent event, Player shooter) {
        shooter.sendTitle("", color("&7I can't tame a block..."), 5, 25 ,5);
    }
}
