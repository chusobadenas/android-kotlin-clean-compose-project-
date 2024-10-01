package com.jesusbadenas.kotlin_clean_compose_project.data.di

import android.content.Context
import androidx.room.Room
import com.jesusbadenas.kotlin_clean_compose_project.data.BuildConfig
import com.jesusbadenas.kotlin_clean_compose_project.data.api.APIService
import com.jesusbadenas.kotlin_clean_compose_project.data.api.Network
import com.jesusbadenas.kotlin_clean_compose_project.data.api.exception.InternalServerErrorException
import com.jesusbadenas.kotlin_clean_compose_project.data.api.exception.NetworkException
import com.jesusbadenas.kotlin_clean_compose_project.data.db.AppDatabase
import com.jesusbadenas.kotlin_clean_compose_project.data.repository.UserDataRepository
import com.jesusbadenas.kotlin_clean_compose_project.domain.repository.UserRepository
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val CACHE_SIZE_MB: Long = 5 * 1024 * 1024
private const val DB_NAME = "app-database"
private const val INTERNAL_SERVER_ERROR = 500
private const val INTERNAL_SERVER_ERROR_INTERCEPTOR = "internal_server_error_interceptor"
private const val NETWORK_INTERCEPTOR = "network_interceptor"

val dataModule = module {
    factory { HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC } }
    factory(named(INTERNAL_SERVER_ERROR_INTERCEPTOR)) { provideInternalServerInterceptor() }
    factory(named(NETWORK_INTERCEPTOR)) { provideNetworkInterceptor() }
    factory {
        provideOkHttpClient(
            androidContext(),
            get(),
            get(named(INTERNAL_SERVER_ERROR_INTERCEPTOR)),
            get(named(NETWORK_INTERCEPTOR))
        )
    }
    factory { provideAPIService(get()) }
    factory<UserRepository> { UserDataRepository(get(), get()) }
    single { provideRetrofit(get()) }
    single { provideDatabase(androidContext()) }
}

private fun provideInternalServerInterceptor(): Interceptor = Interceptor { chain ->
    val request = chain.request()
    val response = chain.proceed(request)
    if (response.code == INTERNAL_SERVER_ERROR) {
        throw InternalServerErrorException()
    }
    response
}

private fun provideNetworkInterceptor() = Interceptor { chain ->
    val request = chain.request()
    val response = chain.proceed(request)
    if (!Network.isConnected()) {
        throw NetworkException()
    }
    response
}

private fun provideOkHttpClient(
    context: Context,
    logInterceptor: HttpLoggingInterceptor,
    internalServerInterceptor: Interceptor,
    networkInterceptor: Interceptor,
): OkHttpClient =
    OkHttpClient.Builder().apply {
        // Enable cache
        val myCache = Cache(context.cacheDir, CACHE_SIZE_MB)
        cache(myCache)
        // Enable logging
        if (BuildConfig.DEBUG) {
            addInterceptor(logInterceptor)
        }
        // Interceptors
        addInterceptor(internalServerInterceptor)
        addInterceptor(networkInterceptor)
    }.build()

private fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
    Retrofit.Builder().apply {
        baseUrl(APIService.API_BASE_URL)
        client(okHttpClient)
        addConverterFactory(MoshiConverterFactory.create())
    }.build()

private fun provideAPIService(retrofit: Retrofit): APIService =
    retrofit.create(APIService::class.java)

private fun provideDatabase(context: Context) =
    Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, DB_NAME).build()
