package com.address.dsmproject.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.util.*

@Entity
@Table(name = "road_number")
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
    val compositeKey: CompositeKey = CompositeKey(UUID.randomUUID(), UUID.randomUUID())

    @Column(length = 40, nullable = false)
    val cityProvinceName: String = cityProvinceName

    @Column(length = 40, nullable = false)
    val countyDistricts: String = countyDistricts

    @Column(length = 40, nullable = false)
    val eupMyeonDong: String = eupMyeonDong

    @Column(length = 40, nullable = false)
    val beobJeongLi: String = beobJeongLi

    @Column(length = 40, nullable = false)
    val cityProvinceNameEng: String = cityProvinceNameEng

    @Column(length = 40, nullable = false)
    val countyDistrictsEng: String = countyDistrictsEng

    @Column(length = 40, nullable = false)
    val eupMyeonDongEng: String = eupMyeonDongEng

    @Column(length = 40, nullable = false)
    val beobJeongLiEng: String = beobJeongLiEng
}