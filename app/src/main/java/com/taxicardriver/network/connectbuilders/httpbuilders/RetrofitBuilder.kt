package com.taxicardriver.network.connectbuilders.httpbuilders

import com.taxicardriver.network.apiservices.DriverHomeScreenApiService
import com.taxicardriver.network.apiservices.SetCarArrivalRadiusService
import com.taxicardriver.network.apiservices.UserAuthApiService
import com.taxicardriver.network.apiservices.UserRegisterApiService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    private const val BASE_URL = "http://192.168.0.187:8080/"

    /////////////////////////////////////////////////////////////////////////////////////
    // Create a basic Retrofit without interceptor for services where JWT is not required
    private val baseRetrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val userRegisterApiService: UserRegisterApiService by lazy {
        baseRetrofit.create(UserRegisterApiService::class.java)
    }

    val userAuthApiService: UserAuthApiService by lazy {
        baseRetrofit.create(UserAuthApiService::class.java)
    }
    /////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////


    /////////////////////////////////////////////////////////////////////////////////////
    // Interceptor for adding JWT to the request header
    private val authInterceptor = Interceptor { chain ->
        val token = getToken() // Assume that the getToken() method retrieves the token correctly
        val newRequest = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()
        chain.proceed(newRequest)
    }

    // Retrofit client with interceptor for services requiring JWT
    private val authClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .build()

    private val authRetrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(authClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // Service that requires JWT authorisation
    val driverHomeScreenApiService: DriverHomeScreenApiService by lazy {
        authRetrofit.create(DriverHomeScreenApiService::class.java)
    }

    // Service reguest for set car arrival radius update//
    val setCarArrivalRadiusService: SetCarArrivalRadiusService by lazy {
        authRetrofit.create(SetCarArrivalRadiusService::class.java)
    }
    /////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////

    fun getToken(): String? {
        // Here should be the logic for retrieving a stored JWT token
        // For example, from SharedPreferences or other data storage source
        return "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJnbWFpbDdAY29tIiwicm9sZSI6IlVTRVIiLCJ1c2VySWQiOjUsImlhdCI6MTcxOTUwMTg2NSwiZXhwIjoxNzIwMzY1ODY1fQ.yFktM5FsGq1IMY3leDL2Q7jQS6vRMG9QTEBPJh2xAOdk8Ybk6zpsvN-8WC1rntxB"
    }
}

