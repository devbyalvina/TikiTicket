package com.tikiticket.tickets.payment.domain

interface PaymentRepository {
    /**
     *  결제
     */
    fun savePayment(payment: Payment): Payment

    /**
     *  결제 히스토리 저장
     */
    fun savePaymentHistory(paymentHistory: PaymentHistory): PaymentHistory

    /**
     *  결제 내역 조회
     */
    fun findPaymentById(id: Long): Payment?
}