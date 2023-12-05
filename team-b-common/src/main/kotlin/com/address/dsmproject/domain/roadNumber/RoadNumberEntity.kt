package com.address.dsmproject.domain.roadNumber

import com.address.dsmproject.domain.roadNumber.type.RoadNumberType
import com.address.dsmproject.global.entity.BaseUUIDEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "tbl_road_number")
class RoadNumberEntity(
    override val id: UUID,

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

    isRepresent: Boolean?,

    @Column(columnDefinition = "INT")
    val mainBuildingNumber: Int?,

    @Column(columnDefinition = "INT")
    val subBuildingNumber: Int?,

    @Column(columnDefinition = "INT")
    val postalCode: Int?,

    @Column(columnDefinition = "VARCHAR(80)")
    val roadName: String?,

    @Column(columnDefinition = "VARCHAR(100)")
    val roadNameEng: String?,

    @Column(columnDefinition = "VARCHAR(40)")
    val buildingName: String?,

    @Column(columnDefinition = "INT")
    val mainJibunNumber: Int?,

    @Column(columnDefinition = "INT")
    val subJibunNumber: Int?,

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(9)", nullable = false)
    val type: RoadNumberType,

    @Column(columnDefinition = "VARCHAR(500)", nullable = false)
    val korFullText: String,

    @Column(columnDefinition = "VARCHAR(500)", nullable = false)
    val engFullText: String,

    @Column(columnDefinition = "CHAR(26)", nullable = false)
    val managementNumber: String,
) : BaseUUIDEntity() {
    @Column(columnDefinition = "BIT(1)")
    var isRepresent = isRepresent
        protected set
}
