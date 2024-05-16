package kz.tolegen.mechtatest.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import kz.tolegen.mechtatest.ui.feature.main.MechtaTestMainScreen
import kz.tolegen.mechtatest.ui.theme.MechtaTestTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MechtaTestTheme {
                MechtaTestMainScreen()
            }
        }
    }
}