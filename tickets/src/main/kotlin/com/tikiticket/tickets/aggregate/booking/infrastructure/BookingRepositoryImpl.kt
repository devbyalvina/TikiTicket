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
    private val redisMapRepository: RedisMapRepository
): BookingRepository {
    /**
     * 예약
     */
    override fun storeBooking(booking: Booking): Booking {
        return bookingJpaRepository.save<BookingEntity>(BookingEntity.of(booking)).toDomain()
    }

    /**
     * 임시 예약
     */
    override fun storeTemporaryBooking(booking: Booking): Booking {
        val mapName = RedisMapNameType.TemporaryBooking.name
        val bookingMapEntity = BookingMapEntity(Pair(booking.bookerId, booking.seatId), booking, 5, TimeUnit.MINUTES)

        redisMapRepository.save(mapName, bookingMapEntity)
        val savedBooking = redisMapRepository.findByKey(mapName, bookingMapEntity.key)!!

        return savedBooking as Booking
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