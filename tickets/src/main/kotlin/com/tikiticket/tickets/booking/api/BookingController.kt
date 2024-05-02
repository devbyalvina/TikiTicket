package com.tikiticket.tickets.booking.api

import com.tikiticket.tickets.booking.api.dto.MakeBookingResponse
import com.tikiticket.tickets.booking.application.MakeBookingCommand
import com.tikiticket.tickets.booking.application.MakeBookingUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/bookings")
class BookingController (
    private val makeBookingUseCase: MakeBookingUseCase
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
}