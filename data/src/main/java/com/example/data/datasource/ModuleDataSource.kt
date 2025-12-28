package com.example.data.datasource

import com.example.domain.model.Module

interface ModuleDataSource {
    fun getModules(): List<Module>
}