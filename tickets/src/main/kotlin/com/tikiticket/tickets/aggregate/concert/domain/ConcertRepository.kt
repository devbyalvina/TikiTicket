package com.tikiticket.tickets.aggregate.concert.domain

import java.time.LocalDate

interface ConcertRepository {
    /**
     * 콘서트 조회
     */
    fun findConcert(concertId: Long): Concert?

    /**
     *  콘서트 스케줄 목록 조회
     */
    fun findConcertsByDateRange(startDate: LocalDate, endDate: LocalDate): List<Concert>?

    /**
     *  콘서트 좌석 목록 조회
     */
    fun findConcertSeats(concertId: Long): List<ConcertSeat>?

    /**
     *  콘서트 좌석 조회
     */
    fun findConcertSeat(concertSeatId: Long): ConcertSeat?

    /**
     *  콘서트 좌석 조회 For Update
     */
    fun findConcertSeatForUpdate(concertSeatId: Long): ConcertSeat?

    /**
     *  콘서트 좌석 저장
     */
    fun updateConcertSeat(concertSeat: ConcertSeat)
    /**
     *  콘서트 좌석 수정
     */
    fun storeConcertSeat(concertSeat: ConcertSeat): ConcertSeat
}