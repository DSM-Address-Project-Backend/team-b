package com.address.dsmproject

import com.address.dsmproject.scheduler.SaveAddressScheduler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController(
    private val saveAddressScheduler: SaveAddressScheduler
) {

    @GetMapping
    fun test() {
        saveAddressScheduler.saveRoadAddressScheduler()
    }
}