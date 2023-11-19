package com.address.dsmproject.util

import com.address.dsmproject.dto.CommonJusoParam
import com.address.dsmproject.dto.JusoParam
import com.address.dsmproject.feign.JusoClient
import com.address.dsmproject.util.JusoConstants.Common
import com.address.dsmproject.util.JusoConstants.EnglishAddress
import com.address.dsmproject.util.JusoConstants.RoadAddress
import org.springframework.stereotype.Component

@Component
class JusoUtil(
    private val jusoClient: JusoClient,
) {
    fun downloadJusoFile(jusoParam: JusoParam): ByteArray {
        return jusoClient.downloadJusoFile(
            reqType = jusoParam.language,
            yyyy = jusoParam.year,
            ctprvnCd = Common.CTPRVN_CD,
            yyyyMM = jusoParam.year + jusoParam.month,
            fileName = jusoParam.fileName,
            intNum = Common.INT_NUM,
            intFileNo = Common.INT_FILE_NO,
            realFileName = jusoParam.realFileName,
        ).body ?: throw RuntimeException() // TODO: 추후 예외 처리
    }

    fun buildJusoParam(language: String, year: Int, month: String): JusoParam {
        val commonJusoParam = buildCommonParam(year, month)
        val yyMMZip = commonJusoParam.yyyyMM.slice(2..3) + commonJusoParam.mm + Common.ZIP
        var fileName = ""
        var realFileName = ""
        when (language) {
            RoadAddress.KOR_LANGUAGE -> {
                fileName = commonJusoParam.yyyyMM + RoadAddress.KOR_ZIP_NAME
                realFileName = RoadAddress.KOR_REAL_FILE_NAME + yyMMZip
            }

            RoadAddress.ENG_LANGUAGE -> {
                fileName = commonJusoParam.yyyyMM + RoadAddress.ENG_ZIP_NAME
                realFileName = RoadAddress.ENG_REAL_FILE_NAME + yyMMZip
            }

            EnglishAddress.ENG_LANGUAGE -> {
                fileName = commonJusoParam.yyyyMM + EnglishAddress.ZIP_NAME
                realFileName = commonJusoParam.yyyyMM + EnglishAddress.REAL_FILE_NAME
            }
        }

        return JusoParam(
            language = language,
            year = commonJusoParam.yyyy,
            month = commonJusoParam.mm,
            yyMMZip = yyMMZip,
            fileName = fileName,
            realFileName = realFileName,
        )
    }

    private fun buildCommonParam(year: Int, month: String): CommonJusoParam {
        val yyyy = year.toString()
        val mm = month.padStart(2, '0')
        return CommonJusoParam(yyyy = yyyy, mm = mm, yyyyMM = yyyy + mm)
    }
}
