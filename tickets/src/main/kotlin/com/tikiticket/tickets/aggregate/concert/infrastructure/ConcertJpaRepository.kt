package com.tikiticket.tickets.aggregate.concert.infrastructure

import org.springframework.data.jpa.repository.JpaRepository

interface ConcertJpaRepository: JpaRepository<ConcertEntity, Long> {
}