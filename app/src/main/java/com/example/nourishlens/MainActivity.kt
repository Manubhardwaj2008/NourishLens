package com.example.nourishlens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nourishlens.data.SampleData
import com.example.nourishlens.ui.components.NourishLensBottomNavigation
import com.example.nourishlens.ui.components.NourishLensTopHeader
import com.example.nourishlens.ui.screens.*
import com.example.nourishlens.ui.theme.NourishLensTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NourishLensTheme {
                NourishLensApp()
            }
        }
    }
}

@Composable
fun NourishLensApp() {
    var isLoggedIn by remember { mutableStateOf(false) }
    var inspectorName by remember { mutableStateOf("Natwar Goyal (Senior Inspector)") }
    var scansList by remember { mutableStateOf(SampleData.createInitialScans()) }
    var currentScan by remember { mutableStateOf(scansList.first()) }
    var selectedTab by remember { mutableIntStateOf(0) }

    val tabTitles = listOf("Scanner", "Product Dossier", "Swaps & Remediation", "Audit Log", "Inspector Profile")

    if (!isLoggedIn) {
        LoginScreen(
            onLoginSuccess = { name, station ->
                inspectorName = name
                isLoggedIn = true
            },
            onGuestLogin = {
                isLoggedIn = true
            }
        )
    } else {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                NourishLensTopHeader(
                    currentTabTitle = tabTitles.getOrElse(selectedTab) { "Scanner" },
                    onProfileClick = { selectedTab = 4 }
                )
            },
            bottomBar = {
                NourishLensBottomNavigation(
                    selectedTab = selectedTab,
                    onTabSelected = { newTab -> selectedTab = newTab }
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.TopCenter
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .widthIn(max = 600.dp)
                ) {
                    when (selectedTab) {
                        0 -> LiveScannerScreen(
                            currentScan = currentScan,
                            onScanSelected = { selected ->
                                currentScan = selected
                                if (scansList.none { it.id == selected.id }) {
                                    scansList = listOf(selected) + scansList
                                }
                            },
                            onNavigateToDossier = { selectedTab = 1 }
                        )
                        1 -> ProductScorecardScreen(
                            currentScan = currentScan,
                            onNavigateToSwaps = { selectedTab = 2 }
                        )
                        2 -> CleanSwapsScreen(
                            currentScan = currentScan,
                            onSelectSwapProduct = { swap ->
                                currentScan = swap
                                selectedTab = 1
                            }
                        )
                        3 -> PantryHistoryScreen(
                            scansList = scansList,
                            onSelectScan = { scan ->
                                currentScan = scan
                                selectedTab = 1
                            }
                        )
                        4 -> ProfileScreen(
                            onResetData = {
                                scansList = SampleData.createInitialScans()
                                currentScan = scansList.first()
                            },
                            onSignOut = {
                                isLoggedIn = false
                            }
                        )
                    }
                }
            }
        }
    }
}
