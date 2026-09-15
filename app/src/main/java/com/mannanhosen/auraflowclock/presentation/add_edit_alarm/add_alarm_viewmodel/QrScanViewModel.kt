package com.mannanhosen.auraflowclock.presentation.add_edit_alarm.add_alarm_viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class QrScanViewModel @Inject constructor() : ViewModel() {
    private val _scannedValue = MutableStateFlow<String?>(null)
    val scannedValue: StateFlow<String?> = _scannedValue.asStateFlow()

    fun onQrScanned(value: String) {
        if (_scannedValue.value == null) {
            _scannedValue.value = value
        }
    }
}