package com.address.dsmproject.dto

data class JusoParam(
    val language: String,
    val year: String,
    val month: String,
    val yyMMZip: String,
    val fileName: String,
    val realFileName: String,
)

data class CommonJusoParam(
    val yyyy: String,
    val mm: String,
    val yyyyMM: String,
)