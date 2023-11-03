package com.sipalingandroid.pbiexam.common

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Nightlight
import androidx.compose.material.icons.rounded.DarkMode
import androidx.compose.material.icons.rounded.LightMode
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sipalingandroid.pbiexam.ui.presentation.about.ThemeViewModel
import com.sipalingandroid.pbiexam.util.DataStoreUtil
import kotlinx.coroutines.launch

@Composable
fun ThemeSwitcher(
    modifier: Modifier = Modifier,
    darkTheme: Boolean = false,
    dataStoreUtil: DataStoreUtil,
    themeViewModel: ThemeViewModel = viewModel(),
    size: Dp = 150.dp,
    iconSize: Dp = size / 3,
    padding: Dp = 10.dp,
    borderWidth: Dp = 1.dp,
    parentShape: Shape = CircleShape,
    toggleShape: Shape = CircleShape,
    animationSpec: AnimationSpec<Dp> = tween(durationMillis = 300),
//    onClick: () -> Unit
) {
    val offset by animateDpAsState(
        targetValue = if (darkTheme) 0.dp else size,
        animationSpec = animationSpec, label = ""
    )
    val coroutineScope = rememberCoroutineScope()
    var switchState by remember { themeViewModel.isDarkThemeEnabled }

//    Box(modifier = Modifier
//        .width(size * 2)
//        .height(size)
//        .clip(shape = parentShape)
//        .clickable { onClick() }
//        .background(MaterialTheme.colorScheme.secondaryContainer)
//    ) {
//        Box(
//            modifier = Modifier
//                .size(size)
//                .offset(x = offset)
//                .padding(all = padding)
//                .clip(shape = toggleShape)
//                .background(MaterialTheme.colorScheme.primary)
//        ) {}
        Switch(
            modifier = modifier,
            checked = switchState,
            onCheckedChange = {
                switchState = it

                coroutineScope.launch {
                    dataStoreUtil.saveTheme(it)
                }
            },
            thumbContent = {
                Icon(
                    modifier = Modifier
                        .size(SwitchDefaults.IconSize),
                    imageVector = if (switchState) Icons.Rounded.DarkMode else Icons.Rounded.LightMode,
                    contentDescription = "Switch Icon"
                )
            },
            colors = SwitchDefaults.colors(
                checkedTrackColor = MaterialTheme.colorScheme.primary,
                checkedThumbColor = MaterialTheme.colorScheme.onPrimary,
            ),
        )
//    }

//    Box(modifier = Modifier
//        .width(size * 2)
//        .height(size)
//        .clip(shape = parentShape)
//        .clickable { onClick() }
//        .background(MaterialTheme.colorScheme.secondaryContainer)
//    ) {
//        Box(
//            modifier = Modifier
//                .size(size)
//                .offset(x = offset)
//                .padding(all = padding)
//                .clip(shape = toggleShape)
//                .background(MaterialTheme.colorScheme.primary)
//        ) {}
//        Row(
//            modifier = Modifier
//                .border(
//                    border = BorderStroke(
//                        width = borderWidth,
//                        color = MaterialTheme.colorScheme.primary
//                    ),
//                    shape = parentShape
//                )
//        ) {
//            Box(
//                modifier = Modifier.size(size),
//                contentAlignment = Alignment.Center
//            ) {
//                Icon(
//                    modifier = Modifier.size(iconSize),
//                    imageVector = Icons.Default.Nightlight,
//                    contentDescription = "Theme Icon",
//                    tint = if (darkTheme) MaterialTheme.colorScheme.secondaryContainer
//                    else MaterialTheme.colorScheme.primary
//                )
//            }
//            Box(
//                modifier = Modifier.size(size),
//                contentAlignment = Alignment.Center
//            ) {
//                Icon(
//                    modifier = Modifier.size(iconSize),
//                    imageVector = Icons.Default.LightMode,
//                    contentDescription = "Theme Icon",
//                    tint = if (darkTheme) MaterialTheme.colorScheme.primary
//                    else MaterialTheme.colorScheme.secondaryContainer
//                )
//            }
//        }
//    }
}