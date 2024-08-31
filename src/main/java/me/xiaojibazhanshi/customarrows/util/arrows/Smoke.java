package me.xiaojibazhanshi.customarrows.util.arrows;

import me.xiaojibazhanshi.customarrows.CustomArrows;
import me.xiaojibazhanshi.customarrows.runnables.SmokeCloudTask;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;

public class Smoke {

    private Smoke() {

    }

    public static void createProgressiveSmokeCloud(Location location, Color color) {
        // I KNOW THE CODE'S UGLY BUT HEY IT LOOKS AWESOME IN-GAME SO SHUT UP
        int firstSmokeAmount = 400;
        int secondSmokeAmount = 350;
        int thirdSmokeAmount = 250;
        int fourthSmokeAmount = 225;
        int fifthSmokeAmount = 200;

        int period = 1;

        SmokeCloudTask firstIteration = new SmokeCloudTask(firstSmokeAmount, location, 2, 10, color);
        SmokeCloudTask secondIteration = new SmokeCloudTask(secondSmokeAmount, location, 3, 15, color);
        SmokeCloudTask thirdIteration = new SmokeCloudTask(thirdSmokeAmount, location, 4, 20, color);
        SmokeCloudTask fourthIteration = new SmokeCloudTask(fourthSmokeAmount, location, 4, 25, color);
        SmokeCloudTask fifthIteration = new SmokeCloudTask(fifthSmokeAmount, location, 4, 25, color);

        Bukkit.getScheduler().runTaskTimer(CustomArrows.getInstance(), firstIteration, 2, period);
        Bukkit.getScheduler().runTaskTimer(CustomArrows.getInstance(), secondIteration, firstSmokeAmount / 8, period);
        Bukkit.getScheduler().runTaskTimer(CustomArrows.getInstance(), thirdIteration, firstSmokeAmount / 5, period);
        Bukkit.getScheduler().runTaskTimer(CustomArrows.getInstance(), fourthIteration, firstSmokeAmount / 3, period);
        Bukkit.getScheduler().runTaskTimer(CustomArrows.getInstance(), fifthIteration, firstSmokeAmount / 2, period);
    }

}
