package com.ecom.sample

import androidx.multidex.MultiDexApplication
import com.ecom.sample.di.databaseModules
import com.ecom.sample.di.remoteModules
import com.ecom.sample.di.repositoryModules
import com.ecom.sample.di.viewModelModules
import org.koin.android.ext.android.startKoin

class AppApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        // Configure the koin dependency injection.
        startKoin(
            this, listOf(
                remoteModules,
                databaseModules,
                repositoryModules,
                viewModelModules
            )
        )
    }
}