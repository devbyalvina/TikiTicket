package com.tikiticket.tickets.aggregate.payment.infrastructure

import com.tikiticket.tickets.aggregate.payment.domain.Payment
import com.tikiticket.tickets.aggregate.payment.domain.PaymentHistory
import com.tikiticket.tickets.aggregate.payment.domain.PaymentRepository
import org.springframework.stereotype.Repository

@Repository
class PaymentRepositoryImpl: com.tikiticket.tickets.aggregate.payment.domain.PaymentRepository {
    override fun savePayment(payment: com.tikiticket.tickets.aggregate.payment.domain.Payment): com.tikiticket.tickets.aggregate.payment.domain.Payment {
        TODO("Not yet implemented")
    }

    override fun savePaymentHistory(paymentHistory: com.tikiticket.tickets.aggregate.payment.domain.PaymentHistory): com.tikiticket.tickets.aggregate.payment.domain.PaymentHistory {
        TODO("Not yet implemented")
    }

    override fun findPaymentById(id: Long): com.tikiticket.tickets.aggregate.payment.domain.Payment? {
        TODO("Not yet implemented")
    }
}