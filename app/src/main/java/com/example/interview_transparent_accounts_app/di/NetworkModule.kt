package com.example.interview_transparent_accounts_app.di

import com.example.interview_transparent_accounts_app.network.ApiService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * NetworkModule is a Dagger Hilt module responsible for providing all network-related dependencies
 * required by the application. It configures and supplies instances for JSON parsing, HTTP communication,
 * and API service creation.
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL      = "https://webapi.developers.erstegroup.com/api/csas/public/sandbox/v3/"
    private const val WEB_API_KEY   = "WEB-API-key"
    private const val API_KEY       = "634829a7-8912-4995-8a31-35a6b0f393ea"
    private const val CONTENT_TYPE  = "Content-Type"
    private const val APP_JSON      = "application/json"

    /**
     * Provide custom Gson with DateAdapter registration.
     */
    @Provides
    @Singleton
    fun provideGson(): Gson =
        GsonBuilder()
            .create()

    /**
     * Provide HTTP logging interceptor.
     */
    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    /**
     * Provide OkHttpClient and add required headers.
     */
    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader(CONTENT_TYPE, APP_JSON)
                    .addHeader(WEB_API_KEY, API_KEY)
                    .build()
                chain.proceed(request)
            }
            .build()

    /**
     * Provide Retrofit instance with base URL.
     */
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    /**
     * Provide API service.
     */
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)
}