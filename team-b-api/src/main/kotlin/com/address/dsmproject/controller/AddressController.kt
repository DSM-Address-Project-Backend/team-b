package com.address.dsmproject.controller

import com.address.dsmproject.controller.dto.AutoCompletionResponse
import com.address.dsmproject.service.AddressService
import jakarta.validation.constraints.NotNull
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Validated
@RequestMapping("/address")
@RestController
class AddressController(
    private val addressService: AddressService,
) {

    @GetMapping("/help")
    fun autoCompletion(@RequestParam @NotNull keyword: String): List<AutoCompletionResponse> {
        return addressService.autoCompletion(keyword)
    }
}