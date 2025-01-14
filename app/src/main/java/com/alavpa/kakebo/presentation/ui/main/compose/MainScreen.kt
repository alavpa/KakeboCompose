package com.alavpa.kakebo.presentation.ui.main.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.alavpa.kakebo.presentation.components.BottomNavItem
import com.alavpa.kakebo.presentation.ui.lines.AddLinesViewModel
import com.alavpa.kakebo.presentation.ui.lines.compose.AddLinesScreen
import com.alavpa.kakebo.presentation.ui.statistics.StatisticsViewModel
import com.alavpa.kakebo.presentation.ui.statistics.compose.StatisticsScreen

@Composable
fun MainScreen() {
    val snackbarHostState = remember { SnackbarHostState() }
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = { BottomBar(navController) },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Outcome.route,
            modifier = Modifier.padding(paddingValues = paddingValues)
        ) {
            composable(BottomNavItem.Income.route) {
                IncomeScreenContainer(snackbarHostState = snackbarHostState)
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
private fun BottomBar(navController: NavController) {
    NavigationBar {
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route
        BottomNavItem.navItems.forEach { navigationItem ->
            NavigationBarItem(
                selected = currentRoute == navigationItem.route,
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
                    navController.navigate(navigationItem.route) {
                        val destination = navController.graph.findStartDestination().id
                        popUpTo(destination) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
private fun OutcomeScreenContainer(
    viewModel: AddLinesViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState
) {
    val state by viewModel.state.collectAsState()
    AddLinesScreen(
        state = state,
        event = viewModel.events,
        isIncome = false,
        userInteractions = viewModel
    ) { successMessage ->
        snackbarHostState.showSnackbar(message = successMessage)
    }
}

@Composable
private fun IncomeScreenContainer(
    viewModel: AddLinesViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState
) {
    val state by viewModel.state.collectAsState()
    AddLinesScreen(
        state = state,
        event = viewModel.events,
        isIncome = true,
        userInteractions = viewModel
    ) { successMessage ->
        snackbarHostState.showSnackbar(message = successMessage)
    }
}

@Composable
fun StatisticsScreenContainer(viewModel: StatisticsViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    StatisticsScreen(state, viewModel)
}
