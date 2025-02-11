package com.example.interview_transparent_accounts_app.util

import java.text.SimpleDateFormat
import java.util.Locale

/**
 * Converts a date-time string in the format "yyyy-MM-dd'T'HH:mm:ss" to a formatted date string
 * in the format "dd.MM.yyyy".
 *
 * @return A formatted date string ("dd.MM.yyyy") if parsing is successful; otherwise, returns the original string.
 */
fun String.toFormattedDate(): String =
    try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val date = inputFormat.parse(this)
        val outputFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        date?.let { outputFormat.format(it) } ?: this
    } catch (e: Exception) {
        this
    }
