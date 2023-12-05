package com.address.dsmproject

import com.address.dsmproject.job.SaveJobConfiguration
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class TestController(
    private val jobLauncher: JobLauncher,
    private val saveJobConfiguration: SaveJobConfiguration
) {

    @GetMapping
    fun test() {
        jobLauncher.run(
            saveJobConfiguration.saveJob(),
            JobParametersBuilder().addString(SaveJobConfiguration.JOB_NAME, UUID.randomUUID().toString())
                .toJobParameters()
        )
    }
}