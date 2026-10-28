package com.example.nourishlens.ui.components

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nourishlens.model.ExtractedFields
import com.example.nourishlens.ocr.BarcodeDecoder
import com.example.nourishlens.ui.theme.*

@Composable
fun DecodedBarcodeCard(
    extractedFields: ExtractedFields,
    modifier: Modifier = Modifier
) {
    if (extractedFields.barcodeValue.isBlank()) return

    val decoded = BarcodeDecoder.decodeBarcodeData(
        extractedFields.barcodeValue,
        extractedFields.barcodeFormat
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .border(1.5.dp, ForestPrimary.copy(alpha = 0.4f), RoundedCornerShape(18.dp)),
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
                        imageVector = Icons.Default.QrCodeScanner,
                        contentDescription = null,
                        tint = ForestPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "DECODED BARCODE / QR DATA",
                        style = MaterialTheme.typography.labelSmall,
                        color = ForestPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp,
                        letterSpacing = 0.5.sp
                    )
                }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = HerbalMint
                ) {
                    Text(
                        text = if (decoded.isGs1Compliant) "GS1 VERIFIED ✓" else "SCANNED BARCODE",
                        style = MaterialTheme.typography.labelSmall,
                        color = ForestPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Raw Barcode Display Surface
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = SurfaceWarmOat,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Text(
                        text = "FORMAT: ${decoded.formatName.uppercase()}",
                        style = MaterialTheme.typography.labelSmall,
                        color = CharcoalVariant,
                        fontSize = 9.sp
                    )
                    Text(
                        text = decoded.rawValue,
                        style = MaterialTheme.typography.titleMedium,
                        color = ForestPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Decoded Data Grid
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                if (decoded.gtinCountryOfOrigin.isNotBlank()) {
                    BarcodeDataRow(
                        icon = Icons.Default.Public,
                        label = "GS1 Country of Origin",
                        value = decoded.gtinCountryOfOrigin
                    )
                }

                if (decoded.embeddedFssaiLic.isNotBlank()) {
                    BarcodeDataRow(
                        icon = Icons.Default.Verified,
                        label = "Embedded FSSAI License",
                        value = "Lic #${decoded.embeddedFssaiLic}"
                    )
                }

                if (decoded.embeddedBatch.isNotBlank()) {
                    BarcodeDataRow(
                        icon = Icons.Default.ConfirmationNumber,
                        label = "Encoded Batch / Lot ID",
                        value = decoded.embeddedBatch
                    )
                }

                if (decoded.embeddedMrp.isNotBlank()) {
                    BarcodeDataRow(
                        icon = Icons.Default.Sell,
                        label = "Encoded Retail Price",
                        value = decoded.embeddedMrp
                    )
                }

                if (decoded.embeddedExpiry.isNotBlank()) {
                    BarcodeDataRow(
                        icon = Icons.Default.Event,
                        label = "Encoded Expiration Date",
                        value = decoded.embeddedExpiry
                    )
                }
            }
        }
    }
}

@Composable
private fun BarcodeDataRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String
) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = SurfaceContainerLow,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = ForestPrimary,
                modifier = Modifier.size(16.dp)
            )
            Column {
                Text(
                    text = label.uppercase(),
                    style = MaterialTheme.typography.labelSmall,
                    color = CharcoalVariant,
                    fontSize = 9.sp
                )
                Text(
                    text = value,
                    style = MaterialTheme.typography.bodySmall,
                    color = CharcoalEspresso,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )
            }
        }
    }
}
