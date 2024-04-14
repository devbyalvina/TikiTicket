package com.tikiticket.tickets.booking.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import java.time.LocalDateTime

class BookingServiceTest {
    // Mock Repository 생성
    private val bookingRepository: BookingRepository = mock(BookingRepository::class.java)

    // 테스트 대상 서비스
    private val bookingService = BookingService(bookingRepository)

    @Test
    fun `예매한다`() {
        // Given
        val booking = Booking(
            1L,
            bookerId = "user123",
            BookingStatusType.BOOKED,
            LocalDateTime.now().plusMinutes(5),
            123L,
            "Concert",
            "Artist",
            LocalDateTime.now(),
            "Venue",
            1234L,
            10000L,
            LocalDateTime.now(),
            LocalDateTime.now(),
            LocalDateTime.now()
        )

        // When
        `when`(bookingRepository.saveBooking(booking)).thenReturn(booking)

        val savedBooking = bookingService.makeBooking(booking)

        // Then
        assertEquals(booking, savedBooking)
    }

    @Test
    fun `예매 내역을 조회한다`() {
        // Given
        val bookingId = 1L
        val booking = Booking(
            bookingId,
            bookerId = "user123",
            BookingStatusType.BOOKED,
            LocalDateTime.now().plusMinutes(5),
            123L,
            "Concert",
            "Artist",
            LocalDateTime.now(),
            "Venue",
            1234L,
            10000L,
            LocalDateTime.now(),
            LocalDateTime.now(),
            LocalDateTime.now()
        )

        // When
        `when`(bookingRepository.findBookingById(bookingId)).thenReturn(booking)

        val foundBooking = bookingService.findBooking(bookingId)

        // Then
        assertEquals(booking, foundBooking)
    }

    @Test
    fun `예매 내역을 변경한다`() {
        // Given
        val booking = Booking(
            id = 1L,
            bookerId = "user123",
            BookingStatusType.PAID,
            LocalDateTime.now().plusMinutes(3),
            123L,
            "Concert",
            "Artist",
            LocalDateTime.now(),
            "Venue",
            1234L,
            10000L,
            LocalDateTime.now().minusMinutes(2),
            LocalDateTime.now().minusMinutes(2),
            LocalDateTime.now()
        )

        // When
        bookingService.modifyBooking(booking)

        // Then
        Mockito.verify(bookingRepository, Mockito.times(1)).updateBooking(booking)
    }
}