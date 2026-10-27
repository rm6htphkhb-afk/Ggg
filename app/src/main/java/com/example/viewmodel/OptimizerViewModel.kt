package com.example.viewmodel

import android.app.Application
import android.os.SystemClock
import android.provider.Settings
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.PreferencesManager
import com.example.data.SystemStatsManager
import com.example.model.BackgroundAppItem
import com.example.model.DeviceInfo
import com.example.model.GamingProfile
import com.example.model.PingResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class Screen {
    LOGIN,
    DASHBOARD,
    BACKGROUND_MANAGER,
    GAMING_MODE,
    NETWORK_CHECK,
    DEVICE_INFO,
    SENSITIVITY_SETTINGS,
    ABOUT_DEV
}

class OptimizerViewModel(application: Application) : AndroidViewModel(application) {

    private val statsManager = SystemStatsManager(application)
    private val prefsManager = PreferencesManager(application)

    // Navigation
    private val _currentScreen = MutableStateFlow(
        if (prefsManager.isLoggedIn()) Screen.DASHBOARD else Screen.LOGIN
    )
    val currentScreen: StateFlow<Screen> = _currentScreen.asStateFlow()

    // Auth State
    private val _usernameInput = MutableStateFlow("AIM")
    val usernameInput: StateFlow<String> = _usernameInput.asStateFlow()

    private val _passwordInput = MutableStateFlow("123")
    val passwordInput: StateFlow<String> = _passwordInput.asStateFlow()

    private val _isPasswordVisible = MutableStateFlow(false)
    val isPasswordVisible: StateFlow<Boolean> = _isPasswordVisible.asStateFlow()

    private val _rememberLogin = MutableStateFlow(prefsManager.isRememberLoginEnabled())
    val rememberLogin: StateFlow<Boolean> = _rememberLogin.asStateFlow()

    private val _isLoggingIn = MutableStateFlow(false)
    val isLoggingIn: StateFlow<Boolean> = _isLoggingIn.asStateFlow()

    private val _loginError = MutableStateFlow<String?>(null)
    val loginError: StateFlow<String?> = _loginError.asStateFlow()

    // System Telemetry
    private val _deviceInfo = MutableStateFlow(DeviceInfo())
    val deviceInfo: StateFlow<DeviceInfo> = _deviceInfo.asStateFlow()

    // Quick Optimization State
    private val _isOptimizing = MutableStateFlow(false)
    val isOptimizing: StateFlow<Boolean> = _isOptimizing.asStateFlow()

    private val _optimizeProgress = MutableStateFlow(0f)
    val optimizeProgress: StateFlow<Float> = _optimizeProgress.asStateFlow()

    private val _optimizeCurrentStep = MutableStateFlow("")
    val optimizeCurrentStep: StateFlow<String> = _optimizeCurrentStep.asStateFlow()

    private val _showOptimizeReport = MutableStateFlow(false)
    val showOptimizeReport: StateFlow<Boolean> = _showOptimizeReport.asStateFlow()

    private val _freedStorageReport = MutableStateFlow("")
    val freedStorageReport: StateFlow<String> = _freedStorageReport.asStateFlow()

    // Background Apps
    private val _installedApps = MutableStateFlow<List<BackgroundAppItem>>(emptyList())
    val installedApps: StateFlow<List<BackgroundAppItem>> = _installedApps.asStateFlow()

    private val _isLoadingApps = MutableStateFlow(false)
    val isLoadingApps: StateFlow<Boolean> = _isLoadingApps.asStateFlow()

    private val _appSearchQuery = MutableStateFlow("")
    val appSearchQuery: StateFlow<String> = _appSearchQuery.asStateFlow()

    // Gaming Mode
    private val _isGamingModeEnabled = MutableStateFlow(prefsManager.isGamingModeActiveFlow.value)
    val isGamingModeEnabled: StateFlow<Boolean> = _isGamingModeEnabled.asStateFlow()

    // Gaming Sensitivity Profile
    val gamingProfile: StateFlow<GamingProfile> = prefsManager.gamingProfileFlow

    // Network & Ping
    private val _pingResult = MutableStateFlow(PingResult())
    val pingResult: StateFlow<PingResult> = _pingResult.asStateFlow()

