package work.businessasusual.data.di

import work.businessasusual.data.remote.EndpointConfig
import work.businessasusual.data.remote.FailoverInterceptor
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
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

// Retrofit needs a syntactically valid base URL, but the actual host is chosen at
// runtime by FailoverInterceptor from EndpointConfig. We use the first (preferred)
// candidate as the placeholder base; if none are configured we fall back to a dummy.
private fun placeholderBase(candidates: List<String>): String =
    candidates.firstOrNull() ?: "http://localhost/"

private val HR_CLIENT = named("hrClient")
private val REGISTRY_CLIENT = named("registryClient")

val dataModule = module {

    // ---- Shared JSON ----
    single {
        Json {
            ignoreUnknownKeys = true   // tolerate new backend fields / contract version bumps
            isLenient = true
            explicitNulls = false
        }
    }

    // ---- Per-service OkHttp clients with live -> local failover ----
    single(HR_CLIENT) {
        OkHttpClient.Builder()
            .addInterceptor(FailoverInterceptor(EndpointConfig.hrCandidates))
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }
    single(REGISTRY_CLIENT) {
        OkHttpClient.Builder()
            .addInterceptor(FailoverInterceptor(EndpointConfig.registryCandidates))
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    // ---- Mobile UI contract networking (per-module) ----
    single<MobileUiApi> {
        val json: Json = get()
        Retrofit.Builder()
            .baseUrl(placeholderBase(EndpointConfig.hrCandidates))
            .client(get(HR_CLIENT))
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(MobileUiApi::class.java)
    }
    single<MobileUiRepository> { MobileUiRepositoryImpl(get()) }

    // ---- Backend-driven module discovery ----
    single<ModuleDiscoveryApi> {
        val json: Json = get()
        Retrofit.Builder()
            .baseUrl(placeholderBase(EndpointConfig.registryCandidates))
            .client(get(REGISTRY_CLIENT))
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(ModuleDiscoveryApi::class.java)
    }
    single<ModuleRepository> { ModuleRepositoryImpl(get()) }
}
