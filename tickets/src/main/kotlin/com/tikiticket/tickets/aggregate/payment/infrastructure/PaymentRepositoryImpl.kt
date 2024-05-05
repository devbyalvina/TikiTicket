package com.tikiticket.tickets.aggregate.payment.infrastructure

import com.tikiticket.tickets.aggregate.payment.domain.Payment
import com.tikiticket.tickets.aggregate.payment.domain.PaymentHistory
import com.tikiticket.tickets.aggregate.payment.domain.PaymentRepository
import org.springframework.stereotype.Repository

@Repository
class PaymentRepositoryImpl: PaymentRepository {
    override fun savePayment(payment: Payment): Payment {
        TODO("Not yet implemented")
    }

    override fun savePaymentHistory(paymentHistory: PaymentHistory): PaymentHistory {
        TODO("Not yet implemented")
    }

    override fun findPaymentById(id: Long): Payment? {
        TODO("Not yet implemented")
    }
}