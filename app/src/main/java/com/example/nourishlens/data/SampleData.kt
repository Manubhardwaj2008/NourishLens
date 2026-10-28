package com.example.nourishlens.data

import com.example.nourishlens.R
import com.example.nourishlens.model.AdditiveInfo
import com.example.nourishlens.model.ExtractedFields
import com.example.nourishlens.model.FssaiLabVerification
import com.example.nourishlens.model.IngredientAnalysis
import com.example.nourishlens.model.NutrientLabComparison
import com.example.nourishlens.model.NutritionalFacts
import com.example.nourishlens.model.ProductScan
import com.example.nourishlens.rules.LegalMetrologyRuleEngine

object SampleData {

    val sampleOatMilkFields = ExtractedFields(
        commodityName = "Urban Platter Oat Barista",
        manufacturerName = "Urban Platter Foods Pvt Ltd",
        manufacturerAddress = "Plot 18, TTC Industrial Area, Mahape, Navi Mumbai - 400710",
        countryOfOrigin = "India",
        netQuantity = "1000 ml",
        mrp = "₹ 325.00",
        mrpTaxInclusive = true,
        mfgDate = "02/2026",
        expiryDate = "11/2026",
        consumerCare = "support@urbanplatter.in | 1800-890-1212",
        fssaiLicNo = "11517018000624",
        vegetarianStatus = "100% Vegetarian",
        batchNumber = "UP-OAT-2026-88",
        barcodeValue = "8901030889124",
        barcodeFormat = "EAN-13 GTIN Barcode",
        glareDetected = false,
        surfaceCurvatureScore = 10,
        ocrConfidence = 96,
        nutritionalFacts = NutritionalFacts(
            calories = "140 kcal",
            carbohydrates = "18.5 g",
            sugar = "2.0 g",
            addedSugar = "0.0 g",
            protein = "3.2 g",
            totalFat = "4.8 g",
            saturatedFat = "0.5 g",
            transFat = "0.0 g",
            cholesterol = "0 mg",
            sodium = "110 mg",
            vitaminsAndMinerals = listOf("Vitamin B12 (35% RDA)", "Vitamin D2 (20% RDA)", "Calcium (180 mg)"),
            servingSize = "Per 100 ml"
        ),
        ingredientAnalysis = IngredientAnalysis(
            fullIngredientsText = "Filtered Water, Whole Grain Oats (12%), Cold Pressed Sunflower Oil, Sea Salt, Calcium Carbonate, Vitamin B12.",
            beneficialIngredients = listOf(
                AdditiveInfo(
                    code = "OAT-FIBER",
                    name = "Whole Grain Beta-Glucan Oat Fiber",
                    category = "Heart Healthy Fiber",
                    isHarmful = false,
                    description = "Clinically proven to reduce blood LDL cholesterol and regulate glycemic response.",
                    fssaiStatus = "FSSAI Category 13.0 Approved Whole Grain Component"
                ),
                AdditiveInfo(
                    code = "VIT-B12",
                    name = "Cyanocobalamin (Vitamin B12)",
                    category = "Essential Micronutrient",
                    isHarmful = false,
                    description = "Supports red blood cell production, metabolic energy, and neural function.",
                    fssaiStatus = "FSSAI Fortification Guideline Compliant"
                )
            ),
            harmfulOrCautionaryAdditives = emptyList(),
            fssaiHfssAlerts = emptyList(),
            safetyScoreRatio = 95,
            safetyRatioLabel = "95:5 Clean Safety Ratio (100% Pure Plant-Based)",
            safetyTier = "VERY SAFE & NUTRITIOUS (FSSAI APPROVED)"
        ),
        labVerification = FssaiLabVerification(
            labName = "FSSAI National Food Laboratory (NFL) Ghaziabad / NABL #TC-8812",
            reportNo = "NFL/2026/FSSAI-IN-99412",
            testingDate = "15 March 2026",
            overallVerificationStatus = "100% VERIFIED BY FSSAI NFL LAB - MATCHES CERTIFIED TEST REPORT",
            isVerifiedMatchesLab = true,
            averageVariance = "1.1% Variance (Well Within Permitted ±20% FSSAI Limit)",
            labNutrientGrid = listOf(
                NutrientLabComparison("Energy (Calories)", "140 kcal", "7.0% RDA", "138 kcal", -1.4, true),
                NutrientLabComparison("Protein", "3.2 g", "5.9% RDA", "3.25 g", +1.5, true),
                NutrientLabComparison("Total Carbohydrates", "18.5 g", "14.2% RDA", "18.2 g", -1.6, true),
                NutrientLabComparison("Total Sugars", "2.0 g", "4.0% RDA", "2.02 g", +1.0, true),
                NutrientLabComparison("Total Fat", "4.8 g", "7.1% RDA", "4.75 g", -1.0, true),
                NutrientLabComparison("Saturated Fat", "0.5 g", "2.2% RDA", "0.51 g", +2.0, true),
                NutrientLabComparison("Sodium", "110 mg", "5.5% RDA", "108 mg", -1.8, true)
            )
        )
    )

