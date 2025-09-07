package br.com.arcasoftware.stayfit.outbound.persistence.model

import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import java.util.UUID

@MappedSuperclass
open class BaseEntity(
    @Id
    @GeneratedValue
    open val id: Long? = null,
    open val dataPointUid: UUID? = null
)
