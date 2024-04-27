package com.tikiticket.tickets.appcore.application.scheduler

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.SchedulingConfigurer
import org.springframework.scheduling.config.ScheduledTaskRegistrar
import java.util.concurrent.Executor
import java.util.concurrent.Executors

@Configuration
class TicketQueueSchedulerConfiguration: SchedulingConfigurer {
    override fun configureTasks(taskRegistrar: ScheduledTaskRegistrar) {
        taskRegistrar.setScheduler(taskScheduler())
    }

    @Bean(destroyMethod = "shutdown")
    fun taskScheduler(): Executor {
        return Executors.newScheduledThreadPool(2)
    }
}