package com.example.interview_transparent_accounts_app.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.interview_transparent_accounts_app.model.TransparentAccount
import com.example.interview_transparent_accounts_app.repository.TransparentAccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel responsible for managing the state and data of a single transparent account's details.
 *
 * @property repository The repository used to perform network operations for retrieving transparent account data.
 */
@HiltViewModel
class TransparentAccountDetailViewModel @Inject constructor(
    private val repository: TransparentAccountRepository
) : ViewModel() {

    /**
     * State holding the account detail.
     */
    private val _accountDetail = mutableStateOf<TransparentAccount?>(null)
    val accountDetail: State<TransparentAccount?> = _accountDetail

    /**
     * Loading and error states.
     */
    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _errorMessage = mutableStateOf("")
    val errorMessage: State<String> = _errorMessage

    /**
     * Load account detail by account id.
     */
    fun loadAccountDetail(id: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = ""
            val result = repository.getTransparentAccountDetail(id)
            result.fold(
                onSuccess = { account ->
                    _accountDetail.value = account
                },
                onFailure = {
                    _errorMessage.value = "Failed to load data."
                }
            )
            _isLoading.value = false
        }
    }

    /**
     * Refresh account detail (pull-to-refresh)
     */
    fun refreshAccountDetail(id: String) {
        loadAccountDetail(id)
    }
}