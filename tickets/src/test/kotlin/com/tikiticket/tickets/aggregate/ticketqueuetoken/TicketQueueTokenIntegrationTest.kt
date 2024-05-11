package com.tikiticket.tickets.aggregate.ticketqueuetoken

import com.tikiticket.tickets.RedisTestSupport
import com.tikiticket.tickets.aggregate.ticketqueuetoken.application.CreateTokenInMemoryUseCase
import com.tikiticket.tickets.aggregate.ticketqueuetoken.application.CreateTokenUseCase
import com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TicketQueueToken
import com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TicketQueueTokenError
import com.tikiticket.tickets.global.domain.exception.CustomException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.TestConstructor
import java.time.LocalDateTime
import java.util.concurrent.CompletableFuture
import java.util.concurrent.atomic.AtomicInteger

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class TicketQueueTokenIntegrationTest (
    val createTokenUseCase: CreateTokenUseCase,
    val createTokenInMemoryUseCase: CreateTokenInMemoryUseCase
): RedisTestSupport() {
    @Test
    fun `대기열 생성 요청이 동시에 들어오는 경우 1개만 성공한다`() {
        // given
        val failCount = AtomicInteger(0)

        // when
        val futures = mutableListOf<CompletableFuture<TicketQueueToken>>()
        repeat(10 ) {
            futures.add (
                CompletableFuture.supplyAsync {
                    runCatching {
                        createTokenUseCase ("user01", LocalDateTime.now())
                    }.onFailure {
                        failCount.incrementAndGet()
                    }.getOrNull()
                }
            )
        }

        CompletableFuture.allOf(*futures.toTypedArray()).join()

        // then
        Assertions.assertEquals(9, failCount.get())
    }

    private val log: Logger = LoggerFactory.getLogger(TicketQueueTokenIntegrationTest::class.java)

    @Test
    fun `메모리 저장소에 대기열 토큰 생성 요청 시 토큰 생성에 성공한다`() {
        // When
        val result = createTokenInMemoryUseCase("user01")

        // Then
        assertNotNull(result)
    }

    @Test
    fun `메모리 저장소에 대기열 토큰이 이미 있는 경우 토큰 생성 요청 시 예외를 반환한다`() {
        // When
        val result = createTokenInMemoryUseCase("user01")
        val exception = Assertions.assertThrows(CustomException::class.java) {
            createTokenInMemoryUseCase("user01")
        }

        // Then
        Assertions.assertEquals(TicketQueueTokenError.TOKEN_ALREADY_ISSUED, exception.customError)
    }
}