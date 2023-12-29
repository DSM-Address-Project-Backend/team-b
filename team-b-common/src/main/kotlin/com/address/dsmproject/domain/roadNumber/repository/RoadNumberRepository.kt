package com.address.dsmproject.domain.roadNumber.repository

import com.address.dsmproject.domain.roadNumber.RoadNumberEntity
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.util.UUID

interface RoadNumberRepository : JpaRepository<RoadNumberEntity, UUID> {
    @Modifying
    @Transactional
    @Query(value = "truncate table tbl_road_number", nativeQuery = true)
    fun truncateTable()
}
