package com.example.mamabear.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mamabear.data.models.*
import com.example.mamabear.data.repository.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * ViewModel for the Home Screen that manages and provides dashboard data,
 * including the mother's profile, upcoming appointments, and recent health records.
 */
class HomeViewModel : ViewModel() {
    private val motherRepository = MotherRepository()
    private val reminderRepository = ReminderRepository()
    private val recordRepository = RecordRepository()
    private val savingsRepository = SavingsRepository()
    private val authRepository = AuthRepository()

    private val _uiState = MutableStateFlow(HomeUiState())

    /**
     * A [StateFlow] representing the current UI state of the Home Screen.
     */
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    /**
     * Loads the dashboard data for the authenticated user.
     *
     * This function fetches the mother's profile, identifies the next appointment from reminders,
     * and retrieves the latest health record. It updates [uiState] to reflect loading,
     * success, or error states.
     */
    fun loadDashboardData() {
        val user = authRepository.getCurrentUser()
        if (user == null) {
            _uiState.update { it.copy(error = "Authentication required. Please login again.", isLoading = false) }
            return
        }
        val motherId = user.uid

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            
            try {
                // Combine flows for real-time updates on Home Screen
                combine(
                    flow { emit(motherRepository.getMotherById(motherId)) },
                    reminderRepository.getRemindersFlow(motherId),
                    recordRepository.getRecordsFlow(motherId),
                    savingsRepository.getSavingsFlow(motherId)
                ) { mother, reminders, records, savings ->
                    val nextAppt = reminders
                        .filter { it.title.contains("Appointment", ignoreCase = true) || it.title.contains("Clinic", ignoreCase = true) }
                        .sortedBy { it.date }
                        .firstOrNull()

                    val latestRecord = records.firstOrNull()
                    val totalSaved = savings.sumOf { it.amountSaved }

                    HomeUiState(
                        mother = mother,
                        nextAppointment = nextAppt,
                        reminders = reminders.take(3), // Show top 3 reminders
                        latestRecord = latestRecord,
                        totalSaved = totalSaved,
                        isLoading = false,
                        error = null
                    )
                }.collect { newState ->
                    _uiState.value = newState
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(
                    isLoading = false,
                    error = e.localizedMessage ?: "Failed to load dashboard data"
                ) }
            }
        }
    }
}

/**
 * UI state for the Home Screen.
 *
 * @property mother The profile information of the mother.
 * @property nextAppointment The soonest upcoming appointment or clinic visit.
 * @property latestRecord The most recent health record available.
 * @property isLoading Indicates if a data fetch operation is in progress.
 * @property error An optional error message if an operation fails.
 */
data class HomeUiState(
    val mother: MotherModel? = null,
    val nextAppointment: ReminderModel? = null,
    val reminders: List<ReminderModel> = emptyList(),
    val latestRecord: HealthRecordsModel? = null,
    val totalSaved: Int = 0,
    val isLoading: Boolean = false,
    val error: String? = null
)
