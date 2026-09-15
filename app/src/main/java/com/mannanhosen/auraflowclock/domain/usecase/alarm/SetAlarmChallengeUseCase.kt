package com.mannanhosen.auraflowclock.domain.usecase.alarm

import com.mannanhosen.auraflowclock.data.model.ChallengeType
import com.mannanhosen.auraflowclock.domain.repository.AlarmRepository
import javax.inject.Inject

class SetAlarmChallengeUseCase @Inject constructor(
    private val repository: AlarmRepository
) {
    suspend operator fun invoke(alarmId: Int, type: ChallengeType, qrValue: String? = null) {
        repository.updateChallengeType(alarmId, type, qrValue)
    }
}