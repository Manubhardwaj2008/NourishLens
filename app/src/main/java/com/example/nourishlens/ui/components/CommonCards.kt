package com.example.nourishlens.ui.components

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nourishlens.model.ComplianceRule
import com.example.nourishlens.model.SeverityLevel
import com.example.nourishlens.ui.theme.*

@Composable
fun ComplianceStatusPill(
    isCompliant: Boolean,
    score: Int,
    modifier: Modifier = Modifier
) {
    val bgColor = if (isCompliant) HerbalMint else NonComplianceRedBg
    val textColor = if (isCompliant) ForestPrimary else NonComplianceRedText
    val icon = if (isCompliant) Icons.Default.CheckCircle else Icons.Default.Error

    Surface(
        shape = RoundedCornerShape(20.dp),
        color = bgColor,
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = textColor,
                modifier = Modifier.size(16.dp)
            )
            Text(
                text = if (isCompliant) "COMPLIANT ($score%)" else "NON-COMPLIANT ($score%)",
                style = MaterialTheme.typography.labelMedium,
                color = textColor,
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp
            )
        }
    }
}

@Composable
fun ComplianceRuleCard(
    rule: ComplianceRule,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(!rule.isPassed) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .border(
                1.dp,
                if (rule.isPassed) OutlineHairline.copy(alpha = 0.5f) else NonComplianceRed.copy(alpha = 0.4f),
                RoundedCornerShape(12.dp)
            ),
        colors = CardDefaults.cardColors(
            containerColor = if (rule.isPassed) CardMilledLinen else SurfaceWarmOat
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .padding(14.dp)
        ) {
            // Header Row
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
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(
                                if (rule.isPassed) ComplianceGreenBg else NonComplianceRedBg
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (rule.isPassed) Icons.Default.Check else Icons.Default.Close,
                            contentDescription = null,
                            tint = if (rule.isPassed) ComplianceGreen else NonComplianceRedText,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text(
                                text = rule.ruleCode,
                                style = MaterialTheme.typography.labelSmall,
                                color = if (rule.isPassed) ForestPrimary else NonComplianceRedText,
                                fontWeight = FontWeight.Bold
                            )
                            if (rule.severity == SeverityLevel.HIGH && !rule.isPassed) {
                                Surface(
                                    shape = RoundedCornerShape(4.dp),
                                    color = NonComplianceRed
                                ) {
                                    Text(
                                        text = "CRITICAL",
                                        color = Color.White,
                                        style = MaterialTheme.typography.labelSmall,
                                        fontSize = 8.sp,
                                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                    )
                                }
                            }
                        }
                        Text(
                            text = rule.ruleTitle,
                            style = MaterialTheme.typography.titleMedium,
                            color = CharcoalEspresso,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                }

                Icon(
                    imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null,
                    tint = CharcoalVariant,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Value preview line
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Declared Value: ",
                    style = MaterialTheme.typography.bodySmall,
                    color = CharcoalVariant,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = rule.foundValue,
                    style = MaterialTheme.typography.bodySmall,
                    color = if (rule.isPassed) ForestPrimary else NonComplianceRedText,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // Expanded details
            AnimatedVisibility(visible = expanded) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                ) {
                    HorizontalDivider(color = OutlineHairline.copy(alpha = 0.3f))
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Legal Reference:",
                        style = MaterialTheme.typography.labelSmall,
                        color = ForestPrimary,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = rule.legalReference,
                        style = MaterialTheme.typography.bodySmall,
                        color = CharcoalEspresso,
                        fontSize = 12.sp
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Requirement:",
                        style = MaterialTheme.typography.labelSmall,
                        color = CharcoalVariant,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = rule.requirementSummary,
                        style = MaterialTheme.typography.bodySmall,
                        color = CharcoalEspresso,
                        fontSize = 12.sp
                    )

                    if (!rule.isPassed) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = TerracottaFixed.copy(alpha = 0.5f),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Warning,
                                        contentDescription = null,
                                        tint = TerracottaSecondary,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Text(
                                        text = "Remediation Guidance:",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = OnTerracottaFixed,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = rule.remediationGuidance,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = OnTerracottaFixed,
                                    fontSize = 11.sp
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Penalty: ${rule.penaltySection}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = NonComplianceRedText,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ExtractedFieldRow(
    label: String,
    value: String,
    isValid: Boolean,
    onValueChange: (String) -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(SurfaceContainerLow, RoundedCornerShape(8.dp))
            .padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label.uppercase(),
                style = MaterialTheme.typography.labelSmall,
                color = CharcoalVariant,
                fontSize = 10.sp,
                letterSpacing = 0.5.sp
            )
            Text(
                text = if (value.isNotBlank()) value else "MISSING / NOT FOUND",
                style = MaterialTheme.typography.bodyMedium,
                color = if (isValid) CharcoalEspresso else NonComplianceRedText,
                fontWeight = if (isValid) FontWeight.Medium else FontWeight.Bold,
                fontSize = 13.sp
            )
        }

        Icon(
            imageVector = if (isValid) Icons.Default.CheckCircle else Icons.Default.Cancel,
            contentDescription = null,
            tint = if (isValid) ComplianceGreen else NonComplianceRedText,
            modifier = Modifier.size(18.dp)
        )
    }
}
