package com.example.domain.repository

import com.example.domain.model.HrAction

interface HrRepository {
    suspend fun getHrActions(): List<HrAction>
}