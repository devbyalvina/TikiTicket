package com.tikiticket.tickets.concert.domain

import java.time.LocalDate

interface ConcertRepository {
    /**
     *  API.4. 콘서트 스케줄 목록 조회
     */
    fun findConcertsByDateRange(startDate: LocalDate, endDate: LocalDate): List<Concert>?

    /**
     *  API.4. 콘서트 좌석 목록 조회
     */
    fun findConcertWithSeatsById(concertId: Long): Concert?
}