    val sampleGranolaFields = ExtractedFields(
        commodityName = "Desi Honey Oat Granola",
        manufacturerName = "VedaPure Organics Pvt Ltd",
        manufacturerAddress = "Nilgiri Estate, Coonoor Road, Ooty, Tamil Nadu - 643001",
        countryOfOrigin = "India",
        netQuantity = "350 g",
        mrp = "₹ 499.00",
        mrpTaxInclusive = true,
        mfgDate = "03/2026",
        expiryDate = "12/2026",
        consumerCare = "care@vedapure.in | 1800-112-8800",
        fssaiLicNo = "12421003000882",
        vegetarianStatus = "100% Vegetarian",
        batchNumber = "VP-GRN-9912",
        barcodeValue = "8901242100300",
        barcodeFormat = "EAN-13 GTIN Barcode",
        glareDetected = false,
        surfaceCurvatureScore = 12,
        ocrConfidence = 94,
        nutritionalFacts = NutritionalFacts(
            calories = "380 kcal",
            carbohydrates = "54.0 g",
            sugar = "8.5 g",
            addedSugar = "0.0 g",
            protein = "12.0 g",
            totalFat = "11.2 g",
            saturatedFat = "1.8 g",
            transFat = "0.0 g",
            cholesterol = "0 mg",
            sodium = "45 mg",
            vitaminsAndMinerals = listOf("Iron (3.2 mg)", "Magnesium (95 mg)", "Vitamin E (12% RDA)"),
            servingSize = "Per 100 g"
        ),
        ingredientAnalysis = IngredientAnalysis(
            fullIngredientsText = "Rolled Avena Oats (65%), Raw Forest Honey (18%), Almonds, Pumpkin Seeds, Kerala Cardamom, Cold Pressed Sesame Oil.",
            beneficialIngredients = listOf(
                AdditiveInfo(
                    code = "RAW-HONEY",
                    name = "Nilgiri Forest Raw Honey",
                    category = "Bio-Active Natural Sweetener",
                    isHarmful = false,
                    description = "Contains natural flavonoids, anti-inflammatory polyphenols, and prebiotics.",
                    fssaiStatus = "Agmark Grade A Certified Natural Forest Honey"
                ),
                AdditiveInfo(
                    code = "K-CARDAMOM",
                    name = "Whole Green Cardamom",
                    category = "Natural Botanical Spice",
                    isHarmful = false,
                    description = "Aids digestion and acts as a powerful natural antioxidant.",
                    fssaiStatus = "FSSAI Permitted Spices Category"
                )
            ),
            harmfulOrCautionaryAdditives = emptyList(),
            fssaiHfssAlerts = emptyList(),
            safetyScoreRatio = 92,
            safetyRatioLabel = "92:8 Organic Clean Ratio",
            safetyTier = "VERY SAFE & HEALTHY (FSSAI ORGANIC CERTIFIED)"
        ),
        labVerification = FssaiLabVerification(
            labName = "NABL Accredited Testing Laboratory #TC-7718 / FSSAI Approved",
            reportNo = "NABL/2026/VP-8821",
            testingDate = "10 March 2026",
            overallVerificationStatus = "100% VERIFIED BY NABL CERTIFIED FOOD LAB",
            isVerifiedMatchesLab = true,
            averageVariance = "0.8% Variance (Within Permitted ±20% FSSAI Limit)",
            labNutrientGrid = listOf(
                NutrientLabComparison("Energy (Calories)", "380 kcal", "19.0% RDA", "378 kcal", -0.5, true),
                NutrientLabComparison("Protein", "12.0 g", "22.2% RDA", "12.1 g", +0.8, true),
                NutrientLabComparison("Total Carbohydrates", "54.0 g", "41.5% RDA", "53.6 g", -0.7, true),
                NutrientLabComparison("Total Sugars", "8.5 g", "17.0% RDA", "8.55 g", +0.5, true),
                NutrientLabComparison("Total Fat", "11.2 g", "16.7% RDA", "11.1 g", -0.8, true),
                NutrientLabComparison("Saturated Fat", "1.8 g", "8.1% RDA", "1.81 g", +0.5, true),
                NutrientLabComparison("Sodium", "45 mg", "2.2% RDA", "44.2 mg", -1.7, true)
            )
        )
    )

