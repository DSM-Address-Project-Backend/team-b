package com.address.dsmproject.service

import com.address.dsmproject.dto.UnzipTargetFile
import com.address.dsmproject.util.FileUtil
import com.address.dsmproject.util.JusoUtil
import org.springframework.stereotype.Service

@Service
class SaveJusoFileService(
    private val jusoUtil: JusoUtil,
    private val fileUtil: FileUtil,
) {

    fun execute(unzipTargetFile: UnzipTargetFile) {
        val jusoParam = jusoUtil.buildJusoParam(
            language = unzipTargetFile.reqType,
            year = unzipTargetFile.year,
            month = unzipTargetFile.month
        )
        fileUtil.writeFile(unzipTargetFile, jusoUtil.downloadJusoFile(jusoParam))
    }
}
