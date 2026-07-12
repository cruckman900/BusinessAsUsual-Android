package work.businessasusual.domain.di

import work.businessasusual.domain.usecase.GetHrActionsUseCase
import work.businessasusual.domain.usecase.GetModulesUseCase
import work.businessasusual.domain.usecase.GetEmployeeDetailSpecUseCase
import work.businessasusual.domain.usecase.GetEmployeeFormSpecUseCase
import work.businessasusual.domain.usecase.GetEmployeeListSpecUseCase
import work.businessasusual.domain.usecase.GetModuleUiUseCase
import work.businessasusual.domain.usecase.GetNavigationUseCase
import org.koin.dsl.module

val domainModule = module {

	// ---- Existing ----
	factory { GetModulesUseCase(get()) }
	factory { GetHrActionsUseCase(get()) }

	// ---- New: Mobile UI contract use cases ----
	factory { GetModuleUiUseCase(get()) }
	factory { GetNavigationUseCase(get()) }
	factory { GetEmployeeListSpecUseCase(get()) }
	factory { GetEmployeeDetailSpecUseCase(get()) }
	factory { GetEmployeeFormSpecUseCase(get()) }
}
