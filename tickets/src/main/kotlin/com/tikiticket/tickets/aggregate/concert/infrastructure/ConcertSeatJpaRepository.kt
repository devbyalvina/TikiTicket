package com.tikiticket.tickets.aggregate.concert.infrastructure

import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query

interface ConcertSeatJpaRepository: JpaRepository<ConcertSeatEntity, Long> {
    /**
     *  콘서트 좌석 목록 조회
     */
    fun findByConcertId(concertId: Long): List<ConcertSeatEntity>

    /**
     *  콘서트 좌석 조회 For Update
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select c from ConcertSeatEntity c where c.concertSeatId = :concertSeatId")
    fun findForUpdateOrNullById(concertSeatId: Long): ConcertSeatEntity?
}