package com.tikiticket.tickets.aggregate.booking.infrastructure

import org.springframework.data.jpa.repository.JpaRepository

interface BookingJpaRepository: JpaRepository<BookingEntity, Long> {
}