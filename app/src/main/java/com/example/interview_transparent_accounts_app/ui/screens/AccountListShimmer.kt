package com.example.interview_transparent_accounts_app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Displays a list of shimmer placeholders for the accounts list.
 *
 * This composable repeats the [ShimmerAccountListItem] a fixed number of times to simulate
 * the loading state while the actual account data is being fetched.
 */
@Composable
fun AccountListShimmer() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        repeat(5) {
            ShimmerAccountListItem()
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}