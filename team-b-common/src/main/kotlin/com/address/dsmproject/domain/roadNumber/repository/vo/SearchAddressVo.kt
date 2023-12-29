package com.address.dsmproject.domain.roadNumber.repository.vo

import com.address.dsmproject.domain.roadNumber.type.RoadNumberType
import com.querydsl.core.annotations.QueryProjection

data class SearchAddressVo @QueryProjection constructor(
    val postalCode: Int,
    val representAddressNameKor: String,
    val representAddressNameEng: String,
    val jibunNameKor: String,
    val jibunNameEng: String,
    val isRepresent: Boolean,
    val managementNumber: String,
    val type: RoadNumberType,
)
