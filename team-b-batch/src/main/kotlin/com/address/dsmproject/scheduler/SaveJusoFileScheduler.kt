package com.address.dsmproject.scheduler

import com.address.dsmproject.dto.UnzipTargetFile
import com.address.dsmproject.service.SaveJusoFileService
import com.address.dsmproject.util.JusoConstants.EnglishAddress
import com.address.dsmproject.util.JusoConstants.RoadAddress
import com.address.dsmproject.util.targetYearAndMonth
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

@Component
class SaveAddressScheduler(
    private val saveJusoFileService: SaveJusoFileService,
) {

    @Scheduled(cron = "0 0 0 1 * * ") // 매달 1일 0시 실행
    fun saveKoreaRoadAddressScheduler() {
        deleteFileIfExists(RoadAddress.KOR_FILE_PATH)
        deleteDirectoryIfExists(RoadAddress.KOR_ZIP_FILE_PATH)
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

    @Scheduled(cron = "0 0 0 1 * * ") // 매달 1일 0시 실행
    fun saveEnglishRoadAddressScheduler() {
        deleteFileIfExists(RoadAddress.ENG_FILE_PATH)
        deleteDirectoryIfExists(RoadAddress.ENG_ZIP_FILE_PATH)
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

    @Scheduled(cron = "0 0 0 1 * * ") // 매달 1일 0시 실행
    fun saveEnglishAddressScheduler() {
        deleteFileIfExists(EnglishAddress.FILE_PATH)
        deleteDirectoryIfExists(EnglishAddress.ZIP_FILE_PATH)
        val (year, month) = targetYearAndMonth()
        saveJusoFileService.execute(
            UnzipTargetFile(
                reqType = EnglishAddress.ENG_LANGUAGE,
                zipFilePath = EnglishAddress.ZIP_FILE_PATH,
                unzipTargetDirectoryPath = EnglishAddress.FILE_PATH,
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
