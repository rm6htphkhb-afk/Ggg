package com.example.data

import android.content.Context
import android.content.SharedPreferences
import com.example.model.GamingProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PreferencesManager(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences("s4x_prefs", Context.MODE_PRIVATE)

    private val _isLoggedInFlow = MutableStateFlow(isLoggedIn())
    val isLoggedInFlow: StateFlow<Boolean> = _isLoggedInFlow.asStateFlow()

    private val _gamingProfileFlow = MutableStateFlow(loadCurrentProfile())
    val gamingProfileFlow: StateFlow<GamingProfile> = _gamingProfileFlow.asStateFlow()

    private val _isGamingModeActiveFlow = MutableStateFlow(prefs.getBoolean(KEY_GAMING_MODE_ACTIVE, false))
    val isGamingModeActiveFlow: StateFlow<Boolean> = _isGamingModeActiveFlow.asStateFlow()

    fun isLoggedIn(): Boolean {
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false) && prefs.getBoolean(KEY_REMEMBER_LOGIN, false)
    }

    fun setLoggedIn(loggedIn: Boolean, remember: Boolean) {
        prefs.edit()
            .putBoolean(KEY_IS_LOGGED_IN, loggedIn)
            .putBoolean(KEY_REMEMBER_LOGIN, remember)
            .apply()
        _isLoggedInFlow.value = loggedIn
    }

    fun logout() {
        prefs.edit()
            .putBoolean(KEY_IS_LOGGED_IN, false)
            .putBoolean(KEY_REMEMBER_LOGIN, false)
            .apply()
        _isLoggedInFlow.value = false
    }

    fun isRememberLoginEnabled(): Boolean {
        return prefs.getBoolean(KEY_REMEMBER_LOGIN, false)
    }

    fun setGamingModeActive(active: Boolean) {
        prefs.edit().putBoolean(KEY_GAMING_MODE_ACTIVE, active).apply()
        _isGamingModeActiveFlow.value = active
    }

    fun loadCurrentProfile(): GamingProfile {
        val id = prefs.getString(KEY_PROFILE_ID, "balanced") ?: "balanced"
        val name = prefs.getString(KEY_PROFILE_NAME, "Balanced") ?: "Balanced"
        val general = prefs.getInt(KEY_GENERAL_SENS, 100)
        val redDot = prefs.getInt(KEY_RED_DOT_SENS, 95)
        val s2x = prefs.getInt(KEY_SCOPE_2X_SENS, 88)
        val s4x = prefs.getInt(KEY_SCOPE_4X_SENS, 76)
        val sniper = prefs.getInt(KEY_SNIPER_SENS, 60)
        val freeLook = prefs.getInt(KEY_FREE_LOOK_SENS, 110)
        val gyro = prefs.getInt(KEY_GYRO_SENS, 120)
        val target = prefs.getString(KEY_TARGET_FOCUS, "DEFAULT") ?: "DEFAULT"
        val touch = prefs.getString(KEY_TOUCH_RESPONSE, "FAST") ?: "FAST"

        return GamingProfile(
            id = id,
            name = name,
            generalSens = general,
            redDotSens = redDot,
            scope2xSens = s2x,
            scope4xSens = s4x,
            sniperSens = sniper,
            freeLookSens = freeLook,
            gyroSens = gyro,
            targetFocus = target,
            touchResponse = touch
        )
    }

    fun saveProfile(profile: GamingProfile) {
        prefs.edit()
            .putString(KEY_PROFILE_ID, profile.id)
            .putString(KEY_PROFILE_NAME, profile.name)
            .putInt(KEY_GENERAL_SENS, profile.generalSens)
            .putInt(KEY_RED_DOT_SENS, profile.redDotSens)
            .putInt(KEY_SCOPE_2X_SENS, profile.scope2xSens)
            .putInt(KEY_SCOPE_4X_SENS, profile.scope4xSens)
            .putInt(KEY_SNIPER_SENS, profile.sniperSens)
            .putInt(KEY_FREE_LOOK_SENS, profile.freeLookSens)
            .putInt(KEY_GYRO_SENS, profile.gyroSens)
            .putString(KEY_TARGET_FOCUS, profile.targetFocus)
            .putString(KEY_TOUCH_RESPONSE, profile.touchResponse)
            .apply()
        _gamingProfileFlow.value = profile
    }

    fun resetToDefaults(): GamingProfile {
        val defaultProfile = GamingProfile(
            id = "balanced",
            name = "Balanced",
            generalSens = 100,
            redDotSens = 95,
            scope2xSens = 88,
            scope4xSens = 76,
            sniperSens = 60,
            freeLookSens = 110,
            gyroSens = 120,
            targetFocus = "DEFAULT",
            touchResponse = "FAST"
        )
        saveProfile(defaultProfile)
        return defaultProfile
    }

    fun getPresetProfile(type: String): GamingProfile {
        return when (type) {
            "RESPONSIVE" -> GamingProfile(
                id = "responsive",
                name = "Responsive",
                generalSens = 145,
                redDotSens = 130,
                scope2xSens = 120,
                scope4xSens = 105,
                sniperSens = 85,
                freeLookSens = 150,
                gyroSens = 160,
                targetFocus = "HEAD",
                touchResponse = "ULTRA"
            )
            "BALANCED" -> GamingProfile(
                id = "balanced",
                name = "Balanced",
                generalSens = 100,
                redDotSens = 95,
                scope2xSens = 88,
                scope4xSens = 76,
                sniperSens = 60,
                freeLookSens = 110,
                gyroSens = 120,
                targetFocus = "DEFAULT",
                touchResponse = "FAST"
            )
            else -> GamingProfile(
                id = "custom",
                name = "Custom",
                generalSens = 120,
                redDotSens = 110,
                scope2xSens = 100,
                scope4xSens = 90,
                sniperSens = 75,
                freeLookSens = 130,
                gyroSens = 140,
                targetFocus = "BODY",
                touchResponse = "FAST"
            )
        }
    }

    fun getLastOptimizationTime(): Long {
        return prefs.getLong(KEY_LAST_OPT_TIME, 0L)
    }

    fun setLastOptimizationTime(timestamp: Long) {
        prefs.edit().putLong(KEY_LAST_OPT_TIME, timestamp).apply()
    }

    companion object {
        private const val KEY_IS_LOGGED_IN = "key_is_logged_in"
        private const val KEY_REMEMBER_LOGIN = "key_remember_login"
        private const val KEY_GAMING_MODE_ACTIVE = "key_gaming_mode_active"

        private const val KEY_PROFILE_ID = "key_profile_id"
        private const val KEY_PROFILE_NAME = "key_profile_name"
        private const val KEY_GENERAL_SENS = "key_general_sens"
        private const val KEY_RED_DOT_SENS = "key_red_dot_sens"
        private const val KEY_SCOPE_2X_SENS = "key_scope_2x_sens"
        private const val KEY_SCOPE_4X_SENS = "key_scope_4x_sens"
        private const val KEY_SNIPER_SENS = "key_sniper_sens"
        private const val KEY_FREE_LOOK_SENS = "key_free_look_sens"
        private const val KEY_GYRO_SENS = "key_gyro_sens"
        private const val KEY_TARGET_FOCUS = "key_target_focus"
        private const val KEY_TOUCH_RESPONSE = "key_touch_response"
        private const val KEY_LAST_OPT_TIME = "key_last_opt_time"
    }
}
