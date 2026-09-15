package com.mannanhosen.auraflowclock.di

import android.app.Application
import androidx.room.Room
import com.mannanhosen.auraflowclock.data.database.AlarmDataBase
import com.mannanhosen.auraflowclock.data.repository.AlarmRepositoryImp
import com.mannanhosen.auraflowclock.data.repository.BoxBreathingRepositoryImp
import com.mannanhosen.auraflowclock.data.repository.StopwatchRepositoryImp
import com.mannanhosen.auraflowclock.data.repository.TimerRepositoryImp
import com.mannanhosen.auraflowclock.domain.repository.AlarmRepository
import com.mannanhosen.auraflowclock.domain.repository.BoxBreathingRepository
import com.mannanhosen.auraflowclock.domain.repository.StopwatchRepository
import com.mannanhosen.auraflowclock.domain.repository.TimerRepository
import com.mannanhosen.auraflowclock.domain.usecase.alarm.AddAlarm
import com.mannanhosen.auraflowclock.domain.usecase.alarm.AlarmUseCases
import com.mannanhosen.auraflowclock.domain.usecase.alarm.DeleteAlarm
import com.mannanhosen.auraflowclock.domain.usecase.alarm.GetAlarm
import com.mannanhosen.auraflowclock.domain.usecase.alarm.GetAlarms
import com.mannanhosen.auraflowclock.domain.usecase.box_breathing.BoxBreathingUseCases
import com.mannanhosen.auraflowclock.domain.usecase.box_breathing.ObserveBoxBreathingSettings
import com.mannanhosen.auraflowclock.domain.usecase.box_breathing.SaveBoxBreathingSettings
import com.mannanhosen.auraflowclock.domain.usecase.stopwatch.ClearLaps
import com.mannanhosen.auraflowclock.domain.usecase.stopwatch.GetLaps
import com.mannanhosen.auraflowclock.domain.usecase.stopwatch.ObserveRunState
import com.mannanhosen.auraflowclock.domain.usecase.stopwatch.PersistRunState
import com.mannanhosen.auraflowclock.domain.usecase.stopwatch.RecordLap
import com.mannanhosen.auraflowclock.domain.usecase.stopwatch.StopwatchUseCases
import com.mannanhosen.auraflowclock.domain.usecase.timer.ObserveTimerRunState
import com.mannanhosen.auraflowclock.domain.usecase.timer.PersistTimerRunState
import com.mannanhosen.auraflowclock.domain.usecase.timer.TimerUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlin.jvm.java

@Module
@InstallIn(SingletonComponent :: class)
object AppModule {
    @Provides
    @Singleton
    fun provideAlarmDataBase(
        app : Application
    ) : AlarmDataBase {
        return Room.databaseBuilder(
            app,
            AlarmDataBase :: class.java,
            AlarmDataBase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            . build()
    }

    @Provides
    @Singleton
    fun provideAlarmRepository(alarmDataBase: AlarmDataBase): AlarmRepository{
        return AlarmRepositoryImp(alarmDataBase.alarmDao)
    }

    @Provides
    @Singleton
    fun provideAlarmUseCase(repository: AlarmRepository) : AlarmUseCases {
        return AlarmUseCases(
            getAlarm = GetAlarm(repository),
            deleteAlarm = DeleteAlarm(repository),
            addAlarm = AddAlarm(repository),
            getAlarms = GetAlarms(repository)
        )
    }

    // ✅ notun jog kora holo — Stopwatch feature er jonno (shob Room-based, DataStore lagbe na)

    @Provides
    @Singleton
    fun provideStopwatchRepository(alarmDataBase: AlarmDataBase): StopwatchRepository {
        return StopwatchRepositoryImp(alarmDataBase.stopwatchDao)
    }

    @Provides
    @Singleton
    fun provideStopwatchUseCases(repository: StopwatchRepository): StopwatchUseCases {
        return StopwatchUseCases(
            recordLap = RecordLap(repository),
            getLaps = GetLaps(repository),
            clearLaps = ClearLaps(repository),
            observeRunState = ObserveRunState(repository),
            persistRunState = PersistRunState(repository)
        )

    }



    @Provides
    @Singleton
    fun provideTimerRepository(alarmDataBase: AlarmDataBase): TimerRepository {
        return TimerRepositoryImp(alarmDataBase.timerDao)
    }

    @Provides
    @Singleton
    fun provideTimerUseCases(repository: TimerRepository): TimerUseCases {
        return TimerUseCases(
            observeRunState = ObserveTimerRunState(repository),
            persistRunState = PersistTimerRunState(repository)
        )
    }


        @Provides
        @Singleton
        fun provideBoxBreathingRepository(alarmDataBase: AlarmDataBase): BoxBreathingRepository {
            return BoxBreathingRepositoryImp(alarmDataBase.boxBreathingDao)
        }

        @Provides
        @Singleton
        fun provideBoxBreathingUseCases(repository: BoxBreathingRepository): BoxBreathingUseCases {
            return BoxBreathingUseCases(
                observeSettings = ObserveBoxBreathingSettings(repository),
                saveSettings = SaveBoxBreathingSettings(repository)
            )
        }
    }
