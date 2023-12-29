package com.address.dsmproject.presentation

import com.address.dsmproject.presentation.dto.response.AutoCompletionsResponse
import com.address.dsmproject.presentation.dto.response.SearchAddressResponse
import com.address.dsmproject.presentation.dto.response.TotalPageCountResponse
import com.address.dsmproject.service.AddressService
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
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
    fun autoCompletion(
        @RequestParam @NotBlank keyword: String,
    ): AutoCompletionsResponse {
        return addressService.autoCompletion(keyword.replace(" ", ""))
    }

    @GetMapping("/search")
    fun searchAddress(
        @RequestParam @NotNull @Min(1) page: Int,
        @RequestParam @NotBlank keyword: String,
    ): SearchAddressResponse {
        return addressService.searchAddress(page, keyword.replace(" ", ""))
    }

    @GetMapping("/search/count")
    fun addressCount(
        @RequestParam @NotBlank keyword: String,
    ): TotalPageCountResponse {
        return addressService.getTotalPageCount(keyword.replace(" ", ""))
    }
}
