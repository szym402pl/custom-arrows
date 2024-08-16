package me.xiaojibazhanshi.customarrows.listeners;

import me.xiaojibazhanshi.customarrows.managers.ArrowManager;
import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.GeneralUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.List;

public class PlayerLeaveListener implements Listener {

    private final ArrowManager arrowManager;

    public PlayerLeaveListener(ArrowManager arrowManager) {
        this.arrowManager = arrowManager;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        List<CustomArrow> arrowsExecutingPQE = new ArrayList<>(List.of(
                retrieveCustomArrow("stealth_arrow")
        ));

        for (CustomArrow customArrow : arrowsExecutingPQE) {
            if (customArrow == null) return;

            customArrow.onPlayerLeave(event, event.getPlayer());
        }
    }

    private CustomArrow retrieveCustomArrow(String id) {
        return arrowManager.getCustomArrows().get(GeneralUtil.createStringNSKey(id));
    }

}
