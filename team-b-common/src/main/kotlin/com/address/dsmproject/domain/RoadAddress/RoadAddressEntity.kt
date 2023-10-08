package com.address.dsmproject.domain.RoadAddress

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "tbl_road_address")
class RoadAddressEntity (
    id: UUID? = null,
    buildingMainNumber: Int,
    buildingSubNumber: Int,
    streetNumber: String,
    postalCode: Int
) {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "BINARY(16)", nullable = false)
    val id: UUID? = id

    @Column(nullable = false)
    val buildingMainNumber: Int = buildingMainNumber

    @Column(nullable = false)
    val buildingSubNumber: Int = buildingSubNumber

    @Column(columnDefinition = "VARCHAR(80)", nullable = false)
    val streetNumber: String = streetNumber

    @Column(name = "postal_code")
    val postalCode: Int = postalCode
}