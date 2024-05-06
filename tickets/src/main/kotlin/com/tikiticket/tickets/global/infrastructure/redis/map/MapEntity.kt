package com.tikiticket.tickets.global.infrastructure.redis.map

import java.util.concurrent.TimeUnit

interface MapEntity {
    val key: Any
    val value: Any
    val ttl: Long?
    val ttlUnit: TimeUnit?
}