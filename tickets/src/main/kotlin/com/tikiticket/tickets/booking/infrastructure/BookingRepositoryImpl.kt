package com.tikiticket.tickets.booking.infrastructure

import com.tikiticket.tickets.booking.domain.Booking
import com.tikiticket.tickets.booking.domain.BookingRepository
import org.springframework.stereotype.Repository

@Repository
class BookingRepositoryImpl (
    private val bookingJpaRepository: BookingJpaRepository
): BookingRepository{
    override fun storeBooking(booking: Booking): Booking {
        return bookingJpaRepository.save<BookingEntity?>(BookingEntity.of(booking)).toDomain()
    }

    override fun findBookingById(id: Long): Booking? {
        TODO("Not yet implemented")
    }

    override fun findBookingByIdForUpdate(id: Long): Booking? {
        TODO("Not yet implemented")
    }

    override fun changeBooking(booking: Booking) {
        TODO("Not yet implemented")
    }
}