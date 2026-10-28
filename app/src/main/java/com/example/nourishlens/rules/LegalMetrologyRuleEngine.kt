package com.example.nourishlens.rules

import com.example.nourishlens.model.ComplianceRule
import com.example.nourishlens.model.ComplianceResult
import com.example.nourishlens.model.ExtractedFields
import com.example.nourishlens.model.SeverityLevel

object LegalMetrologyRuleEngine {

    fun evaluateCompliance(fields: ExtractedFields, rawText: String): ComplianceResult {
        val rules = mutableListOf<ComplianceRule>()

        // 1. Rule 6(1)(a) - Manufacturer / Packer Name & Full Address
        val mfgPresent = fields.manufacturerName.isNotBlank() || fields.manufacturerAddress.isNotBlank()
        val mfgAddressComplete = fields.manufacturerAddress.length > 8 || fields.manufacturerName.contains("Pvt", ignoreCase = true) || fields.manufacturerName.contains("Ltd", ignoreCase = true) || rawText.contains("Mfg by", ignoreCase = true) || rawText.contains("Packed by", ignoreCase = true) || rawText.contains("Manufactured", ignoreCase = true)
        val r1Passed = mfgPresent && mfgAddressComplete
        rules.add(
            ComplianceRule(
                ruleCode = "LM-6-1-A",
                ruleTitle = "Manufacturer / Packer Identity & Complete Address",
                legalReference = "Rule 6(1)(a) of Legal Metrology (Packaged Commodities) Rules, 2011",
                isPassed = r1Passed,
                foundValue = if (r1Passed) "${fields.manufacturerName} - ${fields.manufacturerAddress}".trim(' ', '-') else if (fields.manufacturerName.isNotBlank()) fields.manufacturerName else "NOT DECLARED",
                requirementSummary = "Full name and complete registered postal address of manufacturer/packer must be clearly visible.",
                remediationGuidance = "Add complete manufacturer address including plot/building no., street, city, state and PIN code on label.",
                penaltySection = "Section 36(1) LM Act 2009: Fine up to ₹25,000 for 1st offense",
                severity = SeverityLevel.HIGH
            )
        )

        // 2. Rule 6(1)(aa) - Country of Origin
        val originPresent = fields.countryOfOrigin.isNotBlank() || rawText.contains("Country of Origin", ignoreCase = true) || rawText.contains("Made in India", ignoreCase = true) || rawText.contains("Product of India", ignoreCase = true) || rawText.contains("Bharat", ignoreCase = true)
        rules.add(
            ComplianceRule(
                ruleCode = "LM-6-1-AA",
                ruleTitle = "Declaration of Country of Origin",
                legalReference = "Rule 6(1)(aa) of Legal Metrology (Packaged Commodities) Rules, 2011",
                isPassed = originPresent,
                foundValue = if (fields.countryOfOrigin.isNotBlank()) fields.countryOfOrigin else if (originPresent) "Made in India (Inferred)" else "MISSING",
                requirementSummary = "The name of country of origin must be declared on every imported or domestically packaged commodity.",
                remediationGuidance = "Affix 'Country of Origin: India' or origin nation on principal display panel.",
                penaltySection = "Section 36 LM Act 2009: Notice for non-declaration",
                severity = SeverityLevel.MEDIUM
            )
        )

        // 3. Rule 6(1)(b) - Generic / Common Commodity Name
        val namePresent = fields.commodityName.isNotBlank() && fields.commodityName.length >= 3
        rules.add(
            ComplianceRule(
                ruleCode = "LM-6-1-B",
                ruleTitle = "Common or Generic Name of Commodity",
                legalReference = "Rule 6(1)(b) of Legal Metrology (Packaged Commodities) Rules, 2011",
                isPassed = namePresent,
                foundValue = if (namePresent) fields.commodityName else "UNSPECIFIED / UNREADABLE",
                requirementSummary = "The common or generic name of commodity contained in the package must be stated.",
                remediationGuidance = "Display generic description prominently (e.g., 'Roasted Fox Nuts' or 'Oat Milk Drink').",
                penaltySection = "Section 36 LM Act 2009: Non-compliant package seizure",
                severity = SeverityLevel.HIGH
            )
        )

        // 4. Rule 6(1)(c) - Net Quantity Standard Declaration
        val netQtyText = fields.netQuantity.trim()
        val hasNetQty = netQtyText.isNotBlank()
        val isNonStandardUnit = netQtyText.contains("gm", ignoreCase = true) || netQtyText.contains("approx", ignoreCase = true) || netQtyText.contains("~", ignoreCase = true)
        val hasStandardUnit = netQtyText.matches(Regex(".*\\d+\\s*(g|kg|ml|l|N|g.|ml.|L|kg.)\\b", RegexOption.IGNORE_CASE))
        val r4Passed = hasNetQty && !isNonStandardUnit && hasStandardUnit
        rules.add(
            ComplianceRule(
                ruleCode = "LM-6-1-C",
                ruleTitle = "Net Quantity Standard Units & Exact Measure",
                legalReference = "Rule 6(1)(c) & Rule 12 Legal Metrology (Packaged Commodities) Rules, 2011",
                isPassed = r4Passed,
                foundValue = if (hasNetQty) netQtyText else "MISSING",
                requirementSummary = "Net quantity must be declared using standard units (g, kg, ml, L, N). Non-standard units like 'gm' or approximations like 'approx' are strictly illegal.",
                remediationGuidance = if (isNonStandardUnit) "Replace non-standard notation 'gm' or 'approx' with legal unit 'g' or 'kg'." else "Declare net quantity prominently with approved symbol (g, kg, ml, L).",
                penaltySection = "Section 36(2) LM Act 2009: Penalty for non-standard quantity declaration",
                severity = SeverityLevel.HIGH
            )
        )

        // 5. Rule 6(1)(d) - Date of Manufacture / Packing / Import
        val datePresent = fields.mfgDate.isNotBlank() || rawText.contains("Mfg Date", ignoreCase = true) || rawText.contains("Packed Date", ignoreCase = true) || rawText.contains("Pkd", ignoreCase = true) || rawText.contains("Mfd", ignoreCase = true)
        rules.add(
            ComplianceRule(
                ruleCode = "LM-6-1-D",
                ruleTitle = "Month & Year of Manufacture / Packing",
                legalReference = "Rule 6(1)(d) of Legal Metrology (Packaged Commodities) Rules, 2011",
                isPassed = datePresent,
                foundValue = if (fields.mfgDate.isNotBlank()) fields.mfgDate else if (datePresent) "Date detected in OCR" else "MISSING",
                requirementSummary = "Month and year in which commodity is manufactured, packed or imported must be stated.",
                remediationGuidance = "Format as 'Mfg Date: MM/YYYY' or 'Packed: Month YYYY' in legible font size.",
                penaltySection = "Section 36 LM Act 2009: Fine up to ₹25,000",
                severity = SeverityLevel.HIGH
            )
        )

        // 6. Rule 6(1)(e) - MRP & Inclusive of All Taxes Declaration
        val hasMrp = fields.mrp.isNotBlank() && (fields.mrp.contains("₹") || fields.mrp.contains("Rs") || fields.mrp.contains("MRP") || fields.mrp.matches(Regex(".*\\d+.*")))
        val hasTaxText = fields.mrpTaxInclusive || rawText.contains("inclusive of all taxes", ignoreCase = true) || rawText.contains("incl. of all taxes", ignoreCase = true) || rawText.contains("incl of all taxes", ignoreCase = true)
        val r6Passed = hasMrp && hasTaxText
        rules.add(
            ComplianceRule(
                ruleCode = "LM-6-1-E",
                ruleTitle = "Maximum Retail Price (MRP) with Tax Inclusion Clause",
                legalReference = "Rule 6(1)(e) of Legal Metrology (Packaged Commodities) Rules, 2011",
                isPassed = r6Passed,
                foundValue = if (hasMrp) "${fields.mrp} ${if (hasTaxText) "(Incl. of all taxes)" else "[TAX CLAUSE MISSING]"}" else "MISSING",
                requirementSummary = "MRP must be clearly stated in Indian Rupees with mandatory 'Inclusive of all taxes' declaration.",
                remediationGuidance = if (!hasTaxText && hasMrp) "Mandatory clause missing: Append '(Inclusive of all taxes)' right next to the MRP figure." else "Declare MRP as 'MRP ₹ XX.XX (Inclusive of all taxes)'.",
                penaltySection = "Section 36(2) LM Act 2009: Penalty for non-declaration or overcharging",
                severity = SeverityLevel.HIGH
            )
        )

        // 7. Rule 6(1)(f) - Consumer Care Helpline Details
        val hasContact = fields.consumerCare.isNotBlank() || rawText.contains("Consumer Care", ignoreCase = true) || rawText.contains("Customer Care", ignoreCase = true) || rawText.contains("1800", ignoreCase = true) || rawText.contains("@", ignoreCase = true)
        rules.add(
            ComplianceRule(
                ruleCode = "LM-6-1-F",
                ruleTitle = "Consumer Care Officer Helpline & Contact Email",
                legalReference = "Rule 6(1)(f) of Legal Metrology (Packaged Commodities) Rules, 2011",
                isPassed = hasContact,
                foundValue = if (fields.consumerCare.isNotBlank()) fields.consumerCare else if (hasContact) "Helpline/Email detected" else "MISSING",
                requirementSummary = "Name, address, telephone number or email address of person/office for consumer complaints.",
                remediationGuidance = "Include dedicated helpline number (e.g. 1800-XXX-XXXX) and email address (e.g. care@company.com).",
                penaltySection = "Section 36 LM Act 2009: Regulatory non-compliance notice",
                severity = SeverityLevel.MEDIUM
            )
        )

        // 8. FSSAI License & Classification Standard
        val hasFssai = fields.fssaiLicNo.isNotBlank() && fields.fssaiLicNo.length >= 10 || rawText.contains("FSSAI", ignoreCase = true) || rawText.contains("Lic No", ignoreCase = true)
        rules.add(
            ComplianceRule(
                ruleCode = "FSSAI-2-2",
                ruleTitle = "FSSAI License Number & Vegetarian/Non-Veg Logo",
                legalReference = "FSSAI Food Safety and Standards (Packaging and Labelling) Regulations",
                isPassed = hasFssai,
                foundValue = if (fields.fssaiLicNo.isNotBlank()) "Lic #${fields.fssaiLicNo} (${fields.vegetarianStatus})" else if (hasFssai) "FSSAI Lic Detected (${fields.vegetarianStatus})" else "MISSING",
                requirementSummary = "Valid 14-digit FSSAI License Number and Green/Brown dot symbol mandatory on packaged food.",
                remediationGuidance = "Print FSSAI logo alongside 14-digit License No. and green vegetarian dot symbol.",
                penaltySection = "FSSAI Act 2006 Section 52: Penalty for misbranded food up to ₹3 Lakhs",
                severity = SeverityLevel.HIGH
            )
        )

        // 9. Surface Integrity, Glare & Font Size Legibility
        val glareClean = !fields.glareDetected
        val curveAcceptable = fields.surfaceCurvatureScore < 35
        val r9Passed = glareClean && curveAcceptable
        rules.add(
            ComplianceRule(
                ruleCode = "LM-DEC-LEGIBILITY",
                ruleTitle = "Label Legibility, Glare & Packaging Surface Quality",
                legalReference = "Rule 9 Legal Metrology (Packaged Commodities) Rules, 2011",
                isPassed = r9Passed,
                foundValue = "Glare: ${if (fields.glareDetected) "DETECTED (HIGH)" else "Minimal"} | Curvature Distortion: ${fields.surfaceCurvatureScore}%",
                requirementSummary = "Declarations must be legible, prominent, and unaffected by glare or cylindrical package warping.",
                remediationGuidance = "Adjust lighting angle or reposition camera to eliminate specular glare on glossy pouches.",
                penaltySection = "Audit Quality Advisory: Re-scan required if distortion exceeds threshold",
                severity = SeverityLevel.LOW
            )
        )

        val passedCount = rules.count { it.isPassed }
        val totalCount = rules.size
        val score = ((passedCount.toDouble() / totalCount.toDouble()) * 100).toInt()
        val isCompliant = rules.none { !it.isPassed && it.severity == SeverityLevel.HIGH }

        return ComplianceResult(
            isCompliant = isCompliant,
            overallScore = score,
            passedRulesCount = passedCount,
            totalRulesCount = totalCount,
            rules = rules,
            scanNotes = if (isCompliant) "Product is fully compliant with Legal Metrology (Packaged Commodities) Rules 2011 & FSSAI standards." else "${rules.count { !it.isPassed }} violation(s) detected under Legal Metrology Rules."
        )
    }
}
