package dev.twme.entitylogger.record.pool;

import dev.twme.entitylogger.record.data.EntityNearSpawnPlayerData;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * A pool of spawn data for entities near players.
 */
public class EntityNearPlayerSpawnPool {
    /*
     * player spawn pools
     */
    private static final HashMap<UUID, EntityNearPlayerSpawnPool> pools = new HashMap<>();

    /*
     * The spawn data for the pool.
     */
    private final Player player;

    private final List<EntityNearSpawnPlayerData> spawnData = new ArrayList<>();
    private final Map<EntityType,Integer> entityCount = new HashMap<>();
    private long oldestSpawnTime = Long.MAX_VALUE;
    private long lastSpawnTime = 0;
    private long newestSpawnTimeDifference = 0;
    private int totalCount = 0;

    /**
     * Creates a new instance of the EntityNearPlayerSpawnPool class.
     * @param player The player for the pool.
     */
    public EntityNearPlayerSpawnPool(Player player) {
        this.player = player;
    }

    /**
     * Adds a spawn data to the pool.
     * @param data The spawn data to add.
     */
    public static void addToPool(EntityNearSpawnPlayerData data) {
        var uuid = data.closestPlayer.getUniqueId();
        if (!pools.containsKey(uuid)) {
            pools.put(uuid, new EntityNearPlayerSpawnPool(data.closestPlayer));
        }
        pools.get(uuid).addSpawnData(data);
    }

    /**
     * Gets the player spawn pools.
     * @return The player spawn pools.
     */
    public static Map<UUID, EntityNearPlayerSpawnPool> getPools() {
        return pools;
    }

    /**
     * Removes a player spawn pool.
     * @param uuid The UUID of the player to remove the pool for.
     */
    public static void removePool(UUID uuid) {
        pools.remove(uuid);
    }

    /**
     * Gets the player for the pool.
     * @return The player for the pool.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Adds a spawn data to the pool.
     * @param data The spawn data to add.
     */
    private void addSpawnData(EntityNearSpawnPlayerData data) {
        spawnData.add(data);
        entityCount.put(data.entity.getType(), entityCount.getOrDefault(data.entity.getType(), 0) + 1);
        oldestSpawnTime = Math.min(data.spawnTime, oldestSpawnTime);
        if (lastSpawnTime == 0) {
            lastSpawnTime = data.spawnTime;
        }
        newestSpawnTimeDifference = data.spawnTime - lastSpawnTime;
        lastSpawnTime = data.spawnTime;
        totalCount++;
    }

    /**
     * Gets the spawn data in the pool.
     * @return The spawn data in the pool.
     */
    public List<EntityNearSpawnPlayerData> getSpawnData() {
        return spawnData;
    }

    /**
     * Gets the entity count.
     * @return The entity count.
     */
    public int getEntityCount(EntityType type) {
        return entityCount.get(type);
    }

    /**
     * Gets the entity types in the pool.
     * @return The entity types in the pool.
     */
    public Set<EntityType> getEntityTypes() {
        return entityCount.keySet();
    }

    /**
     * Gets the difference between the oldest and newest spawn times.
     * @return The difference between the oldest and newest spawn times.
     */
    public long getSpawnTimeDifference() {
        return lastSpawnTime - oldestSpawnTime;
    }

    /**
     * Gets the newest spawn time difference.
     * @return The newest spawn time difference.
     */
    public long getNewestSpawnTimeDifference() {
        return newestSpawnTimeDifference;
    }

    /**
     * Gets the oldest spawn time.
     * @return The oldest spawn time.
     */
    public long getOldestSpawnTime() {
        return oldestSpawnTime;
    }

    /**
     * Gets the total count of entities spawned.
     * @return The total count of entities spawned.
     */
    public int getTotalCount() {
        return totalCount;
    }

    /**
     * Gets the average distance between the entities and the player.
     * @return The average distance between the entities and the player.
     */
    public double getAverageDistance() {
        return spawnData.stream().mapToDouble(data -> data.distance).average().orElse(0);
    }

    /**
     * Gets the average location of the entities.
     * @return The average location of the entities.
     */
    public Location getAverageLocation() {
        var x = spawnData.stream().mapToDouble(data -> data.spawnLocation.getX()).average().orElse(0);
        var y = spawnData.stream().mapToDouble(data -> data.spawnLocation.getY()).average().orElse(0);
        var z = spawnData.stream().mapToDouble(data -> data.spawnLocation.getZ()).average().orElse(0);
        return new Location(spawnData.get(0).spawnLocation.getWorld(), x, y, z);
    }
}