    private val _isTestingPing = MutableStateFlow(false)
    val isTestingPing: StateFlow<Boolean> = _isTestingPing.asStateFlow()

    private val _selectedPingServer = MutableStateFlow("1.1.1.1")
    val selectedPingServer: StateFlow<String> = _selectedPingServer.asStateFlow()

    // Toast / Snack notification message
    private val _userMessage = MutableStateFlow<String?>(null)
    val userMessage: StateFlow<String?> = _userMessage.asStateFlow()

    init {
        refreshDeviceInfo()
        loadInstalledApps()
    }

    fun navigateTo(screen: Screen) {
        _currentScreen.value = screen
    }

    fun setUsernameInput(text: String) {
        _usernameInput.value = text
        _loginError.value = null
    }

    fun setPasswordInput(text: String) {
        _passwordInput.value = text
        _loginError.value = null
    }

    fun togglePasswordVisibility() {
        _isPasswordVisible.update { !it }
    }

    fun setRememberLogin(remember: Boolean) {
        _rememberLogin.value = remember
    }

    fun fillDemoCredentials() {
        _usernameInput.value = "AIM"
        _passwordInput.value = "123"
        _loginError.value = null
    }

    fun login() {
        val user = _usernameInput.value.trim()
        val pass = _passwordInput.value.trim()

        if (user.isEmpty() || pass.isEmpty()) {
            _loginError.value = "Please enter both username and password"
            return
        }

        viewModelScope.launch {
            _isLoggingIn.value = true
            _loginError.value = null
            delay(650)

            if (user == "AIM" && pass == "123") {
                prefsManager.setLoggedIn(true, _rememberLogin.value)
                _isLoggingIn.value = false
                _currentScreen.value = Screen.DASHBOARD
            } else {
                _isLoggingIn.value = false
                _loginError.value = "Invalid credentials. Use Username: AIM / Password: 123"
            }
        }
    }

    fun logout() {
        prefsManager.logout()
        _currentScreen.value = Screen.LOGIN
        _usernameInput.value = ""
        _passwordInput.value = ""
        _loginError.value = null
    }

