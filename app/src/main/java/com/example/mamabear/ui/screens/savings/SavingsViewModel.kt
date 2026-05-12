package com.example.mamabear.ui.screens.savings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mamabear.data.models.SavingsModel
import com.example.mamabear.data.repository.SavingsRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SavingsViewModel : ViewModel() {

    private val repository = SavingsRepository()
    private val _message = MutableStateFlow("")
    val message: StateFlow<String> = _message

    private val _savings = MutableStateFlow<List<SavingsModel>>(emptyList())
    val savings: StateFlow<List<SavingsModel>> = _savings

    init {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            viewModelScope.launch {
                repository.getSavingsFlow(user.uid).collectLatest { updatedSavings ->
                    _savings.value = updatedSavings
                }
            }
        }
    }

    fun fetchSavings() {
        // No longer needed
    }

    fun addSavings(amount: Int) {
        viewModelScope.launch {
            try {
                val user = FirebaseAuth.getInstance().currentUser
                if (user != null) {
                    val newSavings = SavingsModel(
                        motherId = user.uid,
                        amountSaved = amount,
                        savingsGoal = 10000 // Default goal for now
                    )
                    repository.insertSavings(newSavings)
                    _message.value = "Savings Added"
                    fetchSavings()
                }
            } catch (e: Exception) {
                _message.value = e.message.toString()
            }
        }
    }
}
