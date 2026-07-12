package work.businessasusual.domain.model

data class Module(
    val id: String,
    val name: String,
    val description: String,
    val icon: ModuleIcon // Android drawable resource ID
)