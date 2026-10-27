package com.example.ui.screens

import android.content.Intent
import android.net.Uri
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Policy
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
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
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.viewmodel.OptimizerViewModel
import com.example.viewmodel.Screen

@Composable
fun AboutScreen(
    viewModel: OptimizerViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var showPrivacyDialog by remember { mutableStateOf(false) }
    var showTermsDialog by remember { mutableStateOf(false) }
    var showLicensesDialog by remember { mutableStateOf(false) }

    val contactPhone = "+94728588068"

    Scaffold(
        containerColor = CyberBlack,
        topBar = {
            S4XTopBar(
                title = "ABOUT S4X",
                subtitle = "DEVELOPER & SYSTEM METADATA",
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
            // S4X Emblem & App Info Card
            item {
                GamingGlowCard(
                    modifier = Modifier.fillMaxWidth(),
                    borderColor = NeonCyan.copy(alpha = 0.4f),
                    backgroundColor = CyberSurface
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.s4x_logo),
                            contentDescription = "S4X Logo",
                            modifier = Modifier
                                .size(88.dp)
                                .clip(RoundedCornerShape(18.dp))
                                .border(1.5.dp, NeonCyan, RoundedCornerShape(18.dp))
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text = "S4X MOBILE OPTIMIZER",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Black,
                                letterSpacing = 1.sp
                            ),
                            color = TextPrimary
                        )

                        Text(
                            text = "Version 1.0.0 (Production Release)",
                            style = MaterialTheme.typography.bodySmall,
                            color = NeonCyan
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "Next-generation Android device responsiveness, hardware monitoring, and latency diagnostics platform.",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSecondary,
                            modifier = Modifier.padding(horizontal = 12.dp)
                        )
                    }
                }
            }

            // Developer Details & Contact Action Card
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
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "DEVELOPER INFORMATION",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.2.sp
                            ),
                            color = NeonPurple
                        )

                        DeveloperDetailRow("Developer Name", "S4X")
                        DeveloperDetailRow("Contact Phone", contactPhone)
                        DeveloperDetailRow("Application Name", "S4X MOBILE OPTIMIZER")
                        DeveloperDetailRow("Build Version", "1.0.0")

                        Spacer(modifier = Modifier.height(4.dp))

                        // Contact Developer Button (Opens dialer)
                        Button(
                            onClick = {
                                try {
                                    val dialIntent = Intent(Intent.ACTION_DIAL).apply {
                                        data = Uri.parse("tel:$contactPhone")
                                    }
                                    context.startActivity(dialIntent)
                                } catch (_: Exception) {}
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .testTag("contact_developer_button"),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = NeonCyan,
                                contentColor = CyberBlack
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Call, contentDescription = null, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "CONTACT DEVELOPER ($contactPhone)",
                                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Black)
                            )
                        }

                        // Send SMS button
                        Button(
                            onClick = {
                                try {
                                    val smsIntent = Intent(Intent.ACTION_SENDTO).apply {
                                        data = Uri.parse("smsto:$contactPhone")
                                    }
                                    context.startActivity(smsIntent)
                                } catch (_: Exception) {}
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(46.dp)
                                .testTag("sms_developer_button"),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = CyberSurfaceElevated,
                                contentColor = NeonPurple
                            ),
                            shape = RoundedCornerShape(12.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, CyberBorder)
                        ) {
                            Icon(imageVector = Icons.Default.Message, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "SEND MESSAGE / SMS",
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                    }
                }
            }

            // Legal & Open Source Policy Links
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
                            text = "LEGAL & COMPLIANCE",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.2.sp
                            ),
                            color = TextSecondary
                        )

                        LegalButtonRow(
                            title = "Privacy Policy",
                            desc = "Zero-telemetry collection, local device privacy",
                            icon = Icons.Default.Policy,
                            tag = "privacy_policy_button",
                            onClick = { showPrivacyDialog = true }
                        )

                        LegalButtonRow(
                            title = "Terms & Conditions",
                            desc = "Guidelines on hardware monitoring & gaming mode",
                            icon = Icons.Default.Gavel,
                            tag = "terms_conditions_button",
                            onClick = { showTermsDialog = true }
                        )

                        LegalButtonRow(
                            title = "Open-Source Licenses",
                            desc = "Jetpack Compose, AndroidX, and Kotlin libraries",
                            icon = Icons.Default.Code,
                            tag = "open_source_licenses_button",
                            onClick = { showLicensesDialog = true }
                        )
                    }
                }
            }

            // Session Logout Action
            item {
                Button(
                    onClick = { viewModel.logout() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("about_logout_button"),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CyberDark,
                        contentColor = StatusError
                    ),
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, StatusError.copy(alpha = 0.5f))
                ) {
                    Icon(imageVector = Icons.Default.Logout, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("LOGOUT OF S4X SESSION", fontWeight = FontWeight.Bold)
                }
            }
        }
    }

    // Privacy Policy Dialog
    if (showPrivacyDialog) {
        PolicyDialog(
            title = "Privacy Policy",
            content = """
                S4X MOBILE OPTIMIZER respects user privacy:
                
                1. Local Storage: All sensitivity profiles and session preferences are stored strictly on-device in private Android app storage.
                
                2. No Personal Data Harvesting: The app does not transmit device identifiers, contacts, locations, or personal files to third-party servers.
                
                3. Network Testing: Network latency measurements are conducted using standard DNS queries to public anycast resolvers (such as Cloudflare 1.1.1.1 and Google 8.8.8.8) strictly for ping reporting.
                
                4. App Management: The Background Manager uses standard Android PackageManager APIs and redirects user actions to native Android system settings without accessing app contents.
            """.trimIndent(),
            onDismiss = { showPrivacyDialog = false }
        )
    }

    // Terms & Conditions Dialog
    if (showTermsDialog) {
        PolicyDialog(
            title = "Terms & Conditions",
            content = """
                Terms of Use for S4X MOBILE OPTIMIZER:
                
                1. System Telemetry: Memory, battery, and CPU telemetry reflect Android-supported system metrics.
                
                2. Realistic Optimization: S4X does not claim to bypass Android OS security, alter third-party game code, or violate game fair-play policies.
                
                3. Network Latency: Latency is governed by your ISP, routing infrastructure, and physical distance to servers. S4X provides diagnostics and does not artificially manipulate external network physics.
                
                4. Compliance: S4X complies with Google Play policy guidelines and Android background execution limits.
            """.trimIndent(),
            onDismiss = { showTermsDialog = false }
        )
    }

    // Open Source Licenses Dialog
    if (showLicensesDialog) {
        PolicyDialog(
            title = "Open-Source Licenses",
            content = """
                This software incorporates the following open-source components:
                
                • Android Jetpack Compose (Apache License 2.0)
                • AndroidX Core KTX, Lifecycle, DataStore (Apache License 2.0)
                • Kotlin Coroutines & Standard Library (Apache License 2.0)
                • Material Design 3 Components (Apache License 2.0)
                • Coil Image Loading Library (Apache License 2.0)
                
                Copyright (c) 2026 S4X. All rights reserved.
            """.trimIndent(),
            onDismiss = { showLicensesDialog = false }
        )
    }
}

@Composable
private fun DeveloperDetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, style = MaterialTheme.typography.bodySmall, color = TextSecondary)
        Text(text = value, style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold), color = TextPrimary)
    }
}

@Composable
private fun LegalButtonRow(
    title: String,
    desc: String,
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
            Icon(imageVector = icon, contentDescription = null, tint = NeonCyan, modifier = Modifier.size(18.dp))
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold), color = TextPrimary)
            Text(desc, style = MaterialTheme.typography.bodySmall, color = TextMuted)
        }
    }
}

@Composable
private fun PolicyDialog(
    title: String,
    content: String,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = CyberSurface,
        title = {
            Text(text = title, fontWeight = FontWeight.Bold, color = TextPrimary)
        },
        text = {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(vertical = 4.dp)
            ) {
                Text(
                    text = content,
                    color = TextSecondary,
                    style = MaterialTheme.typography.bodySmall,
                    lineHeight = 20.sp
                )
            }
        },
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(
                    containerColor = NeonCyan,
                    contentColor = CyberBlack
                )
            ) {
                Text("CLOSE", fontWeight = FontWeight.Bold)
            }
        }
    )
}
