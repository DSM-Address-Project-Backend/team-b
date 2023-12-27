package com.address.dsmproject.controller

import com.address.dsmproject.domain.roadNumber.RoadNumberEntity
import com.address.dsmproject.service.AddressService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class AddressController(
    private val addressService: AddressService,
) {

    @GetMapping("/search")
    fun a(@RequestParam keyword: String, @RequestParam(defaultValue = "0") page: Long): List<RoadNumberEntity> {
        return addressService.getAddress(keyword, page)
    }
}