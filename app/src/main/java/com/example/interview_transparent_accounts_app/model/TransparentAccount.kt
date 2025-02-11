package com.example.interview_transparent_accounts_app.model

/**
* A single transparent account object with all its details
*/
data class TransparentAccount(
    /**
     * Account number with prefix.
     */
    val accountNumber: String,
    /**
     * Bank code.
     */
    val bankCode: String,
    /**
     * Date from which the account is transparent.
     */
    val transparencyFrom: String,
    /**
     * Date by which the account is transparent (including).
     */
    val transparencyTo: String,
    /**
     * Date by which entries are valid.
     */
    val publicationTo: String,
    /**
     * Date of last update.
     */
    val actualizationDate: String,
    /**
     * Actual account balance.
     */
    val balance: Double,
    /**
     * Account currency.
     */
    val currency: String,
    /**
     * Account owner name.
     */
    val name: String,
    /**
     * Account description.
     */
    val description: String,
    /**
     * Account note.
     */
    val note: String? = null,
    /**
     * International bank account number.
     *
     */
    val iban: String? = null,
    /**
     * Downloadable accounts documents.
     */
    val statements: List<String> = emptyList(),
)