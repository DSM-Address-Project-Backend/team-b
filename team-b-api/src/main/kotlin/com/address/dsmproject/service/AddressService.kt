package com.address.dsmproject.service

import com.address.dsmproject.controller.dto.AutoCompletionsResponse
import com.address.dsmproject.domain.roadNumber.repository.AddressRepository
import org.springframework.stereotype.Service

@Service
class AddressService(
    private val addressRepository: AddressRepository,
) {

    fun autoCompletion(keyword: String): AutoCompletionsResponse {
        val languageType = checkLanguage(keyword)

        val result = when (languageType) {
            KOREAN -> addressRepository.autoCompletionWithKor(keyword)
            else -> addressRepository.autoCompletionWithEng(keyword)
        }

        val items = result.map { "${it.cityProvinceName} ${it.countyDistricts} ${it.eupMyeonDong}" }

        return AutoCompletionsResponse(items)
    }

    private fun checkLanguage(keyword: String) =
        if (keyword.matches("^[^a-zA-Z]*$".toRegex())) KOREAN else ENGLISH

    companion object {
        const val KOREAN = "KOREAN"
        const val ENGLISH = "ENGLISH"
    }
}
