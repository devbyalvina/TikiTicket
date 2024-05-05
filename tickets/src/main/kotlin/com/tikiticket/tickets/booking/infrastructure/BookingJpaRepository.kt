package com.tikiticket.tickets.booking.infrastructure

import org.springframework.data.jpa.repository.JpaRepository

interface BookingJpaRepository: JpaRepository<BookingEntity, Long> {
}