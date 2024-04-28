package com.tikiticket.tickets.booking.application

import com.tikiticket.tickets.appcore.domain.exception.CustomException
import com.tikiticket.tickets.booking.domain.Booking
import com.tikiticket.tickets.booking.domain.BookingError
import com.tikiticket.tickets.booking.domain.BookingService
import com.tikiticket.tickets.booking.domain.BookingStatusType
import com.tikiticket.tickets.concert.domain.Concert
import com.tikiticket.tickets.concert.domain.ConcertSeat
import com.tikiticket.tickets.concert.domain.ConcertService
import com.tikiticket.tickets.concert.domain.SeatStatusType
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class MakeBookingUseCaseTest {
    @Test
    fun `예매에 성공한다`() {
        // Given
        val userId = "user01"
        val seatId = 1L
        val concertId = 1L
        val seatNo = 1L
        val bookingService = mockk<BookingService>()
        val concertService = mockk<ConcertService> {
            every { findConcert(any()) } returns Concert(concertId, "Concert", "Artist", LocalDateTime.now(), "Venue", LocalDateTime.now(), LocalDateTime.now(), emptyList())
            every { findConcertSeatForUpdate(any(), any()) } returns ConcertSeat(seatId, concertId, seatNo, SeatStatusType.AVAILABLE, 10000L, LocalDateTime.now(), LocalDateTime.now())
            every { updateConcertSeat(any()) } returns Unit
        }
        val makeBookingUseCase = MakeBookingUseCase(bookingService, concertService)
        val command = MakeBookingCommand(userId, concertId, seatNo)
        val expectedBooking = Booking(1, bookerId = "user123", BookingStatusType.BOOKED, LocalDateTime.now().plusMinutes(5), concertId, "Concert", "Artist", LocalDateTime.now(), "Venue", seatNo, 10000L, LocalDateTime.now(), LocalDateTime.now())

        // Mock BookingService.makeBooking() 메서드에 대한 응답 정의
        every { bookingService.makeBooking(any()) } returns expectedBooking

        // When
        val booking = makeBookingUseCase(command)

        // Then
        verify(exactly = 1) { concertService.findConcert(concertId) }
        verify(exactly = 1) { concertService.findConcertSeatForUpdate(concertId, seatNo) }
        verify(exactly = 1) { concertService.updateConcertSeat(any()) }
        verify(exactly = 1) { bookingService.makeBooking(any()) }
        assert(booking == expectedBooking)
    }

    @Test
    fun `콘서트가 없는 경우 예외를 반환한다`() {
        // Given
        val userId = "user01"
        val concertId = 1L
        val seatNo = 1L
        val bookingService = mockk<BookingService>()
        val concertService = mockk<ConcertService> {
            every { findConcert(any()) } returns null
        }
        val makeBookingUseCase = MakeBookingUseCase(bookingService, concertService)
        val command = MakeBookingCommand(userId, concertId, seatNo)

        // When & Then
        val exception = org.junit.jupiter.api.assertThrows<CustomException> {
            makeBookingUseCase(command)
        }
        assert(exception.customError == BookingError.CONCERT_NOT_FOUND)
        verify(exactly = 1) { concertService.findConcert(concertId) }
        verify(exactly = 0) { concertService.findConcertSeatForUpdate(any(), any()) }
        verify(exactly = 0) { concertService.updateConcertSeat(any()) }
        verify(exactly = 0) { bookingService.makeBooking(any()) }
    }

    @Test
    fun `콘서트 좌석이 없는 경우 예외를 반환한다`() {
        // Given
        val userId = "user01"
        val concertId = 1L
        val seatNo = 1L
        val bookingService = mockk<BookingService>()
        val concertService = mockk<ConcertService> {
            every { findConcert(any()) } returns Concert(concertId, "Concert", "Artist", LocalDateTime.now(), "Venue", LocalDateTime.now(), LocalDateTime.now(), emptyList())
            every { findConcertSeatForUpdate(any(), any()) } returns null
        }
        val makeBookingUseCase = MakeBookingUseCase(bookingService, concertService)
        val command = MakeBookingCommand(userId, concertId, seatNo)

        // When & Then
        val exception = org.junit.jupiter.api.assertThrows<CustomException> {
            makeBookingUseCase(command)
        }
        assert(exception.customError == BookingError.CONCERT_SEAT_NOT_FOUND)
        verify(exactly = 1) { concertService.findConcert(concertId) }
        verify(exactly = 1) { concertService.findConcertSeatForUpdate(concertId, seatNo) }
        verify(exactly = 0) { concertService.updateConcertSeat(any()) }
        verify(exactly = 0) { bookingService.makeBooking(any()) }
    }

    @Test
    fun `좌석이 이미 예매된 경우 예외를 반환한다`() {
        // Given
        val userId = "user01"
        val seatId = 1L
        val concertId = 1L
        val seatNo = 1L
        val bookingService = mockk<BookingService>()
        val concertService = mockk<ConcertService> {
            every { findConcert(any()) } returns Concert(concertId, "Concert", "Artist", LocalDateTime.now(), "Venue", LocalDateTime.now(), LocalDateTime.now(), listOf(
                ConcertSeat(seatId, concertId, seatNo, SeatStatusType.BOOKED, 10000L, LocalDateTime.now(), LocalDateTime.now())
            ))
            every { findConcertSeatForUpdate(any(), any()) } returns ConcertSeat(seatId, concertId, seatNo, SeatStatusType.BOOKED, 10000L, LocalDateTime.now(), LocalDateTime.now())
        }
        val makeBookingUseCase = MakeBookingUseCase(bookingService, concertService)
        val command = MakeBookingCommand(userId, concertId, seatNo)

        // When & Then
        val exception = org.junit.jupiter.api.assertThrows<CustomException> {
            makeBookingUseCase(command)
        }
        assert(exception.customError == BookingError.SEAT_NOT_AVAILABLE)
        verify(exactly = 1) { concertService.findConcert(concertId) }
        verify(exactly = 1) { concertService.findConcertSeatForUpdate(concertId, seatNo) }
        verify(exactly = 0) { concertService.updateConcertSeat(any()) }
        verify(exactly = 0) { bookingService.makeBooking(any()) }
    }
}