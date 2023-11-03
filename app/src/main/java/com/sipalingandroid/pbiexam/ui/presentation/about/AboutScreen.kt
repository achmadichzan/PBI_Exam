package com.sipalingandroid.pbiexam.ui.presentation.about

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.sipalingandroid.pbiexam.R
import com.sipalingandroid.pbiexam.common.Socials
import com.sipalingandroid.pbiexam.common.ThemeSwitcher
import com.sipalingandroid.pbiexam.common.listSocials
import com.sipalingandroid.pbiexam.ui.theme.PBIExamTheme
import com.sipalingandroid.pbiexam.ui.theme.fontFamily
import com.sipalingandroid.pbiexam.util.DataStoreUtil

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    themeViewModel: ThemeViewModel = viewModel(),
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val context = LocalContext.current

    val dataStoreutil = DataStoreUtil(context)
    val systemTheme = when (context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
        Configuration.UI_MODE_NIGHT_YES -> { true }
        Configuration.UI_MODE_NIGHT_NO -> { false }
        else -> { false }
    }

    val theme = dataStoreutil.getTheme(systemTheme).collectAsState(initial = systemTheme)

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(
                    text = stringResource(R.string.about_me),
                    fontFamily = fontFamily,
                ) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = "Go back",
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
                        MaterialTheme.colorScheme.onSecondaryContainer
                    } else {
                        MaterialTheme.colorScheme.secondaryContainer
                    }
                ),
                actions = {
                    PBIExamTheme(
                        darkTheme = theme.value,
                        content = {
                            ThemeSwitcher(
                                dataStoreUtil = dataStoreutil,
                                themeViewModel = themeViewModel,
                                size = 40.dp
                            )
                        }
                    )
                }
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.developer),
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(16.dp))

                Image(
                    modifier = Modifier
                        .size(130.dp),
                    painter = painterResource(R.drawable.achmad_ichzan),
                    contentDescription = "Developer picture"
                )
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = stringResource(R.string.achmad_ichzan),
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp

                )
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = stringResource(R.string.socials),
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp
                )

                LazyHorizontalGrid(
                    rows = GridCells.Adaptive(80.dp),
                    modifier = Modifier
                        .height(100.dp),
                ) {
                    items(listSocials) { socialItem ->
                        SocialItem(socials = socialItem, onClick = { url ->
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                            context.startActivity(intent)
                        })
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SocialItem(
    socials: Socials,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Card(
        modifier = modifier
            .fillMaxHeight()
            .padding(horizontal = 5.dp, vertical = 8.dp)
            .width(75.dp)
            .shadow(2.dp, RoundedCornerShape(8.dp)),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
        onClick = {
            val url = getSocialUrl(context, socials.name)
            onClick(url)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(socials.icon),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(38.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(socials.name),
                fontFamily = fontFamily,
                fontSize = 12.sp,
            )
        }
    }
}

private fun getSocialUrl(context: Context, socialName: Int): String {
    return when (socialName) {
        R.string.linkedin -> context.getString(R.string.linkedin_link)
        R.string.instagram -> context.getString(R.string.instagram_link)
        R.string.github -> context.getString(R.string.github_link)
        R.string.solos -> context.getString(R.string.solos_link)
        else -> ""
    }
}