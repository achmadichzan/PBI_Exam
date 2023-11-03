package com.sipalingandroid.pbiexam.ui.presentation.instruction

import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.Scale
import coil.size.Size
import com.sipalingandroid.pbiexam.R
import com.sipalingandroid.pbiexam.common.DropdownImage
import com.sipalingandroid.pbiexam.common.InstructionItem
import com.sipalingandroid.pbiexam.navigation.Screen
import com.sipalingandroid.pbiexam.ui.theme.DarkGreen
import com.sipalingandroid.pbiexam.ui.theme.Periwinkle
import com.sipalingandroid.pbiexam.ui.theme.fontFamily

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun InstructionScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        modifier = modifier
            .fillMaxSize()
//            .statusBarsPadding()
//            .navigationBarsPadding()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = { Text(
                    text = stringResource(R.string.instruction),
                    fontFamily = fontFamily,
                ) },
                actions = {
                    IconButton(onClick = { navController.navigate(Screen.About.route) }) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "About Developer",
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(0.9f)
                    .clip(RoundedCornerShape(35)),
                colors = topAppBarColors(
                    containerColor = if (isSystemInDarkTheme()) {
                        MaterialTheme.colorScheme.primaryContainer
                    } else {
                        MaterialTheme.colorScheme.onPrimaryContainer
                    }
                )
            )
        }
    ) { innerPadding ->
        val lazyListState = rememberLazyListState()
        val context = LocalContext.current

        LazyColumn(
            state = lazyListState,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                //                .padding(innerPadding.calculateTopPadding())
                //                .padding(innerPadding.calculateBottomPadding())
                .consumeWindowInsets(innerPadding)
                .testTag("item_list"),
            contentPadding = innerPadding,
        ) {
            item {
                Text(
                    text = context.resources.getString(R.string.information),
                    fontFamily = fontFamily,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
            val items = listOf(
                InstructionItem(
                    text = R.string.first_step,
                    imageResource = R.drawable.login
                ),
                InstructionItem(
                    text = R.string.second_step,
                    imageResource = R.drawable.kerjakan
                ),
                InstructionItem(
                    text = R.string.third_step,
                    imageResource = R.drawable.kerja_soal
                ),
                InstructionItem(
                    text = R.string.fourth_step,
                    imageResource = R.drawable.score
                )
            )
            items.forEach { item ->
                item {
                    val imageLoader = ImageLoader.Builder(context)
                        .components {
                            if (SDK_INT >= 28) {
                                add(ImageDecoderDecoder.Factory())
                            } else {
                                add(GifDecoder.Factory())
                            }
                        }
                        .build()

                    Text(
                        text = stringResource(id = item.text),
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Justify,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )

                    DropdownImage(
                        item = {
                            Image(
                                painter = rememberAsyncImagePainter(
                                    ImageRequest.Builder(context)
                                        .data(data = item.imageResource).apply {
                                            size(Size.ORIGINAL)
                                            scale(Scale.FIT)
                                        }.build(),
                                    imageLoader = imageLoader
                                ),
                                contentDescription = null,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    )
                }
            }
        }
    }
}