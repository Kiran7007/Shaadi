package com.peopleinteractive.shaadi.di

import androidx.room.Room
import com.peopleinteractive.shaadi.BuildConfig
import com.peopleinteractive.shaadi.data.api.ApiService
import com.peopleinteractive.shaadi.data.db.AppDatabase
import com.peopleinteractive.shaadi.data.repository.PeopleRepository
import com.peopleinteractive.shaadi.data.repository.PeopleRepositoryImpl
import com.peopleinteractive.shaadi.ui.people.PeopleViewModel
import com.peopleinteractive.shaadi.util.DATABASE_NAME
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val remoteModules = module {
    single {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(get()))
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

