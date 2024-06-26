package com.tikiticket.tickets.aggregate.booking.application

import com.tikiticket.tickets.aggregate.booking.application.GetBookingUseCase
import com.tikiticket.tickets.global.domain.exception.CustomException
import com.tikiticket.tickets.aggregate.booking.domain.Booking
import com.tikiticket.tickets.aggregate.booking.domain.BookingError
import com.tikiticket.tickets.aggregate.booking.domain.BookingService
import com.tikiticket.tickets.aggregate.booking.domain.BookingStatusType
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDateTime

class GetBookingUseCaseTest {
    @Test
    fun `예매 내역을 조회한다`() {
        // Given
        val bookingId = 1L
        val bookingService = mockk<BookingService>()
        val getBookingUseCase = GetBookingUseCase(bookingService)
        val booking = Booking(
            id = bookingId,
            bookerId = "booker123",
            bookingStatus = BookingStatusType.BOOKED,
            expiryDateTime = LocalDateTime.now().plusMinutes(5),
            concertId = 1L,
            concertName = "Concert",
            artistName = "Artist",
            concertDate = LocalDateTime.now(),
            venue = "Venue",
            seatId = 1L,
            seatNo = 1L,
            ticketPrice = 10000L,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )

        every { bookingService.findBooking(bookingId) } returns booking

        // When
        val result = getBookingUseCase(bookingId)

        // Then
        assertEquals(booking, result)
    }

    @Test
    fun `예매 내역이 없으면 예외를 반환한다`() {
        // Given
        val bookingId = 1L
        val bookingService = mockk<BookingService>()
        val getBookingUseCase = GetBookingUseCase(bookingService)

        every { bookingService.findBooking(bookingId) } returns null

        // When & Then
        assertThrows<CustomException> { getBookingUseCase(bookingId) }.also {
            assertEquals(BookingError.BOOKING_NOT_FOUND, it.customError)
        }
    }
}