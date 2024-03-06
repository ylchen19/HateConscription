package com.example.HateConscription.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.example.HateConscription.calendar.DateNDaysDataState
import com.example.HateConscription.dateData.DataRepository
import com.example.HateConscription.dateData.DataStateRepository
import com.example.HateConscription.dateData.DataStateSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Singleton
    @Provides
    fun provideDataStore(@ApplicationContext context: Context): DataStore<DateNDaysDataState> {
        return DataStoreFactory.create(
            serializer = DataStateSerializer,
            produceFile = { context.dataStoreFile("DateData.json") },
            corruptionHandler = null
        )
    }

    @Provides
    fun provideDataStateRepository(
        dataStateStore: DataStore<DateNDaysDataState>
    ): DataRepository = DataStateRepository(
            dataStateStore = dataStateStore
        )

}