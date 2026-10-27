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
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.CenterFocusStrong
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Restore
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
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
import com.example.model.GamingProfile
import com.example.ui.components.GamingGlowCard
import com.example.ui.components.S4XTopBar
import com.example.ui.theme.CyberBlack
import com.example.ui.theme.CyberBorder
import com.example.ui.theme.CyberDark
import com.example.ui.theme.CyberSurface
import com.example.ui.theme.CyberSurfaceElevated
import com.example.ui.theme.ElectricViolet
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonPurple
import com.example.ui.theme.StatusSuccess
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.viewmodel.OptimizerViewModel
import com.example.viewmodel.Screen

@Composable
fun SensitivityScreen(
    viewModel: OptimizerViewModel,
    modifier: Modifier = Modifier
) {
    val profile by viewModel.gamingProfile.collectAsState()

    Scaffold(
        containerColor = CyberBlack,
        topBar = {
            S4XTopBar(
                title = "GAMING PREFERENCES",
                subtitle = "SENSITIVITY & ERGONOMIC PROFILES",
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
            // Profile Presets Card
            item {
                GamingGlowCard(
                    modifier = Modifier.fillMaxWidth(),
                    borderColor = NeonCyan.copy(alpha = 0.4f),
                    backgroundColor = CyberSurface
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "PRESET PROFILES",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.2.sp
                            ),
                            color = NeonCyan
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            PresetChip(
                                label = "Balanced",
                                isSelected = profile.id == "balanced",
                                onClick = { viewModel.applyPreset("BALANCED") },
                                modifier = Modifier.weight(1f)
                            )
                            PresetChip(
                                label = "Responsive",
                                isSelected = profile.id == "responsive",
                                onClick = { viewModel.applyPreset("RESPONSIVE") },
                                modifier = Modifier.weight(1f)
                            )
                            PresetChip(
                                label = "Custom",
                                isSelected = profile.id == "custom",
                                onClick = { viewModel.applyPreset("CUSTOM") },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }

            // Target Focus Selector: HEAD, BODY, DEFAULT
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
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.CenterFocusStrong,
                                contentDescription = null,
                                tint = NeonPurple,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "CONTROL FOCUS PROFILE",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 1.2.sp
                                ),
                                color = NeonPurple
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            FocusChip(
                                label = "DEFAULT",
                                desc = "Neutral Ergonomics",
                                isSelected = profile.targetFocus == "DEFAULT",
                                onClick = { viewModel.updateTargetFocus("DEFAULT") },
                                modifier = Modifier.weight(1f)
                            )
                            FocusChip(
                                label = "HEAD",
                                desc = "High Precision Aim",
                                isSelected = profile.targetFocus == "HEAD",
                                onClick = { viewModel.updateTargetFocus("HEAD") },
                                modifier = Modifier.weight(1f)
                            )
                            FocusChip(
                                label = "BODY",
                                desc = "Consistent Tracking",
                                isSelected = profile.targetFocus == "BODY",
                                onClick = { viewModel.updateTargetFocus("BODY") },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }

            // Custom Sensitivity Sliders (0 - 200)
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
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = "CUSTOM SENSITIVITY CALIBRATION (0 - 200)",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.2.sp
                            ),
                            color = TextSecondary
                        )

                        SensitivitySliderRow(
                            name = "General Camera Sensitivity",
                            value = profile.generalSens,
                            onValueChange = { viewModel.updateGeneralSens(it) }
                        )

                        SensitivitySliderRow(
                            name = "Red Dot / Holographic",
                            value = profile.redDotSens,
                            onValueChange = { viewModel.updateRedDotSens(it) }
                        )

                        SensitivitySliderRow(
                            name = "2X Scope Sensitivity",
                            value = profile.scope2xSens,
                            onValueChange = { viewModel.updateScope2xSens(it) }
                        )

                        SensitivitySliderRow(
                            name = "4X Scope Sensitivity",
                            value = profile.scope4xSens,
                            onValueChange = { viewModel.updateScope4xSens(it) }
                        )

                        SensitivitySliderRow(
                            name = "Sniper Scope Precision",
                            value = profile.sniperSens,
                            onValueChange = { viewModel.updateSniperSens(it) }
                        )

                        SensitivitySliderRow(
                            name = "Free Look / 360 View",
                            value = profile.freeLookSens,
                            onValueChange = { viewModel.updateFreeLookSens(it) }
                        )

                        SensitivitySliderRow(
                            name = "Gyroscope Sensitivity",
                            value = profile.gyroSens,
                            onValueChange = { viewModel.updateGyroSens(it) }
                        )
                    }
                }
            }

            // Action Buttons: Save & Reset
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = { viewModel.resetSensitivityToDefault() },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .testTag("reset_sens_button"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = CyberSurfaceElevated,
                            contentColor = TextPrimary
                        ),
                        shape = RoundedCornerShape(10.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, CyberBorder)
                    ) {
                        Icon(imageVector = Icons.Default.Restore, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("RESET DEFAULT", fontWeight = FontWeight.Bold)
                    }

                    Button(
                        onClick = { viewModel.applyPreset("CUSTOM") },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .testTag("save_profile_button"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = NeonCyan,
                            contentColor = CyberBlack
                        ),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Save, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("SAVE PROFILE", fontWeight = FontWeight.Black)
                    }
                }
            }

            // Compliance & Education Note
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
                            text = "ℹ️ Aim Calibration Guidelines",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = NeonPurple
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Sensitivity presets store your preferred touch response benchmarks for consistent muscle memory across different games. These values are saved within S4X and do not modify external game files or inject aiming scripts.",
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
private fun PresetChip(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(if (isSelected) NeonCyan.copy(alpha = 0.2f) else CyberDark)
            .border(
                1.dp,
                if (isSelected) NeonCyan else CyberBorder,
                RoundedCornerShape(10.dp)
            )
            .clickable { onClick() }
            .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
            color = if (isSelected) NeonCyan else TextPrimary
        )
    }
}

@Composable
private fun FocusChip(
    label: String,
    desc: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(if (isSelected) NeonPurple.copy(alpha = 0.2f) else CyberDark)
            .border(
                1.dp,
                if (isSelected) NeonPurple else CyberBorder,
                RoundedCornerShape(10.dp)
            )
            .clickable { onClick() }
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Black),
                color = if (isSelected) NeonPurple else TextPrimary
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = desc,
                style = MaterialTheme.typography.labelSmall,
                color = TextMuted,
                fontSize = 9.sp
            )
        }
    }
}

@Composable
private fun SensitivitySliderRow(
    name: String,
    value: Int,
    onValueChange: (Int) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                color = TextPrimary
            )
            Text(
                text = "$value / 200",
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                color = NeonCyan
            )
        }
        Slider(
            value = value.toFloat(),
            onValueChange = { onValueChange(it.toInt()) },
            valueRange = 0f..200f,
            colors = SliderDefaults.colors(
                thumbColor = NeonCyan,
                activeTrackColor = NeonCyan,
                inactiveTrackColor = CyberDark
            )
        )
    }
}
