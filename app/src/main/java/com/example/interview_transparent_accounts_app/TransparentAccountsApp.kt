package com.example.interview_transparent_accounts_app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Application class required for Hilt DI.
 */
@HiltAndroidApp
class TransparentAccountsApp : Application()