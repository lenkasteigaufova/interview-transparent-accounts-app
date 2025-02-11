package com.example.interview_transparent_accounts_app.repository

import com.example.interview_transparent_accounts_app.model.TransparentAccount
import com.example.interview_transparent_accounts_app.model.TransparentAccountsResponse
import com.example.interview_transparent_accounts_app.network.ApiService
import javax.inject.Inject

/**
 * TransparentAccountRepository is responsible for retrieving transparent account data from the remote API.
 */
class TransparentAccountRepository @Inject constructor(
    private val apiService: ApiService
) {
    /**
     * Retrieves a paginated list of transparent accounts.
     *
     * @param page The zero-based page number to retrieve.
     * @param size The number of records to retrieve per page.
     * @return A [Result] containing a [TransparentAccountsResponse] on success, or a failure result if an error occurs.
     */
    suspend fun getTransparentAccounts(page: Int, size: Int): Result<TransparentAccountsResponse> {
        return try {
            val response = apiService.getTransparentAccounts(page, size)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Empty response body"))
            } else {
                Result.failure(Exception("Error fetching accounts: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Retrieves detailed information for a specific transparent account.
     *
     * @param id The unique identifier of the transparent account.
     * @return A [Result] containing a [TransparentAccount] on success, or a failure result if an error occurs.
     */
    suspend fun getTransparentAccountDetail(id: String): Result<TransparentAccount> {
        return try {
            val response = apiService.getTransparentAccountDetail(id)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Empty response body"))
            } else {
                Result.failure(Exception("Error fetching account detail: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}