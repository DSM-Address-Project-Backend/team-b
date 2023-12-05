package com.address.dsmproject.job.dto

import com.address.dsmproject.domain.roadNumber.RoadNumberEntity
import com.address.dsmproject.domain.roadNumber.type.RoadNumberType
import java.util.UUID

data class AddressEngInfo(
    var cityProvinceNameEng: String,
    var countyDistrictsEng: String,
    var eupMyeonDongEng: String,
    var beobJeongLiEng: String,
    var roadNameEng: String,
)

data class AddressCommonInfo(
    val cityProvinceName: String,
    val countyDistricts: String,
    val eupMyeonDong: String,
    val beobJeongLi: String,
    val postalCode: Int,
    val roadName: String,
    var addressEngInfo: AddressEngInfo? = null,
)

data class AddressRoadInfo(
    val mainBuildingNumber: Int,
    val subBuildingNumber: Int,
    val buildingName: String,
)

data class AddressJibunInfo(
    val mainJibunNumber: Int,
    val subJibunNumber: Int,
    val represents: Boolean
)

data class AddressInfo( // AddressInfo 하나당 도로명 하나, 지번 여러 개
    val common: AddressCommonInfo, // 1개
    val road: AddressRoadInfo, // 1개
    val jibuns: MutableList<AddressJibunInfo> // 1 ~ N개
) {
    companion object {
        fun of(split: List<String>) = AddressInfo(
            common = AddressCommonInfo(
                cityProvinceName = split[2],
                countyDistricts = split[3],
                eupMyeonDong = split[4],
                beobJeongLi = split[5],
                postalCode = split[16].toInt(),
                roadName = split[10],
            ),
            road = AddressRoadInfo(
                mainBuildingNumber = split[12].toInt(),
                subBuildingNumber = split[13].toInt(),
                buildingName = split[21]
            ),
            jibuns = mutableListOf(
                AddressJibunInfo(
                    mainJibunNumber = split[7].toInt(),
                    subJibunNumber = split[8].toInt(),
                    represents = true
                )
            )
        )
    }
}

fun AddressInfo.toRoadNumberEntity(
    isRepresent: Boolean,
    mainJibunNumber: Int,
    subJibunNumber: Int,
    type: RoadNumberType,
    korFullText: String,
    engFullText: String,
    managementNumber: String,
) = RoadNumberEntity(
    id = UUID.randomUUID(),
    cityProvinceName = this.common.cityProvinceName,
    countyDistricts = this.common.countyDistricts,
    eupMyeonDong = this.common.eupMyeonDong,
    beobJeongLi = this.common.beobJeongLi,
    cityProvinceNameEng = this.common.addressEngInfo!!.cityProvinceNameEng,
    countyDistrictsEng = this.common.addressEngInfo!!.countyDistrictsEng,
    eupMyeonDongEng = this.common.addressEngInfo!!.eupMyeonDongEng,
    beobJeongLiEng = this.common.addressEngInfo!!.beobJeongLiEng,
    isRepresent = isRepresent,
    mainBuildingNumber = this.road.mainBuildingNumber,
    subBuildingNumber = this.road.subBuildingNumber,
    postalCode = this.common.postalCode,
    roadName = this.common.roadName,
    roadNameEng = this.common.addressEngInfo!!.roadNameEng,
    buildingName = this.road.buildingName,
    mainJibunNumber = mainJibunNumber,
    subJibunNumber = subJibunNumber,
    type = type,
    korFullText = korFullText,
    engFullText = engFullText,
    managementNumber = managementNumber,
)
