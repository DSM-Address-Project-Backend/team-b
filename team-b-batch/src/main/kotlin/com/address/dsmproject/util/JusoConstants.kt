package com.address.dsmproject.util

object JusoConstants {
    const val ZIP = ".zip"
    const val KOR_ZIP_NAME = "_도로명주소_한글_전체분$ZIP"
    const val KOR_REAL_FILE_NAME = "RNADDR_KOR_"
    const val ENG_ZIP_NAME = "_%EB%8F%84%EB%A1%9C%EB%AA%85%EC%A3%BC%EC%86%8C%20%EC%98%81%EC%96%B4_%EC%A0%84%EC%B2%B4%EB%B6%84$ZIP"
    const val ENG_REAL_FILE_NAME = "RN_ENG_"
    const val KOR_LANGUAGE = "ALLRNADR_KOR"
    const val ENG_LANGUAGE = "ALLRNADR_ENG"
    private const val RESOURCE_PATH = "team-b-batch/src/main/resources/"
    const val KOR_ADDRESS_ZIP_FILE_PATH = "${RESOURCE_PATH}korAddress.zip"
    const val KOR_ADDRESS_FILE_PATH = "${RESOURCE_PATH}unzipKor"
    const val ENG_ADDRESS_ZIP_FILE_PATH = "${RESOURCE_PATH}engAddress.zip"
    const val ENG_ADDRESS_FILE_PATH = "${RESOURCE_PATH}unzipEng"
    const val CTPRVN_CD = "00"
    const val INT_NUM = "undefined"
    const val INT_FILE_NO = "undefined"
    const val FILE_RESOURCES = "${RESOURCE_PATH}*.txt"
}
