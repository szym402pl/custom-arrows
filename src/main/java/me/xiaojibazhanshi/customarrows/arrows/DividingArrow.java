package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import org.apache.commons.math3.geometry.euclidean.threed.Plane;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.util.Vector;
import org.joml.Vector3d;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class DividingArrow extends CustomArrow {

    private Map<UUID, List<Arrow>> activeArrows;

    public DividingArrow() {
        super(ArrowFactory.changeTippedColor
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&fDividing Arrow", "dividing_arrow",
                                List.of("", "This arrow can divide mid-air.", "Quickly shoot before it lands!")),
                        Color.WHITE));

        this.activeArrows = new HashMap<>();
    }

    @Override
    public void onShoot(EntityShootBowEvent event, Player shooter) {
        UUID uuid = shooter.getUniqueId();

        if (!activeArrows.containsKey(uuid)) return;
        event.setCancelled(true);

        List<Arrow> arrows = activeArrows.get(uuid);
    }

    private void divideArrow(Arrow arrow) {
        Vector direction = arrow.getVelocity();
        Vector originalLocation = arrow.getLocation().toVector();

        Vector3D direction3D = new Vector3D(direction.getX(), direction.getY(), direction.getZ());
        Vector3D originalLocation3D = new Vector3D(originalLocation.getX(), originalLocation.getY(), originalLocation.getZ());

        Plane plane = new Plane(originalLocation3D, direction3D);
        Vector3D v = plane.getV();
    }
}
