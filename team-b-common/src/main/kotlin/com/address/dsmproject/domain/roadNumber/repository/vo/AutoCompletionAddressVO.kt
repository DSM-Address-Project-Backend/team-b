package com.address.dsmproject.domain.roadNumber.repository.vo

import com.querydsl.core.annotations.QueryProjection

data class AutoCompletionAddressVO @QueryProjection constructor(
    val cityProvinceName: String,
    val countyDistricts: String,
    val eupMyeonDong: String,
    val roadName: String
)