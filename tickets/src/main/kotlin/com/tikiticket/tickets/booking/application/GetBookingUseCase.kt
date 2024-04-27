package com.tikiticket.tickets.booking.application

import com.tikiticket.tickets.appcore.application.exception.CustomException
import com.tikiticket.tickets.appcore.application.log.LogLevel
import com.tikiticket.tickets.booking.domain.Booking
import com.tikiticket.tickets.booking.domain.BookingError
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
            ?: throw CustomException(LogLevel.INFO, BookingError.BOOKING_NOT_FOUND)

        return booking
    }
}