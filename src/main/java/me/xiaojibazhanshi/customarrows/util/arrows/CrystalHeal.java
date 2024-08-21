package me.xiaojibazhanshi.customarrows.util.arrows;

import me.xiaojibazhanshi.customarrows.util.Util;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.util.Vector;

import java.util.Map;
import java.util.UUID;

public class CrystalHeal {

    public static void updateHealCrystalMap(Map<UUID, EnderCrystal> crystalMap, Player shooter, Entity arrow) {
        Location arrowLocation = arrow.getLocation();
        UUID uuid = shooter.getUniqueId();

        World world = arrowLocation.getWorld();
        assert world != null;

        if (crystalMap.containsKey(uuid)) crystalMap.get(uuid).remove();

        crystalMap.remove(uuid);
        crystalMap.put(uuid, createACrystal(arrowLocation, arrow.getWorld(), shooter));

        Util.removeCrystalAfter(uuid, 600, crystalMap);
    }

    private static EnderCrystal createACrystal(Location location, World world, Player owner) {
        EnderCrystal crystal = (EnderCrystal) world.spawnEntity(location, EntityType.END_CRYSTAL);
        crystal.setShowingBottom(false);
        crystal.setCustomName("  ");
        crystal.setCustomNameVisible(false);

        String crystalName = Util.color("&4" + owner.getName() + "&c's Heal Crystal");
        Location crystalTextLocation = crystal.getLocation().clone().add(new Vector(0, 2.2, 0));

        TextDisplay display = world.spawn(crystalTextLocation, TextDisplay.class, textDisplay -> {
            textDisplay.setText(crystalName);
            textDisplay.setBillboard(Display.Billboard.CENTER);
        });

        Util.removeEntityAfter(display, 30 * 20);
        return crystal;
    }

}
