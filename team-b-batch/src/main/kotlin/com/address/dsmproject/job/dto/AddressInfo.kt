package com.address.dsmproject.job.dto

import com.address.dsmproject.domain.roadNumber.RoadNumberEntity
import com.address.dsmproject.domain.roadNumber.type.RoadNumberType

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
    val buildingName: String?,
)

data class AddressJibunInfo(
    val mainJibunNumber: Int,
    val subJibunNumber: Int,
    val represents: Boolean
)

data class AddressInfo(
    val common: AddressCommonInfo,
    val road: AddressRoadInfo,
    val jibuns: MutableList<AddressJibunInfo>
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
                buildingName = split[21] ?: split[22]
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

fun AddressInfo.toRoadNumberEntity(managementNumber: String): List<RoadNumberEntity> {
    val commonKorFullText =
        this.common.cityProvinceName + this.common.countyDistricts + this.common.eupMyeonDong + this.common.beobJeongLi
    val commonEngFullText =
        this.common.addressEngInfo?.cityProvinceNameEng + this.common.addressEngInfo?.countyDistrictsEng + this.common.addressEngInfo?.eupMyeonDongEng + this.common.addressEngInfo?.beobJeongLiEng
    val addressKorFullText =
        commonKorFullText + this.road.mainBuildingNumber + this.road.subBuildingNumber + this.road.buildingName
    val addressEngFullText =
        commonEngFullText + this.road.mainBuildingNumber + this.road.subBuildingNumber + this.road.buildingName
    return this.jibuns.map { jibun ->
        val jibunKorFullText = commonKorFullText + jibun.mainJibunNumber + jibun.subJibunNumber
        val jibunEngFullText = commonEngFullText + jibun.mainJibunNumber + jibun.subJibunNumber

        RoadNumberEntity(
            cityProvinceName = this.common.cityProvinceName,
            countyDistricts = this.common.countyDistricts,
            eupMyeonDong = this.common.eupMyeonDong,
            beobJeongLi = this.common.beobJeongLi,
            cityProvinceNameEng = this.common.addressEngInfo?.cityProvinceNameEng,
            countyDistrictsEng = this.common.addressEngInfo?.countyDistrictsEng,
            eupMyeonDongEng = this.common.addressEngInfo?.eupMyeonDongEng,
            beobJeongLiEng = this.common.addressEngInfo?.beobJeongLiEng,
            isRepresent = jibun.represents,
            postalCode = this.common.postalCode,
            roadName = this.common.roadName,
            roadNameEng = this.common.addressEngInfo?.roadNameEng,
            buildingName = this.road.buildingName,
            mainJibunNumber = jibun.mainJibunNumber,
            subJibunNumber = jibun.subJibunNumber,
            type = RoadNumberType.JIBUN,
            korFullText = jibunKorFullText,
            engFullText = jibunEngFullText,
            managementNumber = managementNumber,
        )
    }.plus(
        RoadNumberEntity(
            cityProvinceName = this.common.cityProvinceName,
            countyDistricts = this.common.countyDistricts,
            eupMyeonDong = this.common.eupMyeonDong,
            beobJeongLi = this.common.beobJeongLi,
            cityProvinceNameEng = this.common.addressEngInfo?.cityProvinceNameEng,
            countyDistrictsEng = this.common.addressEngInfo?.countyDistrictsEng,
            eupMyeonDongEng = this.common.addressEngInfo?.eupMyeonDongEng,
            beobJeongLiEng = this.common.addressEngInfo?.beobJeongLiEng,
            mainBuildingNumber = this.road.mainBuildingNumber,
            subBuildingNumber = this.road.subBuildingNumber,
            postalCode = this.common.postalCode,
            roadName = this.common.roadName,
            roadNameEng = this.common.addressEngInfo?.roadNameEng,
            buildingName = this.road.buildingName,
            type = RoadNumberType.ROAD_NAME,
            korFullText = addressKorFullText,
            engFullText = addressEngFullText,
            managementNumber = managementNumber,
        )
    )
}
