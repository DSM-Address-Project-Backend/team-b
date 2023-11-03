package com.address.dsmproject

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

internal const val BASE_PACKAGE = "com.address.dsmproject"

@EnableScheduling
@SpringBootApplication
class DsmProjectBatchApplication

fun main(args: Array<String>) {
    runApplication<DsmProjectBatchApplication>(*args)
}
