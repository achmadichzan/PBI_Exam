package com.sipalingandroid.pbiexam.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

//@Composable
//fun CustomFAB(
//    onClick: @Composable () -> Unit,
//    modifier: Modifier = Modifier,
//    shape: Shape = FloatingActionButtonDefaults.shape,
//    containerColor: Color = FloatingActionButtonDefaults.containerColor,
//    contentColor: Color = contentColorFor(containerColor),
//    elevation: FloatingActionButtonElevation = FloatingActionButtonDefaults.elevation(),
//    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
//    content: @Composable () -> Unit,
//) {
//    Box(
//        modifier = modifier
//            .size(50.dp)
//            .background(MaterialTheme.colorScheme.primary)
//            .clickable { onClick() },
//        contentAlignment = Alignment.Center
//    ) {
//        Icon(
//            imageVector = Icons.Filled.Create,
//            contentDescription = "Exam icon",
//            tint = MaterialTheme.colorScheme.primary
//        )
//    }
//}

//@Preview
//@Composable
//fun PrevFAB() {
//    CustomFAB()
//}
