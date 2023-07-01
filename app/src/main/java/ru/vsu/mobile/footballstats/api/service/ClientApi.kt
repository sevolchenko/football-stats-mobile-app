package ru.vsu.mobile.footballstats.api.service

import com.squareup.picasso.OkHttp3Downloader
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ClientApi {
    private var mHttpLoggingInterceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    private var mOkHttpClient = OkHttpClient
        .Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("x-rapidapi-host", "v3.football.api-sports.io")
                .addHeader("x-rapidapi-key", "878bff667b28e83c4ee4eee51966a3e1")
                .addHeader("Accept", "application/json")
                .build()
            chain.proceed(request)
        }
        .addInterceptor(mHttpLoggingInterceptor)
        .build()

    val downloader = OkHttp3Downloader(mOkHttpClient)

    private var mRetrofit: Retrofit? = null

    private const val URL = "https://v3.football.api-sports.io"

    val client: Retrofit?
        get() {
            if(mRetrofit == null){
                mRetrofit = Retrofit.Builder()
                    .baseUrl(URL)
                    .client(mOkHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return mRetrofit
        }
}