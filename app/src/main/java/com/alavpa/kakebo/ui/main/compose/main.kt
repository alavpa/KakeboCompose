package com.alavpa.kakebo.ui.main.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.alavpa.kakebo.ui.components.BottomNavItem
import com.alavpa.kakebo.ui.components.SnackbarInteractions
import com.alavpa.kakebo.ui.outcome.OutcomeViewModel
import com.alavpa.kakebo.ui.outcome.compose.OutcomeScreen
import com.alavpa.kakebo.ui.theme.KakeboTheme

@Composable
fun MainScreen() {
    val snackbarHostState = remember { SnackbarHostState() }
    var navigationSelectedItem by remember { mutableIntStateOf(1) }
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                BottomNavItem.navItems.forEachIndexed { index, navigationItem ->
                    NavigationBarItem(
                        selected = index == navigationSelectedItem,
                        label = {
                            Text(stringResource(navigationItem.label))
                        },
                        icon = {
                            Icon(
                                navigationItem.icon,
                                contentDescription = stringResource(navigationItem.label)
                            )
                        },
                        onClick = {
                            navigationSelectedItem = index
                            navController.navigate(navigationItem.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Outcome.route,
            modifier = Modifier.padding(paddingValues = paddingValues)
        ) {
            composable(BottomNavItem.Income.route) {
                IncomeScreenContainer()
            }
            composable(BottomNavItem.Outcome.route) {
                OutcomeScreenContainer(snackbarHostState = snackbarHostState)
            }
            composable(BottomNavItem.Statistics.route) {
                StatisticsScreenContainer()
            }
        }
    }
}

@Composable
fun OutcomeScreenContainer(
    viewModel: OutcomeViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState
) {
    val state by viewModel.state.collectAsState()
    OutcomeScreen(state, viewModel) { successMessage ->
        showSnackbarMessage(snackbarHostState, successMessage, viewModel)
    }
}

@Composable
fun IncomeScreenContainer() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("INCOME", style = KakeboTheme.typography.titleLarge)
    }
}

@Composable
fun StatisticsScreenContainer() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("STATISTICS", style = KakeboTheme.typography.titleLarge)
    }
}

private suspend fun showSnackbarMessage(
    snackbarHostState: SnackbarHostState,
    message: String,
    snackbarInteractions: SnackbarInteractions
) {
    val result = snackbarHostState.showSnackbar(message = message)
    if (result == SnackbarResult.Dismissed) {
        snackbarInteractions.onMessageDismissed()
    }
}