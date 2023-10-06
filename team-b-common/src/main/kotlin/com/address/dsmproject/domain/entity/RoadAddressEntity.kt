package com.address.dsmproject.domain.entity

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
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BINARY(16)", nullable = false)
    val id: UUID? = id

    @Column(nullable = false)
    val buildingMainNumber: Int = buildingMainNumber

    @Column(nullable = false)
    val buildingSubNumber: Int = buildingSubNumber

    @Column(length = 80, nullable = false)
    val streetNumber: String = streetNumber

    @Column(name = "postal_code")
    val postalCode: Int = postalCode
}