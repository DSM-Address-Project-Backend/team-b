package com.address.dsmproject.service

import com.address.dsmproject.dto.AddressInfoParam
import com.address.dsmproject.dto.UnzipFile
import com.address.dsmproject.feign.AddressClient
import com.address.dsmproject.util.FileUtil
import com.address.dsmproject.util.JusoConstants.CTPRVN_CD
import com.address.dsmproject.util.JusoConstants.ENG_LANGUAGE
import com.address.dsmproject.util.JusoConstants.ENG_REAL_FILE_NAME
import com.address.dsmproject.util.JusoConstants.ENG_ZIP_NAME
import com.address.dsmproject.util.JusoConstants.INT_FILE_NO
import com.address.dsmproject.util.JusoConstants.INT_NUM
import com.address.dsmproject.util.JusoConstants.KOR_LANGUAGE
import com.address.dsmproject.util.JusoConstants.KOR_REAL_FILE_NAME
import com.address.dsmproject.util.JusoConstants.KOR_ZIP_NAME
import com.address.dsmproject.util.JusoConstants.ZIP
import org.springframework.stereotype.Service
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

@Service
class SaveAddressService(
    private val addressClient: AddressClient,
    private val fileUtil: FileUtil,
) {

    fun execute(unzipFile: UnzipFile) {
        Files.write(
            Paths.get(unzipFile.zipFilePath), getAddressInfo(unzipFile.reqType, unzipFile.year, unzipFile.month)
        )
        val zipFile = File(unzipFile.zipFilePath)
        val unzipTargetDirectory = File(unzipFile.unzipTargetDirectoryPath)
        unzipTargetDirectory.mkdir()

        fileUtil.unzip(zipFile, unzipTargetDirectory)
    }

    private fun getAddressInfo(language: String, year: Int, month: String): ByteArray {
        val addressInfoParam = buildAddressInfoParam(language, year, month)

        return addressClient.getAddressInfo(
            reqType = language,
            yyyy = addressInfoParam.year,
            ctprvnCd = CTPRVN_CD,
            yyyyMM = addressInfoParam.year + addressInfoParam.month,
            fileName = addressInfoParam.fileName,
            intNum = INT_NUM,
            intFileNo = INT_FILE_NO,
            realFileName = addressInfoParam.realFileName,
        ).body ?: throw RuntimeException("Body 없음") // TODO: 추후 예외 처리 추가
    }

    private fun buildAddressInfoParam(language: String, year: Int, month: String): AddressInfoParam {
        val yyyy = year.toString()
        val mm = month.padStart(2, '0')
        val yyyyMM = yyyy + mm
        val yyMMZip = "${yyyy.slice(2..3)}$mm$ZIP"
        var fileName = ""
        var realFileName = ""
        when (language) {
            KOR_LANGUAGE -> {
                fileName = "$yyyyMM$KOR_ZIP_NAME"
                realFileName = "$KOR_REAL_FILE_NAME$yyMMZip"
            }

            ENG_LANGUAGE -> {
                fileName = "$yyyyMM$ENG_ZIP_NAME"
                realFileName = "$ENG_REAL_FILE_NAME$yyMMZip"
            }
        }

        return AddressInfoParam(yyyy, mm, yyMMZip, fileName, realFileName)
    }
}
