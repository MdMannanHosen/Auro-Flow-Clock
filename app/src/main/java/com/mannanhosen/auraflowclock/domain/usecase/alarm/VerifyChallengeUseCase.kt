package com.mannanhosen.auraflowclock.domain.usecase.alarm

import javax.inject.Inject

class VerifyChallengeUseCase @Inject constructor() {   // ✅ ফিক্স: @Inject constructor() যোগ করা হলো
    fun verifyMathAnswer(correctAnswer: Int, userAnswer: Int): Boolean {
        return correctAnswer == userAnswer
    }

    fun verifyQrCode(targetValue: String, scannedValue: String): Boolean {
        return targetValue.trim() == scannedValue.trim()
    }
}