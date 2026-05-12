package com.example.mamabear.ui.screens.reminders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mamabear.data.models.ReminderModel
import com.example.mamabear.data.repository.ReminderRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ReminderViewModel : ViewModel() {

    private val repository = ReminderRepository()
    private val _message = MutableStateFlow("")
    val message: StateFlow<String> = _message

    private val _reminders = MutableStateFlow<List<ReminderModel>>(emptyList())
    val reminders: StateFlow<List<ReminderModel>> = _reminders

    init {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            viewModelScope.launch {
                repository.getRemindersFlow(user.uid).collectLatest { updatedReminders ->
                    _reminders.value = updatedReminders
                }
            }
        }
    }

    fun addReminder(title: String, description: String, date: String) {
        viewModelScope.launch {
            try {
                val user = FirebaseAuth.getInstance().currentUser
                if (user != null) {
                    val newReminder = ReminderModel(
                        motherId = user.uid,
                        title = title,
                        description = description,
                        date = date
                    )
                    repository.insertReminder(newReminder)
                    _message.value = "Reminder Added"
                }
            } catch (e: Exception) {
                _message.value = e.message.toString()
            }
        }
    }

    fun updateReminder(reminder: ReminderModel) {
        viewModelScope.launch {
            try {
                repository.updateReminder(reminder)
                _message.value = "Reminder Updated"
            } catch (e: Exception) {
                _message.value = e.message.toString()
            }
        }
    }

    fun deleteReminder(reminderId: String) {
        viewModelScope.launch {
            try {
                repository.deleteReminder(reminderId)
                _message.value = "Reminder Deleted"
            } catch (e: Exception) {
                _message.value = e.message.toString()
            }
        }
    }
}
