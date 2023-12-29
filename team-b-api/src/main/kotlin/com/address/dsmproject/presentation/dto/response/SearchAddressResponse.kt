package com.address.dsmproject.presentation.dto.response

data class SearchAddressResponse(
    val items: List<AddressElement>,
) {
    data class AddressElement(
        val type: Int,
        val postalCode: Int,
        val representAddressNameKor: String,
        val representAddressNameEng: String,
        val jibuns: List<JibunElement>,
    ) {
        data class JibunElement(
            val kor: String,
            val eng: String,
        )
    }
}
