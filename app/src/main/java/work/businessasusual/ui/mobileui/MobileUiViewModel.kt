package work.businessasusual.ui.mobileui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import work.businessasusual.domain.model.ModuleUi
import work.businessasusual.domain.usecase.GetModuleUiUseCase
import work.businessasusual.domain.usecase.GetScreenDataUseCase
import work.businessasusual.domain.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MobileUiViewModel(
private val moduleId: String,
private val getModuleUi: GetModuleUiUseCase,
private val getScreenData: GetScreenDataUseCase,
) : ViewModel() {

private val _state = MutableStateFlow<MobileUiState>(MobileUiState.Loading)
val state: StateFlow<MobileUiState> = _state.asStateFlow()

// Cache of screenKey -> rows so switching between list screens is instant after first load.
private val _screenData = MutableStateFlow<Map<String, List<Map<String, String>>>>(emptyMap<String, List<Map<String, String>>>())
val screenData: StateFlow<Map<String, List<Map<String, String>>>> = _screenData.asStateFlow()

init { load() }

fun load() {
viewModelScope.launch {
_state.value = MobileUiState.Loading
_state.value = when (val res = getModuleUi(moduleId)) {
is Resource.Success -> MobileUiState.Success(res.data)
is Resource.Error -> MobileUiState.Error(res.message)
}
}
}

/** Lazily loads (and caches) the rows for a given list screen. */
fun loadScreenData(screenKey: String) {
if (_screenData.value.containsKey(screenKey)) return
viewModelScope.launch {
when (val res = getScreenData(moduleId, screenKey)) {
is Resource.Success -> _screenData.update { it + (screenKey to res.data) }
is Resource.Error -> _screenData.update { it + (screenKey to emptyList()) }
}
}
}
}

sealed interface MobileUiState {
data object Loading : MobileUiState
data class Success(val module: ModuleUi) : MobileUiState
data class Error(val message: String) : MobileUiState
}
