package com.example.interview_transparent_accounts_app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.interview_transparent_accounts_app.R
import com.example.interview_transparent_accounts_app.util.toFormattedDate
import com.example.interview_transparent_accounts_app.viewmodel.TransparentAccountDetailViewModel

/**
 * Displays the detailed information of a specific transparent account.
 *
 * @param accountNumber The unique identifier (account number with prefix) of the transparent account.
 * @param viewModel The view model that provides the account detail data and business logic.
 * @param navController The navigation controller used to navigate back or to other screens.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountDetailScreen(
    accountNumber: String,
    viewModel: TransparentAccountDetailViewModel = hiltViewModel(),
    navController: NavController
) {
    val accountDetail by viewModel.accountDetail
    val isLoading by viewModel.isLoading
    val errorMessage by viewModel.errorMessage

    // Create a pull-to-refresh state
    val pullToRefreshState = rememberPullToRefreshState()

    // Load the account detail when the screen is composed.
    LaunchedEffect(accountNumber) {
        viewModel.loadAccountDetail(accountNumber)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = accountDetail?.name ?: stringResource(R.string.account_detail)) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(R.string.back))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onSecondary
                )
            )
        }
    ) { paddingValues ->
        PullToRefreshBox(
            modifier = Modifier.padding(paddingValues).fillMaxSize(),
            state = pullToRefreshState,
            isRefreshing = isLoading,
            onRefresh = { viewModel.refreshAccountDetail(accountNumber) }
        ) {
            when {
                errorMessage.isNotEmpty() -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
                    }
                }
                else -> {
                    accountDetail?.let { account ->
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                                .padding(16.dp)
                        ) {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text(
                                        text = stringResource(R.string.balance, account.balance, account.currency),
                                        style = MaterialTheme.typography.headlineSmall,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    DetailItem(
                                        label = stringResource(R.string.account_number),
                                        value = account.accountNumber,
                                        showDivider = false
                                    )
                                    DetailItem(
                                        label = stringResource(R.string.bank_code),
                                        value = account.bankCode
                                    )
                                    DetailItem(
                                        label = stringResource(R.string.transparency_from),
                                        value = account.transparencyFrom.toFormattedDate()
                                    )
                                    DetailItem(
                                        label = stringResource(R.string.transparency_to),
                                        value = account.transparencyTo.toFormattedDate()
                                    )
                                    DetailItem(
                                        label = stringResource(R.string.publication_to),
                                        value = account.publicationTo.toFormattedDate()
                                    )
                                    DetailItem(
                                        label = stringResource(R.string.actualization_date),
                                        value = account.actualizationDate.toFormattedDate()
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            // Additional details (description, note, IBAN) in a separate section.
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    DetailItem(
                                        label = stringResource(R.string.description),
                                        value = account.description,
                                        showDivider = false
                                    )
                                    if (account.note != null) {
                                        DetailItem(
                                            label = stringResource(R.string.note),
                                            value = account.note
                                        )
                                    }
                                    if (account.iban != null) {
                                        DetailItem(
                                            label = stringResource(R.string.iban),
                                            value = account.iban
                                        )
                                    }
                                }
                            }
                            if (account.statements.isNotEmpty()) {
                                Spacer(modifier = Modifier.height(16.dp))
                                // Documents in a separate section.
                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                                ) {
                                    Column(modifier = Modifier.padding(16.dp)) {
                                        Text(
                                            text = stringResource(R.string.documents),
                                            style = MaterialTheme.typography.labelMedium,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                        account.statements.forEachIndexed { index, statement ->
                                            Text(
                                                text = statement,
                                                style = MaterialTheme.typography.bodyLarge
                                            )
                                            if (index < account.statements.size - 1) {
                                                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * A composable function to display a label-value pair with consistent styling.
 *
 * @param label The description of the data field.
 * @param value The value of the data field.
 */
@Composable
fun DetailItem(label: String, value: String, showDivider: Boolean = true) {
    Column {
        if (showDivider) HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}