package com.mannanhosen.auraflowclock.di.bedtime

import android.app.Application
import androidx.room.Room
import com.mannanhosen.auraflowclock.data.database.BedTimeDataBase
import com.mannanhosen.auraflowclock.data.repository.BedTimeRepositoryImp
import com.mannanhosen.auraflowclock.domain.repository.BedTimeRepository
import com.mannanhosen.auraflowclock.domain.usecase.bedtime.AddBedTimeUseCase
import com.mannanhosen.auraflowclock.domain.usecase.bedtime.BedTimeUseCases
import com.mannanhosen.auraflowclock.domain.usecase.bedtime.DeleteBedTimeUseCase
import com.mannanhosen.auraflowclock.domain.usecase.bedtime.GetBedTimeUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlin.jvm.java

@Module
@InstallIn(SingletonComponent ::class)
object BedTimeModule {
    @Provides
    @Singleton
    fun provideBedTimeDataBase(
        app : Application
    ) : BedTimeDataBase{
        return Room.databaseBuilder(
            app,
            BedTimeDataBase :: class.java,
            BedTimeDataBase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            . build()
    }

    @Provides
    @Singleton
    fun provideBedTimeRepository(bedTimeDataBase: BedTimeDataBase) : BedTimeRepository {
        return BedTimeRepositoryImp(bedTimeDataBase.bedTimeDao())
    }

    @Provides
    @Singleton
    fun provideBedTimeUseCase(repository: BedTimeRepository) : BedTimeUseCases{
        return BedTimeUseCases(
            getBedTimeUseCase = GetBedTimeUseCase(repository),
            deleteBedTimeUseCase = DeleteBedTimeUseCase(repository),
            addBedTimeUseCases = AddBedTimeUseCase(repository),

        )
    }

}
