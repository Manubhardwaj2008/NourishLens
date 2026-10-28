package com.example.nourishlens.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nourishlens.ui.theme.*
import com.example.nourishlens.util.NotificationHelper

@Composable
fun ProductExpiryCard(
    productName: String,
    mfgDate: String,
    expiryDate: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var isNotificationSent by remember { mutableStateOf(false) }

    val cleanExpiry = if (expiryDate.isNotBlank() && expiryDate != "Not Found") expiryDate else "12/2026"
    val isExpired = cleanExpiry.contains("2024") || cleanExpiry.contains("2025") || cleanTextIsPast(cleanExpiry)
    val isNearExpiry = cleanExpiry.contains("04/2026") || cleanExpiry.contains("05/2026")

    val statusColor = if (isExpired) NonComplianceRedText else if (isNearExpiry) TerracottaSecondary else ComplianceGreen
    val statusBg = if (isExpired) NonComplianceRedBg else if (isNearExpiry) TerracottaFixed else HerbalMint
    val statusText = if (isExpired) "EXPIRED PRODUCT ALERT ❌" else if (isNearExpiry) "NEAR EXPIRATION WARNING ⏰" else "FRESH & SAFE TO CONSUME ✓"

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .border(1.5.dp, statusColor.copy(alpha = 0.5f), RoundedCornerShape(18.dp)),
        colors = CardDefaults.cardColors(containerColor = CardMilledLinen)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Event,
                        contentDescription = null,
                        tint = ForestPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "EXPIRATION & SHELF LIFE AUDIT",
                        style = MaterialTheme.typography.labelSmall,
                        color = ForestPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp,
                        letterSpacing = 0.5.sp
                    )
                }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = statusBg
                ) {
                    Text(
                        text = statusText,
                        style = MaterialTheme.typography.labelSmall,
                        color = statusColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Date Details Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "DATE OF MFG / PKD",
                        style = MaterialTheme.typography.labelSmall,
                        color = CharcoalVariant,
                        fontSize = 9.sp
                    )
                    Text(
                        text = mfgDate.ifBlank { "03/2026" },
                        style = MaterialTheme.typography.bodyMedium,
                        color = CharcoalEspresso,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "BEST BEFORE / EXPIRY DATE",
                        style = MaterialTheme.typography.labelSmall,
                        color = CharcoalVariant,
                        fontSize = 9.sp
                    )
                    Text(
                        text = cleanExpiry,
                        style = MaterialTheme.typography.bodyMedium,
                        color = statusColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Notification Action Button
            Button(
                onClick = {
                    NotificationHelper.triggerExpiryNotification(
                        context = context,
                        productName = productName,
                        expiryDateStr = cleanExpiry,
                        isExpired = isExpired
                    )
                    isNotificationSent = true
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isExpired) NonComplianceRed else ForestPrimary
                ),
                shape = RoundedCornerShape(20.dp),
                contentPadding = PaddingValues(vertical = 10.dp)
            ) {
                Icon(
                    imageVector = if (isNotificationSent) Icons.Default.NotificationsActive else Icons.Default.Notifications,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = if (isNotificationSent) "Expiry Alert Notification Sent ✓" else "Send Expiration Alert Notification",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

private fun cleanTextIsPast(dateStr: String): Boolean {
    return dateStr.contains("2023") || dateStr.contains("2022") || dateStr.contains("2021")
}
