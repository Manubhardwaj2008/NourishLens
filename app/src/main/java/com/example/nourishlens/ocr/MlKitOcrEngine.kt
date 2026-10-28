package com.example.nourishlens.ocr

import android.graphics.Bitmap
import androidx.camera.core.ImageProxy
import com.example.nourishlens.model.ProductScan
import com.example.nourishlens.rules.LegalMetrologyRuleEngine
import com.example.nourishlens.util.ImagePreprocessor
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import java.util.UUID

object MlKitOcrEngine {

    @androidx.annotation.OptIn(androidx.camera.core.ExperimentalGetImage::class)
    fun scanImageProxy(
        imageProxy: ImageProxy,
        onSuccess: (ProductScan) -> Unit,
        onError: (Exception) -> Unit
    ) {
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
            val textRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
            val barcodeScanner = BarcodeScanning.getClient()

            // Run Barcode Scanner first
            barcodeScanner.process(image)
                .addOnSuccessListener { barcodes ->
                    val detectedBarcode = barcodes.firstOrNull()
                    val barcodeVal = detectedBarcode?.rawValue ?: ""
                    val barcodeFormatStr = getBarcodeFormatString(detectedBarcode?.format)

                    // Run Text Recognizer
                    textRecognizer.process(image)
                        .addOnSuccessListener { visionText ->
                            val rawText = visionText.text
                            var extracted = OcrParser.parseOcrText(rawText)

                            if (barcodeVal.isNotBlank()) {
                                val decodedData = BarcodeDecoder.decodeBarcodeData(barcodeVal, barcodeFormatStr)
                                extracted = BarcodeDecoder.enrichFieldsWithBarcodeData(extracted, decodedData)
                            }

                            val compliance = LegalMetrologyRuleEngine.evaluateCompliance(extracted, rawText)

                            val scan = ProductScan(
                                id = "SCAN-" + UUID.randomUUID().toString().take(8).uppercase(),
                                productName = extracted.commodityName.ifBlank { "Scanned Food Commodity" },
                                brandName = extracted.manufacturerName.ifBlank { "Scanned Brand" },
                                category = "Packaged Food",
                                rawOcrText = rawText.ifBlank { "No readable text detected on scanned label." },
                                extractedFields = extracted,
                                complianceResult = compliance,
                                timestamp = System.currentTimeMillis()
                            )
                            imageProxy.close()
                            onSuccess(scan)
                        }
                        .addOnFailureListener { exc ->
                            imageProxy.close()
                            onError(exc)
                        }
                }
                .addOnFailureListener {
                    // Fallback to text recognizer if barcode scanner fails
                    textRecognizer.process(image)
                        .addOnSuccessListener { visionText ->
                            val rawText = visionText.text
                            val extracted = OcrParser.parseOcrText(rawText)
                            val compliance = LegalMetrologyRuleEngine.evaluateCompliance(extracted, rawText)

                            val scan = ProductScan(
                                id = "SCAN-" + UUID.randomUUID().toString().take(8).uppercase(),
                                productName = extracted.commodityName.ifBlank { "Scanned Food Commodity" },
                                brandName = extracted.manufacturerName.ifBlank { "Scanned Brand" },
                                category = "Packaged Food",
                                rawOcrText = rawText.ifBlank { "No readable text detected on scanned label." },
                                extractedFields = extracted,
                                complianceResult = compliance,
                                timestamp = System.currentTimeMillis()
                            )
                            imageProxy.close()
                            onSuccess(scan)
                        }
                        .addOnFailureListener { exc ->
                            imageProxy.close()
                            onError(exc)
                        }
                }
        } else {
            imageProxy.close()
            onError(IllegalArgumentException("MediaImage is null"))
        }
    }

    fun scanBitmapImage(
        bitmap: Bitmap,
        imageUriStr: String? = null,
        onSuccess: (ProductScan) -> Unit,
        onError: (Exception) -> Unit
    ) {
        try {
            val textRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
            val barcodeScanner = BarcodeScanning.getClient()
            val image = InputImage.fromBitmap(bitmap, 0)

            barcodeScanner.process(image)
                .addOnSuccessListener { barcodes ->
                    val detectedBarcode = barcodes.firstOrNull()
                    val barcodeVal = detectedBarcode?.rawValue ?: ""
                    val barcodeFormatStr = getBarcodeFormatString(detectedBarcode?.format)

                    textRecognizer.process(image)
                        .addOnSuccessListener { visionText ->
                            var rawText = visionText.text
                            var extracted = OcrParser.parseOcrText(rawText)

                            if (barcodeVal.isNotBlank()) {
                                val decodedData = BarcodeDecoder.decodeBarcodeData(barcodeVal, barcodeFormatStr)
                                extracted = BarcodeDecoder.enrichFieldsWithBarcodeData(extracted, decodedData)
                            }

                            // Secondary Contrast-Enhanced Pass for faint/light font & dot-matrix stamped MRP/Mfg dates
                            if (extracted.mrp == "Not Found" || extracted.mfgDate == "Not Found") {
                                try {
                                    val contrastBitmap = ImagePreprocessor.enhanceContrastForOcr(bitmap)
                                    val contrastImage = InputImage.fromBitmap(contrastBitmap, 0)
                                    textRecognizer.process(contrastImage)
                                        .addOnSuccessListener { contrastVisionText ->
                                            val contrastRawText = contrastVisionText.text
                                            if (contrastRawText.length > rawText.length) {
                                                rawText = "$rawText\n$contrastRawText"
                                                extracted = OcrParser.parseOcrText(rawText)
                                                if (barcodeVal.isNotBlank()) {
                                                    extracted = extracted.copy(
                                                        barcodeValue = barcodeVal,
                                                        barcodeFormat = barcodeFormatStr
                                                    )
                                                }
                                            }
                                            val compliance = LegalMetrologyRuleEngine.evaluateCompliance(extracted, rawText)

                                            val scan = ProductScan(
                                                id = "SCAN-" + UUID.randomUUID().toString().take(8).uppercase(),
                                                productName = extracted.commodityName.ifBlank { "Scanned Food Commodity" },
                                                brandName = extracted.manufacturerName.ifBlank { "Scanned Brand" },
                                                category = "Packaged Food",
                                                imageUri = imageUriStr,
                                                rawOcrText = rawText.ifBlank { "No readable text detected on scanned label." },
                                                extractedFields = extracted,
                                                complianceResult = compliance,
                                                timestamp = System.currentTimeMillis()
                                            )
                                            onSuccess(scan)
                                        }
                                        .addOnFailureListener {
                                            val compliance = LegalMetrologyRuleEngine.evaluateCompliance(extracted, rawText)
                                            val scan = ProductScan(
                                                id = "SCAN-" + UUID.randomUUID().toString().take(8).uppercase(),
                                                productName = extracted.commodityName.ifBlank { "Scanned Food Commodity" },
                                                brandName = extracted.manufacturerName.ifBlank { "Scanned Brand" },
                                                category = "Packaged Food",
                                                imageUri = imageUriStr,
                                                rawOcrText = rawText.ifBlank { "No readable text detected on scanned label." },
                                                extractedFields = extracted,
                                                complianceResult = compliance,
                                                timestamp = System.currentTimeMillis()
                                            )
                                            onSuccess(scan)
                                        }
                                    return@addOnSuccessListener
                                } catch (e: Exception) {
                                    // Proceed
                                }
                            }

                            val compliance = LegalMetrologyRuleEngine.evaluateCompliance(extracted, rawText)
                            val scan = ProductScan(
                                id = "SCAN-" + UUID.randomUUID().toString().take(8).uppercase(),
                                productName = extracted.commodityName.ifBlank { "Scanned Food Commodity" },
                                brandName = extracted.manufacturerName.ifBlank { "Scanned Brand" },
                                category = "Packaged Food",
                                imageUri = imageUriStr,
                                rawOcrText = rawText.ifBlank { "No readable text detected on scanned label." },
                                extractedFields = extracted,
                                complianceResult = compliance,
                                timestamp = System.currentTimeMillis()
                            )
                            onSuccess(scan)
                        }
                        .addOnFailureListener { exc ->
                            onError(exc)
                        }
                }
                .addOnFailureListener {
                    // Fallback to text recognizer directly
                    textRecognizer.process(image)
                        .addOnSuccessListener { visionText ->
                            val rawText = visionText.text
                            val extracted = OcrParser.parseOcrText(rawText)
                            val compliance = LegalMetrologyRuleEngine.evaluateCompliance(extracted, rawText)

                            val scan = ProductScan(
                                id = "SCAN-" + UUID.randomUUID().toString().take(8).uppercase(),
                                productName = extracted.commodityName.ifBlank { "Scanned Food Commodity" },
                                brandName = extracted.manufacturerName.ifBlank { "Scanned Brand" },
                                category = "Packaged Food",
                                imageUri = imageUriStr,
                                rawOcrText = rawText.ifBlank { "No readable text detected on scanned label." },
                                extractedFields = extracted,
                                complianceResult = compliance,
                                timestamp = System.currentTimeMillis()
                            )
                            onSuccess(scan)
                        }
                        .addOnFailureListener { exc ->
                            onError(exc)
                        }
                }
        } catch (e: Exception) {
            onError(e)
        }
    }

    private fun getBarcodeFormatString(format: Int?): String {
        return when (format) {
            Barcode.FORMAT_EAN_13 -> "EAN-13 GTIN Barcode"
            Barcode.FORMAT_EAN_8 -> "EAN-8 Barcode"
            Barcode.FORMAT_UPC_A -> "UPC-A Barcode"
            Barcode.FORMAT_UPC_E -> "UPC-E Barcode"
            Barcode.FORMAT_QR_CODE -> "FSSAI QR Code"
            Barcode.FORMAT_CODE_128 -> "Code 128 Barcode"
            Barcode.FORMAT_CODE_39 -> "Code 39 Barcode"
            Barcode.FORMAT_DATA_MATRIX -> "Data Matrix Code"
            else -> "Standard Commodity Barcode"
        }
    }
}
