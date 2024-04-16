package com.tikiticket.tickets.concert.infrastructure

import org.springframework.data.jpa.repository.JpaRepository

interface ConcertJpaRepository: JpaRepository<ConcertEntity, Long> {
}