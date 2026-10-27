package com.example.model

data class DeviceInfo(
    val model: String = "Android Device",
    val manufacturer: String = "Generic",
    val brand: String = "",
    val androidVersion: String = "14",
    val apiLevel: Int = 34,
    val cpuAbi: String = "arm64-v8a",
    val cpuCores: Int = 8,
    val totalRamBytes: Long = 8L * 1024 * 1024 * 1024,
    val availableRamBytes: Long = 4L * 1024 * 1024 * 1024,
    val totalStorageBytes: Long = 128L * 1024 * 1024 * 1024,
    val freeStorageBytes: Long = 64L * 1024 * 1024 * 1024,
    val batteryLevel: Int = 85,
    val isCharging: Boolean = false,
    val batteryTempC: Float = 32.5f,
    val batteryHealth: String = "Good",
    val networkType: String = "Wi-Fi (5GHz)",
    val optimizationScore: Int = 82,
    val refreshRateHz: Int = 120
) {
    val ramUsedBytes: Long get() = (totalRamBytes - availableRamBytes).coerceAtLeast(0)
    val ramPercent: Int get() = if (totalRamBytes > 0) ((ramUsedBytes * 100) / totalRamBytes).toInt().coerceIn(0, 100) else 50
    val storageUsedBytes: Long get() = (totalStorageBytes - freeStorageBytes).coerceAtLeast(0)
    val storagePercent: Int get() = if (totalStorageBytes > 0) ((storageUsedBytes * 100) / totalStorageBytes).toInt().coerceIn(0, 100) else 50
}

data class BackgroundAppItem(
    val packageName: String,
    val appName: String,
    val isRunning: Boolean = false,
    val isSystemApp: Boolean = false,
    val isRestricted: Boolean = false,
    val isGame: Boolean = false,
    val targetSdkVersion: Int = 34
)

data class GamingProfile(
    val id: String = "balanced",
    val name: String = "Balanced",
    val generalSens: Int = 100,
    val redDotSens: Int = 95,
    val scope2xSens: Int = 88,
    val scope4xSens: Int = 76,
    val sniperSens: Int = 60,
    val freeLookSens: Int = 110,
    val gyroSens: Int = 120,
    val targetFocus: String = "DEFAULT", // "DEFAULT", "HEAD", "BODY"
    val touchResponse: String = "FAST"   // "BALANCED", "FAST", "ULTRA"
)

data class PingResult(
    val serverName: String = "Cloudflare Primary",
    val host: String = "1.1.1.1",
    val isTesting: Boolean = false,
    val currentRttMs: Long? = 28,
    val minRttMs: Long? = 24,
    val maxRttMs: Long? = 36,
    val avgRttMs: Long? = 29,
    val packetLossPercent: Int = 0,
    val jitterMs: Long = 3,
    val statusRating: String = "EXCELLENT",
    val message: String = "Optimal latency for competitive multiplayer gaming"
)

data class QuickOptimizeStep(
    val title: String,
    val description: String,
    val isCompleted: Boolean = false,
    val detailInfo: String = ""
)
