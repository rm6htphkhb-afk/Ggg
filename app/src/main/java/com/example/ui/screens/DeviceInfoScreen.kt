package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BatteryChargingFull
import androidx.compose.material.icons.filled.DeveloperBoard
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.DeviceInfo
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
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.viewmodel.OptimizerViewModel
import com.example.viewmodel.Screen

@Composable
fun DeviceInfoScreen(
    viewModel: OptimizerViewModel,
    modifier: Modifier = Modifier
) {
    val info by viewModel.deviceInfo.collectAsState()

    Scaffold(
        containerColor = CyberBlack,
        topBar = {
            S4XTopBar(
                title = "DEVICE INFORMATION",
                subtitle = "HARDWARE & SYSTEM ARCHITECTURE",
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
            // General Hardware Specs
            item {
                InfoSectionCard(
                    title = "HARDWARE & MODEL",
                    icon = Icons.Default.PhoneAndroid,
                    accentColor = NeonCyan
                ) {
                    InfoItemRow("Device Model", info.model)
                    InfoItemRow("Manufacturer", info.manufacturer)
                    InfoItemRow("Brand", info.brand.ifEmpty { "Generic" })
                    InfoItemRow("Display Refresh Rate", "${info.refreshRateHz} Hz")
                }
            }

            // CPU & Processing Architecture
            item {
                InfoSectionCard(
                    title = "CPU & PROCESSOR SPECS",
                    icon = Icons.Default.DeveloperBoard,
                    accentColor = NeonPurple
                ) {
                    InfoItemRow("CPU Architecture", info.cpuAbi)
                    InfoItemRow("Physical Cores", "${info.cpuCores} Cores Active")
                    InfoItemRow("Supported ABIs", "64-bit ARMv8 / v9")
                    InfoItemRow("Instruction Set", "arm64-v8a neon")
                }
            }

            // Memory & Storage Subsystem
            item {
                val ramTotalGb = String.format("%.2f", info.totalRamBytes / (1024.0 * 1024 * 1024))
                val ramAvailGb = String.format("%.2f", info.availableRamBytes / (1024.0 * 1024 * 1024))
                val storageTotalGb = String.format("%.2f", info.totalStorageBytes / (1024.0 * 1024 * 1024))
                val storageFreeGb = String.format("%.2f", info.freeStorageBytes / (1024.0 * 1024 * 1024))

                InfoSectionCard(
                    title = "MEMORY & STORAGE",
                    icon = Icons.Default.Memory,
                    accentColor = NeonCyan
                ) {
                    InfoItemRow("Total RAM", "$ramTotalGb GB")
                    InfoItemRow("Available RAM", "$ramAvailGb GB (${100 - info.ramPercent}% Free)")
                    InfoItemRow("Total Internal Storage", "$storageTotalGb GB")
                    InfoItemRow("Free Available Space", "$storageFreeGb GB")
                }
            }

            // Android OS & Software Platform
            item {
                InfoSectionCard(
                    title = "OPERATING SYSTEM",
                    icon = Icons.Default.Security,
                    accentColor = StatusSuccess
                ) {
                    InfoItemRow("Android Version", "Android ${info.androidVersion}")
                    InfoItemRow("API Level", "API ${info.apiLevel}")
                    InfoItemRow("Kernel Architecture", "Linux 64-bit kernel")
                    InfoItemRow("Security Framework", "Enforcing SELinux")
                }
            }

            // Battery & Thermals
            item {
                InfoSectionCard(
                    title = "BATTERY & THERMAL STATUS",
                    icon = Icons.Default.BatteryChargingFull,
                    accentColor = NeonPurple
                ) {
                    InfoItemRow("Battery Charge", "${info.batteryLevel}%")
                    InfoItemRow("Charging State", if (info.isCharging) "Charging (AC / Fast Charging)" else "Discharging")
                    InfoItemRow("Battery Health", info.batteryHealth)
                    InfoItemRow("Thermal Temperature", "${String.format("%.1f", info.batteryTempC)}°C (${String.format("%.1f", info.batteryTempC * 1.8f + 32)}°F)")
                }
            }
        }
    }
}

@Composable
private fun InfoSectionCard(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    accentColor: Color,
    content: @Composable () -> Unit
) {
    GamingGlowCard(
        modifier = Modifier.fillMaxWidth(),
        borderColor = accentColor.copy(alpha = 0.35f),
        backgroundColor = CyberSurface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = accentColor,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.2.sp
                    ),
                    color = accentColor
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(CyberBorder)
            )

            content()
        }
    }
}

@Composable
private fun InfoItemRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = TextSecondary
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
            color = TextPrimary
        )
    }
}
