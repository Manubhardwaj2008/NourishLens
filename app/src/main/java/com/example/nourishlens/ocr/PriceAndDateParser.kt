package com.example.nourishlens.ocr

object PriceAndDateParser {

    /**
     * Hyper-Precision 5-Tier MRP Extractor
     */
    fun extractMrp(cleanText: String, lines: List<String>): String {
        val normalizedText = cleanText.replace("\n", " ").replace("\r", " ")

        // Tier 1: Explicit MRP Keywords + Currency Symbol + Amount (e.g. "MRP Rs. 150.00", "M.R.P. : ₹ 250", "MAX RETAIL PRICE RS 45.00")
        val t1 = Regex("(?i)(?:M\\.?R\\.?P\\.?|MAX\\.?\\s*RETAIL\\s*PRICE|MAX\\.?\\s*RETAIL|PRICE)[\\s:.-]*?(?:RS\\.?|₹|INR)?[\\s:.-]*?(\\d+(?:\\.\\d{1,2})?)\\b")
        val m1 = t1.find(normalizedText)
        if (m1 != null) {
            val valStr = m1.groupValues[1]
            if (isValidPrice(valStr)) return "₹ $valStr"
        }

        // Tier 2: Currency Symbol + Amount (e.g. "Rs. 150.00", "₹ 50", "RS 45/-", "Rs120")
        val t2 = Regex("(?i)(?:RS\\.?|₹|INR)[\\s:.-]*?(\\d+(?:\\.\\d{1,2})?)\\b")
        val m2 = t2.find(normalizedText)
        if (m2 != null) {
            val valStr = m2.groupValues[1]
            if (isValidPrice(valStr)) return "₹ $valStr"
        }

        // Tier 3: Line-level Search on lines containing 'MRP', 'RS', 'PRICE', 'TAX', or 'INCL'
        for (line in lines) {
            if (line.contains("MRP", ignoreCase = true) || line.contains("RS", ignoreCase = true) || line.contains("PRICE", ignoreCase = true) || line.contains("TAX", ignoreCase = true)) {
                val t3 = Regex("(\\d+(?:\\.\\d{1,2})?)")
                val matches = t3.findAll(line)
                for (match in matches) {
                    val candidate = match.groupValues[1]
                    if ((isValidPrice(candidate)) && candidate != "100") {
                        return "₹ $candidate"
                    }
                }
            }
        }

        // Tier 4: Token-based Search after MRP or RS keyword
        val tokens = normalizedText.split(Regex("\\s+"))
        for (i in tokens.indices) {
            val tok = tokens[i]
            if (tok.contains("MRP", ignoreCase = true) || tok.contains("RS", ignoreCase = true) || tok.contains("₹")) {
                for (j in 1..3) {
                    if (i + j < tokens.size) {
                        val numMatch = Regex("(\\d+(?:\\.\\d{1,2})?)").find(tokens[i + j])
                        if (numMatch != null) {
                            val candidate = numMatch.groupValues[1]
                            if (isValidPrice(candidate)) {
                                return "₹ $candidate"
                            }
                        }
                    }
                }
            }
        }

        // Tier 5: Standalone number near 'INCLUSIVE OF ALL TAXES'
        if (normalizedText.contains("TAX", ignoreCase = true) || normalizedText.contains("INCL", ignoreCase = true)) {
            val t5 = Regex("(\\d{2,4}(?:\\.\\d{2})?)")
            val m5 = t5.find(normalizedText)
            if (m5 != null && isValidPrice(m5.groupValues[1])) {
                return "₹ ${m5.groupValues[1]}"
            }
        }

        return "Not Found"
    }

    private fun isValidPrice(numStr: String): Boolean {
        val num = numStr.toDoubleOrNull() ?: return false
        return num in 1.0..9999.0
    }

    /**
     * Hyper-Precision 5-Tier Manufacturing / Packing Date Extractor
     */
    fun extractMfgDate(cleanText: String, lines: List<String>): String {
        val normalizedText = cleanText.replace("\n", " ").replace("\r", " ")

        // Tier 1: Explicit Mfg/Pkd Keywords + Date String (e.g. "MFG 15/03/2026", "PKD 03/26", "PACKED ON 15.03.2026", "MFD: MAR 2026")
        val t1 = Regex("(?i)(?:M\\.?F\\.?G\\.?|M\\.?F\\.?D\\.?|P\\.?K\\.?D\\.?|PACKED|MFR|DATE\\s*OF\\s*MFG)[\\s:.-]*?([0-9]{1,2}[./-][0-9]{1,2}[./-][0-9]{2,4}|[0-9]{1,2}[./-][0-9]{2,4}|(?:[0-9]{1,2}\\s*)?[A-Za-z]{3,9}\\s*[0-9]{2,4})")
        val m1 = t1.find(normalizedText)
        if (m1 != null) {
            val dateStr = m1.groupValues[1].trim()
            if (dateStr.isNotBlank()) return dateStr
        }

        // Tier 2: Keyword attached directly to date without space (e.g. "MFG15/03/26", "PKD03/2026", "MFD15.03.26")
        val t2 = Regex("(?i)(?:MFG|MFD|PKD|PACKED)([0-9]{1,2}[./-][0-9]{1,2}[./-][0-9]{2,4}|[0-9]{1,2}[./-][0-9]{2,4})")
        val m2 = t2.find(normalizedText)
        if (m2 != null) {
            val dateStr = m2.groupValues[1].trim()
            if (dateStr.isNotBlank()) return dateStr
        }

        // Tier 3: Standalone Date String Formats anywhere in text (e.g. "15/03/2026", "15.03.26", "03/2026", "MAR 2026")
        val t3 = Regex("(?i)\\b([0-9]{2}[./-][0-9]{2}[./-][0-9]{2,4}|[0-9]{2}[./-][0-9]{2,4}|(?:JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC)\\s*[0-9]{2,4})\\b")
        val m3 = t3.find(normalizedText)
        if (m3 != null) {
            val dateStr = m3.groupValues[1].trim()
            if (dateStr.isNotBlank()) return dateStr
        }

        // Tier 4: Line-level Search on lines containing 'MFG', 'PKD', 'PACKED', or 'MFD'
        for (line in lines) {
            if (line.contains("MFG", ignoreCase = true) || line.contains("PKD", ignoreCase = true) || line.contains("PACKED", ignoreCase = true) || line.contains("MFD", ignoreCase = true)) {
                val t4 = Regex("([0-9]{1,2}[./-][0-9]{1,2}[./-][0-9]{2,4}|[0-9]{1,2}[./-][0-9]{2,4}|[A-Za-z]{3,9}\\s*[0-9]{2,4})")
                val m4 = t4.find(line)
                if (m4 != null) {
                    val dateStr = m4.groupValues[1].trim()
                    if (dateStr.isNotBlank()) return dateStr
                }
            }
        }

        // Tier 5: 6-digit MMYYYY or DDMMYYYY date sequences (e.g. 032026 or 15032026)
        val t5 = Regex("\\b(0[1-9]|1[0-2])[./-]?(202[4-9]|2[4-9])\\b")
        val m5 = t5.find(normalizedText)
        if (m5 != null) {
            val mm = m5.groupValues[1]
            val yy = m5.groupValues[2]
            return "$mm/$yy"
        }

        return "Not Found"
    }
}
