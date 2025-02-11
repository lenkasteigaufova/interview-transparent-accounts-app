package com.example.interview_transparent_accounts_app.network

import com.example.interview_transparent_accounts_app.model.TransparentAccount
import com.example.interview_transparent_accounts_app.model.TransparentAccountsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * ApiService defines the Retrofit HTTP API endpoints for retrieving transparent accounts data.
 *
 * This interface includes methods for fetching a paginated list of transparent accounts as well as
 * fetching detailed information for a specific transparent account.
 */
interface ApiService {

    /**
     * Retrieves a paginated list of transparent accounts.
     *
     * @param page The zero-based page number to retrieve.
     * @param size The number of records to return in the response.
     * @param filter A filter string to narrow down the results (default value is "example").
     * @return A [Response] containing a [TransparentAccountsResponse] if the request is successful.
     */
    @GET("transparentAccounts/")
    suspend fun getTransparentAccounts(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 10,
        @Query("filter") filter: String? = null
    ): Response<TransparentAccountsResponse>

    /**
     * Retrieves detailed information for a specific transparent account.
     *
     * @param id The unique identifier of the transparent account.
     * @return A [Response] containing a [TransparentAccount] if the request is successful.
     */
    @GET("transparentAccounts/{id}")
    suspend fun getTransparentAccountDetail(
        @Path("id") id: String
    ): Response<TransparentAccount>
}