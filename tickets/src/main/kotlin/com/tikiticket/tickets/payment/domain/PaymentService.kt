package com.tikiticket.tickets.payment.domain

import org.springframework.stereotype.Service

@Service
class PaymentService (
    private val paymentRepository: PaymentRepository
){
    /**
     * 결제
     */
    fun makePayment(payment: Payment): Payment {
        return paymentRepository.savePayment(payment)
    }

    /**
     * 결제 내역 조회
     */
    fun findPayment(id: Long): Payment? {
        return paymentRepository.findPaymentById(id)
    }
}