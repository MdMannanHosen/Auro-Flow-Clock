package com.mannanhosen.auraflowclock.domain.usecase.bedtime

import com.mannanhosen.auraflowclock.data.model.entity.BedTime
import com.mannanhosen.auraflowclock.data.model.entity.InvalidAlarmException
import com.mannanhosen.auraflowclock.data.model.entity.InvalidBedTimeException
import com.mannanhosen.auraflowclock.domain.repository.BedTimeRepository
import kotlin.jvm.Throws

class AddBedTimeUseCase (
    val repository: BedTimeRepository) {

    @Throws(InvalidBedTimeException:: class)
    suspend operator fun invoke(bedTime: BedTime) {
//        if (bedTime.title.isBlank()) {
//            throw InvalidAlarmException("The title of the alarm can not empty")
//        }
        repository.insertBedTime(bedTime)
    }
}