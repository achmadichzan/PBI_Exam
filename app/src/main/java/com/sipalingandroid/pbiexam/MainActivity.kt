package com.sipalingandroid.pbiexam

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sipalingandroid.pbiexam.navigation.NavGraph
import com.sipalingandroid.pbiexam.ui.presentation.about.ThemeViewModel
import com.sipalingandroid.pbiexam.ui.presentation.exam.ExamViewModel
import com.sipalingandroid.pbiexam.ui.presentation.main.MainScreen
import com.sipalingandroid.pbiexam.ui.theme.PBIExamTheme
import com.sipalingandroid.pbiexam.util.DataStoreUtil

class MainActivity : ComponentActivity() {

    private val splashViewModel: SplashViewModel by viewModels()
    private val themeViewModel: ThemeViewModel by viewModels()
    private lateinit var dataStoreutil: DataStoreUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge(
//            statusBarStyle = SystemBarStyle.auto(
//                Color.TRANSPARENT, Color.TRANSPARENT
////                resources.getColor(R.color.teal_200, null),
////                darkScrim = resources.getColor(R.color.black, null)
//            ),
//            navigationBarStyle = SystemBarStyle.auto(
//                Color.TRANSPARENT, Color.TRANSPARENT
//            )
//        )
        dataStoreutil = DataStoreUtil(applicationContext)
        val systemTheme = when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> { true }
            Configuration.UI_MODE_NIGHT_NO -> { false }
            else -> { false }
        }

        splashScreen.setKeepOnScreenCondition{ splashViewModel.isLoading.value }
        actionBar?.hide()

        setContent {
            val theme = dataStoreutil.getTheme(systemTheme).collectAsState(initial = systemTheme)

            PBIExamTheme(
                darkTheme = theme.value
            ) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(themeViewModel)
                }
            }
        }
    }
}