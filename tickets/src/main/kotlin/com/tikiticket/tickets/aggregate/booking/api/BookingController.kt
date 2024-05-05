package com.tikiticket.tickets.aggregate.booking.api

import com.tikiticket.tickets.aggregate.booking.api.dto.GetBookingResponse
import com.tikiticket.tickets.aggregate.booking.api.dto.MakeBookingResponse
import com.tikiticket.tickets.aggregate.booking.application.GetBookingUseCase
import com.tikiticket.tickets.aggregate.booking.application.MakeBookingCommand
import com.tikiticket.tickets.aggregate.booking.application.MakeBookingUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/bookings")
class BookingController (
    private val makeBookingUseCase: MakeBookingUseCase,
    private val getBookingUseCase: GetBookingUseCase
){
    /**
     *  API.6 예매
     */
    @PatchMapping
    fun makeBooking (
        @RequestHeader("User-Id") userId: String,
        @RequestParam("concert_id") concertId: Long,
        @RequestParam("seat_id") seatId: Long,
    ): ResponseEntity<MakeBookingResponse> {
        val command = MakeBookingCommand(userId, seatId, concertId)
        val completedBooking = makeBookingUseCase(command)
        val response = MakeBookingResponse.of(completedBooking)
        return ResponseEntity.ok(response)
    }

    /**
     *  API.7 예매 내역 조회
     */
    @GetMapping("/{booking_id}")
    fun getBooking (
        @PathVariable("booking_id") bookingId: Long,
    ): ResponseEntity<GetBookingResponse> {
        val retrievedBooking = getBookingUseCase(bookingId)
        val response = GetBookingResponse.of(retrievedBooking)
        return ResponseEntity.ok(response)
    }
}