package com.tikiticket.tickets.booking.application

import com.tikiticket.tickets.appcore.domain.exception.CustomException
import com.tikiticket.tickets.booking.domain.Booking
import com.tikiticket.tickets.booking.domain.BookingError
import com.tikiticket.tickets.booking.domain.BookingService
import com.tikiticket.tickets.booking.domain.BookingStatusType
import com.tikiticket.tickets.concert.domain.ConcertService
import com.tikiticket.tickets.concert.domain.SeatStatusType
import org.springframework.boot.logging.LogLevel
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

/**
 *  API.6 예매
 */
@Component
class MakeBookingUseCase (
    private val bookingService: BookingService,
    private val concertService: ConcertService
) {
    @Transactional
    operator fun invoke(command: MakeBookingCommand): Booking {
        // 콘서트 좌석 상태 변경
        concertService.changeConcertSeatStatus(command.concertSeatId, command.concertId, SeatStatusType.AVAILABLE, SeatStatusType.BOOKED)

        // 콘서트 정보 조회
        val concert = concertService.findConcertWithSeats(command.concertId)
            ?: throw CustomException(LogLevel.INFO, BookingError.CONCERT_NOT_FOUND)

        val concertSeat = concert.seats?.firstOrNull { seat -> seat.id == command.concertSeatId }
            ?: throw CustomException(LogLevel.INFO, BookingError.CONCERT_SEAT_NOT_FOUND)

        // 예매 내역
        val now = LocalDateTime.now()
        val booking = Booking (
            id = 0,
            bookerId = "user123",
            bookingStatus = BookingStatusType.BOOKED,
            expiryDateTime = now.plusMinutes(5),
            concertId = concert.id,
            concertName = concert.concertName,
            artistName = concert.artistName,
            concertDate = concert.concertDate,
            venue = concert.venue,
            seatId = concertSeat.id,
            seatNo = concertSeat.seatNo,
            ticketPrice = concertSeat.ticketPrice,
            createdAt = now,
            updatedAt = now,
        )

        // 예매
        return bookingService.makeBooking(booking)
    }
}