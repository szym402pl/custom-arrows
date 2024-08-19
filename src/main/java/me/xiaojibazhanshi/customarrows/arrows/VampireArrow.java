package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import me.xiaojibazhanshi.customarrows.util.GeneralUtil;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Bee;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.List;

public class VampireArrow extends CustomArrow {

    public VampireArrow() {
        super(ArrowFactory.changeTippedColor
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&4Vampire Arrow", "vampire_arrow",
                                List.of("", "This arrow will steal", "health from your target")),
                        Color.YELLOW));
    }

    @Override
    public void onHitEntity(EntityDamageByEntityEvent event, Player shooter) {
        if (!(event.getEntity() instanceof LivingEntity)) return;

        double maxHealth = shooter.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
        double finalHealth = Math.min(maxHealth, shooter.getHealth() + event.getDamage() * 0.25);

        shooter.setHealth(finalHealth);
    }


}
