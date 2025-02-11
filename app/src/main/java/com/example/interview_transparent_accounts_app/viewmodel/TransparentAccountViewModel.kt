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
 * ViewModel responsible for managing the list of transparent accounts.
 *
 * @property repository The repository used to retrieve transparent account data from the network.
 */
@HiltViewModel
class TransparentAccountViewModel @Inject constructor(
    private val repository: TransparentAccountRepository
) : ViewModel() {

    /**
     * State holding the list of accounts.
     */
    private val _accountsState = mutableStateOf<List<TransparentAccount>>(emptyList())
    val accountsState: State<List<TransparentAccount>> = _accountsState

    /**
     * Loading state.
     */
    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    /**
     * Error state.
     */
    private val _errorMessage = mutableStateOf("")
    val errorMessage: State<String> = _errorMessage

    // Pagination tracking.
    private var currentPage = 0
    private var isLastPage = false
    private val pageSize = 10  // Initial page size as požadované

    init {
        // Load initial page on ViewModel init.
        loadAccounts(reset = true)
    }

    /**
     * Load accounts; if reset is true, the previous list will be cleared.
     */
    fun loadAccounts(reset: Boolean = false) {
        viewModelScope.launch {
            if (reset) {
                currentPage = 0
                isLastPage = false
                _accountsState.value = emptyList()
            }
            if (isLastPage) return@launch
            _isLoading.value = true
            _errorMessage.value = ""
            val result = repository.getTransparentAccounts(currentPage, pageSize)
            result.fold(
                onSuccess = { response ->
                    _accountsState.value += response.accounts
                    if (response.nextPage == null || currentPage >= response.pageCount - 1) {
                        isLastPage = true
                    } else {
                        currentPage++
                    }
                },
                onFailure = {
                    _errorMessage.value = "Failed to load data."
                }
            )
            _isLoading.value = false
        }
    }

    /**
     * Refresh data (pull-to-refresh)
     */
    fun refreshAccounts() {
        loadAccounts(reset = true)
    }
}