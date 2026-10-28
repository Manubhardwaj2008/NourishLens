package com.example.nourishlens.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nourishlens.R
import com.example.nourishlens.ui.theme.*

@Composable
fun NourishLensTopHeader(
    currentTabTitle: String = "Scanner",
    onProfileClick: () -> Unit = {}
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = SurfaceWarmOat,
        shadowElevation = 2.dp
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 600.dp)
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Logo & App Name
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.app_logo),
                        contentDescription = "NourishLens Official Logo",
                        modifier = Modifier
                            .size(42.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Fit
                    )

                    Column {
                        Text(
                            text = "NourishLens",
                            style = MaterialTheme.typography.titleLarge,
                            color = ForestPrimary,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = (-0.5).sp
                        )
                        Text(
                            text = currentTabTitle.uppercase(),
                            style = MaterialTheme.typography.labelSmall,
                            color = CharcoalVariant,
                            fontSize = 10.sp,
                            letterSpacing = 1.2.sp
                        )
                    }
                }

                // Right Status Badge & Profile
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = SurfaceContainer,
                        modifier = Modifier.height(30.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(text = "🌱", fontSize = 12.sp)
                            Text(
                                text = "Legal Metrology Active",
                                style = MaterialTheme.typography.labelMedium,
                                color = ForestPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .border(1.5.dp, ForestPrimary, CircleShape)
                            .clickable { onProfileClick() },
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.profile_kartikey),
                            contentDescription = "Natwar Goyal Profile",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun NourishLensBottomNavigation(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = SurfaceWarmOat,
        tonalElevation = 6.dp,
        shadowElevation = 8.dp
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 600.dp)
                    .navigationBarsPadding()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                NavItem(
                    icon = if (selectedTab == 0) Icons.Default.CameraAlt else Icons.Outlined.CameraAlt,
                    label = "Scanner",
                    isSelected = selectedTab == 0,
                    onClick = { onTabSelected(0) }
                )
                NavItem(
                    icon = if (selectedTab == 1) Icons.Default.Verified else Icons.Outlined.Verified,
                    label = "Dossier",
                    isSelected = selectedTab == 1,
                    onClick = { onTabSelected(1) }
                )
                NavItem(
                    icon = if (selectedTab == 2) Icons.Default.Spa else Icons.Outlined.Spa,
                    label = "Swaps",
                    isSelected = selectedTab == 2,
                    onClick = { onTabSelected(2) }
                )
                NavItem(
                    icon = if (selectedTab == 3) Icons.Default.History else Icons.Outlined.History,
                    label = "Audit Log",
                    isSelected = selectedTab == 3,
                    onClick = { onTabSelected(3) }
                )
                NavItem(
                    icon = if (selectedTab == 4) Icons.Default.Person else Icons.Outlined.Person,
                    label = "Profile",
                    isSelected = selectedTab == 4,
                    onClick = { onTabSelected(4) }
                )
            }
        }
    }
}

@Composable
private fun NavItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(if (isSelected) ForestPrimary.copy(alpha = 0.12f) else Color.Transparent)
                .padding(horizontal = 12.dp, vertical = 4.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = if (isSelected) ForestPrimary else CharcoalVariant,
                modifier = Modifier.size(22.dp)
            )
        }
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = if (isSelected) ForestPrimary else CharcoalVariant,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            fontSize = 11.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}
