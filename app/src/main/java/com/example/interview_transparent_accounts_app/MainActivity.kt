package com.example.interview_transparent_accounts_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.interview_transparent_accounts_app.ui.navigation.Screen
import com.example.interview_transparent_accounts_app.ui.screens.AccountDetailScreen
import com.example.interview_transparent_accounts_app.ui.screens.AccountListScreen
import com.example.interview_transparent_accounts_app.ui.theme.InterviewTransparentAccountsAppTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * MainActivity is the primary entry point of the application, hosting the entire Compose UI.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InterviewTransparentAccountsAppTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Screen.AccountList.route) {
                    // Account list screen
                    composable(route = Screen.AccountList.route) {
                        AccountListScreen(navController = navController)
                    }
                    // Account detail screen with parameter
                    composable(
                        route = Screen.AccountDetail.route,
                        arguments = listOf(navArgument("accountNumber") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val accountNumber = backStackEntry.arguments?.getString("accountNumber") ?: ""
                        AccountDetailScreen(accountNumber = accountNumber, navController = navController)
                    }
                }
            }
        }
    }
}