package com.address.dsmproject.service

import com.address.dsmproject.feign.AddressClient
import com.address.dsmproject.util.JusoConstants.ENG_REAL_FILE_NAME
import com.address.dsmproject.util.JusoConstants.ENG_REQ_TYPE
import com.address.dsmproject.util.JusoConstants.ENG_ZIP_NAME
import com.address.dsmproject.util.JusoConstants.KOR_REAL_FILE_NAME
import com.address.dsmproject.util.JusoConstants.KOR_REQ_TYPE
import com.address.dsmproject.util.JusoConstants.KOR_ZIP_NAME
import com.address.dsmproject.util.JusoConstants.ZIP
import org.springframework.stereotype.Service
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.time.LocalDateTime
import java.util.zip.ZipFile

@Service
class SaveAddressService(
    private val addressClient: AddressClient,
) {

    fun execute(reqType: String, zipFilePath: String, filePath: String) {
        Files.write(Paths.get(zipFilePath), getAddressInfo(reqType))
        val zipFile = File(zipFilePath)
        val unzipFile = File(filePath)
        unzipFile.mkdir()
        unzip(zipFile, unzipFile)
    }

    private fun getAddressInfo(reqType: String): ByteArray {
        val currentDateTime = LocalDateTime.now()
        val yyyy = currentDateTime.year.toString()
        val mm = currentDateTime.month.toString().padStart(2, '0')
        val yyyyMM = yyyy + mm
        val yyMMZip = "${yyyy.slice(2..3)}$mm$ZIP"
        var fileName = ""
        var realFileName = ""
        when (reqType) {
            KOR_REQ_TYPE -> {
                fileName = "$yyyyMM$KOR_ZIP_NAME"
                realFileName = "$KOR_REAL_FILE_NAME$yyMMZip"
            }

            ENG_REQ_TYPE -> {
                fileName = "$yyyyMM$ENG_ZIP_NAME"
                realFileName = "$ENG_REAL_FILE_NAME$yyMMZip"
            }
        }

        return addressClient.getAddressInfo(
            reqType = reqType,
            yyyy = yyyy,
            ctprvnCd = "00",
            yyyyMM = yyyyMM,
            fileName = fileName,
            intNum = "undefined",
            intFileNo = "undefined",
            realFileName = realFileName,
        ).body ?: throw RuntimeException("Body 없음") // TODO: 추후 예외 처리 추가
    }

    private fun unzip(zipFile: File, unzipFile: File) {
        ZipFile(zipFile).use { zip ->
            for (entry in zip.entries()) {
                val entryFileOutputStream = File(unzipFile, entry.name).outputStream()
                entryFileOutputStream.use { out ->
                    zip.getInputStream(entry).use { it.copyTo(out) }
                }
            }
        }
    }
}
