package com.tikiticket.tickets.booking.application

import com.tikiticket.tickets.booking.application.exception.BookingError
import com.tikiticket.tickets.booking.application.exception.BookingException
import com.tikiticket.tickets.booking.domain.Booking
import com.tikiticket.tickets.booking.domain.BookingService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

/**
 *  API.7 예매 내역 조회
 */
@Component
class GetBookingUseCase (
    private val bookingService: BookingService,
) {
    @Transactional
    operator fun invoke(bookingId: Long): Booking {
        val booking = bookingService.findBooking(bookingId)
            ?: throw BookingException(BookingError.BOOKING_NOT_FOUND)

        return booking
    }
}