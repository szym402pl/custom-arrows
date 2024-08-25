package me.xiaojibazhanshi.customarrows.util.arrows;

import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Arrow;
import org.bukkit.util.Vector;

import java.util.Map;

public class TimeFreeze {

    private TimeFreeze() {

    }

    public static void freezeInPlace(Arrow arrow, Map<Arrow, Vector> frozenArrows) {
        frozenArrows.put(arrow, arrow.getVelocity());

        arrow.setVelocity(arrow.getVelocity().clone().multiply(0.0015));
        arrow.setGravity(false);
        arrow.setInvulnerable(true);
        arrow.setPickupStatus(AbstractArrow.PickupStatus.DISALLOWED);
    }

    public static void unFreeze(Arrow arrow, Map<Arrow, Vector> frozenArrows, boolean motionLess) {
        if (arrow.isDead() || arrow.isOnGround()) return;

        Vector originalVelocity = frozenArrows.get(arrow);

        arrow.setVelocity(motionLess ? originalVelocity.multiply(0.002) : originalVelocity);
        arrow.setGravity(true);
        arrow.setInvulnerable(false);
        arrow.setPickupStatus(AbstractArrow.PickupStatus.ALLOWED);
    }

}
