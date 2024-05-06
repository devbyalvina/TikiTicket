package com.tikiticket.tickets.aggregate.booking.infrastructure

import com.tikiticket.tickets.aggregate.booking.domain.Booking
import com.tikiticket.tickets.global.infrastructure.redis.map.MapEntity
import java.util.concurrent.TimeUnit

data class BookingMapEntity(
    override val key: Pair<String, Long>,
    override val value: Booking,
    override val ttl: Long,
    override val ttlUnit: TimeUnit
): MapEntity<Pair<String, Long>, Booking>
