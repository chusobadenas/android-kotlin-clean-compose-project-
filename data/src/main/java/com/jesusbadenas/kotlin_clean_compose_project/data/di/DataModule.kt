package com.jesusbadenas.kotlin_clean_compose_project.data.di

import android.content.Context
import androidx.room.Room
import com.jesusbadenas.kotlin_clean_compose_project.data.BuildConfig
import com.jesusbadenas.kotlin_clean_compose_project.data.api.UsersAPI
import com.jesusbadenas.kotlin_clean_compose_project.data.db.AppDatabase
import com.jesusbadenas.kotlin_clean_compose_project.data.db.DBConstants
import com.jesusbadenas.kotlin_clean_compose_project.data.exception.NetworkException
import com.jesusbadenas.kotlin_clean_compose_project.data.local.UserLocalDataSource
import com.jesusbadenas.kotlin_clean_compose_project.data.local.UserLocalDataSourceImpl
import com.jesusbadenas.kotlin_clean_compose_project.data.remote.UserRemoteDataSource
import com.jesusbadenas.kotlin_clean_compose_project.data.remote.UserRemoteDataSourceImpl
import com.jesusbadenas.kotlin_clean_compose_project.data.repository.UserRepositoryImpl
import com.jesusbadenas.kotlin_clean_compose_project.data.util.NetworkChecker
import com.jesusbadenas.kotlin_clean_compose_project.domain.repository.UserRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
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
private const val NETWORK_CHECKER_INTERCEPTOR = "network_checker_interceptor"

val dataModule = module {
    factory<HttpLoggingInterceptor> {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }
    factory(named(NETWORK_CHECKER_INTERCEPTOR)) { provideNetworkCheckerInterceptor(get()) }
    factory<Moshi> { provideMoshi() }
    factory<OkHttpClient> {
        provideOkHttpClient(
            context = androidContext(),
            logInterceptor = get(),
            networkCheckerInterceptor = get(named(NETWORK_CHECKER_INTERCEPTOR))
        )
    }
    factory<UsersAPI> { provideUsersAPIService(get()) }
    factory<UserLocalDataSource> { UserLocalDataSourceImpl(usersDao = get<AppDatabase>().userDao()) }
    factory<UserRemoteDataSource> { UserRemoteDataSourceImpl(get()) }
    factory<UserRepository> { UserRepositoryImpl(get(), get()) }
    single<AppDatabase> { provideDatabase(androidContext()) }
    single<NetworkChecker> { NetworkChecker(androidContext()) }
    single<Retrofit> { provideRetrofit(get(), get()) }
}

private fun provideNetworkCheckerInterceptor(networkChecker: NetworkChecker) =
    Interceptor { chain ->
        val request = chain.request()
        val response = chain.proceed(request)
        if (!networkChecker.isConnected()) {
            throw NetworkException(response.message)
        }
        response
    }

private fun provideMoshi() = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private fun provideOkHttpClient(
    context: Context,
    logInterceptor: HttpLoggingInterceptor,
    networkCheckerInterceptor: Interceptor
): OkHttpClient = OkHttpClient.Builder().apply {
    // Enable cache
    cache(Cache(context.cacheDir, CACHE_SIZE_MB))
    // Enable logging
    if (BuildConfig.DEBUG) {
        addInterceptor(logInterceptor)
    }
    // Error interceptors
    addInterceptor(networkCheckerInterceptor)
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
