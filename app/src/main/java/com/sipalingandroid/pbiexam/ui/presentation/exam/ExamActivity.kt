package com.sipalingandroid.pbiexam.ui.presentation.exam

import android.app.KeyguardManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.sipalingandroid.pbiexam.R
import com.sipalingandroid.pbiexam.ui.theme.PBIExamTheme
import java.util.Timer
import java.util.TimerTask

class ExamActivity : ComponentActivity() {

    private var timer: Timer? = null
    private var myTimerTask: MyTimerTask? = null

    companion object {
        private const val TAG = "ExamActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PBIExamTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val context = LocalContext.current
                    ExamScreen(modifier = Modifier.padding(all = 12.dp))
                    LaunchedEffect(true) {
                        onBackPressedDispatcher.addCallback(
                            this@ExamActivity,
                            object : OnBackPressedCallback(true) {
                                override fun handleOnBackPressed() {
                                    Toast.makeText(
                                        context,
                                        context.resources.getString(R.string.exit),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        )
                    }
                }
            }
        }
        Log.d(TAG, "onCreate: Success")
    }

    override fun onPause() {
        super.onPause()
        if (timer == null) {
            myTimerTask = MyTimerTask(this@ExamActivity)
            timer = Timer()
            Thread.sleep(5000)
            timer?.schedule(myTimerTask, 100, 100)
        }
        Log.d(TAG, "onPause: Success. timer = $timer")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: Called, timer = $timer")
        if (timer != null) {
            timer?.cancel()
            timer?.purge()
            Log.d(TAG, "onResume: Success. timer = $timer")
            timer = null
        }
    }

    override fun onRestart() {
        super.onRestart()
        if (timer != null) {
            timer?.cancel()
            timer?.purge()
            Log.d(TAG, "onRestart: Success. timer = $timer")
            timer = null
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (timer != null) {
            timer?.cancel()
            timer?.purge()
            timer = null
        }
        myTimerTask?.cancel()
        myTimerTask = null
    }

    fun bringApplicationToFront() {
        val keyguardManager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager

        if (!keyguardManager.isKeyguardLocked) {
            val notificationIntent = Intent(this, ExamActivity::class.java)
            notificationIntent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT

            try {
                PendingIntent.getActivity(
                    this,
                    0,
                    notificationIntent,
                    PendingIntent.FLAG_IMMUTABLE
                ).send()
            } catch (e: PendingIntent.CanceledException) {
                e.printStackTrace()
            }
        }
    }

    inner class MyTimerTask(private val context: Context) : TimerTask() {
        override fun run() {
            (context as? ExamActivity)?.bringApplicationToFront()
        }
    }
}