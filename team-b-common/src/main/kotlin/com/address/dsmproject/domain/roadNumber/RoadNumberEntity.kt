package com.address.dsmproject.domain.roadNumber

import com.address.dsmproject.domain.parcelNumber.ParcelNumberEntity
import com.address.dsmproject.domain.roadAddress.RoadAddressEntity
import com.address.dsmproject.global.entity.BaseUUIDEntity
import jakarta.persistence.*

@Entity
@Table(name = "tbl_road_number")
class RoadNumberEntity(
    @Column(columnDefinition = "VARCHAR(40)", nullable = false)
    val cityProvinceName: String,

    @Column(columnDefinition = "VARCHAR(40)", nullable = false)
    val countyDistricts: String,

    @Column(columnDefinition = "VARCHAR(40)", nullable = false)
    val eupMyeonDong: String,

    @Column(columnDefinition = "VARCHAR(40)", nullable = false)
    val beobJeongLi: String,

    @Column(columnDefinition = "VARCHAR(40)", nullable = false)
    val cityProvinceNameEng: String,

    @Column(columnDefinition = "VARCHAR(40)", nullable = false)
    val countyDistrictsEng: String,

    @Column(columnDefinition = "VARCHAR(40)", nullable = false)
    val eupMyeonDongEng: String,

    @Column(columnDefinition = "VARCHAR(40)", nullable = false)
    val beobJeongLiEng: String
) : BaseUUIDEntity() {

    @MapsId
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parcel_number_id")
    val parcelNumberEntity: ParcelNumberEntity? = null

    @MapsId
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "road_address_id")
    val roadAddressEntity: RoadAddressEntity? = null
}
