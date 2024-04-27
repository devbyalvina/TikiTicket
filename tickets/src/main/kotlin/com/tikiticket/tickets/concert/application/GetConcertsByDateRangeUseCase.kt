package com.tikiticket.tickets.concert.application

import com.tikiticket.tickets.appcore.application.exception.CustomException
import com.tikiticket.tickets.appcore.application.log.LogLevel
import com.tikiticket.tickets.concert.domain.Concert
import com.tikiticket.tickets.concert.domain.ConcertError
import com.tikiticket.tickets.concert.domain.ConcertService
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