    val sampleMakhanaFields = ExtractedFields(
        commodityName = "Crispy Roasted Makhana (Fox Nuts)",
        manufacturerName = "Snacko Foods",
        manufacturerAddress = "Snacko Unit, Delhi",
        countryOfOrigin = "India",
        netQuantity = "approx 200g",
        mrp = "₹ 180.00",
        mrpTaxInclusive = false,
        mfgDate = "01/2026",
        expiryDate = "07/2026",
        consumerCare = "",
        fssaiLicNo = "10020011009922",
        vegetarianStatus = "100% Vegetarian",
        batchNumber = "MK-402",
        barcodeValue = "8901002001100",
        barcodeFormat = "EAN-13 GTIN Barcode",
        glareDetected = true,
        surfaceCurvatureScore = 28,
        ocrConfidence = 78,
        nutritionalFacts = NutritionalFacts(
            calories = "440 kcal",
            carbohydrates = "62.0 g",
            sugar = "1.5 g",
            addedSugar = "0.0 g",
            protein = "8.5 g",
            totalFat = "18.5 g",
            saturatedFat = "8.2 g",
            transFat = "0.2 g",
            cholesterol = "12 mg",
            sodium = "680 mg",
            vitaminsAndMinerals = listOf("Calcium (60 mg)", "Potassium (120 mg)"),
            servingSize = "Per 100 g"
        ),
        ingredientAnalysis = IngredientAnalysis(
            fullIngredientsText = "Popped Lotus Seeds (60%), Hydrogenated Palm Kernel Oil, Salt, Preservative (E211 Sodium Benzoate), Synthetic Flavor (INS 320 BHA).",
            beneficialIngredients = listOf(
                AdditiveInfo(
                    code = "FOX-NUT",
                    name = "Euryale Ferox (Lotus Seed)",
                    category = "Natural Ancient Grain",
                    isHarmful = false,
                    description = "Low glycemic index whole food seed rich in natural proteins.",
                    fssaiStatus = "FSSAI Approved Traditional Food"
                )
            ),
            harmfulOrCautionaryAdditives = listOf(
                AdditiveInfo(
                    code = "E211",
                    name = "Sodium Benzoate (E211)",
                    category = "Chemical Preservative",
                    isHarmful = true,
                    description = "Synthetic preservative. Associated with cellular oxidation and hyperactivity when consumed regularly.",
                    fssaiStatus = "FSSAI Restricted Preservative (<120ppm Limit)"
                ),
                AdditiveInfo(
                    code = "INS 320",
                    name = "Butylated Hydroxyanisole (BHA / E320)",
                    category = "Synthetic Antioxidant / Chemical",
                    isHarmful = true,
                    description = "Synthetic antioxidant chemical. Flagged by WHO/FSSAI as endocrine disrupting in high doses.",
                    fssaiStatus = "FSSAI Cautionary Chemical Additive"
                ),
                AdditiveInfo(
                    code = "PALM-FAT",
                    name = "Hydrogenated Palm Kernel Oil",
                    category = "Industrial Processed Fat",
                    isHarmful = true,
                    description = "High saturated and trans fat density. Raises arterial plaque buildup.",
                    fssaiStatus = "FSSAI HFSS Saturated Fat Warning (>8g/100g)"
                )
            ),
            fssaiHfssAlerts = listOf(
                "HIGH SODIUM ALERT (>600mg / 100g threshold exceeded)",
                "HIGH SATURATED FAT ALERT (>8g / 100g limit exceeded)",
                "CHEMICAL PRESERVATIVE ALERT (E211 + BHA 320 present)"
            ),
            safetyScoreRatio = 45,
            safetyRatioLabel = "45:55 Chemical & HFSS Risk Ratio",
            safetyTier = "HIGH RISK - CHEMICAL ADDITIVES & HFSS WARNING"
        ),
        labVerification = FssaiLabVerification(
            labName = "FSSAI Regional Food Testing Laboratory Delhi / NABL #TC-5012",
            reportNo = "NFL/2026/MK-4001",
            testingDate = "02 February 2026",
            overallVerificationStatus = "MISBRANDING ALERT — SATURATED FAT & SODIUM EXCEED LABEL DECLARATION",
            isVerifiedMatchesLab = false,
            averageVariance = "+24.5% Variance (EXCEEDS PERMITTED ±20% FSSAI LIMIT)",
            labNutrientGrid = listOf(
                NutrientLabComparison("Energy (Calories)", "440 kcal", "22.0% RDA", "465 kcal", +5.6, true),
                NutrientLabComparison("Protein", "8.5 g", "15.7% RDA", "8.1 g", -4.7, true),
                NutrientLabComparison("Total Carbohydrates", "62.0 g", "47.6% RDA", "60.5 g", -2.4, true),
                NutrientLabComparison("Total Fat", "18.5 g", "27.6% RDA", "22.8 g", +23.2, false, "⚠ DISCREPANCY EXCEEDS FSSAI ±20% LIMIT"),
                NutrientLabComparison("Saturated Fat", "8.2 g", "37.2% RDA", "11.4 g", +39.0, false, "⚠ DISCREPANCY EXCEEDS FSSAI ±20% LIMIT"),
                NutrientLabComparison("Sodium", "680 mg", "34.0% RDA", "840 mg", +23.5, false, "⚠ DISCREPANCY EXCEEDS FSSAI ±20% LIMIT")
            )
        )
    )

