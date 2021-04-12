package com.ruslan

import android.app.Application
import com.ruslan.doordashlite.modules.doordashApiClientModule
import com.ruslan.doordashlite.modules.doordashApiModule
import com.ruslan.doordashlite.modules.restaurantViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class Application : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@Application)
            modules(
                listOf(
                    doordashApiModule,
                    doordashApiClientModule,
                    restaurantViewModel

                )
            )
        }
    }

}