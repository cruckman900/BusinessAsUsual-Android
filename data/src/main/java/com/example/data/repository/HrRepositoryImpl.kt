package com.example.data.repository

import com.example.data.datasource.HrDataSource
import com.example.domain.model.HrAction
import com.example.domain.repository.HrRepository

class HrRepositoryImpl(
    private val dataSource: HrDataSource
) : HrRepository {

    override suspend fun getHrActions(): List<HrAction> {
        return dataSource.getHrActions()
    }
}