    val sampleCashewBarFields = ExtractedFields(
        commodityName = "Stoneground Cashew Cacao Bar",
        manufacturerName = "ChocoNut Artisanal",
        manufacturerAddress = "",
        countryOfOrigin = "",
        netQuantity = "50 g",
        mrp = "₹ 120.00",
        mrpTaxInclusive = true,
        mfgDate = "",
        expiryDate = "08/2026",
        consumerCare = "",
        fssaiLicNo = "10018012000551",
        vegetarianStatus = "100% Vegetarian",
        batchNumber = "CN-BAR-11",
        barcodeValue = "8901001801200",
        barcodeFormat = "EAN-13 GTIN Barcode",
        glareDetected = false,
        surfaceCurvatureScore = 8,
        ocrConfidence = 85,
        nutritionalFacts = NutritionalFacts(
            calories = "510 kcal",
            carbohydrates = "42.0 g",
            sugar = "22.0 g",
            addedSugar = "18.0 g",
            protein = "10.2 g",
            totalFat = "28.0 g",
            saturatedFat = "14.5 g",
            transFat = "0.0 g",
            cholesterol = "0 mg",
            sodium = "180 mg",
            vitaminsAndMinerals = listOf("Magnesium (110 mg)", "Iron (2.8 mg)"),
            servingSize = "Per 100 g"
        ),
        ingredientAnalysis = IngredientAnalysis(
            fullIngredientsText = "Raw Cacao Paste, Cashew Paste, High Fructose Syrup, Cocoa Butter, Synthetic Emulsifier (INS 476 PGPR), Artificial Vanillin.",
            beneficialIngredients = listOf(
                AdditiveInfo(
                    code = "RAW-CACAO",
                    name = "Stoneground Organic Raw Cacao",
                    category = "Antioxidant Polyphenol",
                    isHarmful = false,
                    description = "Rich in natural cocoa flavanols supporting cardiovascular circulation.",
                    fssaiStatus = "FSSAI Approved Food Ingredient"
                )
            ),
            harmfulOrCautionaryAdditives = listOf(
                AdditiveInfo(
                    code = "INS 476",
                    name = "Polyglycerol Polyricinoleate (PGPR / E476)",
                    category = "Synthetic Emulsifier",
                    isHarmful = true,
                    description = "Synthetic chocolate emulsifier derived from castor bean oil. Excessive intake causes liver enlargement in animal trials.",
                    fssaiStatus = "FSSAI Restricted Emulsifier"
                ),
                AdditiveInfo(
                    code = "HFCS-SUGAR",
                    name = "High Fructose Corn Syrup",
                    category = "Refined Artificial Sweetener",
                    isHarmful = true,
                    description = "Causes rapid insulin spikes and hepatic fat accumulation.",
                    fssaiStatus = "FSSAI HFSS Added Sugar Warning (>10% Energy)"
                )
            ),
            fssaiHfssAlerts = listOf(
                "HIGH ADDED SUGAR WARNING (18g Added Sugar / 100g)",
                "SYNTHETIC EMULSIFIER ALERT (INS 476 PGPR)"
            ),
            safetyScoreRatio = 52,
            safetyRatioLabel = "52:48 High Sugar & Emulsifier Caution Ratio",
            safetyTier = "MODERATE RISK - ADDED SUGAR & SYNTHETIC EMULSIFIER"
        ),
        labVerification = FssaiLabVerification(
            labName = "NABL Accredited Food Analytical Laboratory #TC-6019",
            reportNo = "NFL/2026/CN-8802",
            testingDate = "28 February 2026",
            overallVerificationStatus = "VERIFIED ACCURATE WITHIN FSSAI ±20% TOLERANCE LIMIT",
            isVerifiedMatchesLab = true,
            averageVariance = "1.5% Variance (Within Permitted ±20% FSSAI Limit)",
            labNutrientGrid = listOf(
                NutrientLabComparison("Energy (Calories)", "510 kcal", "25.5% RDA", "505 kcal", -0.9, true),
                NutrientLabComparison("Protein", "10.2 g", "18.8% RDA", "10.3 g", +0.9, true),
                NutrientLabComparison("Total Carbohydrates", "42.0 g", "32.3% RDA", "41.5 g", -1.2, true),
                NutrientLabComparison("Total Sugars", "22.0 g", "44.0% RDA", "22.4 g", +1.8, true),
                NutrientLabComparison("Total Fat", "28.0 g", "41.7% RDA", "27.8 g", -0.7, true),
                NutrientLabComparison("Saturated Fat", "14.5 g", "65.9% RDA", "14.6 g", +0.6, true),
                NutrientLabComparison("Sodium", "180 mg", "9.0% RDA", "176 mg", -2.2, true)
            )
        )
    )

