package work.businessasusual.ui.icons

import work.businessasusual.R

/**
 * Resolves a backend-provided icon key to a local drawable resource.
 * Unknown or null keys fall back to a generic module icon so newly
 * discovered modules always render.
 */
fun iconKeyToDrawableRes(iconKey: String?): Int =
    when (iconKey?.trim()?.lowercase()) {
        "hr", "people", "person", "employees" -> R.drawable.ic_hr
        "finance", "money", "attach_money", "payroll" -> R.drawable.ic_finance
        "crm", "business", "business_center", "customers" -> R.drawable.ic_crm
        else -> R.drawable.ic_module
    }
