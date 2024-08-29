package me.xiaojibazhanshi.customarrows.runnables;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Slime;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import javax.annotation.Nullable;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

import static me.xiaojibazhanshi.customarrows.util.GeneralUtil.removeEntityAfter;

public class SlimeArmyTask implements Consumer<BukkitTask> {

    private final int slimeAmount;

    private final LivingEntity trackedEntity;
    private final World world;

    private final Location customLocation;

    private int counter = 1;

    public SlimeArmyTask(LivingEntity trackedEntity, int slimeAmount, @Nullable Location customLocation) {
        this.trackedEntity = trackedEntity;
        this.world = trackedEntity.getWorld();

        this.customLocation = customLocation;
        this.slimeAmount = slimeAmount;
    }

    @Override
    public void accept(BukkitTask bukkitTask) {
        if (counter >= slimeAmount || trackedEntity.isDead()) bukkitTask.cancel();

        int minSize = 1;
        int maxSize = 4;

        ThreadLocalRandom random = ThreadLocalRandom.current();
        int randomSize = random.nextInt(minSize, maxSize + 1); // + 1 because of exclusivity of nextInt bound

        Vector yAdjustment = new Vector(0, 1.5, 0);

        Location location = trackedEntity.getEyeLocation().clone();
        if (customLocation != null) location = customLocation;

        location.add(yAdjustment);

        Slime slime = world.spawn(location, Slime.class);

        slime.setSize(randomSize);
        slime.setAbsorptionAmount(randomSize);
        slime.setTarget(trackedEntity);

        removeEntityAfter(slime, 30 * 20);

        counter++;
    }

}
