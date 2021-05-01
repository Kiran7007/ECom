package com.ecom.sample.di

import androidx.room.Room
import com.ecom.sample.BuildConfig
import com.ecom.sample.data.db.AppDatabase
import com.ecom.sample.data.remote.ApiService
import com.ecom.sample.data.repository.CartRepository
import com.ecom.sample.data.repository.CartRepositoryImpl
import com.ecom.sample.data.repository.ProductRepository
import com.ecom.sample.data.repository.ProductRepositoryImpl
import com.ecom.sample.ui.viewmodel.CartViewModel
import com.ecom.sample.ui.viewmodel.ProductViewModel
import com.ecom.sample.utils.DATABASE_NAME
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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

    single(createOnStart = false) {
        val database: AppDatabase = get()
        database.cartDao()
    }
}

val repositoryModules = module {
    single<ProductRepository> { ProductRepositoryImpl(get(), get(), get()) }
    single<CartRepository> { CartRepositoryImpl(get(), get()) }
}

val viewModelModules = module {
    viewModel { ProductViewModel(get()) }
    viewModel { CartViewModel(get()) }
}

