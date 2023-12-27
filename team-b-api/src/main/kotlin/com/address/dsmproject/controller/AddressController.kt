package com.address.dsmproject.controller

import com.address.dsmproject.domain.roadNumber.RoadNumberEntity
import com.address.dsmproject.domain.roadNumber.repository.vo.AutoCompletionAddressVO
import com.address.dsmproject.service.AddressService
import jakarta.validation.constraints.NotNull
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Validated
@RestController
class AddressController(
    private val addressService: AddressService,
) {

    @GetMapping("/address")
    fun autoCompletion(@RequestParam @NotNull keyword: String): List<AutoCompletionAddressVO> {
        return addressService.autoCompletion(keyword)
    }

    @GetMapping("/search")
    fun a(@RequestParam @NotNull keyword: String, @RequestParam(defaultValue = "0") page: Long): List<RoadNumberEntity> {
        return addressService.getAddress(keyword, page)
    }
}