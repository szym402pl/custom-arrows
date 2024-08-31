package me.xiaojibazhanshi.customarrows.util.arrows;

import org.bukkit.entity.*;

import static me.xiaojibazhanshi.customarrows.util.GeneralUtil.color;

public class Taming {

    private Taming() {
    }

    public static boolean tameAnimal(Entity entity, Player player) {
        if (entity == null) return false;
        EntityType type = entity.getType();

        return switch (type) {
            case CAT -> tameCat((Cat) entity, player);
            case WOLF -> tameWolf((Wolf) entity, player);
            case OCELOT -> tameOcelot((Ocelot) entity, player);
            case HORSE -> tameHorse((Horse) entity, player);
            case PARROT -> tameParrot((Parrot) entity, player);
            default -> false;
        };
    }

    private static boolean tameWolf(Wolf wolf, Player player) {
        if (!wolf.isTamed()) {
            wolf.setOwner(player);
            wolf.setTamed(true);
            player.sendTitle("", color("&7You have tamed a wolf!"), 5, 25, 5);
            return true;
        } else {
            player.sendTitle("", color("&7This wolf is already tamed."), 5, 25, 5);
            return false;
        }
    }

    private static boolean tameCat(Cat cat, Player player) {
        if (!cat.isTamed()) {
            cat.setOwner(player);
            cat.setTamed(true);
            player.sendTitle("", color("&7You have tamed an cat!"), 5, 25, 5);
            return true;
        } else {
            player.sendTitle("", color("&7This cat is already tamed."), 5, 25, 5);
            return false;
        }
    }

    private static boolean tameOcelot(Ocelot ocelot, Player player) {
        if (!ocelot.isTrusting()) {
            ocelot.setTrusting(true);
            player.sendTitle("", color("&7You have tamed an ocelot!"), 5, 25, 5);
            return true;
        } else {
            player.sendTitle("", color("&7This ocelot is already tamed."), 5, 25, 5);
            return false;
        }
    }

    private static boolean tameHorse(Horse horse, Player player) {
        if (!horse.isTamed()) {
            horse.setTamed(true);
            horse.setOwner(player);
            player.sendTitle("", color("&7You have tamed a horse!"), 5, 25, 5);
            return true;
        } else {
            player.sendTitle("", color("&7This horse is already tamed."), 5, 25, 5);
            return false;
        }
    }

    private static boolean tameParrot(Parrot parrot, Player player) {
        if (!parrot.isTamed()) {
            parrot.setTamed(true);
            parrot.setOwner(player);
            player.sendTitle("", color("&7You have tamed a parrot!"), 5, 25, 5);
            return true;
        } else {
            player.sendTitle("", color("&7This parrot is already tamed."), 5, 25, 5);
            return false;
        }
    }

}
