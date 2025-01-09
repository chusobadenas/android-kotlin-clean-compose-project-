package com.jesusbadenas.kotlin_clean_compose_project.data.di

import android.content.Context
import androidx.room.Room
import com.jesusbadenas.kotlin_clean_compose_project.data.BuildConfig
import com.jesusbadenas.kotlin_clean_compose_project.data.api.Network
import com.jesusbadenas.kotlin_clean_compose_project.data.api.UsersAPI
import com.jesusbadenas.kotlin_clean_compose_project.data.db.AppDatabase
import com.jesusbadenas.kotlin_clean_compose_project.data.db.DBConstants
import com.jesusbadenas.kotlin_clean_compose_project.data.remote.UserRemoteDataSource
import com.jesusbadenas.kotlin_clean_compose_project.data.remote.UserRemoteDataSourceImpl
import com.jesusbadenas.kotlin_clean_compose_project.data.repository.UserRepositoryImpl
import com.jesusbadenas.kotlin_clean_compose_project.domain.repository.UserRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val CACHE_SIZE_MB: Long = 5 * 1024 * 1024

val dataModule = module {
    factory<HttpLoggingInterceptor> {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }
    }
    factory<Moshi> { provideMoshi() }
    factory<OkHttpClient> { provideOkHttpClient(androidContext(), get()) }
    factory<UsersAPI> { provideUsersAPIService(get()) }
    factory<UserRemoteDataSource> { UserRemoteDataSourceImpl(get()) }
    factory<UserRepository> { UserRepositoryImpl(get(), get()) }
    single<Retrofit> { provideRetrofit(get(), get()) }
    single<AppDatabase> { provideDatabase(androidContext()) }
    single<Network> { Network(androidContext()) }
}

private fun provideMoshi() = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private fun provideOkHttpClient(
    context: Context,
    logInterceptor: HttpLoggingInterceptor
): OkHttpClient = OkHttpClient.Builder().apply {
    // Enable cache
    cache(Cache(context.cacheDir, CACHE_SIZE_MB))
    // Enable logging
    if (BuildConfig.DEBUG) {
        addInterceptor(logInterceptor)
    }
}.build()

private fun provideRetrofit(moshi: Moshi, okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder().apply {
    baseUrl(UsersAPI.API_BASE_URL)
    client(okHttpClient)
    addConverterFactory(MoshiConverterFactory.create(moshi))
}.build()

private fun provideUsersAPIService(retrofit: Retrofit): UsersAPI = retrofit
    .create(UsersAPI::class.java)

private fun provideDatabase(context: Context) = Room
    .databaseBuilder(context.applicationContext, AppDatabase::class.java, DBConstants.DB_NAME)
    .build()
