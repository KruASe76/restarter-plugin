package me.kruase.restarter_plugin

import org.bukkit.event.Listener
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent


class RestarterEvents : Listener {
    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        Restarter.mainTaskId?.let { Restarter.instance.server.scheduler.cancelTask(it) }
    }

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        Restarter.instance.server.scheduler.scheduleSyncDelayedTask(
            Restarter.instance,
            {
                if (Restarter.instance.server.onlinePlayers.isNotEmpty()) return@scheduleSyncDelayedTask
                Restarter.scheduleRestart()
            },
            1
        )
    }
}
