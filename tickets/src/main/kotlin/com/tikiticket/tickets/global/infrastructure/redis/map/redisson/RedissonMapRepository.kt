package com.tikiticket.tickets.global.infrastructure.redis.map.redisson

import com.tikiticket.tickets.global.infrastructure.redis.map.MapEntity
import com.tikiticket.tickets.global.infrastructure.redis.map.RedisMapRepository
import org.redisson.api.RMapCache
import org.redisson.api.RedissonClient
import org.springframework.stereotype.Component

@Component
class RedissonMapRepository (
    private val redissonClient: RedissonClient
): RedisMapRepository {
    override fun findByKey(mapName: String, key: Any): Any? {
        val mapCache: RMapCache<Any, Any>? = redissonClient.getMapCache(mapName)
        return mapCache?.get(key)
    }

    override fun save(mapName: String, mapEntity: MapEntity) {
        val mapCache: RMapCache<Any, Any>? = redissonClient.getMapCache(mapName)

        when {
            mapEntity.ttl != null && mapEntity.ttlUnit != null && mapEntity.ttl!! > 0 -> {
                mapCache?.put(mapEntity.key, mapEntity.value, mapEntity.ttl!!, mapEntity.ttlUnit)
            }
            else -> {
                mapCache?.put(mapEntity.key, mapEntity.value)
            }
        }
    }
}