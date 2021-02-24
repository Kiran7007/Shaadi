package com.peopleinteractive.shaadi

import android.app.Application
import com.peopleinteractive.shaadi.di.databaseModules
import com.peopleinteractive.shaadi.di.remoteModules
import com.peopleinteractive.shaadi.di.repositoryModules
import com.peopleinteractive.shaadi.di.viewModelModules
import org.koin.android.ext.android.startKoin

class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()

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