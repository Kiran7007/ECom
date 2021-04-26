package com.example.emanditask

import androidx.multidex.MultiDexApplication
import com.example.emanditask.dinjection.databaseModules
import com.example.emanditask.dinjection.remoteModules
import com.example.emanditask.dinjection.repositoryModules
import com.example.emanditask.dinjection.viewModelModules
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