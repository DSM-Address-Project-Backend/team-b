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
    val buildingName: String,
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
        fun of(split: List<String>): AddressInfo =
            AddressInfo(
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
                    buildingName = if (split[21] == "") split[22] else split[21]
                ),
                jibuns = mutableListOf(
                    AddressJibunInfo(
                        mainJibunNumber = split[7].toInt(),
                        subJibunNumber = split[8].toInt(),
                        represents = true,
                    )
                )
            )
    }
}


fun AddressInfo.toRoadNumberEntity(managementNumber: String): List<RoadNumberEntity> {
    val addressKorFullText: String = common.cityProvinceName
        .addStringIf(common.countyDistricts.isNotBlank(), common.countyDistricts)
        .addStringIf(true, "${common.eupMyeonDong} ${common.roadName} ${road.mainBuildingNumber}")
        .addStringIf(road.subBuildingNumber != 0, road.subBuildingNumber.toString(), "-")
        .addStringIf(road.buildingName.isNotBlank(), "(${road.buildingName})")

    val addressEngFullText: String? = common.addressEngInfo?.let {
        road.mainBuildingNumber.toString()
            .addStringIf(road.subBuildingNumber != 0, road.subBuildingNumber.toString(), "-")
            .addStringIf(true, "${it.roadNameEng}, ${it.eupMyeonDongEng}", ", ")
            .addStringIf(it.countyDistrictsEng.isNotBlank(), it.countyDistrictsEng, ", ")
            .addStringIf(true, "${it.cityProvinceNameEng}, Korea", ", ")
    }

    return this.jibuns.map {
        val jibunKorFullText = common.cityProvinceName
            .addStringIf(common.countyDistricts.isNotBlank(), common.countyDistricts)
            .addStringIf(true, common.eupMyeonDong)
            .addStringIf(common.beobJeongLi.isNotBlank(), common.beobJeongLi)
            .addStringIf(true, it.mainJibunNumber.toString())
            .addStringIf(it.subJibunNumber != 0, it.subJibunNumber.toString(), "-")

        val jibunEngFullText = common.addressEngInfo?.let { eng ->
            it.mainJibunNumber.toString()
                .addStringIf(it.subJibunNumber != 0, "${it.subJibunNumber}", "-")
                .addStringIf(eng.beobJeongLiEng.isNotBlank(), eng.beobJeongLiEng, ", ")
                .addStringIf(true, eng.eupMyeonDongEng, ", ")
                .addStringIf(eng.countyDistrictsEng.isNotBlank(), eng.countyDistrictsEng, ", ")
                .addStringIf(true, "${eng.cityProvinceNameEng}, Korea", ", ")
        }

        RoadNumberEntity(
            cityProvinceName = this.common.cityProvinceName,
            countyDistricts = this.common.countyDistricts,
            eupMyeonDong = this.common.eupMyeonDong,
            beobJeongLi = this.common.beobJeongLi,
            cityProvinceNameEng = this.common.addressEngInfo?.cityProvinceNameEng,
            countyDistrictsEng = this.common.addressEngInfo?.countyDistrictsEng,
            eupMyeonDongEng = this.common.addressEngInfo?.eupMyeonDongEng,
            beobJeongLiEng = this.common.addressEngInfo?.beobJeongLiEng,
            isRepresent = it.represents,
            postalCode = this.common.postalCode,
            roadName = this.common.roadName,
            roadNameEng = this.common.addressEngInfo?.roadNameEng,
            buildingName = this.road.buildingName,
            mainJibunNumber = it.mainJibunNumber,
            subJibunNumber = it.subJibunNumber,
            type = RoadNumberType.JIBUN,
            korFullText = jibunKorFullText,
            engFullText = jibunEngFullText,
            managementNumber = managementNumber,
            korInitialFullText = ""
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
            korInitialFullText = ""
        )
    )
}

fun String.addStringIf(condition: Boolean, target: String, prefix: String = " "): String =
    if (condition) this + prefix + target else this
