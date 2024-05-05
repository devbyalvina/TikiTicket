package com.tikiticket.tickets.aggregate.booking.domain

interface BookingRepository {
    /**
     * 예약
     */
    fun storeBooking(booking: Booking): Booking

    /**
     * 예약 내역 조회
     */
    fun findBookingById(id: Long): Booking?

    /**
     * 예약 내역 조회 For Update
     */
    fun findBookingByIdForUpdate(id: Long): Booking?

    /**
     * 예약 내역 변경
     */
    fun changeBooking(booking: Booking)
}