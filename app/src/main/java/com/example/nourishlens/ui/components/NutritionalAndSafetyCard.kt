package com.example.nourishlens.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nourishlens.model.AdditiveInfo
import com.example.nourishlens.model.ExtractedFields
import com.example.nourishlens.ui.theme.*

@Composable
fun NutritionalAndSafetyCard(
    extractedFields: ExtractedFields,
    modifier: Modifier = Modifier
) {
    val nutrition = extractedFields.nutritionalFacts
    val analysis = extractedFields.ingredientAnalysis

    val animatedRatio by animateFloatAsState(
        targetValue = analysis.safetyScoreRatio / 100f,
        label = "SafetyRatioAnimation"
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // 1. Statistical Safety Ratio & Indian Food Law Compliance Gauge
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(18.dp))
                .border(
                    1.5.dp,
                    if (analysis.safetyScoreRatio >= 80) ComplianceGreen.copy(alpha = 0.5f)
                    else if (analysis.safetyScoreRatio >= 60) TerracottaAccent.copy(alpha = 0.5f)
                    else NonComplianceRed.copy(alpha = 0.5f),
                    RoundedCornerShape(18.dp)
                ),
            colors = CardDefaults.cardColors(containerColor = CardMilledLinen)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.BarChart,
                                contentDescription = null,
                                tint = ForestPrimary,
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = "STATISTICAL SAFETY RATIO (FSSAI 2020)",
                                style = MaterialTheme.typography.labelSmall,
                                color = ForestPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 10.sp,
                                letterSpacing = 0.5.sp
                            )
                        }

                        Text(
                            text = analysis.safetyRatioLabel,
                            style = MaterialTheme.typography.titleMedium,
                            color = CharcoalEspresso,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = if (analysis.safetyScoreRatio >= 80) HerbalMint else NonComplianceRedBg
                    ) {
                        Text(
                            text = "${analysis.safetyScoreRatio}% CLEAN",
                            style = MaterialTheme.typography.labelSmall,
                            color = if (analysis.safetyScoreRatio >= 80) ForestPrimary else NonComplianceRedText,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Multi-segment Statistical Data Line Bar
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Clean & Whole Ingredients (${analysis.safetyScoreRatio}%)",
                            style = MaterialTheme.typography.labelSmall,
                            color = ForestPrimary,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Chemicals & HFSS (${100 - analysis.safetyScoreRatio}%)",
                            style = MaterialTheme.typography.labelSmall,
                            color = if (analysis.safetyScoreRatio >= 80) CharcoalVariant else NonComplianceRedText,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    // Dual progress bar
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp)
                            .clip(RoundedCornerShape(5.dp))
                            .background(NonComplianceRed.copy(alpha = 0.3f))
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth(animatedRatio)
                                .clip(RoundedCornerShape(5.dp))
                                .background(if (analysis.safetyScoreRatio >= 80) ComplianceGreen else TerracottaAccent)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = SurfaceContainerLow,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.HealthAndSafety,
                            contentDescription = null,
                            tint = ForestPrimary,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "FSSAI Classification: ${analysis.safetyTier}",
                            style = MaterialTheme.typography.bodySmall,
                            color = CharcoalEspresso,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp
                        )
                    }
                }

                // FSSAI HFSS Warning Alerts if present
                if (analysis.fssaiHfssAlerts.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        analysis.fssaiHfssAlerts.forEach { alert ->
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = NonComplianceRedBg,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier.padding(8.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Warning,
                                        contentDescription = null,
                                        tint = NonComplianceRedText,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Text(
                                        text = alert,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = NonComplianceRedText,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 11.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // 2. Core Nutritional Details Table Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp)),
            colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
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
                            imageVector = Icons.Default.Restaurant,
                            contentDescription = null,
                            tint = ForestPrimary,
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = "NUTRITIONAL VALUES (${nutrition.servingSize})",
                            style = MaterialTheme.typography.labelSmall,
                            color = ForestPrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp,
                            letterSpacing = 0.5.sp
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = SurfaceContainer
                    ) {
                        Text(
                            text = "FSSAI Mandatory Panel",
                            style = MaterialTheme.typography.labelSmall,
                            color = CharcoalVariant,
                            fontSize = 9.sp,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Macro Nutrients Grid
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    MacroTile(
                        label = "Energy",
                        value = nutrition.calories,
                        icon = Icons.Default.LocalFireDepartment,
                        color = TerracottaSecondary,
                        modifier = Modifier.weight(1f)
                    )
                    MacroTile(
                        label = "Protein",
                        value = nutrition.protein,
                        icon = Icons.Default.FitnessCenter,
                        color = ForestPrimary,
                        modifier = Modifier.weight(1f)
                    )
                    MacroTile(
                        label = "Carbs",
                        value = nutrition.carbohydrates,
                        icon = Icons.Default.Grain,
                        color = ForestPrimary,
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    MacroTile(
                        label = "Sugar / Added",
                        value = "${nutrition.sugar} / ${nutrition.addedSugar}",
                        icon = Icons.Default.Cake,
                        color = if (nutrition.addedSugar != "0.0 g") NonComplianceRedText else ForestPrimary,
                        modifier = Modifier.weight(1f)
                    )
                    MacroTile(
                        label = "Fat / Sat Fat",
                        value = "${nutrition.totalFat} (${nutrition.saturatedFat})",
                        icon = Icons.Default.WaterDrop,
                        color = ForestPrimary,
                        modifier = Modifier.weight(1f)
                    )
                    MacroTile(
                        label = "Sodium",
                        value = nutrition.sodium,
                        icon = Icons.Default.Shield,
                        color = if ((nutrition.sodium.replace("mg", "").trim().toIntOrNull() ?: 0) > 500) NonComplianceRedText else ForestPrimary,
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Cholesterol & Micronutrients
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(SurfaceWarmOat, RoundedCornerShape(8.dp))
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "CHOLESTEROL & TRANS FAT",
                            style = MaterialTheme.typography.labelSmall,
                            color = CharcoalVariant,
                            fontSize = 9.sp
                        )
                        Text(
                            text = "Cholesterol: ${nutrition.cholesterol} | Trans Fat: ${nutrition.transFat}",
                            style = MaterialTheme.typography.bodySmall,
                            color = CharcoalEspresso,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp
                        )
                    }

                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = ComplianceGreen,
                        modifier = Modifier.size(16.dp)
                    )
                }

                if (nutrition.vitaminsAndMinerals.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Vitamins & Micronutrients:",
                        style = MaterialTheme.typography.labelSmall,
                        color = ForestPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        nutrition.vitaminsAndMinerals.forEach { vit ->
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = HerbalMint
                            ) {
                                Text(
                                    text = vit,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = ForestPrimary,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // 3. Official FSSAI & National Food Testing Laboratory Comparison Card
        FssaiLabVerificationCard(labVerification = extractedFields.labVerification)

        // 3. Chemical & Additive Safety Breakdown
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp)),
            colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Science,
                        contentDescription = null,
                        tint = ForestPrimary,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = "CHEMICAL & ADDITIVE SAFETY ANALYSIS",
                        style = MaterialTheme.typography.labelSmall,
                        color = ForestPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp,
                        letterSpacing = 0.5.sp
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Harmful / Cautionary Chemical Additives
                if (analysis.harmfulOrCautionaryAdditives.isNotEmpty()) {
                    Text(
                        text = "CHEMICALS & HARMFUL ADDITIVES DETECTED:",
                        style = MaterialTheme.typography.labelSmall,
                        color = NonComplianceRedText,
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    analysis.harmfulOrCautionaryAdditives.forEach { additive ->
                        AdditiveTile(additive = additive)
                        Spacer(modifier = Modifier.height(6.dp))
                    }
                } else {
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = HerbalMint,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Verified,
                                contentDescription = null,
                                tint = ComplianceGreen,
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = "Zero harmful chemicals, synthetic preservatives (E211), or artificial sweeteners detected.",
                                style = MaterialTheme.typography.bodySmall,
                                color = ForestPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Beneficial Ingredients
                if (analysis.beneficialIngredients.isNotEmpty()) {
                    Text(
                        text = "BENEFICIAL & WHOLE FOOD INGREDIENTS:",
                        style = MaterialTheme.typography.labelSmall,
                        color = ForestPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    analysis.beneficialIngredients.forEach { beneficial ->
                        AdditiveTile(additive = beneficial)
                        Spacer(modifier = Modifier.height(6.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun MacroTile(
    label: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = SurfaceWarmOat,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.labelMedium,
                color = color,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = CharcoalVariant,
                fontSize = 9.sp
            )
        }
    }
}

@Composable
private fun AdditiveTile(additive: AdditiveInfo) {
    val bgColor = if (additive.isHarmful) NonComplianceRedBg else HerbalMint
    val textColor = if (additive.isHarmful) NonComplianceRedText else ForestPrimary
    val icon = if (additive.isHarmful) Icons.Default.Warning else Icons.Default.Spa

    Surface(
        shape = RoundedCornerShape(10.dp),
        color = bgColor,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
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
                        imageVector = icon,
                        contentDescription = null,
                        tint = textColor,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "${additive.name} (${additive.code})",
                        style = MaterialTheme.typography.titleMedium,
                        color = textColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color.White.copy(alpha = 0.7f)
                ) {
                    Text(
                        text = additive.category,
                        style = MaterialTheme.typography.labelSmall,
                        color = textColor,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = additive.description,
                style = MaterialTheme.typography.bodySmall,
                color = CharcoalEspresso,
                fontSize = 11.sp
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = "FSSAI Regulation: ${additive.fssaiStatus}",
                style = MaterialTheme.typography.labelSmall,
                color = textColor,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
