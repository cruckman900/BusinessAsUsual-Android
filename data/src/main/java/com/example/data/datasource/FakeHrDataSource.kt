package com.example.data.datasource

import com.example.domain.model.HrAction

class FakeHrDataSource {

    fun getHrActions(): List<HrAction> =
        listOf(
            HrAction(
                id = "onboarding",
                title = "Onboard Employee",
                description = "Start the onboarding process for a new hire."
            ),
            HrAction(
                id = "directory",
                title = "Employee Directory",
                description = "Browse and manage employee records."
            ),
            HrAction(
                id = "benefits",
                title = "Benefits Management",
                description = "Manage employee benefits and enrollment."
            )
        )
}