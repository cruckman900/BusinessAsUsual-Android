package com.example.data.datasource

import com.example.domain.model.Module
import com.example.domain.model.ModuleIcon

class FakeModuleDataSource : ModuleDataSource {

    override fun getModules(): List<Module> =
        listOf(
            Module(
                id = "hr",
                name = "HR",
                description = "Manage employees, onboarding, and benefits",
                icon = ModuleIcon.HR
            ),
            Module(
                id = "finance",
                name = "Finance",
                description = "Invoices, payroll, and reporting",
                icon = ModuleIcon.FINANCE
            ),
            Module(
                id = "crm",
                name = "CRM",
                description = "Customer relationships and communication",
                icon = ModuleIcon.CRM
            )
        )
}