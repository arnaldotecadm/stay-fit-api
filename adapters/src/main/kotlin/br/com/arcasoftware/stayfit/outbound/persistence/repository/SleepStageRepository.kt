package br.com.arcasoftware.stayfit.outbound.persistence.repository

import br.com.arcasoftware.stayfit.outbound.persistence.model.SleepStageEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SleepStageRepository : JpaRepository<SleepStageEntity, Long>