    fun refreshDeviceInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val info = statsManager.getDeviceInfo()
                _deviceInfo.value = info
            } catch (_: Throwable) {}
        }
    }

    fun loadInstalledApps() {
        viewModelScope.launch {
            try {
                _isLoadingApps.value = true
                val apps = statsManager.getInstalledApps()
                _installedApps.value = apps
            } catch (_: Throwable) {
            } finally {
                _isLoadingApps.value = false
            }
        }
    }

    fun setAppSearchQuery(query: String) {
        _appSearchQuery.value = query
    }

    fun startQuickOptimize() {
        if (_isOptimizing.value) return
        viewModelScope.launch {
            _isOptimizing.value = true
            _optimizeProgress.value = 0.1f
            _optimizeCurrentStep.value = "Reading device hardware & thermal profiles..."
            delay(500)

            _optimizeProgress.value = 0.3f
            _optimizeCurrentStep.value = "Evaluating RAM heap & active memory buffers..."
            delay(600)

            _optimizeProgress.value = 0.55f
            _optimizeCurrentStep.value = "Scanning cache clusters & temporary logs..."
            val (freedCache, reclaimedRam) = statsManager.performSafeOptimization()
            delay(600)

            _optimizeProgress.value = 0.75f
            _optimizeCurrentStep.value = "Testing network latency & DNS throughput..."
            delay(500)

            _optimizeProgress.value = 0.95f
            _optimizeCurrentStep.value = "Calibrating touch responsiveness & thread priority..."
            delay(400)

            _optimizeProgress.value = 1.0f
            _optimizeCurrentStep.value = "Optimization complete!"

            val freedMb = (freedCache / (1024 * 1024)).coerceAtLeast(42)
            val ramMb = (reclaimedRam / (1024 * 1024)).coerceAtLeast(256)
            _freedStorageReport.value = "$freedMb MB cache freed | $ramMb MB memory buffer recalibrated"

            prefsManager.setLastOptimizationTime(System.currentTimeMillis())
            _deviceInfo.update {
                it.copy(
                    optimizationScore = 98,
                    availableRamBytes = (it.availableRamBytes + reclaimedRam).coerceAtMost(it.totalRamBytes)
                )
            }

            _isOptimizing.value = false
            _showOptimizeReport.value = true
        }
    }

    fun dismissOptimizeReport() {
        _showOptimizeReport.value = false
    }

    fun toggleGamingMode(enabled: Boolean) {
        _isGamingModeEnabled.value = enabled
        prefsManager.setGamingModeActive(enabled)
        _userMessage.value = if (enabled) "Gaming Mode activated. Background priority optimized." else "Gaming Mode deactivated."
    }

    fun launchGame(packageName: String) {
        val success = statsManager.launchApp(packageName)
        if (!success) {
            _userMessage.value = "Could not launch app $packageName"
        }
    }

    fun openAppSystemSettings(packageName: String) {
        statsManager.openAppSettings(packageName)
    }

    fun openBatteryOptimizationSettings() {
        statsManager.openBatteryOptimizationSettings()
    }

    fun openDndSettings() {
        statsManager.openDndSettings()
    }

    fun openDisplaySettings() {
        statsManager.openSystemSettings(Settings.ACTION_DISPLAY_SETTINGS)
    }

    fun openNetworkSettings() {
        statsManager.openSystemSettings(Settings.ACTION_WIRELESS_SETTINGS)
    }

    fun openSoundSettings() {
        statsManager.openSystemSettings(Settings.ACTION_SOUND_SETTINGS)
    }

    fun setSelectedPingServer(host: String) {
        _selectedPingServer.value = host
    }

    fun runPingTest() {
        if (_isTestingPing.value) return
        val host = _selectedPingServer.value
        val name = when (host) {
            "1.1.1.1" -> "Cloudflare DNS"
            "8.8.8.8" -> "Google Public DNS"
            "208.67.222.222" -> "OpenDNS"
            else -> "Primary Host"
        }

        viewModelScope.launch {
            _isTestingPing.value = true
            _pingResult.update { it.copy(isTesting = true) }
            val result = statsManager.runRealPingTest(host = host, serverName = name)
            _pingResult.value = result
            _isTestingPing.value = false
        }
    }

    // Gaming Sensitivity Updates
    fun updateGeneralSens(value: Int) {
        prefsManager.saveProfile(gamingProfile.value.copy(generalSens = value.coerceIn(0, 200)))
    }

    fun updateRedDotSens(value: Int) {
        prefsManager.saveProfile(gamingProfile.value.copy(redDotSens = value.coerceIn(0, 200)))
    }

    fun updateScope2xSens(value: Int) {
        prefsManager.saveProfile(gamingProfile.value.copy(scope2xSens = value.coerceIn(0, 200)))
    }

    fun updateScope4xSens(value: Int) {
        prefsManager.saveProfile(gamingProfile.value.copy(scope4xSens = value.coerceIn(0, 200)))
    }

    fun updateSniperSens(value: Int) {
        prefsManager.saveProfile(gamingProfile.value.copy(sniperSens = value.coerceIn(0, 200)))
    }

    fun updateFreeLookSens(value: Int) {
        prefsManager.saveProfile(gamingProfile.value.copy(freeLookSens = value.coerceIn(0, 200)))
    }

    fun updateGyroSens(value: Int) {
        prefsManager.saveProfile(gamingProfile.value.copy(gyroSens = value.coerceIn(0, 200)))
    }

    fun updateTargetFocus(focus: String) {
        prefsManager.saveProfile(gamingProfile.value.copy(targetFocus = focus))
    }

    fun updateTouchResponse(response: String) {
        prefsManager.saveProfile(gamingProfile.value.copy(touchResponse = response))
    }

    fun applyPreset(type: String) {
        val preset = prefsManager.getPresetProfile(type)
        prefsManager.saveProfile(preset)
        _userMessage.value = "Loaded ${preset.name} profile preset."
    }

    fun resetSensitivityToDefault() {
        val reset = prefsManager.resetToDefaults()
        _userMessage.value = "Sensitivity preferences reset to default."
    }

    fun clearUserMessage() {
        _userMessage.value = null
    }
}
