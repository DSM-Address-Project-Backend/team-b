package com.address.dsmproject.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.io.Serializable
import java.util.*

@Embeddable
class CompositeKey (
    @Column(columnDefinition = "BINARY(16)", nullable = false)
    val roadNameAddressId: UUID,

    @Column(columnDefinition = "BINARY(16)", nullable = false)
    val parceNumberId: UUID
): Serializable