package com.address.dsmproject

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

internal const val BASE_PACKAGE = "com.address.dsmproject"

@SpringBootApplication
class DsmProjectBatchApplication

fun main(args: Array<String>) {
    runApplication<DsmProjectBatchApplication>(*args)
}
