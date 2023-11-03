package com.address.dsmproject.scheduler

import com.address.dsmproject.dto.UnzipFile
import com.address.dsmproject.job.SaveJobConfiguration
import com.address.dsmproject.service.SaveAddressService
import com.address.dsmproject.util.JusoConstants.ENG_ADDRESS_FILE_PATH
import com.address.dsmproject.util.JusoConstants.ENG_ADDRESS_ZIP_FILE_PATH
import com.address.dsmproject.util.JusoConstants.ENG_LANGUAGE
import com.address.dsmproject.util.JusoConstants.KOR_ADDRESS_FILE_PATH
import com.address.dsmproject.util.JusoConstants.KOR_ADDRESS_ZIP_FILE_PATH
import com.address.dsmproject.util.JusoConstants.KOR_LANGUAGE
import com.address.dsmproject.util.targetYearAndMonth
import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

@Component
class SaveAddressScheduler(
    private val saveAddressService: SaveAddressService,
    private val jobLauncher: JobLauncher,
    private val saveJobConfiguration: SaveJobConfiguration,
) {

    @Scheduled(cron = "0 0 0 1 * * ") // 매달 1일 0시 실행
    fun saveKoreaAddressScheduler() {
        deleteFileIfExists(KOR_ADDRESS_FILE_PATH)
        deleteDirectoryIfExists(KOR_ADDRESS_ZIP_FILE_PATH)
        val (year, month) = targetYearAndMonth()
        saveAddressService.execute(
            UnzipFile(
                reqType = KOR_LANGUAGE,
                zipFilePath = KOR_ADDRESS_ZIP_FILE_PATH,
                unzipTargetDirectoryPath = KOR_ADDRESS_FILE_PATH,
                year = year,
                month = month,
            )
        )
        jobLauncher.run(saveJobConfiguration.saveJob(), JobParameters())
    }

    @Scheduled(cron = "0 0 0 1 * * ") // 매달 1일 0시 실행
    fun saveEnglishAddressScheduler() {
        deleteFileIfExists(ENG_ADDRESS_ZIP_FILE_PATH)
        deleteDirectoryIfExists(ENG_ADDRESS_ZIP_FILE_PATH)
        val (year, month) = targetYearAndMonth()
        saveAddressService.execute(
            UnzipFile(
                reqType = ENG_LANGUAGE,
                zipFilePath = ENG_ADDRESS_ZIP_FILE_PATH,
                unzipTargetDirectoryPath = ENG_ADDRESS_FILE_PATH,
                year = year,
                month = month,
            )
        )
    }

    private fun deleteFileIfExists(targetFilePath: String) {
        Files.deleteIfExists(Paths.get(targetFilePath))
    }

    private fun deleteDirectoryIfExists(targetDirectoryPath: String) {
        val isExistFile = File(targetDirectoryPath).exists()
        if (isExistFile) {
            Files.walk(Paths.get(targetDirectoryPath))
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete)
        }
    }
}
