package work.businessasusual.ui.icons

import work.businessasusual.domain.model.ModuleIcon
import work.businessasusual.R

fun ModuleIcon.toDrawableRes(): Int =
    when (this) {
        ModuleIcon.HR -> R.drawable.ic_hr
        ModuleIcon.FINANCE -> R.drawable.ic_finance
        ModuleIcon.CRM -> R.drawable.ic_crm
    }