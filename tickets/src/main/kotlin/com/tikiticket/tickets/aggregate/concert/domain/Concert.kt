package com.tikiticket.tickets.aggregate.concert.domain

import java.time.LocalDateTime

data class Concert(
    val id: Long,
    val concertName: String,
    val artistName: String,
    val concertDate: LocalDateTime,
    val venue: String,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?,
    val seats: ConcertSeats?
)

data class ConcertSeats (
    val list: List<ConcertSeat>?
)