package work.businessasusual.data.datasource

import work.businessasusual.domain.model.HrAction

interface HrDataSource {
    fun getHrActions(): List<HrAction>
}