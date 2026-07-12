package work.businessasusual.ui.hr

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import work.businessasusual.domain.model.HrAction
import work.businessasusual.domain.usecase.GetHrActionsUseCase
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