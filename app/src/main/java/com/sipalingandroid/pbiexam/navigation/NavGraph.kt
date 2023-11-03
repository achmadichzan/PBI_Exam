package com.sipalingandroid.pbiexam.navigation

import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sipalingandroid.pbiexam.R
import com.sipalingandroid.pbiexam.common.ExamDialog
import com.sipalingandroid.pbiexam.ui.presentation.about.AboutScreen
import com.sipalingandroid.pbiexam.ui.presentation.exam.ExamActivity
import com.sipalingandroid.pbiexam.ui.presentation.exam.ExamScreen
import com.sipalingandroid.pbiexam.ui.presentation.exam.ExamViewModel
import com.sipalingandroid.pbiexam.ui.presentation.home.HomeScreen
import com.sipalingandroid.pbiexam.ui.presentation.instruction.InstructionScreen
import com.sipalingandroid.pbiexam.ui.theme.fontFamily

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    viewModel: ExamViewModel = viewModel()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val context = LocalContext.current

    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = currentRoute != Screen.Exam.route && currentRoute != Screen.About.route,
                enter = slideInVertically(initialOffsetY = { 20 }, animationSpec = tween(500)) + expandVertically(),
                exit = slideOutVertically(targetOffsetY = { 0 }, animationSpec = tween(500)) + shrinkVertically()
            ) {
                BottomNav(navController = navController)
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            AnimatedVisibility(
                visible = currentRoute != Screen.Exam.route && currentRoute != Screen.About.route,
                enter = slideInVertically(initialOffsetY = { 20 }, animationSpec = tween(100)) + expandVertically(),
                exit = slideOutVertically(targetOffsetY = { 20 }, animationSpec = tween(900)) + shrinkVertically()
            ) {
                FloatingActionButton(
                    onClick = { viewModel.onExamClick() },
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(
                        defaultElevation = 4.dp, pressedElevation = 0.dp, focusedElevation = 0.dp, hoveredElevation = 0.dp
                    ),
                    modifier = Modifier
                        .padding(8.dp)
                        .offset(y = 60.dp)
                        .scale(1.2f)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Create,
                        contentDescription = "Exam icon",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        },
        modifier = modifier
            .fillMaxSize()
            .background(Color.Transparent)
            .padding(all = 12.dp),
        containerColor = Color.Transparent,
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (viewModel.isDialogShown) {
                ExamDialog(
                    onDismiss = { viewModel.onDismissDialog() },
                    onConfirm = {
                        val intent = Intent(context, ExamActivity::class.java)
                        context.startActivity(intent)
                    }
                )
            }
        }
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier
                .padding(innerPadding)
                .semantics {
                    testTagsAsResourceId = true
                }
        ) {
            composable(route = Screen.Home.route) {
                HomeScreen(navController = navController)
            }
            composable(route = Screen.Exam.route) {
                ExamScreen()
            }
            composable(route = Screen.Instruction.route) {
                InstructionScreen(navController = navController)
            }
            composable(route = Screen.About.route) {
                AboutScreen(navController = navController)
            }
        }
    }
}

@Composable
private fun BottomNav(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    BottomAppBar(
        modifier = modifier
            .fillMaxWidth(1f)
            .clip(RoundedCornerShape(35))
            .background(Color.Transparent)
//            .graphicsLayer {
//                shape = RoundedCornerShape(35)
//                clip = true
//            }
            .height(82.dp),
        contentColor = Color.Green,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        var clickCount by remember { mutableIntStateOf(0) }
        val context = LocalContext.current
        val maxClick = 6

        val navigationItems = listOf(
            NavigationItem(
                title = stringResource(R.string.menu_home),
                icon = Icons.Default.Home,
                screen = Screen.Home
            ),
            NavigationItem(
                title = stringResource(R.string.menu_instruction),
                icon = Icons.Default.Info,
                screen = Screen.Instruction
            ),
        )

        navigationItems.map { item ->
            NavigationBarItem(
                selected = currentRoute == item.screen.route,
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title,
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                label = {
                    AnimatedVisibility(
                        visible = currentRoute == item.screen.route,
                        enter = slideInVertically(initialOffsetY = { 19 }, animationSpec = tween(500)) + expandVertically(),
                        exit = slideOutVertically(targetOffsetY = { 19 }, animationSpec = tween(500)) + shrinkVertically()
                    ) {
                        Text(
                            text = item.title,
                            fontFamily = fontFamily,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                },
                alwaysShowLabel = currentRoute == item.screen.route,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            inclusive = false
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                    if (clickCount < maxClick) {
                        clickCount++
                        if (clickCount > 5) {
                            Toast.makeText(context, "Gabut bang?", Toast.LENGTH_SHORT).show()
                        }
                    }
                },
            )
        }
    }
}