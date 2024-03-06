package com.example.HateConscription.di

import android.content.Context
import androidx.room.Room
import com.example.HateConscription.drinkWaterData.DailyRecordsDao
import com.example.HateConscription.drinkWaterData.DailyRecordsDatabase
import com.example.HateConscription.drinkWaterData.DailyRecordsRepository
import com.example.HateConscription.drinkWaterData.DailyRecordsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DBModule {

    @Singleton
    @Provides
    fun provideDailyRecordsDatabase(@ApplicationContext context: Context): DailyRecordsDatabase {
        return Room.databaseBuilder(context, DailyRecordsDatabase::class.java, "records").build()
    }

    @Singleton
    @Provides
    fun provideDailyRecordsDao(database: DailyRecordsDatabase): DailyRecordsDao {
        return database.dao()
    }

    @Provides
    fun provideDailyRecordsRepository(
        dao: DailyRecordsDao
    ): DailyRecordsRepository = DailyRecordsRepositoryImpl(
        dao = dao
    )
}