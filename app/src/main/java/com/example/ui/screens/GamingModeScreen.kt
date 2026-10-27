package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BatteryChargingFull
import androidx.compose.material.icons.filled.DoNotDisturbOn
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Restore
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.BackgroundAppItem
import com.example.ui.components.CyberMetricBar
import com.example.ui.components.GamingGlowCard
import com.example.ui.components.S4XTopBar
import com.example.ui.theme.CyberBlack
import com.example.ui.theme.CyberBorder
import com.example.ui.theme.CyberDark
import com.example.ui.theme.CyberSurface
import com.example.ui.theme.CyberSurfaceElevated
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonPurple
import com.example.ui.theme.StatusSuccess
import com.example.ui.theme.StatusWarning
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.viewmodel.OptimizerViewModel
import com.example.viewmodel.Screen

@Composable
fun GamingModeScreen(
    viewModel: OptimizerViewModel,
    modifier: Modifier = Modifier
) {
    val isGamingModeEnabled by viewModel.isGamingModeEnabled.collectAsState()
    val deviceInfo by viewModel.deviceInfo.collectAsState()
    val installedApps by viewModel.installedApps.collectAsState()

    // Filter discovered games or popular gaming packages
    val games = installedApps.filter { it.isGame }

    Scaffold(
        containerColor = CyberBlack,
        topBar = {
            S4XTopBar(
                title = "GAMING MODE",
                subtitle = "OPTIMIZATION HUB",
                onBackClick = { viewModel.navigateTo(Screen.DASHBOARD) }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Master Gaming Mode Switch Card
            item {
                GamingGlowCard(
                    modifier = Modifier.fillMaxWidth(),
                    borderColor = if (isGamingModeEnabled) NeonCyan else CyberBorder,
                    backgroundColor = CyberSurface
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(52.dp)
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(if (isGamingModeEnabled) NeonCyan.copy(alpha = 0.2f) else CyberDark)
                                    .border(
                                        1.dp,
                                        if (isGamingModeEnabled) NeonCyan else CyberBorder,
                                        RoundedCornerShape(14.dp)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.SportsEsports,
                                    contentDescription = null,
                                    tint = if (isGamingModeEnabled) NeonCyan else TextSecondary,
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(14.dp))
                            Column {
                                Text(
                                    text = "Enable Gaming Mode",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold
                                    ),
                                    color = TextPrimary
                                )
                                Text(
                                    text = if (isGamingModeEnabled) "Prioritizing gaming responsiveness & DND" else "Gaming optimizations deactivated",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = if (isGamingModeEnabled) StatusSuccess else TextSecondary
                                )
                            }
                        }

                        Switch(
                            checked = isGamingModeEnabled,
                            onCheckedChange = { viewModel.toggleGamingMode(it) },
                            modifier = Modifier.testTag("gaming_mode_toggle"),
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = CyberBlack,
                                checkedTrackColor = NeonCyan,
                                uncheckedThumbColor = TextSecondary,
                                uncheckedTrackColor = CyberDark
                            )
                        )
                    }
                }
            }

            // Real-Time Gaming Telemetry HUD
            item {
                GamingGlowCard(
                    modifier = Modifier.fillMaxWidth(),
                    borderColor = NeonPurple.copy(alpha = 0.4f),
                    backgroundColor = CyberSurface
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Text(
                            text = "PERFORMANCE HUD METRICS",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.2.sp
                            ),
                            color = NeonPurple
                        )

                        // 3 HUD tiles: Temp, Refresh Rate, Battery
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            HudMetricTile(
                                label = "Device Temp",
                                value = "${String.format("%.1f", deviceInfo.batteryTempC)}°C",
                                status = if (deviceInfo.batteryTempC > 40f) "WARM" else "COOL",
                                icon = Icons.Default.Thermostat,
                                color = if (deviceInfo.batteryTempC > 40f) StatusWarning else StatusSuccess,
                                modifier = Modifier.weight(1f)
                            )
                            HudMetricTile(
                                label = "Refresh Rate",
                                value = "${deviceInfo.refreshRateHz} Hz",
                                status = "SMOOTH",
                                icon = Icons.Default.Speed,
                                color = NeonCyan,
                                modifier = Modifier.weight(1f)
                            )
                            HudMetricTile(
                                label = "Battery Level",
                                value = "${deviceInfo.batteryLevel}%",
                                status = if (deviceInfo.isCharging) "CHARGING" else "DRAIN SAFE",
                                icon = Icons.Default.BatteryChargingFull,
                                color = NeonPurple,
                                modifier = Modifier.weight(1f)
                            )
                        }

                        // RAM usage bar
                        val ramUsedGb = String.format("%.1f", deviceInfo.ramUsedBytes / (1024.0 * 1024 * 1024))
                        val ramTotalGb = String.format("%.1f", deviceInfo.totalRamBytes / (1024.0 * 1024 * 1024))
                        CyberMetricBar(
                            title = "Memory Utilization",
                            valueText = "$ramUsedGb GB / $ramTotalGb GB (${deviceInfo.ramPercent}%)",
                            percent = deviceInfo.ramPercent,
                            barColor = NeonCyan
                        )
                    }
                }
            }

            // Quick System Setting Shortcuts & DND
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
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "SYSTEM CONTROLS & SHORTCUTS",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.2.sp
                            ),
                            color = TextSecondary
                        )

                        // DND Mode shortcut
                        ShortcutRow(
                            title = "Do Not Disturb (DND) Setup",
                            subtitle = "Silence incoming alerts and interruptions while gaming",
                            icon = Icons.Default.DoNotDisturbOn,
                            tag = "dnd_shortcut_button",
                            onClick = { viewModel.openDndSettings() }
                        )

                        // Display / Refresh rate shortcut
                        ShortcutRow(
                            title = "Display & Motion Smoothness",
                            subtitle = "Configure 90Hz / 120Hz display refresh rate in Android",
                            icon = Icons.Default.Speed,
                            tag = "display_shortcut_button",
                            onClick = { viewModel.openDisplaySettings() }
                        )

                        // Battery optimization shortcut
                        ShortcutRow(
                            title = "Battery & Performance Profile",
                            subtitle = "Adjust power profile for gaming sessions",
                            icon = Icons.Default.BatteryChargingFull,
                            tag = "battery_shortcut_button",
                            onClick = { viewModel.openBatteryOptimizationSettings() }
                        )

                        // Restore Defaults Button
                        Button(
                            onClick = {
                                viewModel.toggleGamingMode(false)
                                viewModel.resetSensitivityToDefault()
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(46.dp)
                                .testTag("restore_defaults_button"),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = CyberSurfaceElevated,
                                contentColor = NeonCyan
                            ),
                            shape = RoundedCornerShape(10.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, CyberBorder)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Restore,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "RESTORE DEFAULT SETTINGS",
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                    }
                }
            }

            // Game Launcher Hub Section
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "GAME LAUNCHER HUB",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.2.sp
                        ),
                        color = NeonCyan
                    )
                    Text(
                        text = "${games.size} Detected",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextMuted
                    )
                }
            }

            if (games.isEmpty()) {
                item {
                    GamingGlowCard(
                        modifier = Modifier.fillMaxWidth(),
                        borderColor = CyberBorder,
                        backgroundColor = CyberSurface
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.SportsEsports,
                                contentDescription = null,
                                tint = TextMuted,
                                modifier = Modifier.size(40.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "No third-party games detected automatically",
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                                color = TextPrimary
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "You can launch installed apps directly from Background Manager.",
                                style = MaterialTheme.typography.bodySmall,
                                color = TextSecondary
                            )
                            Spacer(modifier = Modifier.height(14.dp))
                            Button(
                                onClick = { viewModel.navigateTo(Screen.BACKGROUND_MANAGER) },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = NeonPurple,
                                    contentColor = TextPrimary
                                ),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text("Browse Installed Apps")
                            }
                        }
                    }
                }
            } else {
                items(games) { game ->
                    GameLauncherItemCard(
                        game = game,
                        onLaunch = { viewModel.launchGame(game.packageName) }
                    )
                }
            }

            // Transparency & Policy Disclaimer Card
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(CyberDark)
                        .border(1.dp, CyberBorder, RoundedCornerShape(12.dp))
                        .padding(14.dp)
                ) {
                    Column {
                        Text(
                            text = "ℹ️ Transparency Notice",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = NeonCyan
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "S4X Mobile Optimizer prepares your device environment (DND, memory trimming, and latency tests) prior to gaming. It launches games using standard Android application launchers and does not alter external game binaries.",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextMuted
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun HudMetricTile(
    label: String,
    value: String,
    status: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(CyberSurfaceElevated)
            .border(1.dp, CyberBorder, RoundedCornerShape(12.dp))
            .padding(10.dp)
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = icon, contentDescription = null, tint = color, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(label, style = MaterialTheme.typography.labelSmall, color = TextMuted, fontSize = 10.sp)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(value, style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold), color = TextPrimary)
            Text(status, style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold), color = color, fontSize = 9.sp)
        }
    }
}

