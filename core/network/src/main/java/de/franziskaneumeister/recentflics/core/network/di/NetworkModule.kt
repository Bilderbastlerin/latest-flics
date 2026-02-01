package de.franziskaneumeister.recentflics.core.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import de.franziskaneumeister.recentflics.core.network.BuildConfig
import de.franziskaneumeister.recentflics.core.network.MoviesDataSource
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
public abstract class DataModule {

    public companion object {

        @Provides
        @Singleton
        internal fun provideRetrofit(client: OkHttpClient): Retrofit {
            val networkJson = Json {
                ignoreUnknownKeys = true // Helps avoid crashes when the API adds new fields
            }
            return Retrofit.Builder()
                .client(client)
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(
                    networkJson.asConverterFactory(
                        "application/json; charset=utf-8".toMediaType()
                    )
                )
                .build()
        }

        @Provides
        @Singleton
        internal fun okHttpClient(): OkHttpClient {
            return OkHttpClient().newBuilder()
                .addInterceptor(
                    Interceptor { chain ->
                        val request: Request = chain.request()
                            .newBuilder()
                            .header("accept", "application/json")
                            .header("Authorization", "Bearer ${BuildConfig.API_KEY}")
                            .build()
                        chain.proceed(request)
                    }
                )
                .addInterceptor(
                    HttpLoggingInterceptor()
                        .setLevel(HttpLoggingInterceptor.Level.BODY) // Should be set to NONE in Production
                )
                .build()
        }

        @Provides
        @Singleton
        internal fun provideStarWarsNetworkDataSource(retrofit: Retrofit): MoviesDataSource {
            return retrofit.create(MoviesDataSource::class.java)
        }

    }
}