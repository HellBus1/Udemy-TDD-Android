package com.udemy.tddmasterclass.data

import com.jakewharton.espresso.OkHttp3IdlingResource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
val client = OkHttpClient.Builder()
    .addInterceptor(interceptor)
    .build()

val idlingResource = OkHttp3IdlingResource.create("okhttp", client)

@Module
@InstallIn(ActivityComponent::class)
class PlaylistModule {

    @Provides
    fun playlistAPI(retrofit: Retrofit) : PlaylistAPI = retrofit.create(PlaylistAPI::class.java)

    @Provides
    fun retrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://192.168.1.3:3002") // change this with your ip
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}