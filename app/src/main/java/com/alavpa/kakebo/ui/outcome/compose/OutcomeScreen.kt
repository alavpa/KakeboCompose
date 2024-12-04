package com.alavpa.kakebo.ui.outcome.compose

import MultiPreview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.alavpa.kakebo.R
import com.alavpa.kakebo.ui.components.CategoryPill
import com.alavpa.kakebo.ui.components.VerticalSpacer
import com.alavpa.kakebo.ui.components.Pad
import com.alavpa.kakebo.ui.outcome.OutcomeState
import com.alavpa.kakebo.ui.outcome.OutcomeUserInteractions
import com.alavpa.kakebo.ui.theme.KakeboTheme

@Composable
fun OutcomeScreen(
    state: OutcomeState,
    userInteractions: OutcomeUserInteractions,
    snackbarHostState: SnackbarHostState
) {
    val successMessage = stringResource(R.string.outcome_success_message)
    LaunchedEffect(state.showSuccess) {
        if (state.showSuccess) {
            val result = snackbarHostState.showSnackbar(message = successMessage)
            if (result == SnackbarResult.Dismissed) {
                userInteractions.onMessageDismissed()
            }
        }
    }
    Box(Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(KakeboTheme.space.horizontal)
        ) {
            VerticalSpacer(height = KakeboTheme.space.vertical)
            LazyRow(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                items(state.categories) { category ->
                    CategoryPill(category.first, category.second) {
                        userInteractions.onClickCategory(category)
                    }
                }
            }
            VerticalSpacer(height = KakeboTheme.space.vertical)
            Text(text = state.formattedText, fontSize = 56.sp)
            Pad(userInteractions)
            VerticalSpacer(height = KakeboTheme.space.vertical)
            TextField(
                value = state.description,
                onValueChange = { userInteractions.onDescriptionChanged(it) },
                label = { Text(stringResource(R.string.concept)) },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                maxLines = 5
            )
        }
    }
}

@MultiPreview
@Composable
fun OutcomeScreenPreview() {
    KakeboTheme {
        OutcomeScreen(
            OutcomeState.INITIAL,
            OutcomeUserInteractions.Stub(),
            remember { SnackbarHostState() }
        )
    }
}