package com.sipalingandroid.pbiexam.ui.presentation.exam

import android.content.res.Configuration
import android.graphics.Bitmap
import android.util.Log
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.web.*
import com.sipalingandroid.pbiexam.R
import com.sipalingandroid.pbiexam.common.Urls
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExamScreen(
    modifier: Modifier = Modifier,
    examViewModel: ExamViewModel = viewModel(),
) {
    val url by remember { mutableStateOf(Urls.BASE_URL) }
    val state = rememberWebViewState(url = url)
    val navigator = rememberWebViewNavigator()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        snackbarHost = { SnackbarHost(
            hostState = snackbarHostState
        ) }
    ) { innerPadding ->

        val pullRefreshState = rememberPullRefreshState(
            refreshing = examViewModel.isRefreshing,
            onRefresh = {
                examViewModel.reloadBySwipe { navigator.reload() }
            }
        )

//        val density = LocalDensity.current.density
//        val isLandscape =
//            LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
//        val height = if (isLandscape) 160.dp else 320.dp

        val density = LocalDensity.current.density
        val screenHeightDp = LocalConfiguration.current.screenHeightDp

        val screenDensityCategory = when {
            density <= 0.75 -> DensityQualifier.LDPI
            density <= 1.0  -> DensityQualifier.MDPI
            density <= 1.5  -> DensityQualifier.HDPI
            density <= 2.0  -> DensityQualifier.XHDPI
            density <= 3.0  -> DensityQualifier.XXHDPI
            else -> DensityQualifier.XXXHDPI
        }

        val height = when (screenDensityCategory) {
            DensityQualifier.LDPI    -> (screenHeightDp * 0.25).dp
            DensityQualifier.MDPI    -> (screenHeightDp * 0.5).dp
            DensityQualifier.HDPI    -> (screenHeightDp * 0.75).dp
            DensityQualifier.XHDPI   -> (screenHeightDp * 1).dp
            DensityQualifier.XXHDPI  -> (screenHeightDp * 1.5).dp
            DensityQualifier.XXXHDPI -> (screenHeightDp * 2).dp
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .pullRefresh(pullRefreshState)
                .verticalScroll(rememberScrollState())
        ) {
            val loadingState = state.loadingState
            val context = LocalContext.current

            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                if (loadingState is LoadingState.Loading) {
                    LinearProgressIndicator(
                        progress = loadingState.progress,
                        modifier = Modifier
                            .fillMaxWidth(1f)
                            .height(4.dp)
                            .clip(RoundedCornerShape(100))
                            .align(Alignment.CenterHorizontally)
                    )
                }

                val webClient = remember {
                    object : AccompanistWebViewClient() {
                        val TAG = "AccompanistWebViewClient"
                        override fun onPageStarted(
                            view: WebView,
                            url: String?,
                            favicon: Bitmap?, ) {
                            super.onPageStarted(view, url, favicon)
                            Log.i(TAG, "onPageStarted: Success")
                        }
                        override fun onPageFinished(
                            view: WebView,
                            url: String?, ) {
                            super.onPageFinished(view, url)
                            Log.i(TAG, "onPageFinished: Success")
                            examViewModel.stopReload()
                        }
                        override fun onReceivedError(
                            view: WebView,
                            request: WebResourceRequest?,
                            error: WebResourceError?, ) {
                            super.onReceivedError(view, request, error)
                            Log.i(TAG, "onReceivedError: Error")
                            val errorContentHtml = context.resources.getString(R.string.web_error)
                            view.loadData(errorContentHtml, "text/html", "UTF-8")
                        }
                    }
                }

                WebView(
                    state = state,
                    modifier = Modifier
                        .padding(top = 4.dp, bottom = 4.dp)
                        .height((height.value * density).dp)
                        .verticalScroll(rememberScrollState())
                        .clip(RoundedCornerShape(2)),
                    navigator = navigator,
                    onCreated = { webView ->
                        webView.settings.apply {
                            javaScriptEnabled = true
                            useWideViewPort = true
                            setSupportZoom(true)
                            builtInZoomControls = true
                            displayZoomControls = false
                        }
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = context.resources.getString(R.string.snackbar),
                                withDismissAction = true,
                                duration = SnackbarDuration.Indefinite,
                            )
                        }
                    },
                    client = webClient,
                    captureBackPresses = false,
                )
            }

            PullRefreshIndicator(
                modifier = Modifier.align(Alignment.TopCenter),
                refreshing = examViewModel.isRefreshing,
                state = pullRefreshState,
            )
        }
    }
}

enum class DensityQualifier {
    LDPI, MDPI, HDPI, XHDPI, XXHDPI, XXXHDPI
}