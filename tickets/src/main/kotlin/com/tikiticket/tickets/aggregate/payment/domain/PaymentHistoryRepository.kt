package com.tikiticket.tickets.aggregate.payment.domain

interface PaymentHistoryRepository {
    /**
     *  결제 히스토리 저장
     */
    fun save(paymentHistory: PaymentHistory): PaymentHistory
}