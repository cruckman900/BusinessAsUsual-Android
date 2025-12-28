package com.example.businessasusualandroid.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Module
import com.example.domain.usecase.GetModulesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val getModulesUseCase: GetModulesUseCase
) : ViewModel() {

    private val _modules = MutableStateFlow<List<Module>>(emptyList())
    val modules: StateFlow<List<Module>> = _modules

    init {
        loadModules()
    }

    private fun loadModules() {
        viewModelScope.launch {
            _modules.value = getModulesUseCase()
        }
    }
}