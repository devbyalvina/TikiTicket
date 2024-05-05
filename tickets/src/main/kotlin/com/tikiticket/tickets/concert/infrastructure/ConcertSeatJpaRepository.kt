package com.tikiticket.tickets.concert.infrastructure

import org.springframework.data.jpa.repository.JpaRepository

interface ConcertSeatJpaRepository: JpaRepository<ConcertSeatEntity, Long> {
    fun findByConcertId(concertId: Long): List<ConcertSeatEntity>
}