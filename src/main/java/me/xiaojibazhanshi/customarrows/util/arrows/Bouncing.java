package me.xiaojibazhanshi.customarrows.util.arrows;

import org.bukkit.block.BlockFace;
import org.bukkit.entity.Arrow;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.UUID;

import static me.xiaojibazhanshi.customarrows.util.GeneralUtil.copyArrow;

public class Bouncing {

    private Bouncing() {
    }

    public static void executeBounce(Arrow originalArrow, BlockFace hitBlockFace, List<UUID> bouncedAlready) {
        Vector velocity = originalArrow.getVelocity();
        originalArrow.remove();

        Vector normal = hitBlockFace.getDirection();
        normal.normalize();

        Vector reflected = velocity.clone().subtract(normal.clone().multiply(2 * velocity.dot(normal)));

        double speedClamp = 0.6;
        Arrow copy = copyArrow(originalArrow, originalArrow.getLocation(), reflected.multiply(speedClamp));

        UUID arrowUUID = copy.getUniqueId();
        bouncedAlready.add(arrowUUID);
    }
}
