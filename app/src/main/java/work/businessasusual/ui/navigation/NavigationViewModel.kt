package work.businessasusual.ui.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import work.businessasusual.domain.usecase.GetModulesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Supplies the navigation drawer with menu items sourced from backend
 * module discovery.
 */
class NavigationViewModel(
    private val getModulesUseCase: GetModulesUseCase
) : ViewModel() {

    private val _items = MutableStateFlow(listOf(dashboardNavigationItem))
    val items: StateFlow<List<NavigationItem>> = _items

    init {
        loadItems()
    }

    private fun loadItems() {
        viewModelScope.launch {
            _items.value = buildNavigationItems(getModulesUseCase())
        }
    }
}
