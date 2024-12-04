package com.alavpa.kakebo.ui.components

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.alavpa.kakebo.R

sealed class BottomNavItem(val route: String, val icon: ImageVector, @StringRes val label: Int) {
    data object Income :
        BottomNavItem("income", Icons.AutoMirrored.Filled.ArrowBack, R.string.income)

    data object Outcome :
        BottomNavItem("outcome", Icons.AutoMirrored.Filled.ArrowForward, R.string.outcome)

    data object Statistics : BottomNavItem("statistics", Icons.Default.Person, R.string.statistics)

    companion object {
        val navItems = listOf(Income, Outcome, Statistics)
    }
}