package com.address.dsmproject.job

import com.address.dsmproject.job.tasklet.SaveAddressTasklet
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class SaveJobConfiguration(
    private val jobRepository: JobRepository,
    private val transactionManager: PlatformTransactionManager,
) {
    companion object {
        const val JOB_NAME = "saveJob"
        const val STEP_NAME = "saveAddressStep"
    }

    @Bean
    fun saveJob(): Job {
        return JobBuilder(JOB_NAME, jobRepository)
            .start(saveStep())
            .build()
    }

    @Bean
    @JobScope
    fun saveStep(): Step {
        return StepBuilder(STEP_NAME, jobRepository)
            .tasklet(SaveAddressTasklet(), transactionManager)
            .build()
    }

}