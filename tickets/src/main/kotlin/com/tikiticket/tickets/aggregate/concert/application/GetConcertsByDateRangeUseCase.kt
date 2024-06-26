package com.tikiticket.tickets.aggregate.concert.application

import com.tikiticket.tickets.global.domain.exception.CustomException
import org.springframework.boot.logging.LogLevel
import com.tikiticket.tickets.aggregate.concert.domain.Concert
import com.tikiticket.tickets.aggregate.concert.domain.ConcertError
import com.tikiticket.tickets.aggregate.concert.domain.ConcertService
import org.springframework.stereotype.Component

/**
 *  API.4 콘서트 스케줄 목록 조회
 */
@Component
class GetConcertsByDateRangeUseCase (
    private val concertService: ConcertService
) {
    operator fun invoke(command: GetConcertsByDateRangeCommand): List<Concert> {
        val concerts = concertService.findConcertsByDateRange(command.startDate, command.endDate)
            ?: throw CustomException(LogLevel.WARN, ConcertError.CONCERT_NOT_FOUND)

        return concerts
    }
}