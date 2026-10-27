package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.DeviceInfo
import com.example.ui.components.CyberMetricBar
import com.example.ui.components.GamingGlowCard
import com.example.ui.components.OptimizationScoreGauge
import com.example.ui.theme.CyberBlack
import com.example.ui.theme.CyberBorder
import com.example.ui.theme.CyberDark
import com.example.ui.theme.CyberSurface
import com.example.ui.theme.CyberSurfaceElevated
import com.example.ui.theme.ElectricViolet
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonCyanGlow
import com.example.ui.theme.NeonPurple
import com.example.ui.theme.StatusSuccess
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.viewmodel.OptimizerViewModel
import com.example.viewmodel.Screen

@Composable
fun DashboardScreen(
    viewModel: OptimizerViewModel,
    modifier: Modifier = Modifier
) {
    val deviceInfo by viewModel.deviceInfo.collectAsState()
    val isOptimizing by viewModel.isOptimizing.collectAsState()
    val pingResult by viewModel.pingResult.collectAsState()
    val isGamingModeEnabled by viewModel.isGamingModeEnabled.collectAsState()

    val pulseAnim = rememberInfiniteTransition(label = "pulse")
    val pulseScale by pulseAnim.animateFloat(
        initialValue = 1f,
        targetValue = 1.03f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_scale"
    )

    Scaffold(
        containerColor = CyberBlack,
        topBar = {
            Surface(
                color = CyberBlack,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.s4x_logo),
                            contentDescription = "S4X Logo",
                            modifier = Modifier
                                .size(38.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .border(1.dp, NeonCyan.copy(alpha = 0.6f), RoundedCornerShape(8.dp))
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "S4X OPTIMIZER",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Black,
                                    letterSpacing = 1.2.sp
                                ),
                                color = TextPrimary
                            )
                            Text(
                                text = if (isGamingModeEnabled) "GAMING MODE ACTIVE" else "STANDBY STATUS",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold
                                ),
                                color = if (isGamingModeEnabled) NeonCyan else TextSecondary
                            )
                        }
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(
                            onClick = { viewModel.refreshDeviceInfo() },
                            modifier = Modifier
                                .size(40.dp)
                                .background(CyberSurface, CircleShape)
                                .border(1.dp, CyberBorder, CircleShape)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Refresh",
                                tint = NeonCyan
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        IconButton(
                            onClick = { viewModel.navigateTo(Screen.ABOUT_DEV) },
                            modifier = Modifier
                                .size(40.dp)
                                .background(CyberSurface, CircleShape)
                                .border(1.dp, CyberBorder, CircleShape)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = "Settings",
                                tint = NeonPurple
                            )
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Hero Status Card with Circular Gauge & Device Banner
            item {
                GamingGlowCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("status_gauge_card"),
                    borderColor = NeonCyan.copy(alpha = 0.4f),
                    backgroundColor = CyberSurface
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Device Model & OS Chip
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(CyberSurfaceElevated)
                                .border(1.dp, CyberBorder, RoundedCornerShape(20.dp))
                                .padding(horizontal = 14.dp, vertical = 6.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.PhoneAndroid,
                                    contentDescription = null,
                                    tint = NeonCyan,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "${deviceInfo.manufacturer} ${deviceInfo.model} • Android ${deviceInfo.androidVersion}",
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                    color = TextPrimary
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Score Gauge
                        OptimizationScoreGauge(
                            score = deviceInfo.optimizationScore,
                            size = 150.dp
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = if (deviceInfo.optimizationScore >= 90) "Device responsiveness & gaming throughput optimal" else "Optimization recommended for enhanced responsiveness",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSecondary
                        )
                    }
                }
            }

            // Real-Time System Telemetry Card
            item {
                GamingGlowCard(
                    modifier = Modifier.fillMaxWidth(),
                    borderColor = CyberBorder,
                    backgroundColor = CyberSurface
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Text(
                            text = "LIVE SYSTEM TELEMETRY",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.2.sp
                            ),
                            color = NeonCyan
                        )

                        // RAM metric
                        val ramUsedGb = String.format("%.1f", deviceInfo.ramUsedBytes / (1024.0 * 1024 * 1024))
                        val ramTotalGb = String.format("%.1f", deviceInfo.totalRamBytes / (1024.0 * 1024 * 1024))
                        CyberMetricBar(
                            title = "RAM Available",
                            valueText = "${String.format("%.1f", deviceInfo.availableRamBytes / (1024.0 * 1024 * 1024))} GB free / $ramTotalGb GB",
                            percent = deviceInfo.ramPercent,
                            barColor = NeonCyan,
                            icon = Icons.Default.Memory
                        )

                        // Storage metric
                        val storageFreeGb = String.format("%.1f", deviceInfo.freeStorageBytes / (1024.0 * 1024 * 1024))
                        val storageTotalGb = String.format("%.1f", deviceInfo.totalStorageBytes / (1024.0 * 1024 * 1024))
                        CyberMetricBar(
                            title = "Internal Storage",
                            valueText = "$storageFreeGb GB free / $storageTotalGb GB",
                            percent = deviceInfo.storagePercent,
                            barColor = NeonPurple,
                            icon = Icons.Default.Storage
                        )

                        // Battery, Temp & Network Chips
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(CyberSurfaceElevated)
                                    .padding(10.dp)
                            ) {
                                Column {
                                    Text("Battery", style = MaterialTheme.typography.labelSmall, color = TextMuted)
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        "${deviceInfo.batteryLevel}% • ${String.format("%.1f", deviceInfo.batteryTempC)}°C",
                                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                                        color = if (deviceInfo.batteryTempC > 40f) StatusSuccess else TextPrimary
                                    )
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(CyberSurfaceElevated)
                                    .padding(10.dp)
                            ) {
                                Column {
                                    Text("Network & Ping", style = MaterialTheme.typography.labelSmall, color = TextMuted)
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        "${deviceInfo.networkType.take(8)}.. • ${pingResult.avgRttMs ?: 28}ms",
                                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                                        color = NeonCyan
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Four Main Buttons Section
            item {
                Text(
                    text = "OPTIMIZATION MODULES",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.2.sp
                    ),
                    color = TextSecondary,
                    modifier = Modifier.padding(start = 4.dp, bottom = 4.dp)
                )
            }

            // 1. QUICK OPTIMIZE (Primary Hero Action)
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .scale(if (!isOptimizing) pulseScale else 1f)
                ) {
                    Button(
                        onClick = { viewModel.startQuickOptimize() },
                        enabled = !isOptimizing,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(64.dp)
                            .testTag("quick_optimize_button"),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = NeonCyan,
                            contentColor = CyberBlack
                        )
                    ) {
                        if (isOptimizing) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = CyberBlack,
                                strokeWidth = 3.dp
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = "OPTIMIZING DEVICE...",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Black,
                                    letterSpacing = 1.sp
                                )
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.Bolt,
                                contentDescription = null,
                                modifier = Modifier.size(28.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "1. QUICK OPTIMIZE",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Black,
                                    letterSpacing = 1.sp
                                )
                            )
                        }
                    }
                }
            }

            // 2. GAMING MODE Card Button
            item {
                ModuleNavCard(
                    title = "2. GAMING MODE",
                    subtitle = "Low latency, DND mode, refresh rate & game launcher",
                    icon = Icons.Default.SportsEsports,
                    accentColor = NeonPurple,
                    tag = "gaming_mode_button",
                    onClick = { viewModel.navigateTo(Screen.GAMING_MODE) }
                )
            }

            // 3. NETWORK CHECK Card Button
            item {
                ModuleNavCard(
                    title = "3. NETWORK CHECK",
                    subtitle = "Real ping latency test, packet loss & connection diagnostics",
                    icon = Icons.Default.Wifi,
                    accentColor = NeonCyan,
                    tag = "network_check_button",
                    onClick = { viewModel.navigateTo(Screen.NETWORK_CHECK) }
                )
            }

            // 4. DEVICE INFORMATION Card Button
            item {
                ModuleNavCard(
                    title = "4. DEVICE INFORMATION",
                    subtitle = "CPU specs, RAM breakdown, battery health & OS architecture",
                    icon = Icons.Default.Info,
                    accentColor = ElectricViolet,
                    tag = "device_info_button",
                    onClick = { viewModel.navigateTo(Screen.DEVICE_INFO) }
                )
            }

            // Extra Essential Modules
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    GamingGlowCard(
                        modifier = Modifier
                            .weight(1f)
                            .testTag("background_manager_button"),
                        borderColor = CyberBorder,
                        backgroundColor = CyberSurface,
                        onClick = { viewModel.navigateTo(Screen.BACKGROUND_MANAGER) }
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Icon(
                                imageVector = Icons.Default.Apps,
                                contentDescription = null,
                                tint = NeonCyan,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "BACKGROUND MANAGER",
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                color = TextPrimary
                            )
                            Text(
                                text = "Inspect & restrict apps",
                                style = MaterialTheme.typography.bodySmall,
                                color = TextMuted
                            )
                        }
                    }

                    GamingGlowCard(
                        modifier = Modifier
                            .weight(1f)
                            .testTag("sensitivity_preferences_button"),
                        borderColor = CyberBorder,
                        backgroundColor = CyberSurface,
                        onClick = { viewModel.navigateTo(Screen.SENSITIVITY_SETTINGS) }
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Icon(
                                imageVector = Icons.Default.Tune,
                                contentDescription = null,
                                tint = NeonPurple,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "SENSITIVITY PROFILES",
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                color = TextPrimary
                            )
                            Text(
                                text = "Head/body touch configs",
                                style = MaterialTheme.typography.bodySmall,
                                color = TextMuted
                            )
                        }
                    }
                }
            }

            // Quick Logout / Developer shortcut
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "S4X Mobile Optimizer v1.0.0",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextMuted
                    )
                    Row(
                        modifier = Modifier
                            .testTag("dashboard_logout_button")
                            .clickable { viewModel.logout() }
                            .padding(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Logout,
                            contentDescription = "Logout",
                            tint = TextSecondary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Logout",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = TextSecondary
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ModuleNavCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    accentColor: Color,
    tag: String,
    onClick: () -> Unit
) {
    GamingGlowCard(
        modifier = Modifier
            .fillMaxWidth()
            .testTag(tag),
        borderColor = accentColor.copy(alpha = 0.35f),
        backgroundColor = CyberSurface,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(CyberSurfaceElevated)
                    .border(1.dp, accentColor.copy(alpha = 0.5f), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = accentColor,
                    modifier = Modifier.size(26.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    ),
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary
                )
            }
        }
    }
}
