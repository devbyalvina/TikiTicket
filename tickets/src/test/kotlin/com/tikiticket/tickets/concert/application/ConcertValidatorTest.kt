package com.tikiticket.tickets.concert.application

import com.tikiticket.tickets.concert.application.exception.ConcertException
import com.tikiticket.tickets.concert.domain.Concert
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ConcertValidatorTest {
    @Test
    fun `좌석목록이 Null이면 예외가 발생한다`() {
        // Given
        val concert = mockk<Concert> {
            every { seats } returns null
        }

        // When & Then
        assertThrows<ConcertException> {
            ConcertValidator.checkSeatsExist(concert)
        }
    }

    @Test
    fun `좌석목록이 empty면 예외가 발생한다`() {
        // Given
        val concert = mockk<Concert> {
            every { seats } returns emptyList()
        }

        // When & Then
        assertThrows<ConcertException> {
            ConcertValidator.checkSeatsExist(concert)
        }
    }

    @Test
    fun `좌석목록이 Null과 empty가 둘 다 아니면 예외가 발생하지 않는다`() {
        // Given
        val concert = mockk<Concert> {
            every { seats } returns listOf(mockk())
        }

        // When & Then
        ConcertValidator.checkSeatsExist(concert)    // 예외가 발생하지 않아야 함
    }
}