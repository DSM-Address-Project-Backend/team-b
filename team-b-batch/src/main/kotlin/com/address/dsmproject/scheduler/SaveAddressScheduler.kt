package com.address.dsmproject.scheduler

import com.address.dsmproject.dto.UnzipFile
import com.address.dsmproject.service.SaveAddressService
import com.address.dsmproject.util.JusoConstants.ENG_ADDRESS_FILE_PATH
import com.address.dsmproject.util.JusoConstants.ENG_ADDRESS_ZIP_FILE_PATH
import com.address.dsmproject.util.JusoConstants.ENG_LANGUAGE
import com.address.dsmproject.util.JusoConstants.KOR_ADDRESS_FILE_PATH
import com.address.dsmproject.util.JusoConstants.KOR_ADDRESS_ZIP_FILE_PATH
import com.address.dsmproject.util.JusoConstants.KOR_LANGUAGE
import com.address.dsmproject.util.getCurrentYearAndMonth
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

@Component
class SaveAddressScheduler(
    private val saveAddressService: SaveAddressService,
) {

    @Scheduled(cron = "0 0 0 1 * * ") // 매달 1일 0시 실행
    fun saveKoreaAddressScheduler() {
        deleteIfExistsFile(KOR_ADDRESS_FILE_PATH)
        deleteIfExistsDirectory(KOR_ADDRESS_ZIP_FILE_PATH)
        val (year, month) = getCurrentYearAndMonth()
        saveAddressService.execute(
            UnzipFile(
                reqType = KOR_LANGUAGE,
                zipFilePath = KOR_ADDRESS_ZIP_FILE_PATH,
                unzipTargetDirectoryPath = KOR_ADDRESS_FILE_PATH,
                year = year,
                month = month,
            )
        )
    }

    @Scheduled(cron = "0 0 0 1 * * ") // 매달 1일 0시 실행
    fun saveEnglishAddressScheduler() {
        deleteIfExistsFile(ENG_ADDRESS_ZIP_FILE_PATH)
        deleteIfExistsDirectory(ENG_ADDRESS_ZIP_FILE_PATH)
        val (year, month) = getCurrentYearAndMonth()
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

    private fun deleteIfExistsFile(deleteFilePath: String) {
        Files.deleteIfExists(Paths.get(deleteFilePath))
    }

    private fun deleteIfExistsDirectory(deleteDirectoryPath: String) {
        val isExistFile = File(deleteDirectoryPath).exists()
        if (isExistFile) {
            Files.walk(Paths.get(deleteDirectoryPath))
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete)
        }
    }
}
