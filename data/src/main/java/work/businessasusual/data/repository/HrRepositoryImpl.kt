package work.businessasusual.data.repository

import work.businessasusual.data.datasource.HrDataSource
import work.businessasusual.domain.model.HrAction
import work.businessasusual.domain.repository.HrRepository

class HrRepositoryImpl(
    private val dataSource: HrDataSource
) : HrRepository {

    override suspend fun getHrActions(): List<HrAction> {
        return dataSource.getHrActions()
    }
}