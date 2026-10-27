package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.icons.filled.CellTower
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Dns
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Router
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SignalCellularAlt
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.PingResult
import com.example.ui.components.GamingGlowCard
import com.example.ui.components.S4XTopBar
import com.example.ui.theme.CyberBlack
import com.example.ui.theme.CyberBorder
import com.example.ui.theme.CyberDark
import com.example.ui.theme.CyberSurface
import com.example.ui.theme.CyberSurfaceElevated
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonPurple
import com.example.ui.theme.StatusError
import com.example.ui.theme.StatusSuccess
import com.example.ui.theme.StatusWarning
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.viewmodel.OptimizerViewModel
import com.example.viewmodel.Screen

@Composable
fun NetworkScreen(
    viewModel: OptimizerViewModel,
    modifier: Modifier = Modifier
) {
    val deviceInfo by viewModel.deviceInfo.collectAsState()
    val pingResult by viewModel.pingResult.collectAsState()
    val isTesting by viewModel.isTestingPing.collectAsState()
    val selectedServer by viewModel.selectedPingServer.collectAsState()

    val servers = listOf(
        Pair("1.1.1.1", "Cloudflare DNS (Global Anycast)"),
        Pair("8.8.8.8", "Google Public DNS"),
        Pair("208.67.222.222", "OpenDNS (Cisco)")
    )

    Scaffold(
        containerColor = CyberBlack,
        topBar = {
            S4XTopBar(
                title = "NETWORK OPTIMIZER",
                subtitle = "REAL-TIME LATENCY DIAGNOSTICS",
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
            // Connection Status Banner Card
            item {
                GamingGlowCard(
                    modifier = Modifier.fillMaxWidth(),
                    borderColor = NeonCyan.copy(alpha = 0.4f),
                    backgroundColor = CyberSurface
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(CyberSurfaceElevated)
                                .border(1.dp, NeonCyan, RoundedCornerShape(12.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = if (deviceInfo.networkType.contains("Wi-Fi")) Icons.Default.Wifi else Icons.Default.SignalCellularAlt,
                                contentDescription = null,
                                tint = NeonCyan,
                                modifier = Modifier.size(28.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = deviceInfo.networkType,
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = TextPrimary
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Active Data Link • Validated Internet Gateway",
                                style = MaterialTheme.typography.bodySmall,
                                color = StatusSuccess
                            )
                        }
                    }
                }
            }

            // Test Server Selector Card
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
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = "SELECT LATENCY TARGET SERVER",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.2.sp
                            ),
                            color = TextSecondary
                        )

                        servers.forEach { (host, name) ->
                            val isSelected = selectedServer == host
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(if (isSelected) CyberSurfaceElevated else CyberDark)
                                    .border(
                                        1.dp,
                                        if (isSelected) NeonCyan else CyberBorder,
                                        RoundedCornerShape(10.dp)
                                    )
                                    .clickable { viewModel.setSelectedPingServer(host) }
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Dns,
                                    contentDescription = null,
                                    tint = if (isSelected) NeonCyan else TextMuted,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = name,
                                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                        color = TextPrimary
                                    )
                                    Text(
                                        text = "IP: $host",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = TextMuted
                                    )
                                }
                                if (isSelected) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = "Selected",
                                        tint = NeonCyan,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Button(
                            onClick = { viewModel.runPingTest() },
                            enabled = !isTesting,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .testTag("run_ping_test_button"),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = NeonCyan,
                                contentColor = CyberBlack
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            if (isTesting) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(20.dp),
                                    color = CyberBlack,
                                    strokeWidth = 2.dp
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text("TRANSMITTING PACKETS...", fontWeight = FontWeight.Black)
                            } else {
                                Icon(imageVector = Icons.Default.Speed, contentDescription = null)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("START REAL PING TEST", fontWeight = FontWeight.Black, letterSpacing = 1.sp)
                            }
                        }
                    }
                }
            }

            // Real Ping Telemetry Results Card
            item {
                GamingGlowCard(
                    modifier = Modifier.fillMaxWidth(),
                    borderColor = when (pingResult.statusRating) {
                        "EXCELLENT" -> StatusSuccess
                        "GOOD" -> NeonCyan
                        "FAIR" -> StatusWarning
                        else -> StatusError
                    }.copy(alpha = 0.5f),
                    backgroundColor = CyberSurface
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "REAL LATENCY MEASUREMENTS",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 1.2.sp
                                ),
                                color = NeonCyan
                            )
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(
                                        when (pingResult.statusRating) {
                                            "EXCELLENT" -> StatusSuccess.copy(alpha = 0.2f)
                                            "GOOD" -> NeonCyan.copy(alpha = 0.2f)
                                            "FAIR" -> StatusWarning.copy(alpha = 0.2f)
                                            else -> StatusError.copy(alpha = 0.2f)
                                        }
                                    )
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = pingResult.statusRating,
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Black),
                                    color = when (pingResult.statusRating) {
                                        "EXCELLENT" -> StatusSuccess
                                        "GOOD" -> NeonCyan
                                        "FAIR" -> StatusWarning
                                        else -> StatusError
                                    }
                                )
                            }
                        }

                        // 4-tile grid: Avg Ping, Min Ping, Max Ping, Packet Loss
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            MetricBox(
                                label = "Avg Latency",
                                value = if (pingResult.avgRttMs != null) "${pingResult.avgRttMs} ms" else "N/A",
                                color = NeonCyan,
                                modifier = Modifier.weight(1f)
                            )
                            MetricBox(
                                label = "Min Ping",
                                value = if (pingResult.minRttMs != null) "${pingResult.minRttMs} ms" else "N/A",
                                color = StatusSuccess,
                                modifier = Modifier.weight(1f)
                            )
                            MetricBox(
                                label = "Max Ping",
                                value = if (pingResult.maxRttMs != null) "${pingResult.maxRttMs} ms" else "N/A",
                                color = StatusWarning,
                                modifier = Modifier.weight(1f)
                            )
                            MetricBox(
                                label = "Packet Loss",
                                value = "${pingResult.packetLossPercent}%",
                                color = if (pingResult.packetLossPercent == 0) StatusSuccess else StatusError,
                                modifier = Modifier.weight(1f)
                            )
                        }

                        // Status explanation message
                        Text(
                            text = pingResult.message,
                            style = MaterialTheme.typography.bodySmall,
                            color = TextPrimary
                        )
                    }
                }
            }

            // Troubleshooting Suggestions & System Settings Shortcut
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
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = "LATENCY TROUBLESHOOTING GUIDE",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.2.sp
                            ),
                            color = TextSecondary
                        )

                        TroubleshootTip("Switch to 5GHz Band", "2.4GHz Wi-Fi is crowded with bluetooth and appliance interference. 5GHz yields cleaner gaming packets.")
                        TroubleshootTip("Check Background Sync", "Pause cloud backups, automated app updates, and video streaming on local devices while in competitive matches.")
                        TroubleshootTip("Router DNS Caching", "Restarting home gateways clears stale DNS entries and resolves routing congestion.")

                        Spacer(modifier = Modifier.height(4.dp))

                        Button(
                            onClick = { viewModel.openNetworkSettings() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(46.dp)
                                .testTag("open_network_settings_button"),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = CyberSurfaceElevated,
                                contentColor = NeonCyan
                            ),
                            shape = RoundedCornerShape(10.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, CyberBorder)
                        ) {
                            Icon(imageVector = Icons.Default.Settings, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("OPEN ANDROID NETWORK SETTINGS", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // Mandatory Technical Transparency Disclaimer
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
                            text = "⚠️ Network Integrity Notice",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = StatusWarning
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Ping depends strictly on your Internet Service Provider (ISP), physical distance to the host game server, peering infrastructure, and network congestion. S4X measures real socket round-trip time without fabricated statistics, and does not falsely claim to magically reduce physical ping beyond your connection's physical capacity.",
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
private fun MetricBox(
    label: String,
    value: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(CyberSurfaceElevated)
            .border(1.dp, CyberBorder, RoundedCornerShape(10.dp))
            .padding(8.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
            Text(label, style = MaterialTheme.typography.labelSmall, color = TextMuted, fontSize = 9.sp)
            Spacer(modifier = Modifier.height(2.dp))
            Text(value, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Black), color = color)
        }
    }
}

@Composable
private fun TroubleshootTip(title: String, desc: String) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top) {
        Box(
            modifier = Modifier
                .padding(top = 5.dp)
                .size(6.dp)
                .clip(CircleShape)
                .background(NeonPurple)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            Text(title, style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold), color = TextPrimary)
            Text(desc, style = MaterialTheme.typography.bodySmall, color = TextSecondary)
        }
    }
}
