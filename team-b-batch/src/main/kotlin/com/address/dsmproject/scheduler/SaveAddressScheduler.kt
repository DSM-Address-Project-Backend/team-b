package com.address.dsmproject.scheduler

import com.address.dsmproject.scheduler.AddressUtil.ENG_ADDRESS_FILE_PATH
import com.address.dsmproject.scheduler.AddressUtil.ENG_ADDRESS_ZIP_FILE_PATH
import com.address.dsmproject.scheduler.AddressUtil.ENG_REQ_TYPE
import com.address.dsmproject.scheduler.AddressUtil.KOR_ADDRESS_FILE_PATH
import com.address.dsmproject.scheduler.AddressUtil.KOR_ADDRESS_ZIP_FILE_PATH
import com.address.dsmproject.scheduler.AddressUtil.KOR_REQ_TYPE
import com.address.dsmproject.service.SaveAddressService
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
        deleteIfExistsFile(KOR_ADDRESS_ZIP_FILE_PATH, KOR_ADDRESS_FILE_PATH)
        saveAddressService.execute(KOR_REQ_TYPE, KOR_ADDRESS_ZIP_FILE_PATH, KOR_ADDRESS_FILE_PATH)
    }

    @Scheduled(cron = "0 0 0 1 * * ") // 매달 1일 0시 실행
    fun saveEnglishAddressScheduler() {
        deleteIfExistsFile(ENG_ADDRESS_ZIP_FILE_PATH, ENG_ADDRESS_FILE_PATH)
        saveAddressService.execute(ENG_REQ_TYPE, ENG_ADDRESS_ZIP_FILE_PATH, ENG_ADDRESS_FILE_PATH)
    }

    private fun deleteIfExistsFile(zipFilePath: String, filePath: String) {
        Files.deleteIfExists(Paths.get(zipFilePath))
        val isExistFile = File(filePath).exists()
        if (isExistFile) {
            Files.walk(Paths.get(filePath))
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete)
        }
        Files.deleteIfExists(Paths.get(filePath))
    }
}
