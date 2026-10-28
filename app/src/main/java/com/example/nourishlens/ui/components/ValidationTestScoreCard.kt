package com.example.nourishlens.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nourishlens.model.FssaiLabVerification
import com.example.nourishlens.ui.theme.*

@Composable
fun ValidationTestScoreCard(
    labVerification: FssaiLabVerification,
    modifier: Modifier = Modifier
) {
    val score = labVerification.validationTestScore
    val isPass = score >= 80

    val mainColor = if (score >= 80) ComplianceGreen else if (score >= 60) TerracottaAccent else NonComplianceRedText
    val containerBg = if (score >= 80) HerbalMint else if (score >= 60) TerracottaFixed.copy(alpha = 0.5f) else NonComplianceRedBg

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .border(1.5.dp, mainColor.copy(alpha = 0.5f), RoundedCornerShape(18.dp)),
        colors = CardDefaults.cardColors(containerColor = CardMilledLinen)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header Title
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
                        imageVector = Icons.Default.VerifiedUser,
                        contentDescription = null,
                        tint = ForestPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "FSSAI VALIDATION TEST SCORE",
                        style = MaterialTheme.typography.labelSmall,
                        color = ForestPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp,
                        letterSpacing = 0.5.sp
                    )
                }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = containerBg
                ) {
                    Text(
                        text = labVerification.validationGrade,
                        style = MaterialTheme.typography.labelSmall,
                        color = mainColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Central Score Circle & Certification Stamp
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Score Circle
                Box(
                    modifier = Modifier
                        .size(76.dp)
                        .clip(CircleShape)
                        .background(mainColor),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "$score",
                            color = Color.White,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            fontSize = 26.sp
                        )
                        Text(
                            text = "/ 100 SCORE",
                            color = Color.White.copy(alpha = 0.85f),
                            style = MaterialTheme.typography.labelSmall,
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = if (isPass) "VALIDATED ACCURATE & SAFE" else "MISBRANDED / HIGH RISK ITEM",
                        style = MaterialTheme.typography.titleMedium,
                        color = mainColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = labVerification.overallVerificationStatus,
                        style = MaterialTheme.typography.bodySmall,
                        color = CharcoalEspresso,
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Official Stamp: ${labVerification.validationStamp}",
                        style = MaterialTheme.typography.labelSmall,
                        color = CharcoalVariant,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))
            HorizontalDivider(color = OutlineHairline.copy(alpha = 0.3f))
            Spacer(modifier = Modifier.height(12.dp))

            // 4 Sub-Scores Matrix Grid
            Text(
                text = "VALIDATION SUB-SCORE BREAKDOWN:",
                style = MaterialTheme.typography.labelSmall,
                color = ForestPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp,
                letterSpacing = 0.5.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SubScoreTile(
                    label = "FSSAI Lab Match",
                    score = "${labVerification.labMatchScore}%",
                    isPass = labVerification.labMatchScore >= 80,
                    modifier = Modifier.weight(1f)
                )
                SubScoreTile(
                    label = "Legal Metrology",
                    score = "${labVerification.ruleMatchScore}%",
                    isPass = labVerification.ruleMatchScore >= 80,
                    modifier = Modifier.weight(1f)
                )
                SubScoreTile(
                    label = "Chemical Purity",
                    score = "${labVerification.purityScore}%",
                    isPass = labVerification.purityScore >= 80,
                    modifier = Modifier.weight(1f)
                )
                SubScoreTile(
                    label = "OCR Readability",
                    score = "${labVerification.ocrAccuracyScore}%",
                    isPass = labVerification.ocrAccuracyScore >= 80,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun SubScoreTile(
    label: String,
    score: String,
    isPass: Boolean,
    modifier: Modifier = Modifier
) {
    val tileBg = if (isPass) HerbalMint else NonComplianceRedBg
    val textColor = if (isPass) ForestPrimary else NonComplianceRedText

    Surface(
        shape = RoundedCornerShape(10.dp),
        color = tileBg,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = score,
                style = MaterialTheme.typography.titleMedium,
                color = textColor,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = CharcoalVariant,
                fontSize = 8.sp,
                textAlign = TextAlign.Center,
                maxLines = 1
            )
        }
    }
}
