package dev.twme.entitylogger.util;

import dev.twme.entitylogger.EntityLogger;
import org.bukkit.NamespacedKey;

/**
 * Persistent keys used by the plugin.
 */
public class PersistentKeys {
    /**
     * The closest player to the entity.
     */
    public static final NamespacedKey ClosestPlayerID = new NamespacedKey(EntityLogger.getInstance(), "ClosestPlayerID");

    /**
     * The UUID of the closest player to the entity.
     */
    public static final NamespacedKey ClosestPlayerUUID = new NamespacedKey(EntityLogger.getInstance(), "ClosestPlayerUUID");

    /**
     * The distance between the entity and the closest player.
     */
    public static final NamespacedKey ClosestPlayerDistance = new NamespacedKey(EntityLogger.getInstance(), "ClosestPlayerDistance");

    /**
     * The time the entity spawned.
     */
    public static final NamespacedKey EntitySpawnTime = new NamespacedKey(EntityLogger.getInstance(), "EntitySpawnTime");

    /**
     * The time the entity despawned.
     */
    public static final NamespacedKey CheckTool = new NamespacedKey(EntityLogger.getInstance(), "CheckTool");
}
