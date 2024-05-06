package com.tikiticket.tickets.global.infrastructure.redis.map

interface RedisMapRepository {
    fun findByKey(mapName: String, key: Any): Any?
    fun save(mapName: String, mapEntity: MapEntity)
}