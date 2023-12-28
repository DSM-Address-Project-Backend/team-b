package com.address.dsmproject.service

import com.address.dsmproject.domain.roadNumber.RoadNumberEntity
import com.address.dsmproject.domain.roadNumber.repository.AddressRepository
import com.address.dsmproject.domain.roadNumber.repository.vo.AutoCompletionAddressVO
import org.springframework.stereotype.Service

@Service
class AddressService(
    private val addressRepository: AddressRepository
) {

    fun autoCompletion(keyword: String): List<AutoCompletionAddressVO> {
        val languageType = checkKeywordLanguage(keyword)

//        return addressRepository.autoCompletion(keyword, checkKeywordLanguage(keyword))

        return emptyList()
    }

    fun getAddress(keyword: String, page: Long): List<RoadNumberEntity> {
        return addressRepository.queryAddress(
            keyword = keyword,
            type = checkKeywordLanguage(keyword),
            page = page,
        )
    }

    private fun checkKeywordLanguage(keyword: String): String =
        if (keyword.matches("^[^a-zA-Z]*$".toRegex())) KOREAN else ENGLISH

    companion object {
        const val KOREAN = "KOREAN"
        const val ENGLISH = "ENGLISH"
    }
}
