package me.kruase.restarter_plugin

import java.io.File
import org.bukkit.plugin.Plugin
import org.bukkit.configuration.file.FileConfiguration


data class RestarterConfig(private val config: FileConfiguration) {
    val restartTimeout = config.getLong("restart-timeout")
}

fun getRestarterConfig(plugin: Plugin): RestarterConfig {
    return try {
        plugin.saveDefaultConfig()
        plugin.reloadConfig()
        RestarterConfig(plugin.config)
    } catch (e: Exception) {
        when (e) {
            is NullPointerException, is NumberFormatException -> {
                newDefaultConfig(plugin)
                RestarterConfig(plugin.config)
            }
            else -> throw e
        }
    }.also { plugin.logger.info("Config loaded!") }
}

fun newDefaultConfig(plugin: Plugin) {
    plugin.logger.severe("Invalid Restarter config detected! Creating a new one (default)...")
    File(plugin.dataFolder, "config.yml").renameTo(
        File(plugin.dataFolder, "config.yml.old-${System.currentTimeMillis()}")
    )
    plugin.saveDefaultConfig()
    plugin.reloadConfig()
    plugin.logger.info("New (default) config created!")
}
