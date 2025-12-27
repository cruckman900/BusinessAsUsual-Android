package com.example.domain.repository

import com.example.domain.model.Module

interface ModuleRepository {
    suspend fun getModules(): List<Module>
}