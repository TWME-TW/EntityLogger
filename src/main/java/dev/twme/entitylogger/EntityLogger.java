package dev.twme.entitylogger;

import dev.twme.entitylogger.command.MainCommands;
import dev.twme.entitylogger.listener.EntitySpawnListener;
import dev.twme.entitylogger.listener.PlayerRightClickListener;
import dev.twme.entitylogger.util.SpawnLogger;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * The main class of the plugin.
 */
public final class EntityLogger extends JavaPlugin {

    /**
     * The instance of the plugin.
     */
    private static EntityLogger instance;

    /**
     * Called when the plugin is enabled.
     */
    @Override
    public void onEnable() {
        instance = this;

        getServer().getPluginManager().registerEvents(new EntitySpawnListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerRightClickListener(), this);

        getCommand("entitylogger").setExecutor(new MainCommands());
        getCommand("entitylogger").setTabCompleter(new MainCommands());

        SpawnLogger.init();
    }

    /**
     * Called when the plugin is disabled.
     */
    @Override
    public void onDisable() {

    }

    /**
     * Gets the instance of the plugin.
     * @return The instance of the plugin.
     */
    public static EntityLogger getInstance() {
        return instance;
    }
}
