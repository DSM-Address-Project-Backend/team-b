package com.address.dsmproject.domain.roadAddress

import com.address.dsmproject.global.entity.BaseUUIDEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "tbl_road_address")
class RoadAddressEntity(

    @Column(columnDefinition = "INT", nullable = false)
    val mainBuildingNumber: Int,

    @Column(columnDefinition = "INT", nullable = false)
    val subBuildingNumber: Int,

    @Column(columnDefinition = "INT", nullable = false)
    val postalCode: Int,

    @Column(columnDefinition = "VARCHAR(40)", nullable = false)
    val buildingName: String,
) : BaseUUIDEntity()
