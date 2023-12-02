package com.address.dsmproject.job.dto

data class AddressCommonInfo(
    val cityProvinceName: String,
    val countyDistricts: String,
    val eupMyeonDong: String,
    val beobJeongLi: String,
    val postalCode: String,
    val roadName: String,
    var cityProvinceNameEng: String? = "",
    var countyDistrictsEng: String? = "",
    var eupMyeonDongEng: String? = "",
    var beobJeongLiEng: String? = "",
    var roadNameEng: String? = ""
) {
    fun updateEngInfo(
        cityProvinceNameEng: String, countyDistrictsEng: String,
        eupMyeonDongEng: String, beobJeongLiEng: String, roadNameEng: String
    ) {
        this.cityProvinceNameEng = cityProvinceNameEng
        this.countyDistrictsEng = countyDistrictsEng
        this.eupMyeonDongEng = eupMyeonDongEng
        this.beobJeongLiEng = beobJeongLiEng
        this.roadNameEng = roadNameEng
    }
}

data class AddressRoadInfo(
    val mainBuildingNumber: String,
    val subBuildingNumber: String,
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
        fun build(split: List<String>): AddressInfo {
            return AddressInfo(
                common = AddressCommonInfo(
                    cityProvinceName = split[2],
                    countyDistricts = split[3],
                    eupMyeonDong = split[4],
                    beobJeongLi = split[5],
                    postalCode = split[16],
                    roadName = split[10]
                ),
                road = AddressRoadInfo(
                    mainBuildingNumber = split[12],
                    subBuildingNumber = split[13],
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
}
