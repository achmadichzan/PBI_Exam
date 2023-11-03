package com.sipalingandroid.pbiexam.common

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sipalingandroid.pbiexam.R
import com.sipalingandroid.pbiexam.ui.theme.fontFamily

@Composable
fun DropdownImage(
    item: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize(),
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.Gray, RoundedCornerShape(35))
                .clickable {
                    expanded = !expanded
                }
                .height(30.dp)
                .noRippleClickable { expanded = !expanded }
                .testTag("first")
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                when {
                    !expanded -> {
                        Text(text = stringResource(R.string.click_this),
                            fontFamily = fontFamily,
                            fontSize = 10.sp
                        )
                        Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = null)
                    }
                    expanded -> {
                        Text(
                            text = stringResource(R.string.click_again),
                            fontFamily = fontFamily,
                            fontSize = 10.sp
                        )
                        Icon(imageVector = Icons.Default.KeyboardArrowUp, contentDescription = null)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        AnimatedVisibility(
            visible = expanded,
            enter = slideInVertically(initialOffsetY = { -20 }, animationSpec = tween(900)) + expandVertically(),
            exit = slideOutVertically(targetOffsetY = { -20 }, animationSpec = tween(900)) + shrinkVertically()
        ) {
            item()
        }
    }
}

fun Modifier.noRippleClickable(
    onClick: () -> Unit
) = composed {
    this.clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() }
    ) {
        onClick()
    }
}