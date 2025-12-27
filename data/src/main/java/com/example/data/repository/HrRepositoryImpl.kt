package com.example.data.repository

import com.example.data.datasource.FakeHrDataSource
import com.example.data.datasource.FakeModuleDataSource
import com.example.domain.model.HrAction
import com.example.domain.repository.HrRepository

class HrRepositoryImpl(
    private val dataSource: FakeHrDataSource
) : HrRepository {

    override suspend fun getHrActions(): List<HrAction> {
        return dataSource.getHrActions()
    }
}