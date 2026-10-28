package com.example.nourishlens.ocr

import com.example.nourishlens.model.ExtractedFields
import java.net.URI

object BarcodeDecoder {

    data class DecodedBarcodeData(
        val rawValue: String,
        val formatName: String,
        val gtinCountryOfOrigin: String = "",
        val embeddedFssaiLic: String = "",
        val embeddedBatch: String = "",
        val embeddedMrp: String = "",
        val embeddedExpiry: String = "",
        val isGs1Compliant: Boolean = false,
        val decodedSummary: String = ""
    )

    fun decodeBarcodeData(rawBarcode: String, formatName: String): DecodedBarcodeData {
        if (rawBarcode.isBlank()) {
            return DecodedBarcodeData(rawValue = "", formatName = "None")
        }

        var country = ""
        var fssaiLic = ""
        var batch = ""
        var mrp = ""
        var expiry = ""
        var isGs1 = false

        // 1. EAN-13 / UPC Country Prefix Lookup
        val cleanDigits = rawBarcode.replace(Regex("[^0-9]"), "")
        if (cleanDigits.length >= 8) {
            val prefix = cleanDigits.take(3)
            country = lookupGtinCountry(prefix)
            if (cleanDigits.length == 13 || cleanDigits.length == 14) {
                isGs1 = true
            }
        }

        // 2. FSSAI / URL / Query Params Parsing
        if (rawBarcode.contains("http://", ignoreCase = true) || rawBarcode.contains("https://", ignoreCase = true) || rawBarcode.contains("fssai", ignoreCase = true)) {
            try {
                val uri = URI(rawBarcode)
                val query = uri.query ?: ""
                val params = query.split("&").associate {
                    val parts = it.split("=")
                    if (parts.size == 2) parts[0].lowercase() to parts[1] else "" to ""
                }

                fssaiLic = params["lic"] ?: params["fssai"] ?: params["license"] ?: ""
                mrp = params["mrp"] ?: params["price"] ?: ""
                batch = params["batch"] ?: params["lot"] ?: ""
                expiry = params["exp"] ?: params["expiry"] ?: ""
            } catch (e: Exception) {
                // Ignore URL parse error
            }
        }

        // 3. GS1 Application Identifier (AI) Parsing (e.g. (01)08901030889124(10)LOT123)
        val gtinAiMatch = Regex("(?:\\(01\\)|01)(\\d{14})").find(rawBarcode)
        val lotAiMatch = Regex("(?:\\(10\\)|10)([A-Za-z0-9]+)").find(rawBarcode)
        val expAiMatch = Regex("(?:\\(17\\)|17)(\\d{6})").find(rawBarcode)

        if (gtinAiMatch != null) isGs1 = true
        if (lotAiMatch != null) batch = lotAiMatch.groupValues[1]
        if (expAiMatch != null) {
            val expRaw = expAiMatch.groupValues[1] // YYMMDD
            if (expRaw.length == 6) {
                expiry = "${expRaw.substring(2, 4)}/20${expRaw.substring(0, 2)}"
            }
        }

        val summary = buildString {
            append("Decoded $formatName: $rawBarcode.")
            if (country.isNotBlank()) append(" Origin: $country.")
            if (isGs1) append(" Verified GS1 Standard Code.")
            if (fssaiLic.isNotBlank()) append(" Embedded FSSAI Lic #$fssaiLic.")
        }

        return DecodedBarcodeData(
            rawValue = rawBarcode,
            formatName = formatName,
            gtinCountryOfOrigin = country,
            embeddedFssaiLic = fssaiLic,
            embeddedBatch = batch,
            embeddedMrp = if (mrp.isNotBlank()) "₹ $mrp" else "",
            embeddedExpiry = expiry,
            isGs1Compliant = isGs1,
            decodedSummary = summary
        )
    }

    private fun lookupGtinCountry(prefix: String): String {
        val num = prefix.toIntOrNull() ?: return "Global GS1 Standard"
        return when (num) {
            in 890..890 -> "India (GS1 Bharat Registered)"
            in 0..19 -> "United States / Canada"
            in 300..379 -> "France"
            in 400..440 -> "Germany"
            in 450..459, in 490..499 -> "Japan"
            in 500..509 -> "United Kingdom"
            in 690..699 -> "China"
            in 760..769 -> "Switzerland"
            in 800..839 -> "Italy"
            in 840..849 -> "Spain"
            in 885..885 -> "Thailand"
            in 888..888 -> "Singapore"
            else -> "GS1 Global Member Country ($prefix)"
        }
    }

    fun enrichFieldsWithBarcodeData(fields: ExtractedFields, decoded: DecodedBarcodeData): ExtractedFields {
        if (decoded.rawValue.isBlank()) return fields

        return fields.copy(
            barcodeValue = decoded.rawValue,
            barcodeFormat = decoded.formatName,
            countryOfOrigin = if (fields.countryOfOrigin.isBlank() || fields.countryOfOrigin == "India") decoded.gtinCountryOfOrigin.ifBlank { fields.countryOfOrigin } else fields.countryOfOrigin,
            fssaiLicNo = if (fields.fssaiLicNo.isBlank() || fields.fssaiLicNo == "Not Found") decoded.embeddedFssaiLic.ifBlank { fields.fssaiLicNo } else fields.fssaiLicNo,
            mrp = if (fields.mrp.isBlank() || fields.mrp == "Not Found") decoded.embeddedMrp.ifBlank { fields.mrp } else fields.mrp,
            batchNumber = if (fields.batchNumber.isBlank() || fields.batchNumber == "None") decoded.embeddedBatch.ifBlank { fields.batchNumber } else fields.batchNumber,
            expiryDate = if (fields.expiryDate.isBlank() || fields.expiryDate == "Not Found") decoded.embeddedExpiry.ifBlank { fields.expiryDate } else fields.expiryDate
        )
    }
}
