package dev.twme.entitylogger.util;

import dev.twme.entitylogger.EntityLogger;
import dev.twme.entitylogger.record.pool.EntityNearPlayerSpawnPool;
import dev.twme.entitylogger.record.pool.EntitySpawnPool;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.logging.Logger;

/**
 * Logs entity spawn data.
 */
public class SpawnLogger implements Runnable{

    /**
     * The instance of the EntityLogger class.
     */
    private static final EntityLogger instance = EntityLogger.getInstance();

    /**
     * The logger for the plugin.
     */
    private static final Logger logger = instance.getLogger();

    /**
     * The interval in ticks to log spawn data.
     */
    private static final int TASK_INTERVAL_TICK = 10;

    /**
     * Initializes the spawn logger.
     */
    public static void init() {
        instance.getServer().getScheduler().runTaskTimer(instance, new SpawnLogger(), 0, TASK_INTERVAL_TICK);
    }

    /**
     * Logs the spawn data for a pool.
     * @param pool The pool to log the spawn data for.
     */
    public static void logSpawn(EntityNearPlayerSpawnPool pool) {

        if (pool.getNewestSpawnTimeDifference() < 1000 && (System.currentTimeMillis() - pool.getOldestSpawnTime()) < 2000) {
            return;
        }

        Player player = pool.getPlayer();

        Set<EntityType> types = pool.getEntityTypes();

        StringBuilder message = new StringBuilder();
        message.append("Player ").append(player.getName()).append(" spawned ");
        for (EntityType type : types) {
            message.append(type).append(" x").append(pool.getEntityCount(type)).append(", ");
        }
        message.append(" in ").append(String.format("%.2f", pool.getSpawnTimeDifference() / 1000F)).append(" seconds.");
        message.append(" Average distance: ").append(String.format("%.2f", pool.getAverageDistance())).append(" blocks.");
        message.append(" Average location: ").append(pool.getAverageLocation().getBlockX()).append(", ").append(pool.getAverageLocation().getBlockY()).append(", ").append(pool.getAverageLocation().getBlockZ());
        logger.info(message.toString());

        EntityNearPlayerSpawnPool.removePool(pool.getPlayer().getUniqueId());
    }

    /**
     * Logs the spawn data for a pool.
     * @param pool The pool to log the spawn data for.
     */
    public static void logSpawn(EntitySpawnPool pool) {
        if (pool.getNewestSpawnTimeDifference() < 1000 && (System.currentTimeMillis() - pool.getOldestSpawnTime()) < 2000) {
            return;
        }

        EntityType type = pool.getType();

        String message = "Entity " + type + " spawned " +
                "x" + pool.getCount() +
                " in " + String.format("%.2f", pool.getSpawnTimeDifference() / 1000F) + " seconds";
        logger.info(message);
        EntitySpawnPool.removePool(pool.getType());
    }

    /**
     * Logs the spawn data for all pools.
     */
    private static void log(){
        if (!EntityNearPlayerSpawnPool.getPools().isEmpty()) {
            for (EntityNearPlayerSpawnPool pool : EntityNearPlayerSpawnPool.getPools().values()) {
                logSpawn(pool);
            }
        }
        if (!EntitySpawnPool.getPools().isEmpty()) {
            for (EntitySpawnPool pool : EntitySpawnPool.getPools().values()) {
                logSpawn(pool);
            }
        }
    }

    /**
     * The task to log spawn data.
     */
    @Override
    public void run() {
        log();
    }
}
