package com.tikiticket.tickets.aggregate.concert.infrastructure

import com.tikiticket.tickets.aggregate.concert.domain.Concert
import com.tikiticket.tickets.aggregate.concert.domain.ConcertRepository
import com.tikiticket.tickets.aggregate.concert.domain.ConcertSeat
import com.tikiticket.tickets.aggregate.concert.domain.ConcertSeats
import com.tikiticket.tickets.global.infrastructure.redis.map.RedisMapNameType
import com.tikiticket.tickets.global.infrastructure.redis.map.RedisMapRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class ConcertRepositoryImpl (
    private val concertJpaRepository: ConcertJpaRepository,
    private val concertSeatJpaRepository: ConcertSeatJpaRepository,
    private val concertRedisMapRepository: RedisMapRepository<Long, Concert>,
): ConcertRepository {
    /**
     * 콘서트 조회
     */
    override fun findConcert(concertId: Long): Concert? {
        val concert =  concertJpaRepository.findByIdOrNull(concertId)?.toDomain()
        val concertSeats = concertSeatJpaRepository.findByConcertId(concertId)?.map { it.toDomain() }
        return concert?.copy(seats = ConcertSeats(concertSeats))
    }

    /**
     * 콘서트 조회 In Memory
     */
    override fun findConcertInMemory(concertId: Long): Concert? {
        val mapName = RedisMapNameType.Concert.name
        return concertRedisMapRepository.findByKey(mapName, concertId)
    }

    /**
     *  콘서트 스케줄 목록 조회
     */
    override fun findConcertsByDateRange(startDate: LocalDate, endDate: LocalDate): List<Concert>? {
        TODO("Not yet implemented")
    }

    /**
     *  콘서트 좌석 목록 조회
     */
    override fun findConcertSeats(concertId: Long): List<ConcertSeat>? {
        return concertSeatJpaRepository.findByConcertId(concertId)?.map { it.toDomain() }
    }

    /**
     *  콘서트 좌석 목록 조회 In Memory
     */
    override fun findConcertSeatsInMemory(concertId: Long): List<ConcertSeat>? {
        TODO("Not yet implemented")
    }

    /**
     *  콘서트 좌석 조회
     */
    override fun findConcertSeat(concertSeatId: Long): ConcertSeat? {
        return concertSeatJpaRepository.findByIdOrNull(concertSeatId)?.toDomain()
    }

    /**
     *  콘서트 좌석 조회 In Memory
     */
    override fun findConcertSeatInMemory(concertSeatId: Long): ConcertSeat? {
        TODO("Not yet implemented")
    }

    /**
     *  콘서트 좌석 조회 For Update
     */
    override fun findConcertSeatForUpdate(concertSeatId: Long): ConcertSeat? {
        return concertSeatJpaRepository.findForUpdateOrNullById(concertSeatId)?.toDomain()
    }

    /**
     *  콘서트 좌석 조회 For Update
     */
    override fun updateConcertSeat(concertSeat: ConcertSeat) {
        TODO("Not yet implemented")
    }

    /**
     *  콘서트 좌석 저장
     */
    override fun storeConcertSeat(concertSeat: ConcertSeat): ConcertSeat {
        return concertSeatJpaRepository.save(ConcertSeatEntity.of(concertSeat)).toDomain()
    }

    /**
     *  콘서트 좌석 저장 In Memory
     */
    override fun storeConcertSeatInMemory(concertSeat: ConcertSeat): ConcertSeat {
        TODO("Not yet implemented")
    }
}