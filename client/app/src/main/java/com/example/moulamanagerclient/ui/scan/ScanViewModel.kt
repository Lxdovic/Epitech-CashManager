package com.example.moulamanagerclient.ui.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ScanViewModel: ViewModel() {
    private val _ean: MutableStateFlow<String> = MutableStateFlow("")
    val ean: StateFlow<String> = _ean

    fun setEan(ean: String) {
        _ean.value = ean
    }
}