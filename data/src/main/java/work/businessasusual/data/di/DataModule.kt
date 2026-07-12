package work.businessasusual.data.di

import work.businessasusual.data.datasource.FakeHrDataSource
import work.businessasusual.data.datasource.FakeModuleDataSource
import work.businessasusual.data.datasource.HrDataSource
import work.businessasusual.data.datasource.ModuleDataSource
import work.businessasusual.data.remote.MobileUiApi
import work.businessasusual.data.repository.HrRepositoryImpl
import work.businessasusual.data.repository.MobileUiRepositoryImpl
import work.businessasusual.data.repository.ModuleRepositoryImpl
import work.businessasusual.domain.repository.HrRepository
import work.businessasusual.domain.repository.MobileUiRepository
import work.businessasusual.domain.repository.ModuleRepository
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

// Emulator loopback to your host machine. Point this at your gateway/HR service.
private const val BASE_URL = "http://10.0.2.2:5001/"

val dataModule = module {

	// ---- Existing: local/fake sources + repositories ----
	single<ModuleDataSource> { FakeModuleDataSource() }
	single<HrDataSource> { FakeHrDataSource() }
	single<ModuleRepository> { ModuleRepositoryImpl(get()) }
	single<HrRepository> { HrRepositoryImpl(get()) }

	// ---- New: Mobile UI contract networking ----
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
}
