package com.address.dsmproject.domain.roadAddress

import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface RoadAddressRepository : CrudRepository<RoadAddressEntity, UUID> {
}