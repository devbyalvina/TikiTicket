package com.tikiticket.tickets.balance

import com.tikiticket.tickets.appcore.infrastructure.JpaConfig
import com.tikiticket.tickets.balance.application.ChangeBalanceCommand
import com.tikiticket.tickets.balance.application.ChangeBalanceUseCase
import com.tikiticket.tickets.balance.domain.Balance
import com.tikiticket.tickets.balance.domain.BalanceService
import com.tikiticket.tickets.balance.domain.TransactionType
import jakarta.transaction.Transactional
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.TestConstructor
import java.time.LocalDateTime
import java.util.concurrent.CompletableFuture
import java.util.concurrent.atomic.AtomicInteger

@SpringBootTest
//@DataJpaTest
@Import(JpaConfig::class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class BalanceIntegrationTest (
    val changeBalanceUseCase: ChangeBalanceUseCase,
    val balanceService: BalanceService,
){
    @Transactional
    @BeforeEach
    fun createBalance() {
        val currentTime = LocalDateTime.now()
        balanceService.storeBalance(Balance(0, "user01", 0, currentTime, currentTime))
    }

    @Test
    fun `동시에 들어오는 충전 요청을 모두 성공한다`() {
        // when
        val failCount = AtomicInteger(0)
        val futures = mutableListOf<CompletableFuture<Balance>>()
        repeat(3 ) {index ->
            futures.add (
                CompletableFuture.supplyAsync {
                    runCatching {
                        changeBalanceUseCase (ChangeBalanceCommand ("user01", TransactionType.CHARGE, ((index+1) * 10000).toLong()))
                    }.onFailure {
                        failCount.incrementAndGet()
                    }.getOrNull()
                }
            )
        }

        // 모든 CompletableFuture의 실행이 완료된 후에 failCount를 검사
        CompletableFuture.allOf(*futures.toTypedArray()).join()

        Assertions.assertEquals(0, failCount.get())
    }
}