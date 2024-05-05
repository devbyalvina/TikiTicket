package com.tikiticket.tickets.aggregate.booking.application

import com.tikiticket.tickets.aggregate.booking.domain.Booking
import com.tikiticket.tickets.aggregate.booking.domain.BookingError
import com.tikiticket.tickets.aggregate.booking.domain.BookingService
import com.tikiticket.tickets.global.domain.exception.CustomException
import org.springframework.boot.logging.LogLevel
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
            ?: throw CustomException(LogLevel.INFO, BookingError.BOOKING_NOT_FOUND)

        return booking
    }
}