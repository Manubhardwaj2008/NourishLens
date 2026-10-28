package com.example.nourishlens.ui.screens

import android.graphics.BitmapFactory
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nourishlens.data.SampleData
import com.example.nourishlens.model.ProductScan
import com.example.nourishlens.ocr.MlKitOcrEngine
import com.example.nourishlens.ui.components.CameraPreview
import com.example.nourishlens.ui.components.ComplianceStatusPill
import com.example.nourishlens.ui.theme.*

@Composable
fun LiveScannerScreen(
    currentScan: ProductScan,
    onScanSelected: (ProductScan) -> Unit,
    onNavigateToDossier: () -> Unit
) {
    val context = LocalContext.current
    var isTorchOn by remember { mutableStateOf(false) }
    var isBatchMode by remember { mutableStateOf(false) }
    var isProcessing by remember { mutableStateOf(false) }
    var selectedSampleIndex by remember { mutableIntStateOf(0) }

    val sampleScans = remember { SampleData.createInitialScans() }

    // Launcher for picking image from device storage files
    val fileStorageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri ->
        uri?.let {
            try {
                isProcessing = true
                val inputStream = context.contentResolver.openInputStream(it)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                if (bitmap != null) {
                    MlKitOcrEngine.scanBitmapImage(
                        bitmap = bitmap,
                        imageUriStr = it.toString(),
                        onSuccess = { newScan ->
                            isProcessing = false
                            onScanSelected(newScan)
                            onNavigateToDossier()
                        },
                        onError = {
                            isProcessing = false
                        }
                    )
                } else {
                    isProcessing = false
                }
            } catch (e: Exception) {
                isProcessing = false
            }
        }
    }

    // Launcher for picking image from gallery
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            try {
                isProcessing = true
                val inputStream = context.contentResolver.openInputStream(it)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                if (bitmap != null) {
                    MlKitOcrEngine.scanBitmapImage(
                        bitmap = bitmap,
                        imageUriStr = it.toString(),
                        onSuccess = { newScan ->
                            isProcessing = false
                            onScanSelected(newScan)
                            onNavigateToDossier()
                        },
                        onError = {
                            isProcessing = false
                        }
                    )
                } else {
                    isProcessing = false
                }
            } catch (e: Exception) {
                isProcessing = false
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceWarmOat)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        // Scanner Viewfinder Canvas Card with Real CameraX Hardware Access
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.88f)
                .clip(RoundedCornerShape(20.dp))
                .border(2.dp, ForestPrimary, RoundedCornerShape(20.dp)),
            colors = CardDefaults.cardColors(containerColor = ForestPrimary)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(ForestPrimary)
            ) {
                // Real Live Camera Feed Viewfinder
                CameraPreview(
                    isTorchOn = isTorchOn,
                    onImageProxyCaptured = { imageProxy ->
                        isProcessing = true
                        MlKitOcrEngine.scanImageProxy(
                            imageProxy = imageProxy,
                            onSuccess = { newScan ->
                                isProcessing = false
                                onScanSelected(newScan)
                            },
                            onError = {
                                isProcessing = false
                            }
                        )
                    },
                    modifier = Modifier.fillMaxSize()
                )

                // Top Floating Scanner Controls
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = SurfaceWarmOat.copy(alpha = 0.95f),
                        shadowElevation = 2.dp
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(TerracottaSecondary)
                            )
                            Text(
                                text = if (isProcessing) "OCR Processing..." else "FSSAI Camera Active",
                                style = MaterialTheme.typography.labelSmall,
                                color = ForestPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 10.sp
                            )
                        }
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        IconButton(
                            onClick = { isTorchOn = !isTorchOn },
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(SurfaceWarmOat.copy(alpha = 0.95f))
                        ) {
                            Icon(
                                imageVector = if (isTorchOn) Icons.Default.FlashOn else Icons.Default.FlashOff,
                                contentDescription = "Torch",
                                tint = ForestPrimary,
                                modifier = Modifier.size(18.dp)
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = SurfaceWarmOat.copy(alpha = 0.95f),
                            modifier = Modifier.clickable { isBatchMode = !isBatchMode }
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PhotoCamera,
                                    contentDescription = null,
                                    tint = ForestPrimary,
                                    modifier = Modifier.size(14.dp)
                                )
                                Text(
                                    text = if (isBatchMode) "Batch: ON" else "Batch: OFF",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = ForestPrimary,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp
                                )
                            }
                        }
                    }
                }

                // Optical Scanner Reticle Frame Overlay (Clean Focus Target)
                Box(
                    modifier = Modifier
                        .size(180.dp)
                        .align(Alignment.Center)
                        .border(2.5.dp, ForestFixed, RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.CenterFocusWeak,
                            contentDescription = "Scanner Reticle",
                            tint = Color.White,
                            modifier = Modifier.size(38.dp)
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Align Food Label",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Device Storage Files Scan Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .border(1.dp, ForestPrimary.copy(alpha = 0.3f), RoundedCornerShape(16.dp)),
            colors = CardDefaults.cardColors(containerColor = CardMilledLinen)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(HerbalMint),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.FolderOpen,
                                contentDescription = null,
                                tint = ForestPrimary,
                                modifier = Modifier.size(18.dp)
                            )
                        }

                        Column {
                            Text(
                                text = "SCAN FROM STORAGE FILES",
                                style = MaterialTheme.typography.labelSmall,
                                color = ForestPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 10.sp,
                                letterSpacing = 0.5.sp
                            )
                            Text(
                                text = "Select Product Photo from Storage",
                                style = MaterialTheme.typography.titleMedium,
                                color = CharcoalEspresso,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { fileStorageLauncher.launch(arrayOf("image/*")) },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = ForestPrimary),
                        shape = RoundedCornerShape(20.dp),
                        contentPadding = PaddingValues(vertical = 8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Folder,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Storage Files",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White,
                            fontSize = 11.sp
                        )
                    }

                    OutlinedButton(
                        onClick = { galleryLauncher.launch("image/*") },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(20.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, ForestPrimary),
                        contentPadding = PaddingValues(vertical = 8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.PhotoLibrary,
                            contentDescription = null,
                            tint = ForestPrimary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Gallery Photos",
                            style = MaterialTheme.typography.labelSmall,
                            color = ForestPrimary,
                            fontSize = 11.sp
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Capture Controls & Benchmark Selector
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "TEST BENCHMARK SAMPLES",
                style = MaterialTheme.typography.labelSmall,
                color = ForestPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp,
                letterSpacing = 1.sp
            )

            Button(
                onClick = { galleryLauncher.launch("image/*") },
                colors = ButtonDefaults.buttonColors(containerColor = ForestPrimary),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                shape = RoundedCornerShape(20.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.AddPhotoAlternate,
                    contentDescription = "Scan Custom Photo",
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Upload Photo",
                    style = MaterialTheme.typography.labelSmall,
                    fontSize = 11.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Horizontal Benchmark Chips
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            sampleScans.forEachIndexed { index, scan ->
                val isSelected = currentScan.id == scan.id
                val isPass = scan.complianceResult.isCompliant
                val chipBg = if (isSelected) ForestPrimary else CardMilledLinen
                val textCol = if (isSelected) Color.White else CharcoalEspresso

                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = chipBg,
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            selectedSampleIndex = index
                            onScanSelected(scan)
                        }
                        .border(
                            1.dp,
                            if (isSelected) ForestPrimary else OutlineHairline.copy(alpha = 0.5f),
                            RoundedCornerShape(16.dp)
                        )
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = if (isPass) "PASS" else "FAIL",
                            style = MaterialTheme.typography.labelSmall,
                            color = if (isSelected) Color.White else (if (isPass) ComplianceGreen else NonComplianceRedText),
                            fontWeight = FontWeight.Bold,
                            fontSize = 9.sp
                        )
                        Text(
                            text = scan.productName.take(10) + "…",
                            style = MaterialTheme.typography.labelSmall,
                            color = textCol,
                            fontSize = 10.sp,
                            textAlign = TextAlign.Center,
                            maxLines = 1
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Live Recognition Ledger Sheet (Bottom Drawer)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(18.dp)),
            colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                // Drag Handle Bar
                Box(
                    modifier = Modifier
                        .width(36.dp)
                        .height(4.dp)
                        .clip(CircleShape)
                        .background(OutlineHairline)
                        .align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(52.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(SurfaceContainerHighest),
                            contentAlignment = Alignment.Center
                        ) {
                            currentScan.imageResId?.let { res ->
                                Image(
                                    painter = painterResource(id = res),
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
                                Text(
                                    text = "FSSAI Lic. #${currentScan.extractedFields.fssaiLicNo}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = ForestPrimary,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp
                                )
                                Icon(
                                    imageVector = Icons.Default.Verified,
                                    contentDescription = null,
                                    tint = ForestPrimary,
                                    modifier = Modifier.size(12.dp)
                                )
                            }
                            Text(
                                text = currentScan.productName,
                                style = MaterialTheme.typography.titleMedium,
                                color = CharcoalEspresso,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                maxLines = 1
                            )
                            Text(
                                text = "${currentScan.extractedFields.netQuantity} • ${currentScan.category}",
                                style = MaterialTheme.typography.bodySmall,
                                color = CharcoalVariant,
                                fontSize = 12.sp
                            )
                        }
                    }

                    // Score pill
                    ComplianceStatusPill(
                        isCompliant = currentScan.complianceResult.isCompliant,
                        score = currentScan.complianceResult.overallScore
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Quick Macro Nutrients & Safety Ratio Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = SurfaceContainer
                    ) {
                        Text(
                            text = "🔥 ${currentScan.extractedFields.nutritionalFacts.calories}",
                            style = MaterialTheme.typography.labelSmall,
                            color = ForestPrimary,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = SurfaceContainer
                    ) {
                        Text(
                            text = "💪 Protein ${currentScan.extractedFields.nutritionalFacts.protein}",
                            style = MaterialTheme.typography.labelSmall,
                            color = ForestPrimary,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = if (currentScan.extractedFields.ingredientAnalysis.safetyScoreRatio >= 80) HerbalMint else NonComplianceRedBg
                    ) {
                        Text(
                            text = "🛡️ ${currentScan.extractedFields.ingredientAnalysis.safetyRatioLabel.take(12)}",
                            style = MaterialTheme.typography.labelSmall,
                            color = if (currentScan.extractedFields.ingredientAnalysis.safetyScoreRatio >= 80) ForestPrimary else NonComplianceRedText,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider(color = OutlineHairline.copy(alpha = 0.3f))
                Spacer(modifier = Modifier.height(10.dp))

                // Summary Note
                Text(
                    text = currentScan.complianceResult.scanNotes,
                    style = MaterialTheme.typography.bodyMedium,
                    color = CharcoalEspresso,
                    fontSize = 13.sp
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Action Button
                Button(
                    onClick = onNavigateToDossier,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = ForestPrimary),
                    shape = RoundedCornerShape(24.dp),
                    contentPadding = PaddingValues(vertical = 12.dp)
                ) {
                    Text(
                        text = "Open Full Legal Metrology Dossier",
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}
