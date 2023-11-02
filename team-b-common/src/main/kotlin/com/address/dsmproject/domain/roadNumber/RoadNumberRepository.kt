package com.address.dsmproject.domain.roadNumber

import org.springframework.data.repository.CrudRepository

interface RoadNumberRepository : CrudRepository<RoadNumberEntity, RoadNumberId> {
}