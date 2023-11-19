package com.address.dsmproject.util

import com.address.dsmproject.dto.UnzipTargetFile
import org.springframework.stereotype.Component
import java.io.File
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
        ZipFile(zipFile).use { zip ->
            for (entry in zip.entries()) {
                val entryFileOutputStream = File(unzipFileTargetDirectory, entry.name).outputStream()
                entryFileOutputStream.use { out ->
                    zip.getInputStream(entry).use { it.copyTo(out) }
                }
            }
        }
    }
}
