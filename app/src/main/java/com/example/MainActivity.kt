package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.screens.AboutScreen
import com.example.ui.screens.BackgroundManagerScreen
import com.example.ui.screens.DashboardScreen
import com.example.ui.screens.DeviceInfoScreen
import com.example.ui.screens.GamingModeScreen
import com.example.ui.screens.LoginScreen
import com.example.ui.screens.NetworkScreen
import com.example.ui.screens.QuickOptimizeDialog
import com.example.ui.screens.SensitivityScreen
import com.example.ui.theme.CyberBlack
import com.example.ui.theme.S4XTheme
import com.example.viewmodel.OptimizerViewModel
import com.example.viewmodel.Screen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            S4XTheme {
                val viewModel: OptimizerViewModel = viewModel()
                val currentScreen by viewModel.currentScreen.collectAsState()
                val userMessage by viewModel.userMessage.collectAsState()
                val snackbarHostState = remember { SnackbarHostState() }

                LaunchedEffect(userMessage) {
                    userMessage?.let {
                        snackbarHostState.showSnackbar(it)
                        viewModel.clearUserMessage()
                    }
                }

                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .imePadding(),
                    containerColor = CyberBlack,
                    snackbarHost = { SnackbarHost(snackbarHostState) }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        AnimatedContent(
                            targetState = currentScreen,
                            transitionSpec = { fadeIn() togetherWith fadeOut() },
                            label = "screen_transition"
                        ) { screen ->
                            when (screen) {
                                Screen.LOGIN -> {
                                    LoginScreen(viewModel = viewModel)
                                }
                                Screen.DASHBOARD -> {
                                    DashboardScreen(viewModel = viewModel)
                                }
                                Screen.BACKGROUND_MANAGER -> {
                                    BackHandler { viewModel.navigateTo(Screen.DASHBOARD) }
                                    BackgroundManagerScreen(viewModel = viewModel)
                                }
                                Screen.GAMING_MODE -> {
                                    BackHandler { viewModel.navigateTo(Screen.DASHBOARD) }
                                    GamingModeScreen(viewModel = viewModel)
                                }
                                Screen.NETWORK_CHECK -> {
                                    BackHandler { viewModel.navigateTo(Screen.DASHBOARD) }
                                    NetworkScreen(viewModel = viewModel)
                                }
                                Screen.DEVICE_INFO -> {
                                    BackHandler { viewModel.navigateTo(Screen.DASHBOARD) }
                                    DeviceInfoScreen(viewModel = viewModel)
                                }
                                Screen.SENSITIVITY_SETTINGS -> {
                                    BackHandler { viewModel.navigateTo(Screen.DASHBOARD) }
                                    SensitivityScreen(viewModel = viewModel)
                                }
                                Screen.ABOUT_DEV -> {
                                    BackHandler { viewModel.navigateTo(Screen.DASHBOARD) }
                                    AboutScreen(viewModel = viewModel)
                                }
                            }
                        }

                        // Global Quick Optimization Modal
                        QuickOptimizeDialog(viewModel = viewModel)
                    }
                }
            }
        }
    }
}
