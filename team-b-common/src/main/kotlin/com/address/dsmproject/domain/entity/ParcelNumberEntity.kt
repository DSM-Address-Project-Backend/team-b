package com.address.dsmproject.domain.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "parcel_number")
class ParcelNumberEntity(
    id: UUID? = null,
    mainAddressNumber: Int,
    subAddressNumber: Int,
    buildingName: String

) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BINARY(16)", nullable = false)
    val id: UUID? = id

    @Column(nullable = false)
    val mainAddressNumber: Int = mainAddressNumber

    @Column(nullable = false)
    val subAddressNumber: Int = subAddressNumber

    @Column(length = 40, nullable = false)
    val buildingName: String = buildingName
}