package com.example.businessasusualandroid

import android.app.Application
import com.example.businessasusualandroid.di.appModule
import com.example.data.di.dataModule
import com.example.domain.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BusinessAsUsualApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@BusinessAsUsualApp)
            modules(
                domainModule,
                dataModule,
                appModule
            )
        }
    }
}