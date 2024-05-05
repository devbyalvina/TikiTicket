package com.tikiticket.tickets.aggregate.payment.domain

interface PaymentRepository {
    /**
     *  결제
     */
    fun savePayment(payment: com.tikiticket.tickets.aggregate.payment.domain.Payment): com.tikiticket.tickets.aggregate.payment.domain.Payment

    /**
     *  결제 히스토리 저장
     */
    fun savePaymentHistory(paymentHistory: com.tikiticket.tickets.aggregate.payment.domain.PaymentHistory): com.tikiticket.tickets.aggregate.payment.domain.PaymentHistory

    /**
     *  결제 내역 조회
     */
    fun findPaymentById(id: Long): com.tikiticket.tickets.aggregate.payment.domain.Payment?
}