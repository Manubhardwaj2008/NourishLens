package com.example.nourishlens.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nourishlens.model.FssaiLabVerification
import com.example.nourishlens.ui.theme.*

@Composable
fun FssaiLabVerificationCard(
    labVerification: FssaiLabVerification,
    modifier: Modifier = Modifier
) {
    val isPass = labVerification.isVerifiedMatchesLab

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .border(
                1.5.dp,
                if (isPass) ComplianceGreen.copy(alpha = 0.5f) else NonComplianceRed.copy(alpha = 0.5f),
                RoundedCornerShape(18.dp)
            ),
        colors = CardDefaults.cardColors(containerColor = CardMilledLinen)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header Title & Lab Status Badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (isPass) HerbalMint else NonComplianceRedBg),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (isPass) Icons.Default.Verified else Icons.Default.Warning,
                            contentDescription = null,
                            tint = if (isPass) ComplianceGreen else NonComplianceRedText,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Column {
                        Text(
                            text = "FSSAI & NABL LAB VERIFICATION",
                            style = MaterialTheme.typography.labelSmall,
                            color = ForestPrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp,
                            letterSpacing = 0.5.sp
                        )
                        Text(
                            text = if (isPass) "Lab Report Match ✓" else "Lab Discrepancy Alert ⚠",
                            style = MaterialTheme.typography.titleMedium,
                            color = if (isPass) ForestPrimary else NonComplianceRedText,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = if (isPass) HerbalMint else NonComplianceRedBg
                ) {
                    Text(
                        text = if (isPass) "100% VERIFIED" else "MISBRANDED",
                        style = MaterialTheme.typography.labelSmall,
                        color = if (isPass) ForestPrimary else NonComplianceRedText,
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Report Metadata Banner
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = SurfaceContainerLow,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Text(
                        text = "Testing Lab: ${labVerification.labName}",
                        style = MaterialTheme.typography.labelSmall,
                        color = CharcoalEspresso,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Report #: ${labVerification.reportNo} | Date: ${labVerification.testingDate}",
                        style = MaterialTheme.typography.bodySmall,
                        color = CharcoalVariant,
                        fontSize = 10.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "FSSAI Variance Status: ${labVerification.averageVariance}",
                        style = MaterialTheme.typography.labelSmall,
                        color = if (isPass) ComplianceGreen else NonComplianceRedText,
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // FSSAI Official Nutrition & Lab Comparison Table
            Text(
                text = "OFFICIAL FSSAI NUTRITION TABLE vs CERTIFIED LAB REPORT:",
                style = MaterialTheme.typography.labelSmall,
                color = ForestPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp,
                letterSpacing = 0.5.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Table Header Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(ForestPrimary, RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                    .padding(horizontal = 8.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Nutrient",
                    color = Color.White,
                    style = MaterialTheme.typography.labelSmall,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1.3f)
                )
                Text(
                    text = "Scanned Label",
                    color = Color.White,
                    style = MaterialTheme.typography.labelSmall,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1.1f)
                )
                Text(
                    text = "FSSAI RDA %",
                    color = Color.White,
                    style = MaterialTheme.typography.labelSmall,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1.0f)
                )
                Text(
                    text = "NFL Lab Test",
                    color = Color.White,
                    style = MaterialTheme.typography.labelSmall,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1.1f)
                )
            }

            // Table Data Rows
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        1.dp,
                        OutlineHairline.copy(alpha = 0.5f),
                        RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)
                    )
            ) {
                labVerification.labNutrientGrid.forEachIndexed { index, row ->
                    val rowBg = if (index % 2 == 0) SurfaceWarmOat else SurfaceContainerLow
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(rowBg)
                            .padding(horizontal = 8.dp, vertical = 6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = row.nutrientName,
                            style = MaterialTheme.typography.bodySmall,
                            color = CharcoalEspresso,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.weight(1.3f)
                        )

                        Text(
                            text = row.labelValue,
                            style = MaterialTheme.typography.bodySmall,
                            color = if (row.isWithinFssaiTolerance) CharcoalEspresso else NonComplianceRedText,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp,
                            modifier = Modifier.weight(1.1f)
                        )

                        Text(
                            text = row.fssaiRdaPercentage,
                            style = MaterialTheme.typography.bodySmall,
                            color = ForestPrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp,
                            modifier = Modifier.weight(1.0f)
                        )

                        Row(
                            modifier = Modifier.weight(1.1f),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(3.dp)
                        ) {
                            Text(
                                text = row.labTestedValue,
                                style = MaterialTheme.typography.bodySmall,
                                color = if (row.isWithinFssaiTolerance) ComplianceGreen else NonComplianceRedText,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp
                            )
                            Icon(
                                imageVector = if (row.isWithinFssaiTolerance) Icons.Default.CheckCircle else Icons.Default.Cancel,
                                contentDescription = null,
                                tint = if (row.isWithinFssaiTolerance) ComplianceGreen else NonComplianceRedText,
                                modifier = Modifier.size(12.dp)
                            )
                        }
                    }

                    if (index < labVerification.labNutrientGrid.size - 1) {
                        HorizontalDivider(color = OutlineHairline.copy(alpha = 0.3f))
                    }
                }
            }
        }
    }
}
