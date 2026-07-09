package com.businessasusual.app.ui.mobileui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.businessasusual.domain.model.ModuleUi
import com.businessasusual.domain.usecase.GetModuleUiUseCase
import com.businessasusual.domain.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MobileUiViewModel(
    private val getModuleUi: GetModuleUiUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<MobileUiState>(MobileUiState.Loading)
    val state: StateFlow<MobileUiState> = _state.asStateFlow()

    init { load() }

    fun load() {
        viewModelScope.launch {
            _state.value = MobileUiState.Loading
            _state.value = when (val res = getModuleUi()) {
                is Resource.Success -> MobileUiState.Success(res.data)
                is Resource.Error -> MobileUiState.Error(res.message)
            }
        }
    }
}

sealed interface MobileUiState {
    data object Loading : MobileUiState
    data class Success(val module: ModuleUi) : MobileUiState
    data class Error(val message: String) : MobileUiState
}