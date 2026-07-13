package work.businessasusual.data.di

import work.businessasusual.data.remote.MobileUiApi
import work.businessasusual.data.remote.ModuleDiscoveryApi
import work.businessasusual.data.repository.MobileUiRepositoryImpl
import work.businessasusual.data.repository.ModuleRepositoryImpl
import work.businessasusual.domain.repository.MobileUiRepository
import work.businessasusual.domain.repository.ModuleRepository
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

// Emulator loopback to your host machine. Point this at your gateway/module services.
private const val BASE_URL = "http://10.0.2.2:5041/"

// ModuleRegistry service used for backend-driven module discovery.
private const val MODULE_REGISTRY_URL = "http://10.0.2.2:5100/"

val dataModule = module {

// ---- Shared networking (JSON + OkHttp) ----
single {
Json {
ignoreUnknownKeys = true   // tolerate new backend fields / contract version bumps
isLenient = true
explicitNulls = false
}
}
single {
OkHttpClient.Builder()
.addInterceptor(HttpLoggingInterceptor().apply {
level = HttpLoggingInterceptor.Level.BODY
})
.build()
}

// ---- Mobile UI contract networking (per-module) ----
single<MobileUiApi> {
val json: Json = get()
Retrofit.Builder()
.baseUrl(BASE_URL)
.client(get())
.addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
.build()
.create(MobileUiApi::class.java)
}
single<MobileUiRepository> { MobileUiRepositoryImpl(get()) }

// ---- Backend-driven module discovery ----
single<ModuleDiscoveryApi> {
val json: Json = get()
Retrofit.Builder()
.baseUrl(MODULE_REGISTRY_URL)
.client(get())
.addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
.build()
.create(ModuleDiscoveryApi::class.java)
}
single<ModuleRepository> { ModuleRepositoryImpl(get()) }
}
