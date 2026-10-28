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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
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
import com.example.nourishlens.model.ProductScan
import com.example.nourishlens.ui.components.ComplianceRuleCard
import com.example.nourishlens.ui.components.ComplianceStatusPill
import com.example.nourishlens.ui.components.DecodedBarcodeCard
import com.example.nourishlens.ui.components.ExtractedFieldRow
import com.example.nourishlens.ui.components.NutritionalAndSafetyCard
import com.example.nourishlens.ui.components.ProductExpiryCard
import com.example.nourishlens.ui.components.ValidationTestScoreCard
import com.example.nourishlens.ui.theme.*

@Composable
fun ProductScorecardScreen(
    currentScan: ProductScan,
    onNavigateToSwaps: () -> Unit = {}
) {
    val compliance = currentScan.complianceResult
    val fields = currentScan.extractedFields

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceWarmOat)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        // Top Breadcrumb & FSSAI Emblem Row
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
                    imageVector = Icons.Default.Verified,
                    contentDescription = null,
                    tint = ForestPrimary,
                    modifier = Modifier.size(18.dp)
                )
                Text(
                    text = "FSSAI DOSSIER #${currentScan.id}",
                    style = MaterialTheme.typography.labelSmall,
                    color = ForestPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.sp,
                    letterSpacing = 0.5.sp
                )
            }

            Surface(
                shape = RoundedCornerShape(16.dp),
                color = SurfaceContainer,
                modifier = Modifier.border(1.dp, ForestPrimary.copy(alpha = 0.2f), RoundedCornerShape(16.dp))
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(ComplianceGreen)
                    )
                    Text(
                        text = fields.vegetarianStatus,
                        style = MaterialTheme.typography.labelSmall,
                        color = ForestPrimary,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Product Dossier Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp)),
            colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Product Image
                Box(
                    modifier = Modifier
                        .size(90.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(SurfaceContainerHighest),
                    contentAlignment = Alignment.Center
                ) {
                    currentScan.imageResId?.let { resId ->
                        Image(
                            painter = painterResource(id = resId),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Spa,
                            contentDescription = null,
                            tint = TerracottaSecondary,
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = currentScan.brandName.uppercase(),
                            style = MaterialTheme.typography.labelSmall,
                            color = TerracottaSecondary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp,
                            letterSpacing = 0.5.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = currentScan.productName,
                        style = MaterialTheme.typography.titleLarge,
                        color = CharcoalEspresso,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )

                    Text(
                        text = "Category: ${currentScan.category}",
                        style = MaterialTheme.typography.bodySmall,
                        color = CharcoalVariant,
                        fontSize = 12.sp
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    // Badges
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = SurfaceContainer
                        ) {
                            Text(
                                text = "Jaivik Bharat",
                                style = MaterialTheme.typography.labelSmall,
                                color = ForestPrimary,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = SurfaceContainer
                        ) {
                            Text(
                                text = "Agmark Grade A",
                                style = MaterialTheme.typography.labelSmall,
                                color = ForestPrimary,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Expiration Audit & Notification Alert Card
        ProductExpiryCard(
            productName = currentScan.productName,
            mfgDate = fields.mfgDate,
            expiryDate = fields.expiryDate
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Decoded Barcode & QR Code Data Card
        DecodedBarcodeCard(extractedFields = fields)

        Spacer(modifier = Modifier.height(14.dp))

        // Official FSSAI & Legal Metrology Validation Test Score Card
        ValidationTestScoreCard(labVerification = fields.labVerification)

        Spacer(modifier = Modifier.height(14.dp))

        // Compliance Banner Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .border(
                    1.5.dp,
                    if (compliance.isCompliant) ComplianceGreen.copy(alpha = 0.4f) else NonComplianceRed.copy(alpha = 0.4f),
                    RoundedCornerShape(16.dp)
                ),
            colors = CardDefaults.cardColors(
                containerColor = if (compliance.isCompliant) HerbalMint else NonComplianceRedBg
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = if (compliance.isCompliant) "LEGAL METROLOGY COMPLIANT" else "NON-COMPLIANT VIOLATIONS FLAG",
                            style = MaterialTheme.typography.labelSmall,
                            color = if (compliance.isCompliant) ForestPrimary else NonComplianceRedText,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp,
                            letterSpacing = 1.sp
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "${compliance.passedRulesCount} / ${compliance.totalRulesCount} Rules Passed",
                            style = MaterialTheme.typography.titleMedium,
                            color = CharcoalEspresso,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }

                    // Score Circle
                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .clip(CircleShape)
                            .background(
                                if (compliance.isCompliant) ForestPrimary else NonComplianceRed
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "${compliance.overallScore}%",
                                color = Color.White,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                            Text(
                                text = "SCORE",
                                color = Color.White.copy(alpha = 0.8f),
                                style = MaterialTheme.typography.labelSmall,
                                fontSize = 8.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = compliance.scanNotes,
                    style = MaterialTheme.typography.bodyMedium,
                    color = CharcoalEspresso,
                    fontSize = 13.sp
                )

                if (!compliance.isCompliant) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        onClick = onNavigateToSwaps,
                        colors = ButtonDefaults.buttonColors(containerColor = NonComplianceRed),
                        shape = RoundedCornerShape(20.dp),
                        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Gavel,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Generate Seller Remediation Notice",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Nutritional Facts, Additives & Statistical Safety Ratio Card
        NutritionalAndSafetyCard(extractedFields = fields)

        Spacer(modifier = Modifier.height(16.dp))

        // Mandatory Declarations Grid
        Text(
            text = "MANDATORY DECLARATIONS (RULE 6)",
            style = MaterialTheme.typography.labelSmall,
            color = ForestPrimary,
            fontWeight = FontWeight.Bold,
            fontSize = 11.sp,
            letterSpacing = 1.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ExtractedFieldRow(
                    label = "Commodity Name",
                    value = fields.commodityName,
                    isValid = fields.commodityName.isNotBlank()
                )

                ExtractedFieldRow(
                    label = "Manufacturer / Packer Address",
                    value = if (fields.manufacturerAddress.isNotBlank()) "${fields.manufacturerName} - ${fields.manufacturerAddress}" else fields.manufacturerName,
                    isValid = fields.manufacturerAddress.isNotBlank()
                )

                ExtractedFieldRow(
                    label = "Declared Net Quantity",
                    value = fields.netQuantity,
                    isValid = fields.netQuantity.isNotBlank() && !fields.netQuantity.contains("approx", ignoreCase = true) && !fields.netQuantity.contains("gm", ignoreCase = true)
                )

                ExtractedFieldRow(
                    label = "Maximum Retail Price (MRP)",
                    value = "${fields.mrp} ${if (fields.mrpTaxInclusive) "(Incl. of all taxes)" else "[TAX CLAUSE MISSING]"}",
                    isValid = fields.mrp.isNotBlank() && fields.mrpTaxInclusive
                )

                ExtractedFieldRow(
                    label = "Month & Year of Manufacture",
                    value = fields.mfgDate,
                    isValid = fields.mfgDate.isNotBlank()
                )

                ExtractedFieldRow(
                    label = "Country of Origin",
                    value = fields.countryOfOrigin,
                    isValid = fields.countryOfOrigin.isNotBlank()
                )

                ExtractedFieldRow(
                    label = "Consumer Care Contact",
                    value = fields.consumerCare,
                    isValid = fields.consumerCare.isNotBlank()
                )

                ExtractedFieldRow(
                    label = "FSSAI License Number",
                    value = fields.fssaiLicNo,
                    isValid = fields.fssaiLicNo.isNotBlank()
                )

                ExtractedFieldRow(
                    label = "Scanned Barcode / QR Code",
                    value = if (fields.barcodeValue.isNotBlank()) "${fields.barcodeValue} • ${fields.barcodeFormat}" else "No Barcode / QR Code Detected",
                    isValid = fields.barcodeValue.isNotBlank()
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Clause-by-Clause Legal Metrology Audit Checklist
        Text(
            text = "CLAUSE-BY-CLAUSE AUDIT CHECKLIST",
            style = MaterialTheme.typography.labelSmall,
            color = ForestPrimary,
            fontWeight = FontWeight.Bold,
            fontSize = 11.sp,
            letterSpacing = 1.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            compliance.rules.forEach { rule ->
                ComplianceRuleCard(rule = rule)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}
