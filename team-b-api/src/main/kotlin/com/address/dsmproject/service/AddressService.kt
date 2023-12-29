package com.address.dsmproject.service

import com.address.dsmproject.domain.roadNumber.repository.AddressRepository
import com.address.dsmproject.domain.roadNumber.repository.vo.SearchAddressVo
import com.address.dsmproject.domain.roadNumber.type.RoadNumberType
import com.address.dsmproject.presentation.dto.response.AutoCompletionsResponse
import com.address.dsmproject.presentation.dto.response.SearchAddressResponse
import com.address.dsmproject.presentation.dto.response.SearchAddressResponse.AddressElement
import com.address.dsmproject.presentation.dto.response.SearchAddressResponse.AddressElement.JibunElement
import org.springframework.stereotype.Service

@Service
class AddressService(
    private val addressRepository: AddressRepository,
) {
    companion object {
        const val KOREAN = "KOREAN"
        const val ENGLISH = "ENGLISH"
    }

    fun autoCompletion(keyword: String): AutoCompletionsResponse {
        val languageType = checkLanguage(keyword)

        val result = when (languageType) {
            KOREAN -> addressRepository.autoCompletionWithKor(keyword)
            else -> addressRepository.autoCompletionWithEng(keyword)
        }

        val response = result.map { "${it.cityProvinceName} ${it.countyDistricts} ${it.eupMyeonDong}" }

        return AutoCompletionsResponse(response)
    }

    fun searchAddress(page: Int, keyword: String): SearchAddressResponse {
        val languageType = checkLanguage(keyword)

        val result = when (languageType) {
            KOREAN -> addressRepository.searchAddressWithKor(page, keyword)
            else -> addressRepository.searchAddressWithEng(page, keyword)
        }

        val response = result.map { address ->
            val sameManagementNumInfos = result
                .filter { it.type == RoadNumberType.JIBUN }
                .filter { address.managementNumber == it.managementNumber }
                .toMutableList()

            val representJibun = sameManagementNumInfos.find { it.isRepresent }
            val jibuns = mutableListOf(
                JibunElement(
                    kor = representJibun?.jibunNameKor ?: "",
                    eng = representJibun?.jibunNameEng ?: "",
                )
            )
            sameManagementNumInfos.map {
                jibuns.add(
                    JibunElement(
                        kor = it.jibunNameKor,
                        eng = it.jibunNameEng,
                    )
                )
            }

            address.toAddressElement(jibuns)
        }.distinct()

        return SearchAddressResponse(response)
    }

    private fun checkLanguage(keyword: String) =
        if (keyword.matches("^[^a-zA-Z]*$".toRegex())) KOREAN else ENGLISH

    private fun SearchAddressVo.toAddressElement(jibuns: MutableList<JibunElement>) = AddressElement(
        type = 0,
        postalCode = this.postalCode,
        representAddressNameKor = this.representAddressNameKor,
        representAddressNameEng = this.representAddressNameEng,
        jibuns = jibuns.distinct(),
    )
}
