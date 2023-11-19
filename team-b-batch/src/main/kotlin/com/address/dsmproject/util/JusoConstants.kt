package com.address.dsmproject.util

import com.address.dsmproject.util.JusoConstants.Common.CTPRVN_CD
import com.address.dsmproject.util.JusoConstants.Common.RESOURCE_PATH
import com.address.dsmproject.util.JusoConstants.Common.ZIP

object JusoConstants {
    object Common {
        const val CTPRVN_CD = "00"
        const val INT_NUM = "undefined"
        const val INT_FILE_NO = "undefined"
        const val ZIP = ".zip"
        const val RESOURCE_PATH = "team-b-batch/src/main/resources/"
    }

    object RoadAddress {
        const val KOR_ZIP_NAME = "_도로명주소_한글_전체분$ZIP"
        const val KOR_REAL_FILE_NAME = "RNADDR_KOR_"
        const val KOR_LANGUAGE = "ALLRNADR_KOR"
        const val KOR_ZIP_FILE_PATH = "${RESOURCE_PATH}korRoadAddress.zip"
        const val KOR_FILE_PATH = "${RESOURCE_PATH}unzipKor"
        const val ENG_ZIP_NAME = "_%EB%8F%84%EB%A1%9C%EB%AA%85%EC%A3%BC%EC%86%8C%20%EC%98%81%EC%96%B4_%EC%A0%84%EC%B2%B4%EB%B6%84$ZIP"
        const val ENG_REAL_FILE_NAME = "RN_ENG_"
        const val ENG_LANGUAGE = "ALLRNADR_ENG"
        const val ENG_ZIP_FILE_PATH = "${RESOURCE_PATH}engRoadAddress.zip"
        const val ENG_FILE_PATH = "${RESOURCE_PATH}unzipEng"
    }

    object EnglishAddress {
        const val ENG_LANGUAGE = "ALLENG"
        const val ZIP_NAME = "_영문주소DB_전체분$ZIP"
        const val REAL_FILE_NAME = ENG_LANGUAGE + CTPRVN_CD + ZIP
        const val ZIP_FILE_PATH = "${RESOURCE_PATH}address.zip"
        const val FILE_PATH = "${RESOURCE_PATH}unzipAddressEng"
    }
}
