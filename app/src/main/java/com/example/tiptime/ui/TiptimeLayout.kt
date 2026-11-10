package com.example.tiptime.ui

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.tiptime.R
import com.example.tiptime.ui.components.TipTimeTopBar
import com.example.tiptime.ui.screens.TipTimeResultScreen
import com.example.tiptime.ui.screens.TipTimeStartScreen
import com.example.tiptime.ui.viewmodels.TipViewModel


@Composable
fun TipTimeLayout(
    tipViewModel : TipViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val currentScreen = Routes.valueOf(
        backStackEntry?.destination?.route ?: Routes.Start.name
    )
    val tipUiState by tipViewModel.uiState.collectAsState()

    val context = LocalContext.current //ADD THIS

    Scaffold(
        topBar = {
            TipTimeTopBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
                showShare = currentScreen == Routes.TipResult,
                onShareClicked = {
                    createShareIntent(context, tip = tipUiState.tip, total = tipUiState.total)
                }
            )
        }
    ) { innerPadding ->


        NavHost(
            navController = navController,
            startDestination = Routes.Start.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable( route = Routes.Start.name) {
                TipTimeStartScreen(
                    tipViewModel = tipViewModel,
                    onNextButtonClicked = { navController.navigate(Routes.TipResult.name) },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(40.dp)
                )

            }
            composable(route = Routes.TipResult.name) {
                TipTimeResultScreen(
                    tipViewModel = tipViewModel,
                    onBackButtonClicked = { navController.navigateUp() },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(40.dp)
                )
            }
        }
    }
}

fun createShareIntent(context: Context, tip: String, total: String) {
    val shareText = context.getString(R.string.tip_amount_total_bill, tip, total)
    // Create an ACTION_SEND implicit intent with order details in the intent extras
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.your_tip))
        putExtra(Intent.EXTRA_TEXT, shareText)
    }
    context.startActivity(
        Intent.createChooser(
            intent,
            context.getString(R.string.your_tip)
        )
    )
}
