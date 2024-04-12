package com.tikiticket.tickets.booking.domain

interface BookingRepository {
    /**
     * 예약
     */
    fun saveBooking(booking: Booking): Booking

    /**
     * 예약 내역 조회
     */
    fun findBookingById(id: Long): Booking?

    /**
     * 예약 내역 변경
     */
    fun updateBooking(booking: Booking)
}