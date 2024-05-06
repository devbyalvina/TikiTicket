package com.tikiticket.tickets.aggregate.booking.infrastructure

import com.tikiticket.tickets.aggregate.booking.domain.Booking
import com.tikiticket.tickets.aggregate.booking.domain.BookingRepository
import com.tikiticket.tickets.global.infrastructure.redis.map.RedisMapNameType
import com.tikiticket.tickets.global.infrastructure.redis.map.RedisMapRepository
import org.springframework.stereotype.Repository
import java.util.concurrent.TimeUnit

@Repository
class BookingRepositoryImpl (
    private val bookingJpaRepository: BookingJpaRepository,
    private val bookingRedisMapRepository: RedisMapRepository<Pair<String, Long>, Booking>
): BookingRepository {
    /**
     * 예약
     */
    override fun storeBooking(booking: Booking): Booking {
        return bookingJpaRepository.save<BookingEntity>(BookingEntity.of(booking)).toDomain()
    }

    /**
     * 예약 In Memory
     */
    override fun storeBookingInMemory(booking: Booking): Booking {
        val mapName = RedisMapNameType.Booking.name
        val bookingMapEntity = BookingMapEntity(Pair(booking.bookerId, booking.seatId), booking, 5, TimeUnit.MINUTES)

        bookingRedisMapRepository.save(mapName, bookingMapEntity)
        val savedBooking = bookingRedisMapRepository.findByKey(mapName, bookingMapEntity.key)!!

        return savedBooking
    }

    /**
     * 예약 내역 조회
     */
    override fun findBookingById(id: Long): Booking? {
        TODO("Not yet implemented")
    }

    /**
     * 예약 내역 조회 For Update
     */
    override fun findBookingByIdForUpdate(id: Long): Booking? {
        TODO("Not yet implemented")
    }

    /**
     * 예약 내역 변경
     */
    override fun changeBooking(booking: Booking) {
        TODO("Not yet implemented")
    }
}