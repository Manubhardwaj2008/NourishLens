# 🌿 NourishLens — Legal Metrology & Food Compliance Scanner

[![Android](https://img.shields.io/badge/Platform-Android_31%2B-264E36?style=flat&logo=android)](https://developer.android.com)
[![Kotlin](https://img.shields.io/badge/Language-Kotlin_2.2.10-0D3721?style=flat&logo=kotlin)](https://kotlinlang.org)
[![Compose](https://img.shields.io/badge/UI-Jetpack_Compose_Material3-264E36?style=flat&logo=jetpackcompose)](https://developer.android.com/jetpack/compose)
[![ML Kit](https://img.shields.io/badge/AI Engine-Google_ML_Kit_OCR_%26_Barcode-D97746?style=flat&logo=google)](https://developers.google.com/ml-kit)
[![CameraX](https://img.shields.io/badge/Camera-CameraX_1.3.4-0D3721?style=flat)](https://developer.android.com/training/camerax)

> **Software System to Check Compliance of Packaged Commodities under Legal Metrology Rules & FSSAI Standards**
> 
> Packaged goods in India must show mandatory declarations such as Net Quantity, Maximum Retail Price (MRP), Date of Manufacture, Country of Origin, and Manufacturer details as per the **Legal Metrology (Packaged Commodities) Rules, 2011** and **FSSAI Food Safety and Standards Regulations, 2020**. Inspecting these by hand is labor-intensive and prone to error. **NourishLens** automates label inspection using on-device neural OCR, barcode scanning, a multi-tier field extraction pipeline, and a deterministic compliance rule checker.

---

## 🚀 Key Features & Architectural Capabilities

### 1. 📜 Legal Metrology Compliance Rule Engine
Evaluates every scanned commodity against statutory requirements:
* **Rule 6(1)(a)**: Full name and complete registered postal address of Manufacturer / Packer / Importer.
* **Rule 6(1)(aa)**: Mandatory Country of Origin declaration (*e.g., "Made in India"*).
* **Rule 6(1)(b)**: Common or generic name of commodity.
* **Rule 6(1)(c)**: Declared Net Quantity using legal standard units (`g`, `kg`, `ml`, `L`, `N`). Detects illegal non-standard units (*e.g. "500 gm", "approx 200g"*).
* **Rule 6(1)(d)**: Month & Year of Manufacture / Packing.
* **Rule 6(1)(e)**: Maximum Retail Price (MRP) & mandatory `"(Inclusive of all taxes)"` declaration.
* **Rule 6(1)(f)**: Consumer Care Helpline telephone and email contact details.
* **FSSAI Regulation 2.2**: Valid 14-digit FSSAI License Number & Vegetarian (Green Dot) / Non-Veg symbol.

---

### 2. 🔍 Precision 5-Tier Stamped MRP & Date Extractor
Designed specifically for real-world Indian packaged goods where prices and dates are stamped in dot-matrix ink or light fonts over glossy/metallic packaging:
* **5-Tier MRP Engine (`PriceAndDateParser.kt`)**: Parses `MRP Rs. 150.00`, `M.R.P. : ₹ 250`, `MAX RETAIL PRICE RS 45.00`, standalone `Rs 50`, `RS 20/-`, and digits adjacent to tax inclusion statements (`INCL OF ALL TAXES 299`).
* **5-Tier Manufacturing Date Engine**: Parses stamped date formats across dot-separated (`15.03.2026`), slash-separated (`03/26`), hyphenated (`15-03-26`), or month abbreviations (`MAR 2026`), as well as unspaced sequences (`MFG15/03/26`).
* **Adaptive Contrast Preprocessor (`ImagePreprocessor.kt`)**: Automatically boosts contrast and sharpens light-colored / faint dot-matrix text before OCR processing.

---

### 3. 📷 Dual On-Device OCR & Barcode / QR Code Scanner
* **Google ML Kit Text Recognition**: Local, 100% offline neural OCR processing.
* **Google ML Kit Barcode Scanning API**: Decodes 1D barcodes (*EAN-13, EAN-8, UPC-A, Code 128*) and 2D QR codes (*FSSAI QR Codes, GS1 Digital Link*).
* **GS1 GTIN Country Lookup (`BarcodeDecoder.kt`)**: Decodes GTIN prefixes (*e.g. 890 $\rightarrow$ "India - GS1 Bharat Registered"*), GS1 Application Identifiers (AIs), embedded lot/batch IDs, retail price, and FSSAI QR parameters.

---

### 4. 🧪 FSSAI Nutrition Panel & Certified Lab Verification Grid
* **FSSAI Recommended Dietary Allowance (RDA %)**: Calculates adult % Daily RDA (based on FSSAI 2000 kcal reference diet) for *Energy, Protein, Total Carbohydrates, Total Sugars, Added Sugars, Total Fat, Saturated Fat, and Sodium*.
* **National Food Testing Laboratory (NFL / NABL) Comparison**: Cross-checks scanned label values against certified laboratory test reports.
* **FSSAI Regulation 2.2.2(4) ±20% Tolerance Verification**: Flags misbranding alerts if lab test results exceed the statutory ±20% legal variance limit.

---

### 5. 🛡️ Chemical Additive & HFSS Safety Analysis
* **Additives & Chemical Inspector**: Identifies synthetic preservatives (*INS 211 Sodium Benzoate*), synthetic antioxidants (*INS 320 BHA*), synthetic emulsifiers (*INS 476 PGPR*), and industrial hydrogenated fats vs beneficial superfoods (*Avena Oats, Raw Forest Honey, Live Probiotics*).
* **FSSAI HFSS Warnings**: Raises High Fat, Sugar, and Salt alerts (*Sodium > 600mg/100g*, *Saturated Fat > 5g/100g*, *Added Sugar > 10g/100g*).
* **Clean-to-Chemical Safety Ratio**: Displays a statistical progress data line (*e.g. 95:5 Clean Safety Ratio*).

---

### 6. ⏰ Expiration Audit & Android Notification Alerts
* **Shelf Life Countdown**: Calculates product freshness status (*Fresh & Safe*, *Near Expiry Warning*, or *Expired Product Alert*).
* **Android Notification System (`NotificationHelper.kt`)**: Triggers high-priority Android system notifications when scanning near-expiry or expired items.

---

### 7. 📁 Device Storage Files Processing Program
* Supports picking image files directly from Android System Storage (*Downloads, Internal Storage, SD Card, Pictures*) via `ActivityResultContracts.OpenDocument()`.

---

## 📱 App Screens & Navigation (Botanical Hearth Theme)

| Screen | Description |
| :--- | :--- |
| **Inspector Authentication (`LoginScreen.kt`)** | Portal login with Officer Email (`natwar.goyal@nourishlens.gov.in`), Password, and Regional Station selection (*New Delhi Region #04*). |
| **Live Scanner Lens (`LiveScannerScreen.kt`)** | Real-time CameraX viewfinder, optical reticle, flash toggle, device storage file selector, and 5 pre-loaded benchmark test products. |
| **Product Scorecard Dossier (`ProductScorecardScreen.kt`)** | Complete legal metrology checklist, FSSAI validation test score (0–100), nutrition RDA table, lab verification grid, and decoded barcode card. |
| **Swaps & Seller Remediation (`CleanSwapsScreen.kt`)** | Official Legal Metrology Section 36 remediation notice generator and certified compliant alternative products. |
| **Audit History Log (`PantryHistoryScreen.kt`)** | Audit history log, status filter chips (*All, Compliant, Violations*), search bar, pass rate analytics, and PDF/JSON report exporter. |
| **Officer Profile (`ProfileScreen.kt`)** | Senior Officer **Natwar Goyal** profile badge, regional jurisdiction metrics, system configuration, and account sign-out. |

---

## 🛠️ Project Tech Stack

* **Language**: Kotlin 2.2.10
* **UI Framework**: Jetpack Compose with Material 3
* **Color Palette**: Botanical Hearth Palette (*Forest Botanical `#0D3721`, Warm Terracotta `#994619`, Warm Oat `#FAF7F2`, Milled Linen `#F3EFE6`, Herbal Mint `#E8F2EA`*)
* **CameraX Engine**: `androidx.camera:camera-camera2:1.3.4`, `androidx.camera:camera-lifecycle:1.3.4`, `androidx.camera:camera-view:1.3.4`
* **AI & Vision ML Kit**:
  * `com.google.mlkit:text-recognition:16.0.1` (On-Device Neural OCR)
  * `com.google.mlkit:barcode-scanning:17.3.0` (On-Device Barcode & QR Code Decoder)
* **Image Loading**: Coil Compose `io.coil-kt:coil-compose:2.6.0`

---

## ⚙️ Building & Running

### Prerequisites
* Android Studio Ladybug (2024.2.1) or newer
* Android SDK 35 / 37
* Device or Emulator running Android 12 (API level 31) or higher with camera support

### Steps
1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/NourishLens.git
   cd NourishLens
   ```
2. Open the project in **Android Studio**.
3. Sync Gradle dependencies (`Gradle Sync`).
4. Build and run the app on an Android device or emulator:
   ```bash
   ./gradlew assembleDebug
   ```

---

## ⚖️ Legal & Regulatory Compliance References

* **Legal Metrology Act, 2009 (Section 36)**
* **Legal Metrology (Packaged Commodities) Rules, 2011 (Rule 6 & Rule 12)**
* **FSSAI Food Safety and Standards (Packaging and Labelling) Regulations**
* **FSSAI Food Safety and Standards (Labelling and Display) Regulations, 2020**
* **GS1 General Specifications & GTIN Allocation Rules**

---

### 👨‍✈️ Lead Enforcement Officer
**Officer Natwar Goyal**  
*Senior Legal Metrology & FSSAI Enforcement Officer*  
Regional Inspection Station #04, New Delhi, India
