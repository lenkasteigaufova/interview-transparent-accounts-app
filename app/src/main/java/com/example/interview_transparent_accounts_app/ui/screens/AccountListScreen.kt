package com.example.interview_transparent_accounts_app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.interview_transparent_accounts_app.R
import com.example.interview_transparent_accounts_app.model.TransparentAccount
import com.example.interview_transparent_accounts_app.ui.navigation.Screen
import com.example.interview_transparent_accounts_app.viewmodel.TransparentAccountViewModel

/**
 * Displays a paginated list of transparent accounts with pull-to-refresh functionality.
 *
 * @param viewModel The view model ([TransparentAccountViewModel]) responsible for providing the list of
 * transparent accounts, as well as managing the loading and error states.
 * @param navController The navigation controller used to navigate between screens, such as moving to the
 * account detail screen.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountListScreen(
    viewModel: TransparentAccountViewModel = hiltViewModel(),
    navController: NavController
) {
    val accounts by viewModel.accountsState
    val isLoading by viewModel.isLoading
    val errorMessage by viewModel.errorMessage

    // Create a pull-to-refresh state
    val pullToRefreshState = rememberPullToRefreshState()

    PullToRefreshBox(
        modifier = Modifier
            .fillMaxSize(),
        state = pullToRefreshState,
        isRefreshing = isLoading,
        onRefresh = {
            viewModel.refreshAccounts()
        }
    ) {
        if (isLoading && accounts.isEmpty()) {
            // Show shimmer placeholders when loading and the list is empty.
            AccountListShimmer()
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp)
            ) {
                itemsIndexed(accounts) { index, account ->
                    AccountListItem(account = account, onClick = {
                        // Navigate to the account detail screen with the accountNumber.
                        navController.navigate(Screen.AccountDetail.createRoute(account.accountNumber))
                    })
                    Spacer(modifier = Modifier.height(8.dp))
                    // If reached the end of list, trigger next page loading.
                    if (index == accounts.lastIndex && !isLoading) {
                        LaunchedEffect(Unit) {
                            viewModel.loadAccounts()
                        }
                    }
                }
            }
        }
    }
    if (errorMessage.isNotEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
        }
    }
}

/**
 * Displays a single transparent account item as a clickable card in the account list.
 *
 * @param account The [TransparentAccount] object containing the details to be displayed.
 * @param onClick A lambda function that is invoked when the account item is clicked.
 */
@Composable
fun AccountListItem(account: TransparentAccount, onClick: () -> Unit) {
    ElevatedCard(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Column for the account name and number.
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = account.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stringResource(R.string.account, account.accountNumber),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            // Highlighted account balance on the right.
            Text(
                text = stringResource(R.string.balance, account.balance, account.currency),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}