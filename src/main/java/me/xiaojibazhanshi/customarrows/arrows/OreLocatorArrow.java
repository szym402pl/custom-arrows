package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityShootBowEvent;

import java.util.List;

import static me.xiaojibazhanshi.customarrows.util.arrows.Laser.createParticleLaser;
import static me.xiaojibazhanshi.customarrows.util.arrows.OreLocator.getColorBasedOffOre;
import static me.xiaojibazhanshi.customarrows.util.arrows.OreLocator.getNearestOre;

public class OreLocatorArrow extends CustomArrow {

    public OreLocatorArrow() {
        super(ArrowFactory.changeTippedColor
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&eOre Locator Arrow", "ore_locator_arrow",
                                List.of("", "This arrow is a laser arrow which", "locates the ores in front of you",
                                        "", "NOTE: The laser color depends", "on the found (or not) ore type!")),
                        Color.YELLOW));
    }

    @Override
    public void onShoot(EntityShootBowEvent event, Player shooter) {
        shooter.playSound(shooter, Sound.ENTITY_SHULKER_SHOOT, 1.0F, 1.0F);

        Arrow arrow = (Arrow) event.getProjectile();
        arrow.remove();

        Location location = shooter.getEyeLocation();

        int maxLength = 50;
        Block nearestOre = getNearestOre(location, maxLength);

        if (nearestOre == null) {
            createParticleLaser(shooter.getEyeLocation(), maxLength, Color.WHITE);
            return;
        }

        Material oreMaterial = nearestOre.getType();
        Location nearestOreLocation = nearestOre.getLocation();

        Color color = getColorBasedOffOre(oreMaterial);
        createParticleLaser(location, nearestOreLocation, color);
    }
}
