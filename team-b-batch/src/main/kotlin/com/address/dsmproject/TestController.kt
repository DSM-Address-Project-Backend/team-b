package com.address.dsmproject

import com.address.dsmproject.job.SaveJobConfiguration
import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController(
    private val jobLauncher: JobLauncher,
    private val saveJobConfiguration: SaveJobConfiguration,
) {

    @GetMapping("/test")
    fun test(): String {
        jobLauncher.run(saveJobConfiguration.saveJob(), JobParameters())
        return "success"
    }
}
