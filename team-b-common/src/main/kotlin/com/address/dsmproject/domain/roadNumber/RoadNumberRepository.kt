package com.address.dsmproject.domain.roadNumber

import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface RoadNumberRepository : CrudRepository<RoadNumberEntity, UUID>
