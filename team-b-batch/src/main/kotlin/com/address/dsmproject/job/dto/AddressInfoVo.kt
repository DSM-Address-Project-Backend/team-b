package com.address.dsmproject.job.dto

import com.address.dsmproject.domain.parcelNumber.ParcelNumberEntity
import com.address.dsmproject.domain.roadAddress.RoadAddressEntity
import com.address.dsmproject.domain.roadNumber.RoadNumberEntity

data class AddressInfoVo(
    val parcelNumberEntity: ParcelNumberEntity,
    val roadAddressEntity: RoadAddressEntity,
    val roadNumberEntity: RoadNumberEntity,
)
