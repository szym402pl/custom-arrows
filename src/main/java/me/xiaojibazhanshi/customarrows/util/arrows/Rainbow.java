package me.xiaojibazhanshi.customarrows.util.arrows;

import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class Rainbow {

    private Rainbow() {

    }

    public static List<Location> filterLocationsToAbove(List<Location> originalLocations, Location center) {
        return originalLocations
                .stream()
                .filter(location -> location.getY() > center.getY())
                .toList();
    }

    public static List<Location> generateVerticalRing(Location center, double radius, double pointDensity,
                                                      double rotationAngle) {
        List<Location> points = new ArrayList<>();

        double step = 2 * Math.PI / (pointDensity * 2 * Math.PI * radius);

        for (double angle = 0.0; angle < 2 * Math.PI; angle += step) {
            double x = radius * Math.cos(angle);
            double y = radius * Math.sin(angle);
            double z = 0;

            Location point = center.clone().add(x, y, z);
            rotateAroundXAxis(point, center, Math.toRadians(rotationAngle));

            points.add(point);
        }

        return points;
    }

    private static void rotateAroundXAxis(Location point, Location center, double angle) {
        double y = point.getY() - center.getY();
        double z = point.getZ() - center.getZ();

        double cosAngle = Math.cos(angle);
        double sinAngle = Math.sin(angle);

        double rotatedY = y * cosAngle - z * sinAngle;
        double rotatedZ = y * sinAngle + z * cosAngle;

        point.setY(center.getY() + rotatedY);
        point.setZ(center.getZ() + rotatedZ);
    }



}
