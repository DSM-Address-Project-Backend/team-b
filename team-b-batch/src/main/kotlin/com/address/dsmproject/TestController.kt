package com.address.dsmproject

import com.address.dsmproject.domain.roadNumber.repository.RoadNumberRepository
import com.address.dsmproject.scheduler.SaveAddressScheduler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController(
    private val scheduler: SaveAddressScheduler,
    private val roadNumberRepository: RoadNumberRepository,
) {

    @GetMapping
    fun a() {
        scheduler.saveRoadAddressScheduler()
    }

    @GetMapping("/search")
    fun b(@RequestParam keyword: String) = roadNumberRepository.fullTestSearch(keyword)
}