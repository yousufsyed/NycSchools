package com.yousufsyed.nycschools.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.yousufsyed.nycschools.dao.NYC_SCHOOLS_DB
import com.yousufsyed.nycschools.dao.NycSchoolDatabase
import com.yousufsyed.nycschools.dao.NycSchoolsDao
import com.yousufsyed.nycschools.network.DefaultNycSchoolRestClient
import com.yousufsyed.nycschools.network.NycSchoolRestClient
import com.yousufsyed.nycschools.network.NycSchoolsApi
import com.yousufsyed.nycschools.provider.*
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideContext(application: Application): Context = application.applicationContext

    @Provides
    fun getOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .build()

    @Provides
    fun getGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @[Provides Singleton]
    fun getNycSchoolApi(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): NycSchoolsApi = Retrofit.Builder()
        .baseUrl("https://data.cityofnewyork.us")
        .addConverterFactory(gsonConverterFactory)
        .client(okHttpClient)
        .build()
        .create(NycSchoolsApi::class.java)

    @[Provides Singleton]
    fun getDatabase(context: Context): NycSchoolDatabase {
        return Room.databaseBuilder(
            context,
            NycSchoolDatabase::class.java,
            NYC_SCHOOLS_DB
        ).build()
    }

    @Provides
    fun getNycSchoolsDao(nycSchoolDatabase: NycSchoolDatabase): NycSchoolsDao =
        nycSchoolDatabase.nycSchoolsDao()

    @Provides
    fun getNycSchoolRestClient(impl: DefaultNycSchoolRestClient): NycSchoolRestClient = impl

    @Provides
    fun getNycSchoolsProvider(impl: DefaultNycSchoolsProvider): NycSchoolsProvider = impl

    @Provides
    fun getDispatcherProvider(impl: DefaultDispatcherProvider): DispatcherProvider = impl

    @Provides
    fun getEventLogger(impl: DefaultEventLogger): EventLogger = impl

    @[Provides Singleton]
    fun getSchoolProvider(impl: DefaultSchoolProvider): SchoolProvider = impl

    @[Provides Singleton]
    fun networkAvailabilityProvider(impl: DefaultNetworkAvailabilityProvider): NetworkAvailabilityProvider =
        impl
}