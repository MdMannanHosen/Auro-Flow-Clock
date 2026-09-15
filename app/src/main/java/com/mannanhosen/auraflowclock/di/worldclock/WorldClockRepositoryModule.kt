package com.mannanhosen.auraflowclock.di.worldclock

import com.mannanhosen.auraflowclock.data.repository.TimeZoneRepositoryImp
import com.mannanhosen.auraflowclock.domain.repository.TimeZoneRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent:: class)
abstract class WorldClockRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindTimeZoneRepository(
        timeZoneRepositoryImp: TimeZoneRepositoryImp
    ) : TimeZoneRepository
}