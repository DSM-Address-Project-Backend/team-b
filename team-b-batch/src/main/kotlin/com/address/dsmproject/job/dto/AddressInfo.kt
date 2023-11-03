package com.address.dsmproject.job.dto

import com.address.dsmproject.domain.parcelNumber.ParcelNumberEntity
import com.address.dsmproject.domain.roadAddress.RoadAddressEntity
import com.address.dsmproject.domain.roadNumber.RoadNumberEntity
import com.address.dsmproject.domain.roadNumber.RoadNumberId
import java.util.UUID

data class AddressInfo(
    val mainBuildingNumber: String,
    val subBuildingNumber: String,
    val postalCode: Int,
    val buildingName: String,
    val cityProvinceName: String,
    val countyDistricts: String,
    val eupMyeonDong: String,
    val beobJeongLi: String,
    val cityProvinceNameEng: String,
    val countyDistrictsEng: String,
    val eupMyeonDongEng: String,
    val beobJeongLiEng: String,
    val mainAddressNumber: Int,
    val subAddressNumber: Int,
    val streetNumber: String,
)

fun AddressInfo.toParcelNumberEntity() = ParcelNumberEntity(
    id = UUID.randomUUID(),
    mainAddressNumber = this.mainAddressNumber,
    subAddressNumber = this.subAddressNumber
)

fun AddressInfo.toRoadNumberEntity(
    parcelNumber: ParcelNumberEntity,
    roadAddressEntity: RoadAddressEntity,
) = RoadNumberEntity(
    roadNumberId = RoadNumberId(
        parcelNumberId = parcelNumber.id,
        roadAddressId = roadAddressEntity.id
    ),
    cityProvinceName = this.cityProvinceName,
    countyDistricts = this.countyDistricts,
    eupMyeonDong = this.eupMyeonDong,
    beobJeongLi = this.beobJeongLi,
    cityProvinceNameEng = this.cityProvinceNameEng,
    countyDistrictsEng = this.countyDistrictsEng,
    eupMyeonDongEng = this.eupMyeonDongEng,
    beobJeongLiEng = this.beobJeongLiEng,
    parcelNumberEntity = parcelNumber,
    roadAddressEntity = roadAddressEntity
)

fun AddressInfo.toRoadAddressEntity() = RoadAddressEntity(
    id = UUID.randomUUID(),
    mainBuildingNumber = this.mainAddressNumber,
    subBuildingNumber = this.subAddressNumber,
    postalCode = this.postalCode,
    buildingName = this.buildingName,
    streetNumber = this.streetNumber,
)
