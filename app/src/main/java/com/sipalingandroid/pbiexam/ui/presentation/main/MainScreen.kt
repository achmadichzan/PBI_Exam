package com.sipalingandroid.pbiexam.ui.presentation.main

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sipalingandroid.pbiexam.navigation.NavGraph
import com.sipalingandroid.pbiexam.ui.presentation.about.ThemeViewModel

@Composable
fun MainScreen(themeViewModel: ThemeViewModel = viewModel()) {
    NavGraph()
}