@Composable
private fun ShortcutRow(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    tag: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .testTag(tag)
            .clip(RoundedCornerShape(10.dp))
            .background(CyberSurfaceElevated)
            .clickable { onClick() }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(CyberDark),
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = NeonCyan, modifier = Modifier.size(20.dp))
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold), color = TextPrimary)
            Text(subtitle, style = MaterialTheme.typography.bodySmall, color = TextSecondary)
        }
    }
}

@Composable
private fun GameLauncherItemCard(
    game: BackgroundAppItem,
    onLaunch: () -> Unit
) {
    GamingGlowCard(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("game_item_${game.packageName}"),
        borderColor = CyberBorder,
        backgroundColor = CyberSurface
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(CyberSurfaceElevated)
                        .border(1.dp, NeonPurple.copy(alpha = 0.5f), RoundedCornerShape(10.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.SportsEsports,
                        contentDescription = null,
                        tint = NeonPurple,
                        modifier = Modifier.size(22.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = game.appName,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                        color = TextPrimary
                    )
                    Text(
                        text = game.packageName,
                        style = MaterialTheme.typography.bodySmall,
                        color = TextMuted,
                        maxLines = 1
                    )
                }
            }

            Button(
                onClick = onLaunch,
                modifier = Modifier
                    .height(38.dp)
                    .testTag("launch_game_${game.packageName}"),
                colors = ButtonDefaults.buttonColors(
                    containerColor = NeonPurple,
                    contentColor = TextPrimary
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text("LAUNCH", fontWeight = FontWeight.Bold, fontSize = 12.sp)
            }
        }
    }
}
