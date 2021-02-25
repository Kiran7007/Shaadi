package com.peopleinteractive.shaadi

import androidx.multidex.MultiDexApplication
import com.peopleinteractive.shaadi.di.databaseModules
import com.peopleinteractive.shaadi.di.remoteModules
import com.peopleinteractive.shaadi.di.repositoryModules
import com.peopleinteractive.shaadi.di.viewModelModules
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