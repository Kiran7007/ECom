package com.example.emanditask.dinjection

import androidx.room.Room
import com.example.emanditask.BuildConfig
import com.example.emanditask.ProductsViewmodel
import com.example.emanditask.dtatabase.AppDatabase
import com.example.emanditask.helpers.DATABASE_NAME
import com.example.emanditask.networkutil.ApiService
import com.example.emanditask.repository.IProductRepository
import com.example.emanditask.repository.ProductRepository
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import org.koin.androidx.viewmodel.ext.koin.viewModel

val remoteModules = module {
    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

    single {
        GsonBuilder()
            .setLenient()
            .create()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(get()))
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
        database.productDao()
    }
}

val repositoryModules = module {
    single<IProductRepository> { ProductRepository(get(), get()) }
}

val viewModelModules = module {
    viewModel { ProductsViewmodel(get()) }
}

