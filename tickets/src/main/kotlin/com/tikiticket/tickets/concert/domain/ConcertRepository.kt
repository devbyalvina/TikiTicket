package com.tikiticket.tickets.concert.domain

import java.time.LocalDate

interface ConcertRepository {
    fun findConcertsByDateRange(startDate: LocalDate, endDate: LocalDate): List<Concert>?
}