package com.example.data.datasource

import com.example.domain.model.HrAction

interface HrDataSource {
    fun getHrActions(): List<HrAction>
}