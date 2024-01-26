package com.emmutua.counterapp.stock

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class StockViewModel : ViewModel() {
    private val _maizeState = MutableStateFlow(Stock())
    val maizeState = _maizeState.asStateFlow()

    private val _beansState = MutableStateFlow(Stock())
    val beansState = _beansState.asStateFlow()

    fun addMaizeStock(
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit
    ) {
        if (_maizeState.value.numberOfKgsRemaining < 150) {
            _maizeState.update {
                it.copy(
                    numberOfKgsRemaining = it.numberOfKgsRemaining + 10
                )
            }
            onSuccess("Added 10 kgs")
        } else {
            onError("Maize stock full")
        }
    }

    fun reduceMaizeStock(
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit
    ) {
        if (_maizeState.value.numberOfKgsRemaining in 10f..150f) {
            _maizeState.update {
                it.copy(
                    numberOfKgsRemaining = it.numberOfKgsRemaining - 10
                )
            }
            onSuccess("Reduced 10 kgs")
        } else {
            onError("Cant go beyond 0")
        }
    }

    fun addBeansStock(
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit
    ) {
        if (_beansState.value.numberOfKgsRemaining < 150) {
            _beansState.update {
                it.copy(
                    numberOfKgsRemaining = it.numberOfKgsRemaining + 10
                )
            }
            onSuccess("Added 10 kgs")
        } else {
            onError("Beans stock full")
        }
    }

    fun reduceBeansStock(
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit
    ) {
        if (_beansState.value.numberOfKgsRemaining in 10f..150f) {
            _beansState.update {
                it.copy(
                    numberOfKgsRemaining = it.numberOfKgsRemaining - 10
                )
            }
            onSuccess("Reduced 10 kgs")
        } else {
            onError("Cant go beyond 0")
        }
    }

    fun calculateProgress(numberOfKgsRemaining: Float): Float {
        val totalKgs = 150f
        return (numberOfKgsRemaining / totalKgs)
    }
}

data class Stock(
    val name: String = "",
    val numberOfKgsRemaining: Float = 0f
)

/**
 * State - Something you see on screen that can change its value
 * StateFlow- Designed to represent state in a reactive way, emits values and can be observed by multiple collectors
 * Emits value and never stops, even if no collectors
 * SharedFlow - Readonly, emits values but cant be modified
 */