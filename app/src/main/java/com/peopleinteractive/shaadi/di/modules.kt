package com.peopleinteractive.shaadi.di

import androidx.room.Room
import com.google.gson.GsonBuilder
import com.peopleinteractive.shaadi.BuildConfig
import com.peopleinteractive.shaadi.data.api.ApiService
import com.peopleinteractive.shaadi.data.db.AppDatabase
import com.peopleinteractive.shaadi.data.repository.PeopleRepository
import com.peopleinteractive.shaadi.data.repository.PeopleRepositoryImpl
import com.peopleinteractive.shaadi.ui.people.PeopleViewModel
import com.peopleinteractive.shaadi.util.DATABASE_NAME
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val remoteModules = module {
    single {
        OkHttpClient.Builder().apply {
            addInterceptor(
                HttpLoggingInterceptor().apply
                {
                    if (BuildConfig.DEBUG) {
                        level = HttpLoggingInterceptor.Level.BODY
                    }
                })
        }.build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .client(get())
            .build()
    }

    single(createOnStart = false) { get<Retrofit>().create(ApiService::class.java) }
}

val databaseModules = module {
    single {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java, DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    single(createOnStart = false) {
        val database: AppDatabase = get()
        database.peopleDao()
    }
}

val repositoryModules = module {
    single<PeopleRepository> { PeopleRepositoryImpl(get(), get()) }
}

val viewModelModules = module {
    viewModel { PeopleViewModel(get()) }
}

