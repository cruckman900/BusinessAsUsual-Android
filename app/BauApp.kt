package com.businessasusual.app

import android.app.Application
import com.businessasusual.app.di.appModule
import com.businessasusual.data.di.dataModule
import com.businessasusual.domain.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BauApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BauApp)
            modules(dataModule, domainModule, appModule)
        }
    }
}