package com.sipalingandroid.pbiexam.ui.presentation.home

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.sipalingandroid.pbiexam.R
import com.sipalingandroid.pbiexam.navigation.Screen
import com.sipalingandroid.pbiexam.ui.theme.fontFamily

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp, vertical = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .size(140.dp),
                painter = painterResource(id = R.drawable.uinam_logo),
                contentDescription = "UIN Alauddin Makassar logo"
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.pbi_exam),
                fontFamily = fontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,)
            Spacer(modifier = Modifier.height(16.dp))
        }
        Text(
            text = stringResource(R.string.about_app),
            fontFamily = fontFamily,
            textAlign = TextAlign.Justify
        )
//        Spacer(modifier = Modifier.height(20.dp))
//        Button(
//            onClick = {
//                navController.navigate(Screen.Exam.route) },
//            modifier = Modifier.align(CenterHorizontally)
//        ) {
//            Text(
//                text = stringResource(R.string.start),
//                fontFamily = fontFamily,
//                fontSize = 16.sp,
//                textAlign = TextAlign.Center,
//                modifier = Modifier.weight(1f)
//            )
//        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(R.string.feedback),
            fontFamily = fontFamily,
            textAlign = TextAlign.Justify,)
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(R.string.feedback_link),
            fontFamily = fontFamily,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.clickable {
                //                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(Urls.FORM_LINK))
                //                context.startActivity(intent)
                Toast.makeText(context, "Feedback link belum tersedia.", Toast.LENGTH_LONG)
                    .show()
            }
        )
    }
}