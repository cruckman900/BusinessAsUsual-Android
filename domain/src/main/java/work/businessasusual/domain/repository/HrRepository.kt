package work.businessasusual.domain.repository

import work.businessasusual.domain.model.HrAction

interface HrRepository {
    suspend fun getHrActions(): List<HrAction>
}