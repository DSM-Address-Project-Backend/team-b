package com.address.dsmproject.dto

data class UnzipTargetFile(
    val reqType: String,
    val zipFilePath: String,
    val unzipTargetDirectoryPath: String,
    val year: Int,
    val month: String,
)