    val sampleDesiDahiFields = ExtractedFields(
        commodityName = "Fresh Creamy Desi Dahi",
        manufacturerName = "Mother Dairy Fruit & Vegetable Pvt Ltd",
        manufacturerAddress = "Patparganj, Delhi - 110092",
        countryOfOrigin = "India",
        netQuantity = "400 g",
        mrp = "₹ 45.00",
        mrpTaxInclusive = true,
        mfgDate = "04/2026",
        expiryDate = "04/2026",
        consumerCare = "consumer.care@motherdairy.com | 1800-180-1018",
        fssaiLicNo = "10014011001895",
        vegetarianStatus = "100% Vegetarian",
        batchNumber = "MD-DH-882",
        barcodeValue = "8901001401100",
        barcodeFormat = "EAN-13 GTIN Barcode",
        glareDetected = false,
        surfaceCurvatureScore = 15,
        ocrConfidence = 98,
        nutritionalFacts = NutritionalFacts(
            calories = "60 kcal",
            carbohydrates = "4.8 g",
            sugar = "4.8 g",
            addedSugar = "0.0 g",
            protein = "4.5 g",
            totalFat = "3.1 g",
            saturatedFat = "2.0 g",
            transFat = "0.0 g",
            cholesterol = "8 mg",
            sodium = "50 mg",
            vitaminsAndMinerals = listOf("Calcium (150 mg)", "Vitamin B12 (0.8 mcg)", "L. Acidophilus Probiotics"),
            servingSize = "Per 100 g"
        ),
        ingredientAnalysis = IngredientAnalysis(
            fullIngredientsText = "Pasteurized Toned Milk, Active Live Starter Cultures (Lactobacillus Bulgaricus, Streptococcus Thermophilus).",
            beneficialIngredients = listOf(
                AdditiveInfo(
                    code = "LIVE-PROBIOTIC",
                    name = "Active Live Probiotic Cultures",
                    category = "Gut Microbiome Bio-Culture",
                    isHarmful = false,
                    description = "Populates gut flora, improves nutrient absorption, and boosts immune system.",
                    fssaiStatus = "FSSAI Probiotic Category Approved"
                )
            ),
            harmfulOrCautionaryAdditives = emptyList(),
            fssaiHfssAlerts = emptyList(),
            safetyScoreRatio = 98,
            safetyRatioLabel = "98:2 Pure Natural Probiotic Ratio",
            safetyTier = "VERY SAFE & PURE (FSSAI PROBIOTIC GRADE)"
        ),
        labVerification = FssaiLabVerification(
            labName = "FSSAI Central Food Testing Laboratory / NABL #TC-1001",
            reportNo = "NFL/2026/MD-10088",
            testingDate = "01 April 2026",
            overallVerificationStatus = "100% VERIFIED BY FSSAI CENTRAL LAB - MATCHES CERTIFIED TEST REPORT",
            isVerifiedMatchesLab = true,
            averageVariance = "0.5% Variance (Within Permitted ±20% FSSAI Limit)",
            labNutrientGrid = listOf(
                NutrientLabComparison("Energy (Calories)", "60 kcal", "3.0% RDA", "60.2 kcal", +0.3, true),
                NutrientLabComparison("Protein", "4.5 g", "8.3% RDA", "4.52 g", +0.4, true),
                NutrientLabComparison("Total Carbohydrates", "4.8 g", "3.7% RDA", "4.78 g", -0.4, true),
                NutrientLabComparison("Total Sugars", "4.8 g", "9.6% RDA", "4.81 g", +0.2, true),
                NutrientLabComparison("Total Fat", "3.1 g", "4.6% RDA", "3.09 g", -0.3, true),
                NutrientLabComparison("Saturated Fat", "2.0 g", "9.1% RDA", "2.01 g", +0.5, true),
                NutrientLabComparison("Sodium", "50 mg", "2.5% RDA", "49.5 mg", -1.0, true)
            )
        )
    )

