package com.example.nourishlens.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
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
import com.example.nourishlens.data.SampleData
import com.example.nourishlens.model.ProductScan
import com.example.nourishlens.ui.components.ComplianceStatusPill
import com.example.nourishlens.ui.theme.*

@Composable
fun PantryHistoryScreen(
    scansList: List<ProductScan>,
    onSelectScan: (ProductScan) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilterTab by remember { mutableIntStateOf(0) } // 0: All, 1: Compliant, 2: Non-Compliant
    var showExportDialog by remember { mutableStateOf(false) }

    val filteredScans = scansList.filter { scan ->
        val matchesQuery = scan.productName.contains(searchQuery, ignoreCase = true) || scan.brandName.contains(searchQuery, ignoreCase = true) || scan.category.contains(searchQuery, ignoreCase = true)
        val matchesFilter = when (selectedFilterTab) {
            1 -> scan.complianceResult.isCompliant
            2 -> !scan.complianceResult.isCompliant
            else -> true
        }
        matchesQuery && matchesFilter
    }

    val totalScans = scansList.size
    val compliantCount = scansList.count { it.complianceResult.isCompliant }
    val nonCompliantCount = scansList.count { !it.complianceResult.isCompliant }
    val passRate = if (totalScans > 0) ((compliantCount.toDouble() / totalScans) * 100).toInt() else 0

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceWarmOat)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        // Search & Export Bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search product, brand or FSSAI…", fontSize = 13.sp) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = CharcoalVariant) },
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                shape = RoundedCornerShape(24.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = SurfaceContainerLow,
                    unfocusedContainerColor = SurfaceContainerLow,
                    focusedBorderColor = ForestPrimary,
                    unfocusedBorderColor = OutlineHairline
                ),
                singleLine = true
            )

            IconButton(
                onClick = { showExportDialog = true },
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(ForestPrimary)
            ) {
                Icon(
                    imageVector = Icons.Default.IosShare,
                    contentDescription = "Export Report",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Audit Summary Stats Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp)),
            colors = CardDefaults.cardColors(containerColor = CardMilledLinen)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "$totalScans",
                        style = MaterialTheme.typography.titleLarge,
                        color = ForestPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    Text(
                        text = "Total Audited",
                        style = MaterialTheme.typography.labelSmall,
                        color = CharcoalVariant,
                        fontSize = 10.sp
                    )
                }

                VerticalDivider(modifier = Modifier.height(30.dp), color = OutlineHairline.copy(alpha = 0.5f))

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "$passRate%",
                        style = MaterialTheme.typography.titleLarge,
                        color = ComplianceGreen,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    Text(
                        text = "Legal Pass Rate",
                        style = MaterialTheme.typography.labelSmall,
                        color = CharcoalVariant,
                        fontSize = 10.sp
                    )
                }

                VerticalDivider(modifier = Modifier.height(30.dp), color = OutlineHairline.copy(alpha = 0.5f))

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "$nonCompliantCount",
                        style = MaterialTheme.typography.titleLarge,
                        color = NonComplianceRedText,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    Text(
                        text = "Violations Flagged",
                        style = MaterialTheme.typography.labelSmall,
                        color = CharcoalVariant,
                        fontSize = 10.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Filter Chips
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                selected = selectedFilterTab == 0,
                onClick = { selectedFilterTab = 0 },
                label = { Text("All ($totalScans)") },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = ForestPrimary,
                    selectedLabelColor = Color.White
                ),
                shape = RoundedCornerShape(16.dp)
            )

            FilterChip(
                selected = selectedFilterTab == 1,
                onClick = { selectedFilterTab = 1 },
                label = { Text("Compliant ($compliantCount)") },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = ForestPrimary,
                    selectedLabelColor = Color.White
                ),
                shape = RoundedCornerShape(16.dp)
            )

            FilterChip(
                selected = selectedFilterTab == 2,
                onClick = { selectedFilterTab = 2 },
                label = { Text("Violations ($nonCompliantCount)") },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = NonComplianceRed,
                    selectedLabelColor = Color.White
                ),
                shape = RoundedCornerShape(16.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // History Scans List
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            filteredScans.forEach { scan ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .clickable { onSelectScan(scan) }
                        .border(1.dp, OutlineHairline.copy(alpha = 0.4f), RoundedCornerShape(14.dp)),
                    colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(SurfaceContainerHighest),
                            contentAlignment = Alignment.Center
                        ) {
                            scan.imageResId?.let { res ->
                                Image(
                                    painter = painterResource(id = res),
                                    contentDescription = null,
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = scan.productName,
                                style = MaterialTheme.typography.titleMedium,
                                color = CharcoalEspresso,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                maxLines = 1
                            )
                            Text(
                                text = "${scan.brandName} • ${scan.extractedFields.netQuantity}",
                                style = MaterialTheme.typography.bodySmall,
                                color = CharcoalVariant,
                                fontSize = 12.sp
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "FSSAI #${scan.extractedFields.fssaiLicNo}",
                                style = MaterialTheme.typography.labelSmall,
                                color = ForestPrimary,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        ComplianceStatusPill(
                            isCompliant = scan.complianceResult.isCompliant,
                            score = scan.complianceResult.overallScore
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }

    if (showExportDialog) {
        AlertDialog(
            onDismissRequest = { showExportDialog = false },
            title = {
                Text(
                    text = "Export Official Audit Report",
                    style = MaterialTheme.typography.headlineSmall,
                    color = ForestPrimary
                )
            },
            text = {
                Column {
                    Text(
                        text = "Official Legal Metrology Audit Summary for regional enforcement authority:",
                        style = MaterialTheme.typography.bodyMedium,
                        color = CharcoalEspresso
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = CardMilledLinen,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = """
                                Station: New Delhi Station #04
                                Inspector: Kartikey Singh (Senior Officer)
                                Total Commodities Audited: $totalScans
                                Compliant Items: $compliantCount
                                Seizure Notices: $nonCompliantCount
                                Legal Metrology Act 2009 Status: ACTIVE
                            """.trimIndent(),
                            style = MaterialTheme.typography.bodySmall,
                            color = CharcoalEspresso,
                            fontSize = 11.sp,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = { showExportDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = ForestPrimary)
                ) {
                    Text("Export Report PDF/JSON")
                }
            },
            dismissButton = {
                TextButton(onClick = { showExportDialog = false }) {
                    Text("Close", color = CharcoalVariant)
                }
            }
        )
    }
}
