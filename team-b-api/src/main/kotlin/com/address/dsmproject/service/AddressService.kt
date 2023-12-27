package com.address.dsmproject.service

import com.address.dsmproject.domain.roadNumber.RoadNumberEntity
import com.address.dsmproject.domain.roadNumber.repository.AddressRepository
import org.springframework.stereotype.Service

@Service
class AddressService(
    private val addressRepository: AddressRepository
) {

    fun getAddress(keyword: String, page: Long): List<RoadNumberEntity> {
        return addressRepository.queryAddress(
            keyword = keyword,
            type = checkKeywordLanguage(keyword),
            page = page,
        )
    }

    private fun checkKeywordLanguage(keyword: String): String =
        if (keyword.matches("^[^a-zA-Z]*$".toRegex())) "KOREAN" else "ENGLISH"
}