    fun createInitialScans(): List<ProductScan> {
        val scan1 = ProductScan(
            id = "SCAN-1001",
            productName = "Urban Platter Oat Barista",
            brandName = "Urban Platter",
            category = "Plant-Based Beverage",
            imageResId = R.drawable.img_oat_milk,
            rawOcrText = """
                Urban Platter Oat Barista Milk
                Mfg by: Urban Platter Foods Pvt Ltd, Plot 18 TTC Ind Area, Mahape, Navi Mumbai - 400710
                Net Qty: 1000 ml
                MRP ₹ 325.00 (Inclusive of all taxes)
                Mfg Date: 02/2026
                FSSAI Lic No: 11517018000624
                Consumer Care: support@urbanplatter.in | 1800-890-1212
                Country of Origin: India
                100% Vegetarian (Green Dot)
                Nutritional Values per 100ml: Energy 140 kcal, Protein 3.2g, Carbs 18.5g, Sugar 2.0g, Fat 4.8g, Sodium 110mg
            """.trimIndent(),
            extractedFields = sampleOatMilkFields,
            complianceResult = LegalMetrologyRuleEngine.evaluateCompliance(
                sampleOatMilkFields,
                "Urban Platter Oat Barista Mfg by Urban Platter Net Qty 1000 ml MRP ₹ 325.00 (Inclusive of all taxes) Mfg Date 02/2026 FSSAI Lic No 11517018000624 support@urbanplatter.in Country of Origin India Made in India 100% Vegetarian"
            )
        )

        val scan2 = ProductScan(
            id = "SCAN-1002",
            productName = "Desi Honey Oat Granola",
            brandName = "VedaPure Naturals",
            category = "Breakfast Cereals",
            imageResId = R.drawable.img_granola,
            rawOcrText = """
                VedaPure Desi Honey Oat Granola
                Rolled Avena oats, Nilgiri forest raw honey
                Mfg by: VedaPure Organics Pvt Ltd, Nilgiri Estate, Coonoor Road, Ooty, Tamil Nadu - 643001
                Net Quantity: 350 g
                MRP ₹ 499.00 (Inclusive of all taxes)
                Mfg Date: 03/2026
                FSSAI Lic No: 12421003000882
                Consumer Care: care@vedapure.in | 1800-112-8800
                Made in India
                Nutritional Values per 100g: Energy 380 kcal, Protein 12.0g, Carbs 54.0g, Sugar 8.5g, Fat 11.2g, Sodium 45mg
            """.trimIndent(),
            extractedFields = sampleGranolaFields,
            complianceResult = LegalMetrologyRuleEngine.evaluateCompliance(
                sampleGranolaFields,
                "VedaPure Desi Honey Oat Granola Mfg by VedaPure Organics Net Quantity 350 g MRP ₹ 499.00 (Inclusive of all taxes) Mfg Date 03/2026 FSSAI Lic No 12421003000882 care@vedapure.in Made in India"
            )
        )

        val scan3 = ProductScan(
            id = "SCAN-1003",
            productName = "Crispy Roasted Makhana",
            brandName = "Snacko Foods",
            category = "Packaged Snacks",
            imageResId = R.drawable.img_makhana,
            rawOcrText = """
                Snacko Roasted Crispy Makhana Fox Nuts
                Mfg by: Snacko Foods, Delhi
                Net Qty: approx 200g
                MRP ₹ 180.00
                Mfg Date: 01/2026
                FSSAI Lic: 10020011009922
                Ingredients: Popped Lotus Seeds, Palm Oil, Salt, Preservative E211 Sodium Benzoate, INS 320 BHA
                Nutritional Values per 100g: Energy 440 kcal, Protein 8.5g, Carbs 62.0g, Sugar 1.5g, Fat 18.5g, Sodium 680mg
            """.trimIndent(),
            extractedFields = sampleMakhanaFields,
            complianceResult = LegalMetrologyRuleEngine.evaluateCompliance(
                sampleMakhanaFields,
                "Snacko Roasted Crispy Makhana Fox Nuts Mfg by Snacko Foods Delhi Net Qty approx 200g MRP ₹ 180.00 Mfg Date 01/2026 FSSAI Lic 10020011009922 Preservative E211 BHA INS 320 Palm Oil"
            )
        )

        val scan4 = ProductScan(
            id = "SCAN-1004",
            productName = "Stoneground Cashew Cacao Bar",
            brandName = "ChocoNut Artisanal",
            category = "Confectionery",
            imageResId = R.drawable.img_cashew_bar,
            rawOcrText = """
                ChocoNut Stoneground Cashew & Raw Cacao Bar
                Net Wt: 50 g
                MRP ₹ 120.00 (Inclusive of all taxes)
                FSSAI Lic No: 10018012000551
                Ingredients: Cacao, Cashews, High Fructose Syrup, INS 476 PGPR Emulsifier
                Nutritional Values per 100g: Energy 510 kcal, Protein 10.2g, Carbs 42.0g, Sugar 22.0g, Fat 28.0g, Sodium 180mg
            """.trimIndent(),
            extractedFields = sampleCashewBarFields,
            complianceResult = LegalMetrologyRuleEngine.evaluateCompliance(
                sampleCashewBarFields,
                "ChocoNut Stoneground Cashew Raw Cacao Bar Net Wt 50 g MRP ₹ 120.00 (Inclusive of all taxes) FSSAI Lic No 10018012000551 INS 476 PGPR High Fructose"
            )
        )

        val scan5 = ProductScan(
            id = "SCAN-1005",
            productName = "Fresh Creamy Desi Dahi",
            brandName = "Mother Dairy",
            category = "Dairy Product",
            imageResId = R.drawable.img_desi_dahi,
            rawOcrText = """
                Mother Dairy Fresh Creamy Desi Dahi
                Mfg by: Mother Dairy Fruit & Vegetable Pvt Ltd, Patparganj, Delhi - 110092
                Net Quantity: 400 g
                MRP ₹ 45.00 (Inclusive of all taxes)
                Mfg Date: 04/2026
                FSSAI Lic No: 10014011001895
                Consumer Care: consumer.care@motherdairy.com | 1800-180-1018
                Product of India
                Nutritional Values per 100g: Energy 60 kcal, Protein 4.5g, Carbs 4.8g, Sugar 4.8g, Fat 3.1g, Sodium 50mg
            """.trimIndent(),
            extractedFields = sampleDesiDahiFields,
            complianceResult = LegalMetrologyRuleEngine.evaluateCompliance(
                sampleDesiDahiFields,
                "Mother Dairy Fresh Creamy Desi Dahi Mfg by Mother Dairy Fruit Vegetable Patparganj Delhi Net Quantity 400 g MRP ₹ 45.00 (Inclusive of all taxes) Mfg Date 04/2026 FSSAI Lic No 10014011001895 consumer.care@motherdairy.com Product of India"
            )
        )

        return listOf(scan1, scan2, scan3, scan4, scan5)
    }
}
