package work.businessasusual.domain.usecase

import work.businessasusual.domain.model.HrAction
import work.businessasusual.domain.repository.HrRepository

class GetHrActionsUseCase(
    private val repository: HrRepository
) {
    suspend operator fun invoke(): List<HrAction> {
        return repository.getHrActions()
    }
}