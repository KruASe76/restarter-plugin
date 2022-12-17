package me.kruase.restarter_plugin

import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.Bukkit


class Restarter : JavaPlugin() {
    companion object {
        lateinit var instance: Restarter
        lateinit var userConfig: RestarterConfig

        var mainTaskId: Int? = null

        fun scheduleRestart() {
            mainTaskId = instance.server.scheduler.scheduleSyncDelayedTask(
                instance,
                {
                    instance.logger.info("Restarting...")
                    Bukkit.spigot().restart()
                },
                userConfig.restartTimeout * 20
            )
        }
    }

    override fun onEnable() {
        instance = this
        userConfig = getRestarterConfig(instance)

        server.pluginManager.registerEvents(RestarterEvents(), instance)

        scheduleRestart()
    }
}
