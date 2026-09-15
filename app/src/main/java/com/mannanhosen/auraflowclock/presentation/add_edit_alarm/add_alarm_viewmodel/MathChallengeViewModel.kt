package com.mannanhosen.auraflowclock.presentation.challenge.dismiss

import androidx.lifecycle.ViewModel
import com.mannanhosen.auraflowclock.domain.usecase.alarm.VerifyChallengeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import kotlin.random.Random

data class MathChallengeUiState(
    val num1: Int = 0,
    val num2: Int = 0,
    val operator: String = "+",
    val correctAnswer: Int = 0,
    val isSolved: Boolean = false
)

@HiltViewModel
class MathChallengeViewModel @Inject constructor(
    private val verifyChallengeUseCase: VerifyChallengeUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(generateProblem())
    val uiState: StateFlow<MathChallengeUiState> = _uiState.asStateFlow()

    private fun generateProblem(): MathChallengeUiState {
        val a = Random.nextInt(10, 50)
        val b = Random.nextInt(1, 20)
        val answer = a + b
        return MathChallengeUiState(num1 = a, num2 = b, operator = "+", correctAnswer = answer)
    }

    fun checkAnswer(userInput: Int): Boolean {
        val correct = verifyChallengeUseCase.verifyMathAnswer(_uiState.value.correctAnswer, userInput)
        if (correct) _uiState.value = _uiState.value.copy(isSolved = true)
        return correct
    }

    fun regenerateProblem() {
        _uiState.value = generateProblem()
    }
}