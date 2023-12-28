package com.address.dsmproject.util

import com.address.dsmproject.util.JusoConstants.Common.RESOURCE_PATH
import com.address.dsmproject.util.JusoConstants.Common.ZIP

object JusoConstants {
    object Common {
        const val CTPRVN_CD = "00"
        const val INT_NUM = "undefined"
        const val INT_FILE_NO = "undefined"
        const val ZIP = ".zip"
        const val RESOURCE_PATH = "./"
    }

    object RoadAddress {
        const val KOR_ZIP_NAME = "_도로명주소_한글_전체분$ZIP"
        const val KOR_REAL_FILE_NAME = "RNADDR_KOR_"
        const val KOR_LANGUAGE = "ALLRNADR_KOR"
        const val KOR_ZIP_FILE_PATH = "${RESOURCE_PATH}korRoadAddress.zip"
        const val KOR_FILE_PATH = "${RESOURCE_PATH}unzipKor"
        const val ENG_ZIP_NAME = "_도로명주소 영어_전체분$ZIP"
        const val ENG_REAL_FILE_NAME = "RN_ENG_"
        const val ENG_LANGUAGE = "ALLRNADR_ENG"
        const val ENG_ZIP_FILE_PATH = "${RESOURCE_PATH}engRoadAddress.zip"
        const val ENG_FILE_PATH = "${RESOURCE_PATH}unzipEng"
    }
}
