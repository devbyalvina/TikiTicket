package com.tikiticket.tickets.payment.api

import com.tikiticket.tickets.payment.api.dto.MakePaymentRequest
import com.tikiticket.tickets.payment.api.dto.MakePaymentResponse
import com.tikiticket.tickets.payment.application.MakePaymentCommand
import com.tikiticket.tickets.payment.application.MakePaymentUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/payments")
class PaymentController (
    private val makePaymentUseCase: MakePaymentUseCase
){
    /**
     *  API.8 예매 내역 결제
     */
    @PatchMapping("/{booking_id}/pay")
    fun makePayment (
        @RequestHeader("User-Id") userId: String,
        @PathVariable("booking_id") bookingId: Long,
        @RequestBody requestBody: MakePaymentRequest,
    ): ResponseEntity<MakePaymentResponse> {
        val command = MakePaymentCommand(bookingId, requestBody.paymentMethod, userId)
        val payment = makePaymentUseCase(command)
        val response = MakePaymentResponse.of(payment)
        return ResponseEntity.ok(response)
    }
}