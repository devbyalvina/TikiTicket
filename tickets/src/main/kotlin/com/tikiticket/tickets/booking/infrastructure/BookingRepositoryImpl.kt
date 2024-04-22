package com.tikiticket.tickets.booking.infrastructure

import com.tikiticket.tickets.booking.domain.Booking
import com.tikiticket.tickets.booking.domain.BookingRepository
import org.springframework.stereotype.Repository

@Repository
class BookingRepositoryImpl: BookingRepository {
    override fun saveBooking(booking: Booking): Booking {
        TODO("Not yet implemented")
    }

    override fun findBookingById(id: Long): Booking? {
        TODO("Not yet implemented")
    }

    override fun findBookingByIdForUpdate(id: Long): Booking? {
        TODO("Not yet implemented")
    }

    override fun updateBooking(booking: Booking) {
        TODO("Not yet implemented")
    }
}