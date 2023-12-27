package com.address.dsmproject.service

import com.address.dsmproject.controller.dto.AutoCompletionResponse
import com.address.dsmproject.domain.roadNumber.repository.AddressRepository
import org.springframework.stereotype.Service

@Service
class AddressService(
    private val addressRepository: AddressRepository,
) {

    fun autoCompletion(keyword: String): List<AutoCompletionResponse> {
        val languageType = checkKeywordLanguage(keyword)

        val result = when (languageType) {
            KOREAN -> addressRepository.autoCompletionWithKor(keyword)
            else -> addressRepository.autoCompletionWithEng(keyword)
        }

        return result.map {
            AutoCompletionResponse(
                cityProvinceName = it.cityProvinceName,
                countyDistricts = it.countyDistricts,
                eupMyeonDong = it.eupMyeonDong,
            )
        }
    }

    private fun checkKeywordLanguage(keyword: String): String =
        if (keyword.matches("^[^a-zA-Z]*$".toRegex())) KOREAN else ENGLISH

    companion object {
        const val KOREAN = "KOREAN"
        const val ENGLISH = "ENGLISH"
    }
}
