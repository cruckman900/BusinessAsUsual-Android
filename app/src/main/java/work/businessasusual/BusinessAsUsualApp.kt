package work.businessasusual

import android.app.Application
import work.businessasusual.di.appModule
import work.businessasusual.data.di.dataModule
import work.businessasusual.domain.di.domainModule
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
