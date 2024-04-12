package com.tikiticket.tickets.booking.domain

import org.springframework.stereotype.Service

@Service
class BookingService (
    private val bookingRepository: BookingRepository
) {
    /**
     * 예약
     */
    fun makeBooking(booking: Booking): Booking {
        return bookingRepository.saveBooking(booking)
    }

    /**
     * 예약 내역 조회
     */
    fun findBooking(id: Long): Booking? {
        return bookingRepository.findBookingById(id)
    }

    /**
     * 예약 내역 변경
     */
    fun modifyBooking(booking: Booking) {
        bookingRepository.updateBooking(booking)
    }
}