package br.com.arcasoftware.stayfit.outbound.persistence.repository

import br.com.arcasoftware.stayfit.outbound.persistence.model.SleepSessionEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface SleepSessionRepository : JpaRepository<SleepSessionEntity, Long>{
    fun findByDataPointUid(dataPointUid: UUID): SleepSessionEntity?
}
