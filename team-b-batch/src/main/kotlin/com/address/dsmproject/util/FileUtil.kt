package com.address.dsmproject.util

import com.address.dsmproject.dto.UnzipTargetFile
import org.springframework.stereotype.Component
import org.springframework.util.PatternMatchUtils
import java.io.BufferedInputStream
import java.io.File
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Paths
import java.util.zip.ZipFile

@Component
class FileUtil {

    fun writeFile(unzipTargetFile: UnzipTargetFile, target: ByteArray) {
        Files.write(Paths.get(unzipTargetFile.zipFilePath), target)
        val zipFile = File(unzipTargetFile.zipFilePath)
        val unzipTargetDirectory = File(unzipTargetFile.unzipTargetDirectoryPath)
        unzipTargetDirectory.mkdir()

        unzip(zipFile, unzipTargetDirectory)
    }

    private fun unzip(zipFile: File, unzipFileTargetDirectory: File) {
        val zip = ZipFile(zipFile, Charset.forName("euc-kr"))
        for (file in zip.entries()) {
            if (PatternMatchUtils.simpleMatch("*.txt", file.name))
                BufferedInputStream(zip.getInputStream(file)).use { bis ->
                    File(unzipFileTargetDirectory, file.name).outputStream().buffered(1024).use { bis.copyTo(it) }
                }
        }
    }
}
