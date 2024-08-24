package me.xiaojibazhanshi.customarrows.util.arrows;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.entity.ZombieVillager;

import java.util.List;
import java.util.Random;

public class Necromancer {

    private Necromancer() {

    }

    public static void spawnOneOfSelected(List<EntityType> selectedEntities, Location location) {
        if (location.getWorld() == null) return;

        Random randomInstance = new Random();
        int random = randomInstance.nextInt(selectedEntities.size());

        location.getWorld().spawnEntity(location, selectedEntities.get(random));
    }

    public static void convertToUndead(Villager villager) {
        Location location = villager.getLocation();
        World world = villager.getWorld();

        villager.remove();

        ZombieVillager zombieVillager = (ZombieVillager) world.spawnEntity(location, EntityType.ZOMBIE_VILLAGER);

        zombieVillager.setVillagerProfession(villager.getProfession());
        if (villager.isAdult()) zombieVillager.setAdult();
    }

}
