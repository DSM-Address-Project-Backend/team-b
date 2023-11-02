package com.address.dsmproject.domain.parcelNumber

import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface ParcelNumberRepository : CrudRepository<ParcelNumberEntity, UUID> {
}