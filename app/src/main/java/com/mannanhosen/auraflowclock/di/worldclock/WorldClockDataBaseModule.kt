package com.mannanhosen.auraflowclock.di.worldclock

import android.content.Context
import androidx.room.Room
import com.mannanhosen.auraflowclock.data.data_source.TimeZoneDao
import com.mannanhosen.auraflowclock.data.database.TimeZoneDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent ::class)
object WorldClockDataBaseModule {
    @Provides
    @Singleton
    fun provideTimeZoneDatabase(
        @ApplicationContext context: Context)
     : TimeZoneDataBase {
        return Room.databaseBuilder(
            context,
            TimeZoneDataBase::class.java,
            TimeZoneDataBase.DATABASE_NAME)
            .build()

    }

    @Provides
    fun provideTimeZoneDao(dataBase: TimeZoneDataBase) : TimeZoneDao {
        return dataBase.timeZoneDao()
    }


}