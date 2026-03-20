package com.measlyclock

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.measlyclock.ui.dashboard.DashboardScreen
import com.measlyclock.ui.dashboard.DashboardViewModel
import com.measlyclock.ui.theme.MeaslyClockTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val app = application as MeaslyClockApp
        enableEdgeToEdge()
        setContent {
            MeaslyClockTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val viewModel: DashboardViewModel = viewModel(
                        factory = DashboardViewModel.factory(app.repository)
                    )
                    DashboardScreen(viewModel = viewModel)
                }
            }
        }
    }
}
