package com.address.dsmproject.domain.parcelNumber

import com.address.dsmproject.global.entity.BaseUUIDEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "tbl_parcel_number")
class ParcelNumberEntity(

    @Column(columnDefinition = "INT", nullable = false)
    val mainAddressNumber: Int,

    @Column(columnDefinition = "INT", nullable = false)
    val subAddressNumber: Int,
) : BaseUUIDEntity()
