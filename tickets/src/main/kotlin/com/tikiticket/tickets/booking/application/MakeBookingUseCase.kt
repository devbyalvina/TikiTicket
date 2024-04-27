package com.tikiticket.tickets.booking.application

import com.tikiticket.tickets.appcore.application.exception.CustomException
import com.tikiticket.tickets.appcore.application.log.LogLevel
import com.tikiticket.tickets.booking.domain.Booking
import com.tikiticket.tickets.booking.domain.BookingError
import com.tikiticket.tickets.booking.domain.BookingService
import com.tikiticket.tickets.booking.domain.BookingStatusType
import com.tikiticket.tickets.concert.domain.ConcertService
import com.tikiticket.tickets.concert.domain.SeatStatusType
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
        // 콘서트 정보 조회
        val concert = concertService.findConcert(command.concertId)
            ?: throw CustomException(LogLevel.INFO, BookingError.CONCERT_NOT_FOUND)

        // 좌석 정보 조회 for update
        val concertSeat = concertService.findConcertSeatForUpdate(command.concertId, command.seatNo)
            ?: throw CustomException(LogLevel.INFO, BookingError.CONCERT_SEAT_NOT_FOUND)

        // 좌석상태체크
        if (concertSeat.seatStatus != SeatStatusType.AVAILABLE) {
            throw CustomException(LogLevel.INFO, BookingError.SEAT_NOT_AVAILABLE)
        }

        // 좌석 정보 업데이트
        concertService.updateConcertSeat(concertSeat.copy(seatStatus = SeatStatusType.BOOKED))

        // 객체 만들기
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
            seatNo = concertSeat.seatNo,
            ticketPrice = concertSeat.ticketPrice,
            createdAt = now,
            updatedAt = now,
        )

        // 저장
        return bookingService.makeBooking(booking)
    }
}