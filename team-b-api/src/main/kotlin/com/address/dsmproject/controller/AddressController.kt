package com.address.dsmproject.controller

import com.address.dsmproject.controller.dto.AutoCompletionsResponse
import com.address.dsmproject.service.AddressService
import jakarta.validation.constraints.NotBlank
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
    fun autoCompletion(@RequestParam @NotBlank keyword: String): AutoCompletionsResponse {
        return addressService.autoCompletion(keyword)
    }
}