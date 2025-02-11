package com.example.interview_transparent_accounts_app.model

/**
 * Represents the response from the transparent accounts API endpoint
 */
data class TransparentAccountsResponse(
    /**
     * Viewed page number
     */
    val pageNumber: Int,
    /**
     * Total number of pages
     */
    val pageCount: Int,
    /**
     * Actual page size
     */
    val pageSize: Int,
    /**
     * Total records count
     */
    val recordCount: Int,
    /**
     * Next page number
     */
    val nextPage: Int?,
    /**
     * Collection of accounts
     */
    val accounts: List<TransparentAccount>,
    /**
     * Downloadable accounts documents
     */
    val statements: List<String>
)