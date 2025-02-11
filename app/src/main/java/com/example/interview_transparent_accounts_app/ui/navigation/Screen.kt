package com.example.interview_transparent_accounts_app.ui.navigation

/**
 * Represents the navigation routes within the application.
 */
sealed class Screen(val route: String) {

    /**
     * Corresponds to the screen displaying the list of transparent accounts with the route "accountList"
     */
    data object AccountList : Screen("accountList")

    /**
     * Corresponds to the account detail screen with a parameterized route "accountDetail/{accountNumber}".
     * It provides the [createRoute] helper function to generate a concrete route string by replacing the parameter
     * with a specific account number.
     */
    data object AccountDetail : Screen("accountDetail/{accountNumber}") {
        /**
         * Returns the route for the account detail screen with the given [accountNumber].
         */
        fun createRoute(accountNumber: String): String {
            return "accountDetail/$accountNumber"
        }
    }
}