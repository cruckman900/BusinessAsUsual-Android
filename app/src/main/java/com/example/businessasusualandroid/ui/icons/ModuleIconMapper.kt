package com.example.businessasusualandroid.ui.icons

import com.example.domain.model.ModuleIcon
import com.example.businessasusualandroid.R

fun ModuleIcon.toDrawableRes(): Int =
    when (this) {
        ModuleIcon.HR -> R.drawable.ic_hr
        ModuleIcon.FINANCE -> R.drawable.ic_finance
        ModuleIcon.CRM -> R.drawable.ic_crm
    }