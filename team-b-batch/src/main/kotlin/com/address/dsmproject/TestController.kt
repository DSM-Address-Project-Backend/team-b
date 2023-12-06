package com.address.dsmproject

import com.address.dsmproject.dto.UnzipTargetFile
import com.address.dsmproject.job.SaveJobConfiguration
import com.address.dsmproject.service.SaveJusoFileService
import com.address.dsmproject.util.JusoConstants
import com.address.dsmproject.util.targetYearAndMonth
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class TestController(
    private val jobLauncher: JobLauncher,
    private val saveJobConfiguration: SaveJobConfiguration,
    private val saveJusoFileService: SaveJusoFileService,
) {

    @GetMapping
    fun test() {
        saveKorRoadNameAddress()
        saveEngRoadNameAddress()

        jobLauncher.run(
            saveJobConfiguration.saveJob(),
            JobParametersBuilder().addString(SaveJobConfiguration.JOB_NAME, UUID.randomUUID().toString())
                .toJobParameters()
        )
    }

    private fun saveKorRoadNameAddress() {
        val (year, month) = targetYearAndMonth()
        saveJusoFileService.execute(
            UnzipTargetFile(
                reqType = JusoConstants.RoadAddress.KOR_LANGUAGE,
                zipFilePath = JusoConstants.RoadAddress.KOR_ZIP_FILE_PATH,
                unzipTargetDirectoryPath = JusoConstants.RoadAddress.KOR_FILE_PATH,
                year = year,
                month = month,
            )
        )
    }

    private fun saveEngRoadNameAddress() {
        val (year, month) = targetYearAndMonth()
        saveJusoFileService.execute(
            UnzipTargetFile(
                reqType = JusoConstants.RoadAddress.ENG_LANGUAGE,
                zipFilePath = JusoConstants.RoadAddress.ENG_ZIP_FILE_PATH,
                unzipTargetDirectoryPath = JusoConstants.RoadAddress.ENG_FILE_PATH,
                year = year,
                month = month,
            )
        )
    }
}
