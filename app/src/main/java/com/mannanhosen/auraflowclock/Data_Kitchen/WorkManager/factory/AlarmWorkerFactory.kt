package com.mannanhosen.auraflowclock.Data_Kitchen.WorkManager.factory

import android.content.Context
import androidx.work.WorkerParameters
import com.mannanhosen.auraflowclock.Data_Kitchen.Manager.WorkRequestManager
import com.mannanhosen.auraflowclock.Data_Kitchen.Repository.AlarmRepository
import com.mannanhosen.auraflowclock.Data_Kitchen.WorkManager.Worker.AlarmWorker
import javax.inject.Inject

class AlarmWorkerFactory  @Inject constructor(
    private val alarmRepository: AlarmRepository,

    private val workRequestManager: WorkRequestManager,
) : ChildWorkerFactory {

    override fun  crete(appContext: Context, params: WorkerParameters): AlarmWorker {
        return AlarmWorker(alarmRepository,workRequestManager, appContext, params)
    }
}