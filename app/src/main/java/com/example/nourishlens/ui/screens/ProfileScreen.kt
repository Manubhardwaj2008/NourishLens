package com.example.nourishlens.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nourishlens.R
import com.example.nourishlens.ui.theme.*

@Composable
fun ProfileScreen(
    onResetData: () -> Unit = {},
    onSignOut: () -> Unit = {}
) {
    var showResetSnackbar by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceWarmOat)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        // Officer Profile Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(18.dp))
                .border(1.dp, ForestPrimary.copy(alpha = 0.2f), RoundedCornerShape(18.dp)),
            colors = CardDefaults.cardColors(containerColor = CardMilledLinen)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Large Avatar Circle
                Box(
                    modifier = Modifier
                        .size(96.dp)
                        .clip(CircleShape)
                        .border(2.5.dp, ForestPrimary, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.profile_kartikey),
                        contentDescription = "Officer Kartikey Singh Profile Photo",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Kartikey Singh",
                    style = MaterialTheme.typography.titleLarge,
                    color = CharcoalEspresso,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )

                Text(
                    text = "Senior Legal Metrology Officer",
                    style = MaterialTheme.typography.bodyMedium,
                    color = ForestPrimary,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(6.dp))

                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = SurfaceContainer
                ) {
                    Text(
                        text = "BADGE #LM-DEL-8842 • REGION 04 (NEW DELHI)",
                        style = MaterialTheme.typography.labelSmall,
                        color = CharcoalVariant,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Officer Enforcement Metrics
        Text(
            text = "ENFORCEMENT METRICS",
            style = MaterialTheme.typography.labelSmall,
            color = ForestPrimary,
            fontWeight = FontWeight.Bold,
            fontSize = 11.sp,
            letterSpacing = 1.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Card(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(14.dp)),
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow)
            ) {
                Column(
                    modifier = Modifier.padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "1,482",
                        style = MaterialTheme.typography.titleLarge,
                        color = ForestPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                    Text(
                        text = "Total Inspections",
                        style = MaterialTheme.typography.labelSmall,
                        color = CharcoalVariant,
                        fontSize = 10.sp
                    )
                }
            }

            Card(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(14.dp)),
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow)
            ) {
                Column(
                    modifier = Modifier.padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "184",
                        style = MaterialTheme.typography.titleLarge,
                        color = TerracottaSecondary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                    Text(
                        text = "Violations Flagged",
                        style = MaterialTheme.typography.labelSmall,
                        color = CharcoalVariant,
                        fontSize = 10.sp
                    )
                }
            }

            Card(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(14.dp)),
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow)
            ) {
                Column(
                    modifier = Modifier.padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "42",
                        style = MaterialTheme.typography.titleLarge,
                        color = NonComplianceRedText,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                    Text(
                        text = "Seizure Notices",
                        style = MaterialTheme.typography.labelSmall,
                        color = CharcoalVariant,
                        fontSize = 10.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // System Configuration
        Text(
            text = "SYSTEM CONFIGURATION & RULE ENGINE",
            style = MaterialTheme.typography.labelSmall,
            color = ForestPrimary,
            fontWeight = FontWeight.Bold,
            fontSize = 11.sp,
            letterSpacing = 1.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow)
        ) {
            Column(
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Legal Metrology Rule Engine",
                            style = MaterialTheme.typography.titleMedium,
                            color = CharcoalEspresso,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                        Text(
                            text = "Rule 6(1) Packaged Commodities Rules 2011",
                            style = MaterialTheme.typography.bodySmall,
                            color = CharcoalVariant,
                            fontSize = 11.sp
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = HerbalMint
                    ) {
                        Text(
                            text = "v2.4 ACTIVE",
                            style = MaterialTheme.typography.labelSmall,
                            color = ForestPrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                        )
                    }
                }

                HorizontalDivider(color = OutlineHairline.copy(alpha = 0.3f))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "ML Kit Optical Engine",
                            style = MaterialTheme.typography.titleMedium,
                            color = CharcoalEspresso,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                        Text(
                            text = "On-Device Neural Text Recognizer (Offline)",
                            style = MaterialTheme.typography.bodySmall,
                            color = CharcoalVariant,
                            fontSize = 11.sp
                        )
                    }

                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = ComplianceGreen,
                        modifier = Modifier.size(20.dp)
                    )
                }

                HorizontalDivider(color = OutlineHairline.copy(alpha = 0.3f))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "FSSAI Packaging Standards",
                            style = MaterialTheme.typography.titleMedium,
                            color = CharcoalEspresso,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                        Text(
                            text = "Food Safety & Standards Regulations 2020",
                            style = MaterialTheme.typography.bodySmall,
                            color = CharcoalVariant,
                            fontSize = 11.sp
                        )
                    }

                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = ComplianceGreen,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                onResetData()
                showResetSnackbar = true
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = TerracottaSecondary),
            shape = RoundedCornerShape(24.dp),
            contentPadding = PaddingValues(vertical = 12.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "Reset Audit History & Reload Benchmark Samples",
                style = MaterialTheme.typography.labelMedium,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedButton(
            onClick = onSignOut,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, NonComplianceRed),
            contentPadding = PaddingValues(vertical = 12.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Logout,
                contentDescription = null,
                tint = NonComplianceRedText,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "Sign Out / Switch Officer Account",
                style = MaterialTheme.typography.labelMedium,
                color = NonComplianceRedText,
                fontWeight = FontWeight.Bold
            )
        }

        if (showResetSnackbar) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "✓ Audit history reset to legal benchmark state.",
                style = MaterialTheme.typography.bodySmall,
                color = ForestPrimary,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}
