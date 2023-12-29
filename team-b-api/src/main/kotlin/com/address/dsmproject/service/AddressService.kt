package com.address.dsmproject.service

import com.address.dsmproject.domain.roadNumber.repository.AddressRepository
import com.address.dsmproject.domain.roadNumber.repository.vo.SearchAddressVo
import com.address.dsmproject.domain.roadNumber.type.RoadNumberType
import com.address.dsmproject.presentation.dto.response.AutoCompletionsResponse
import com.address.dsmproject.presentation.dto.response.SearchAddressResponse
import com.address.dsmproject.presentation.dto.response.SearchAddressResponse.AddressElement
import com.address.dsmproject.presentation.dto.response.SearchAddressResponse.AddressElement.JibunElement
import com.address.dsmproject.presentation.dto.response.TotalPageCountResponse
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
            KOREAN -> addressRepository.autoCompletionWithLanguageType(keyword, KOREAN)
            else -> addressRepository.autoCompletionWithLanguageType(keyword, ENGLISH)
        }

        val response = result.map { "${it.cityProvinceName} ${it.countyDistricts} ${it.eupMyeonDong} ${it.roadName}" }.distinct()

        return AutoCompletionsResponse(response)
    }

    fun searchAddress(page: Int, keyword: String): SearchAddressResponse {
        val languageType = checkLanguage(keyword)

        val result = when (languageType) {
            KOREAN -> addressRepository.searchAddressWithLanguageType(page, keyword, KOREAN)
            else -> addressRepository.searchAddressWithLanguageType(page, keyword, ENGLISH)
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

    private fun SearchAddressVo.toAddressElement(jibuns: MutableList<JibunElement>) = AddressElement(
        type = 0,
        postalCode = this.postalCode,
        representAddressNameKor = this.representAddressNameKor,
        representAddressNameEng = this.representAddressNameEng,
        jibuns = jibuns.distinct(),
    )

    fun getTotalPageCount(keyword: String): TotalPageCountResponse {
        val languageType = checkLanguage(keyword)
        val totalPageCount = when (languageType) {
            KOREAN -> addressRepository.getTotalPageCount(keyword, KOREAN)
            else -> addressRepository.getTotalPageCount(keyword, ENGLISH)
        }

        return TotalPageCountResponse(totalPageCount.toInt())
    }

    private fun checkLanguage(keyword: String) =
        if (keyword.matches("^[^a-zA-Z]*$".toRegex())) KOREAN else ENGLISH
}
