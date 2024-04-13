package com.tikiticket.tickets.concert.domain

import java.time.LocalDate

interface ConcertRepository {
    /**
     * 콘서트 조회
     */
    fun findConcertById(concertId: Long): Concert?

    /**
     *  콘서트 스케줄 목록 조회
     */
    fun findConcertsByDateRange(startDate: LocalDate, endDate: LocalDate): List<Concert>?

    /**
     *  콘서트 좌석 목록 조회
     */
    fun findConcertWithSeatsById(concertId: Long): Concert?

    /**
     *  콘서트 좌석 조회
     */
    fun findConcertSeatByConcertIdAndSeatNo(doncertId: Long, seatNo: Long): ConcertSeat?

    /**
     *  콘서트 좌석 조회 For Update
     */
    fun findConcertSeatForUpdate(concertId: Long, seatNo: Long): ConcertSeat?

    /**
     *  콘서트 좌석 수정
     */
    fun updateConcertSeat(concertSeat: ConcertSeat)
}