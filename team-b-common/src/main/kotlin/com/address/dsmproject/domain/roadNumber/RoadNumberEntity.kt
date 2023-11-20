package com.address.dsmproject.domain.roadNumber

import com.address.dsmproject.domain.parcelNumber.ParcelNumberEntity
import com.address.dsmproject.domain.roadAddress.RoadAddressEntity
import jakarta.persistence.*
import java.io.Serializable
import java.util.UUID

@Entity
@Table(name = "tbl_road_number")
class RoadNumberEntity(
    @EmbeddedId
    val roadNumberId: RoadNumberId,

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
    val beobJeongLiEng: String,

    isRepresent: Boolean,

    @MapsId("parcelNumberId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parcel_number_id")
    val parcelNumberEntity: ParcelNumberEntity,

    @MapsId("roadAddressId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "road_address_id")
    val roadAddressEntity: RoadAddressEntity,
) {
    @Column(columnDefinition = "BIT(1)")
    var isRepresent = isRepresent
        protected set
}

@Embeddable
data class RoadNumberId(
    @Column(columnDefinition = "BINARY(16)", nullable = false)
    val parcelNumberId: UUID,

    @Column(columnDefinition = "BINARY(16)", nullable = false)
    val roadAddressId: UUID,
) : Serializable
