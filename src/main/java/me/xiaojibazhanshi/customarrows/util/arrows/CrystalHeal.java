package me.xiaojibazhanshi.customarrows.util.arrows;

import me.xiaojibazhanshi.customarrows.util.GeneralUtil;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.util.Vector;

import java.util.Map;
import java.util.UUID;

public class CrystalHeal {

    private CrystalHeal() {}

    private static final int DELAY = 20 * 30;

    public static void updateHealCrystalMap(Map<UUID, EnderCrystal> crystalMap, Player shooter, Entity arrow) {
        Location arrowLocation = arrow.getLocation();
        UUID uuid = shooter.getUniqueId();

        World world = arrowLocation.getWorld();
        assert world != null;

        if (crystalMap.containsKey(uuid)) crystalMap.get(uuid).remove();

        crystalMap.remove(uuid);
        crystalMap.put(uuid, createACrystal(arrowLocation, arrow.getWorld(), shooter));

        GeneralUtil.removeCrystalAfter(uuid, DELAY, crystalMap);
    }

    private static EnderCrystal createACrystal(Location location, World world, Player owner) {
        EnderCrystal crystal = (EnderCrystal) world.spawnEntity(location, EntityType.END_CRYSTAL);
        crystal.setShowingBottom(false);
        crystal.setCustomName("  ");
        crystal.setCustomNameVisible(false);

        String crystalName = GeneralUtil.color("&4" + owner.getName() + "&c's Heal Crystal");

        double yAdjustment = 2.2;
        Location crystalTextLocation = crystal.getLocation().clone().add(new Vector(0, yAdjustment, 0));

        TextDisplay display = world.spawn(crystalTextLocation, TextDisplay.class, textDisplay -> {
            textDisplay.setText(crystalName);
            textDisplay.setBillboard(Display.Billboard.CENTER);
        });

        GeneralUtil.removeEntityAfter(display, DELAY);
        return crystal;
    }

}
