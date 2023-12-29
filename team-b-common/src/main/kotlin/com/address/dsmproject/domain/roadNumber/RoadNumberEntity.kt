package com.address.dsmproject.domain.roadNumber

import com.address.dsmproject.domain.roadNumber.type.RoadNumberType
import com.address.dsmproject.global.entity.BaseUUIDEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table

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

    @Column(columnDefinition = "VARCHAR(40)")
    val cityProvinceNameEng: String?,

    @Column(columnDefinition = "VARCHAR(40)")
    val countyDistrictsEng: String?,

    @Column(columnDefinition = "VARCHAR(40)")
    val eupMyeonDongEng: String?,

    @Column(columnDefinition = "VARCHAR(40)")
    val beobJeongLiEng: String?,

    isRepresent: Boolean? = null,

    @Column(columnDefinition = "INT")
    val mainBuildingNumber: Int? = null,

    @Column(columnDefinition = "INT")
    val subBuildingNumber: Int? = null,

    @Column(columnDefinition = "INT")
    val postalCode: Int?,

    @Column(columnDefinition = "VARCHAR(80)")
    val roadName: String?,

    @Column(columnDefinition = "VARCHAR(100)")
    val roadNameEng: String?,

    @Column(columnDefinition = "VARCHAR(40)")
    val buildingName: String?,

    @Column(columnDefinition = "INT")
    val mainJibunNumber: Int? = null,

    @Column(columnDefinition = "INT")
    val subJibunNumber: Int? = null,

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(9)", nullable = false)
    val type: RoadNumberType,

    @Column(columnDefinition = "VARCHAR(500)", nullable = false)
    val korFullText: String,

    @Column
    val korInitialFullText: String,

    @Column(columnDefinition = "VARCHAR(500)", nullable = true)
    val engFullText: String?,

    @Column(columnDefinition = "CHAR(26)", nullable = false)
    val managementNumber: String,
) : BaseUUIDEntity() {
    @Column(columnDefinition = "BIT(1)")
    var isRepresent = isRepresent
        protected set
}
