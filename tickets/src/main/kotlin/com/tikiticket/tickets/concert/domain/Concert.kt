package com.tikiticket.tickets.concert.domain

import java.time.LocalDateTime

data class Concert(
    val id: Long,
    val concertName: String,
    val artistName: String,
    val concertDate: LocalDateTime,
    val venue: String,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?,
    val seats: List<ConcertSeat>?
)
