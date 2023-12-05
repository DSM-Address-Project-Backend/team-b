package com.address.dsmproject.scheduler

import com.address.dsmproject.dto.UnzipTargetFile
import com.address.dsmproject.job.SaveJobConfiguration
import com.address.dsmproject.service.SaveJusoFileService
import com.address.dsmproject.util.JusoConstants.RoadAddress
import com.address.dsmproject.util.targetYearAndMonth
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class SaveAddressScheduler(
    private val saveJusoFileService: SaveJusoFileService,
    private val jobLauncher: JobLauncher,
    private val saveJobConfiguration: SaveJobConfiguration
) {

    @Scheduled(cron = "0 0 0 1 * * ") // 매달 1일 0시 실행
    fun saveRoadAddressScheduler() {
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
                reqType = RoadAddress.KOR_LANGUAGE,
                zipFilePath = RoadAddress.KOR_ZIP_FILE_PATH,
                unzipTargetDirectoryPath = RoadAddress.KOR_FILE_PATH,
                year = year,
                month = month,
            )
        )
    }

    private fun saveEngRoadNameAddress() {
        val (year, month) = targetYearAndMonth()
        saveJusoFileService.execute(
            UnzipTargetFile(
                reqType = RoadAddress.ENG_LANGUAGE,
                zipFilePath = RoadAddress.ENG_ZIP_FILE_PATH,
                unzipTargetDirectoryPath = RoadAddress.ENG_FILE_PATH,
                year = year,
                month = month,
            )
        )
    }
}
