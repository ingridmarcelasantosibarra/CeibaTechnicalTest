package com.ingridsantos.ceibatechnicaltest.di

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

val networkTestModule = module {

    val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .build()
    }

    single(named(MOSHI_RETROFIT_INSTANCE_NAME)) {
        Retrofit.Builder()
            .baseUrl(BASE_TEST_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build()
    }
}

private const val BASE_TEST_URL = "http://localhost:8080/"
private const val TIME_OUT_SECONDS = 2L
const val MOSHI_RETROFIT_INSTANCE_NAME = "moshi_instance"
