package com.sipalingandroid.pbiexam.common

import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.Scale
import com.sipalingandroid.pbiexam.R
import com.sipalingandroid.pbiexam.ui.theme.DarkGreen
import com.sipalingandroid.pbiexam.ui.theme.Periwinkle
import com.sipalingandroid.pbiexam.ui.theme.fontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExamDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = {  },
    onConfirm: () -> Unit = {  },
) {
    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Card(
            shape = RoundedCornerShape(15.dp),
            modifier = modifier
                .fillMaxWidth(0.8f),
//                .border(
//                    width = 1.dp,
//                    color = if (isSystemInDarkTheme()) DarkGreen else Periwinkle,
//                    shape = RoundedCornerShape(15.dp)
//                ),
            border = BorderStroke(1.dp, brush = Brush.linearGradient(
                0.0f to Color.Red,
                0.5f to Color.Green,
                1.0f to Color.Blue,
                start = Offset(10.0f, 10.0f),
                end = Offset(1000.0f, 1000.0f)
            ))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    imageVector = Icons.Outlined.Warning,
                    contentDescription = "Warning dialog",
                    colorFilter = ColorFilter.tint(Color.Red),
                    modifier = Modifier.scale(1.5f)
                )
                Text(
                    text = context.getString(R.string.exam_dialog),
                    fontFamily = fontFamily,
                    letterSpacing = 1.sp
                )
                val imageLoader = ImageLoader.Builder(context)
                    .components {
                        if (SDK_INT >= 28) {
                            add(ImageDecoderDecoder.Factory())
                        } else {
                            add(GifDecoder.Factory())
                        }
                    }
                    .build()
                Image(
                    painter = rememberAsyncImagePainter(
                        ImageRequest.Builder(context)
                            .data(data = R.drawable.swipe_up).apply {
                                size(640)
                                scale(Scale.FIT)
                            }.build(),
                        imageLoader = imageLoader
                    ),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth()
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,)
                {
                    OutlinedButton(onClick = { onDismiss() }) {
                        Text(text = context.getString(R.string.cancel), fontFamily = fontFamily)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = { onConfirm() }) {
                        Text(text = context.getString(R.string.yes), fontFamily = fontFamily)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun DialogPreview() {
    ExamDialog()
}