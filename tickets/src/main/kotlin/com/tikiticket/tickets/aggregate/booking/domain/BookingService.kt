package com.tikiticket.tickets.aggregate.booking.domain

import com.tikiticket.tickets.global.domain.exception.CustomException
import com.tikiticket.tickets.aggregate.payment.domain.PaymentError
import org.springframework.boot.logging.LogLevel
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class BookingService (
    private val bookingRepository: BookingRepository
) {
    /**
     * 예약
     */
    fun makeBooking(booking: Booking): Booking {
        return bookingRepository.storeBooking(booking)
    }

    /**
     * 예약 내역 조회
     */
    fun findBooking(id: Long): Booking? {
        return bookingRepository.findBookingById(id)
    }

    /**
     * 예약 내역 조회 For Update
     */
    fun findBookingForUpdate(id: Long): Booking? {
        return bookingRepository.findBookingByIdForUpdate(id)
    }

    /**
     *  예매 상태 변경
     */
    @Transactional
    fun changeBookingStatus(bookingId: Long, bookingStatus: BookingStatusType, currentDateTime: LocalDateTime): Booking {
        // 예매 내역 조회 For Update
        val booking = findBookingForUpdate(bookingId)
            ?: throw CustomException(LogLevel.INFO, com.tikiticket.tickets.aggregate.payment.domain.PaymentError.BOOKING_NOT_FOUND)

        // 예매 상태 결제로 변경
        val paidBooking = booking.copy(
            bookingStatus = bookingStatus,
            updatedAt = currentDateTime
        )
        bookingRepository.changeBooking(booking)
        return paidBooking
    }
}