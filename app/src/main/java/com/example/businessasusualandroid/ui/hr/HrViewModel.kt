package com.example.businessasusualandroid.ui.hr

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.HrAction
import com.example.domain.usecase.GetHrActionsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HrViewModel(
    private val getHrActionsUseCase: GetHrActionsUseCase
) : ViewModel() {

    private val _actions = MutableStateFlow<List<HrAction>>(emptyList())
    val actions: StateFlow<List<HrAction>> = _actions

    init {
        loadActions()
    }

    private fun loadActions() {
        viewModelScope.launch {
            _actions.value = getHrActionsUseCase()
        }
    }
}