package com.mannanhosen.auraflowclock.Data_Kitchen.WorkManager.factory

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import com.mannanhosen.auraflowclock.Data_Kitchen.WorkManager.Worker.AlarmWorker

interface ChildWorkerFactory {

    fun crete (
        appContext : Context,
        params : WorkerParameters

        ) : AlarmWorker
}