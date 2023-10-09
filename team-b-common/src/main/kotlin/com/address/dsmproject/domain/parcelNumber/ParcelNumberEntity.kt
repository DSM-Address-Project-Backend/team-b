package com.address.dsmproject.domain.parcelNumber

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "tbl_parcel_number")
class ParcelNumberEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "BINARY(16)", nullable = false)
    val id: UUID? = null,

    @Column(columnDefinition = "INT", nullable = false)
    val mainAddressNumber: Int,

    @Column(columnDefinition = "INT", nullable = false)
    val subAddressNumber: Int
)