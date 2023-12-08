package com.address.dsmproject.domain.roadNumber

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface RoadNumberRepository : JpaRepository<RoadNumberEntity, UUID>
