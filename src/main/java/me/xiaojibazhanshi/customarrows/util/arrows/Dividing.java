package me.xiaojibazhanshi.customarrows.util.arrows;

import me.xiaojibazhanshi.customarrows.util.GeneralUtil;
import org.apache.commons.math3.geometry.euclidean.threed.Plane;
import org.apache.commons.math3.geometry.euclidean.threed.Rotation;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.bukkit.entity.Arrow;
import org.bukkit.util.Vector;

import java.util.List;

public class Dividing {

    private Dividing() {}

    public static List<Arrow> divideArrow(Arrow arrow) {
        Vector originalDirection = arrow.getVelocity();
        Vector originalLocation = arrow.getLocation().toVector();

        Vector3D direction3D = new Vector3D(originalDirection.getX(), originalDirection.getY(), originalDirection.getZ());
        Vector3D originalLocation3D = new Vector3D(originalLocation.getX(), originalLocation.getY(), originalLocation.getZ());

        Rotation rotation = new Rotation(direction3D, Math.PI);
        Plane plane = new Plane(originalLocation3D, direction3D);

        Vector3D u = plane.getU().normalize().scalarMultiply(0.5);
        Vector3D uRotated = rotation.applyTo(u);

        Vector newVelocity1 = originalDirection.clone()
                .add(new Vector(u.getX(), u.getY(), u.getZ()))
                .normalize()
                .multiply(originalDirection.length());

        Vector newVelocity2 = originalDirection.clone()
                .add(new Vector(uRotated.getX(), uRotated.getY(), uRotated.getZ()))
                .normalize()
                .multiply(originalDirection.length());

        Arrow arrow1 = GeneralUtil.copyArrow(arrow, arrow.getLocation(), newVelocity1);
        Arrow arrow2 = GeneralUtil.copyArrow(arrow, arrow.getLocation(), newVelocity2);

        GeneralUtil.removeArrowAfter(arrow1, 200);
        GeneralUtil.removeArrowAfter(arrow2, 200);

        arrow.setVisibleByDefault(false);
        return List.of(arrow1, arrow2);
    }

}
