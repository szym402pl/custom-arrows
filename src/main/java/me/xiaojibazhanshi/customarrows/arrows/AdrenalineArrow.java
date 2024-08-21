package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

import static me.xiaojibazhanshi.customarrows.util.arrows.AimAssist.provideAimAssist;
import static me.xiaojibazhanshi.customarrows.util.arrows.Homing.findEntityInSight;

public class AdrenalineArrow extends CustomArrow {

    private final int potionDuration = 20 * 20; // 20 sec

    ArrayList<PotionEffect> effects = new ArrayList<>(List.of(
            new PotionEffect(PotionEffectType.SPEED, potionDuration, 1),
            new PotionEffect(PotionEffectType.HASTE, potionDuration, 0),
            new PotionEffect(PotionEffectType.RESISTANCE, potionDuration, 0),
            new PotionEffect(PotionEffectType.NIGHT_VISION, potionDuration, 0)));

    public AdrenalineArrow() {
        super(ArrowFactory.changeTippedColor
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&4Adrenaline Arrow", "adrenaline_arrow",
                                List.of("", "This arrow gives you", "an adrenaline boost")),
                        Color.MAROON));
    }

    @Override
    public void onShoot(EntityShootBowEvent event, Player shooter) {
        int matchingEffects = shooter.getActivePotionEffects().stream().filter(effects::contains).toList().size();

        if (matchingEffects == effects.size()) {
            provideAimAssist(event.getProjectile(),
                    findEntityInSight(shooter, 30, 1.5));
        }

        for (PotionEffect effect : effects) {
            shooter.addPotionEffect(effect);
        }
    }
}
