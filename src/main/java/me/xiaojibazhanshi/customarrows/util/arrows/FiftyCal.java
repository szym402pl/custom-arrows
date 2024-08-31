package me.xiaojibazhanshi.customarrows.util.arrows;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class FiftyCal {

    private FiftyCal() {}

    public static void applyEffectsIfShotRapidly(Player shooter) {
        PotionEffect existingNausea = shooter.getPotionEffect(PotionEffectType.NAUSEA);
        int amplifier = existingNausea != null ? existingNausea.getAmplifier() + 1 : 0;

        PotionEffect nausea = new PotionEffect(PotionEffectType.NAUSEA, 4 * 20, amplifier, true);
        shooter.addPotionEffect(nausea);

        if (amplifier < 3) return;
        PotionEffect blindness = new PotionEffect(PotionEffectType.BLINDNESS, 60, 1, true);
        double damage = amplifier * 1.25;

        shooter.addPotionEffect(blindness);
        shooter.damage(damage);
    }

}
