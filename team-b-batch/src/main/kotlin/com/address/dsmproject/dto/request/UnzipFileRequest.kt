package com.address.dsmproject.dto.request

data class UnzipFileRequest(
    val reqType: String,
    val zipFilePath: String,
    val unzipTargetDirectoryPath: String,
    val year: Int,
    val month: Int,
)
