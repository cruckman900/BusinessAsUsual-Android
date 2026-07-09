package com.businessasusual.data.di

import com.businessasusual.data.remote.MobileUiApi
import com.businessasusual.data.repository.MobileUiRepositoryImpl
import com.businessasusual.domain.repository.MobileUiRepository
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