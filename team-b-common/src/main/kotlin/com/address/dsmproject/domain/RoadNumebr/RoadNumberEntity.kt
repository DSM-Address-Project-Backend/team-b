package com.address.dsmproject.domain.RoadNumebr

import com.address.dsmproject.domain.ParcelNumber.ParcelNumberEntity
import com.address.dsmproject.domain.RoadAddress.RoadAddressEntity
import jakarta.persistence.*
import java.io.Serializable
import java.util.*

@Entity
@Table(name = "tbl_road_number")
class RoadNumberEntity(
    cityProvinceName: String,
    countyDistricts: String,
    eupMyeonDong: String,
    beobJeongLi: String,
    cityProvinceNameEng: String,
    countyDistrictsEng: String,
    eupMyeonDongEng: String,
    beobJeongLiEng: String

) {
    @EmbeddedId
    val roadNumberId: RoadNumberId = RoadNumberId(UUID.randomUUID(), UUID.randomUUID())

    @ManyToOne
    @JoinColumn(name = "parcel_number_id")
    val parcelNumberEntity: ParcelNumberEntity? = null

    @ManyToOne
    @JoinColumn(name = "road_address_id")
    val roadAddressEntity: RoadAddressEntity? = null

    @Column(columnDefinition = "VARCHAR(40)", nullable = false)
    val cityProvinceName: String = cityProvinceName

    @Column(columnDefinition = "VARCHAR(40)", nullable = false)
    val countyDistricts: String = countyDistricts

    @Column(columnDefinition = "VARCHAR(40", nullable = false)
    val eupMyeonDong: String = eupMyeonDong

    @Column(columnDefinition = "VARCHAR(40", nullable = false)
    val beobJeongLi: String = beobJeongLi

    @Column(columnDefinition = "VARCHAR(40", nullable = false)
    val cityProvinceNameEng: String = cityProvinceNameEng

    @Column(columnDefinition = "VARCHAR(40", nullable = false)
    val countyDistrictsEng: String = countyDistrictsEng

    @Column(columnDefinition = "VARCHAR(40)", nullable = false)
    val eupMyeonDongEng: String = eupMyeonDongEng

    @Column(columnDefinition = "VARCHAR(40", nullable = false)
    val beobJeongLiEng: String = beobJeongLiEng
}

@Embeddable
class RoadNumberId (
    @Column(columnDefinition = "BINARY(16)", nullable = false)
    val parceNumberId: UUID,

    @Column(columnDefinition = "BINARY(16)", nullable = false)
    val roadAddressId: UUID
): Serializable